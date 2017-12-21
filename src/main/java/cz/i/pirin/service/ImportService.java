package cz.i.pirin.service;

import static cz.i.pirin.dao.DimensionDao.DIMENSION_OF_FACTS_CODE;
import static cz.i.pirin.util.Util.parseLong;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cz.i.pirin.common.Entities;
import cz.i.pirin.dao.DimensionDao;
import cz.i.pirin.dao.DimensionValueDao;
import cz.i.pirin.dao.DimensionValueLinkDao;
import cz.i.pirin.dao.FactDao;
import cz.i.pirin.dao.FactValueDao;
import cz.i.pirin.dao.ValueDao;
import cz.i.pirin.entity.db.EntityDb;
import cz.i.pirin.entity.db.dimension.DimensionDb;
import cz.i.pirin.entity.db.dimension.DimensionValueDb;
import cz.i.pirin.entity.db.dimension.DimensionValueLinkDb;
import cz.i.pirin.entity.db.fact.FactDb;
import cz.i.pirin.entity.db.fact.FactValueDb;
import cz.i.pirin.entity.db.fact.ValueDb;
import cz.i.pirin.model.entity.dimension.DimensionMode;
import cz.i.pirin.model.entity.dimension.DimensionStructure;
import cz.i.pirin.model.entity.dimension.ValueType;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class ImportService {
    private static final Logger LOG = LoggerFactory.getLogger(ImportService.class);

    private static final String COMMENT = "###";
    private static final String ENTITY = "@";
    private static final String SEPARATOR = "\\|";

    @Autowired
    private DimensionDao dimensionDao;

    @Autowired
    private DimensionValueDao dimensionValueDao;

    @Autowired
    private DimensionValueLinkDao dimensionValueLinkDao;

    @Autowired
    private FactDao factDao;

    @Autowired
    private FactValueDao factValueDao;

    @Autowired
    private FactValueCrudService factValueCrudService;

    @Autowired
    private ValueDao valueDao;

    @Transactional
    public void importData(String csv) {
        if (StringUtils.isEmpty(csv)) {
            LOG.warn("Csv is empty");
            return;
        }

        Entities entity = null;

        String[] lines = csv.split("\n");
        for (String line : lines) {
            line = line.trim();

            if (line.startsWith(COMMENT))
                continue;

            if (line.startsWith(ENTITY)) {
                entity = Entities.valueOf(line.substring(1).toUpperCase());
                LOG.debug("Processing entity: {}", entity);
                continue;
            }

            if (entity != null)
                processLine(line, entity);
        }
    }

    private void processLine(String line, Entities entity) {
        String[] columns = line.split(SEPARATOR);

        if (isEmpty(columns))
            return;

        switch (entity) {
            case DIMENSION:
                DimensionDb dimension = createDimension(columns);
                LOG.debug("insert dimension: {}", dimension);
                dimensionDao.insert(dimension);
                break;

            case DIMENSION_VALUE:
                DimensionValueDb dimensionValue = createDimensionValue(columns);
                LOG.debug("insert dimension value: {}", dimensionValue);
                dimensionValueDao.insert(dimensionValue);

                List<DimensionValueLinkDb> links = createLinks(columns, dimensionValue.getId());
                for (DimensionValueLinkDb link : links) {
                    LOG.debug("insert dimension value link: {}", links);
                    dimensionValueLinkDao.insert(link);
                }
                break;

            case FACT:
                FactDb fact = createFact(columns);
                LOG.debug("insert fact: {}", fact);
                factDao.insert(fact);
                break;

            case FACT_VALUE:
                FactValueDb factValue = createFactValue(columns);
                LOG.debug("insert fact value: {}", factValue);
                factValueDao.insert(factValue);

                ValueDb valuedb = createValue(columns, factValue.getDimensionId(), factValue.getId());
                valueDao.insert(valuedb);
                break;

            default:
                LOG.warn("Unknown entity: {}", entity);
       }
    }

    private DimensionDb createDimension(String[] columns) {
        DimensionDb dimension = new DimensionDb();
        dimension.setIdExt(parseLong(columns[0]));
        dimension.setCode(columns[1]);
        dimension.setAlias(columns[2]);
        dimension.setStructure(DimensionStructure.valueOf(columns[3]));
        dimension.setMode(DimensionMode.valueOf(columns[4]));
        dimension.setType(ValueType.valueOf(columns[5]));
        dimension.setTextEn(columns[6]);
        dimension.setTextCs(columns[7]);
        dimension.setTextBg(columns[7]);

        return dimension;
    }

    private DimensionValueDb createDimensionValue(String[] columns) {
        DimensionValueDb dimensionValue = new DimensionValueDb();
        dimensionValue.setIdExt(parseLong(columns[0]));
        dimensionValue.setCode(columns[1]);
        dimensionValue.setAlias(columns[2]);
        Long dimensionId = getEntityIdByIdExt(columns[3], dimensionDao::oneByIdExt);
        dimensionValue.setDimensionId(dimensionId);
        dimensionValue.setParentId(getEntityIdByDimensionAndIdExt(dimensionId, columns[4],
            dimensionValueDao::oneByDimensionIdAndIdExt));
        dimensionValue.setTextEn(columns[5]);
        dimensionValue.setTextCs(columns[6]);
        dimensionValue.setTextBg(columns[7]);

        return dimensionValue;
    }

    private List<DimensionValueLinkDb> createLinks(String[] columns, Long ownerId) {
        List<DimensionValueLinkDb> links = new ArrayList<>();
        for (int index = 8; index < columns.length; index +=2) {
            Long dimensionId = getEntityIdByIdExt(columns[index], dimensionDao::oneByIdExt);
            Long valueId = getEntityIdByDimensionAndIdExt(dimensionId, columns[index + 1],
                dimensionValueDao::oneByDimensionIdAndIdExt);

            if (dimensionId != null && valueId != null) {
                DimensionValueLinkDb link = new DimensionValueLinkDb();
                link.setOwnerId(ownerId);
                link.setDimensionId(dimensionId);
                link.setValueId(valueId);
                links.add(link);
            }
        }
        return links;
    }

    private FactDb createFact(String[] columns) {
        FactDb fact = new FactDb();
        fact.setIdExt(parseLong(columns[0]));
        fact.setFactTypeId(getFactType(columns[1]));
        fact.setAlias(columns[2]);
        fact.setName(columns[3]);
        fact.setParentId(getFactParent(columns));

        return fact;
    }

    private FactValueDb createFactValue(String[] columns) {
        FactValueDb factValue = new FactValueDb();
        factValue.setFactId(getEntityIdByIdExt(columns[0], factDao::parentByIdExt));
        factValue.setAlias(columns[1]);
        factValue.setDimensionId(getEntityIdByIdExt(columns[2], dimensionDao::oneByIdExt));

        return factValue;
    }

    private ValueDb createValue(String[] columns, Long dimensionId, Long factValueId) {
        ValueType valueType = getValueType(columns[4]);
        // find dimension value by ext-id
        Object value;
        if (ValueType.DIMENSION_VALUE == valueType) {
            value = getDimensionValue(dimensionId, columns[3]);
        } else {
            value = columns[3];
        }
        ValueDb valueDb = new ValueDb();
        valueDb.setValue(value, valueType);
        valueDb.setFactValueId(factValueId);
        return valueDb;
    }

    private <E extends EntityDb>Long getEntityIdByIdExt(String idExt, Function<Long, E> entityByIdExt) {
        Long parsedIdExt = parseLong(idExt);
        if(parsedIdExt != null) {
            EntityDb entity = entityByIdExt.apply(parsedIdExt);
            if (entity != null)
                return entity.getId();
        }
        return null;
    }

    private <E extends EntityDb>Long getEntityIdByDimensionAndIdExt(Long dimensionId, String idExt,
                                                                 BiFunction<Long, Long, E> entityByDimensionAndIdExt) {
        Long parsedIdExt = parseLong(idExt);
        if(dimensionId != null && parsedIdExt != null) {
            EntityDb entity = entityByDimensionAndIdExt.apply(dimensionId, parsedIdExt);
            if (entity != null)
                return entity.getId();
        }
        return null;
    }

    private Long getFactParent(String[] columns) {
        if (columns.length > 4) {
            return getEntityIdByIdExt(columns[4], factDao::parentByIdExt);
        }
        return null;
    }

    private Long getFactType(String extId) {
        Long dimensionValueIdExt = parseLong(extId);
        if (dimensionValueIdExt != null) {
            DimensionValueDb type = dimensionValueDao.oneByDimensionCodeAndIdExt(DIMENSION_OF_FACTS_CODE, dimensionValueIdExt);
            if (type != null)
                return type.getId();
        }
        return null;
    }

    private Long getDimensionValue(Long dimensionId, String idExt) {
        Long dimensionValueIdExt = parseLong(idExt);
        if (dimensionValueIdExt != null) {
            DimensionValueDb value = dimensionValueDao.oneByDimensionIdAndIdExt(dimensionId, dimensionValueIdExt);
            if (value != null)
                return value.getId();
        }
        return null;
    }

    private ValueType getValueType(String name) {
        try {
            return ValueType.valueOf(name);
        } catch (Exception e) {
            LOG.warn("Unknown value type: {}", name);
        }
        return null;
    }

    /**
     * @param columns @NotNull
     * @return true if all columns are empty
     */
    private boolean isEmpty(String[] columns) {
        boolean empty = true;
        for (String column : columns)
            empty &= StringUtils.isEmpty(column);
        return empty;
    }
}
