package cz.i.service;

import static cz.i.Util.parseLong;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cz.i.common.DimensionMode;
import cz.i.common.DimensionStructure;
import cz.i.common.Entities;
import cz.i.common.ValueType;
import cz.i.dao.DimensionMapper;
import cz.i.dao.DimensionValueLinkMapper;
import cz.i.dao.DimensionValueMapper;
import cz.i.dao.FactMapper;
import cz.i.dao.FactValueMapper;
import cz.i.entity.db.EntityDb;
import cz.i.entity.db.dimension.DimensionDb;
import cz.i.entity.db.dimension.DimensionValueDb;
import cz.i.entity.db.dimension.DimensionValueLinkDb;
import cz.i.entity.db.fact.FactDb;
import cz.i.entity.db.fact.FactValueDb;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class ImportService {
    private static final Logger LOG = LoggerFactory.getLogger(ImportService.class);

    private static final String COMMENT = "###";
    private static final String ENTITY = "@";
    private static final String SEPARATOR = "\\|";
    private static final String DIMENSION_OF_FACTS_CODE = "DFAC";

    @Autowired
    private DimensionMapper dimensionMapper;

    @Autowired
    private DimensionValueMapper dimensionValueMapper;

    @Autowired
    private DimensionValueLinkMapper dimensionValueLinkMapper;

    @Autowired
    private FactMapper factMapper;

    @Autowired
    private FactValueMapper factValueMapper;

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
                dimensionMapper.insert(dimension);
                break;

            case DIMENSION_VALUE:
                DimensionValueDb dimensionValue = createDimensionValue(columns);
                LOG.debug("insert dimension value: {}", dimensionValue);
                dimensionValueMapper.insert(dimensionValue);

                List<DimensionValueLinkDb> links = createLinks(columns, dimensionValue.getId());
                for (DimensionValueLinkDb link : links) {
                    LOG.debug("insert dimension value link: {}", links);
                    dimensionValueLinkMapper.insert(link);
                }
                break;

            case FACT:
                FactDb fact = createFact(columns);
                LOG.debug("insert fact: {}", fact);
                factMapper.insert(fact);
                break;

            case FACT_VALUE:
                FactValueDb factValue = createFactValue(columns);
                LOG.debug("insert fact value: {}", factValue);
                factValueMapper.insert(factValue);
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
        dimensionValue.setDimensionId(getEntityIdByIdExt(columns[3], dimensionMapper::oneByIdExt));
        // TODO: ID EXT is not unique!! ADD DIMENSION ID!
        dimensionValue.setParentId(getEntityIdByIdExt(columns[4], dimensionValueMapper::oneByIdExt));
        dimensionValue.setTextEn(columns[5]);
        dimensionValue.setTextCs(columns[6]);
        dimensionValue.setTextBg(columns[7]);

        return dimensionValue;
    }

    private List<DimensionValueLinkDb> createLinks(String[] columns, Long ownerId) {
        List<DimensionValueLinkDb> links = new ArrayList<>();
        for (int index = 8; index < columns.length; index +=2) {
            Long dimensionId = getEntityIdByIdExt(columns[index], dimensionMapper::oneByIdExt);
            Long valueId = getEntityIdByIdExt(columns[index + 1], dimensionValueMapper::oneByIdExt);

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
        factValue.setFactId(getEntityIdByIdExt(columns[0], factMapper::parentByIdExt));
        factValue.setAlias(columns[1]);
        factValue.setDimensionId(getEntityIdByIdExt(columns[2], dimensionMapper::oneByIdExt));
        factValue.setValueType(getValueType(columns[4]));
        if (factValue.getValueType() != null && factValue.getValueType().equals(ValueType.DIMENSION_VALUE))
            factValue.setDimensionValueId(getDimensionValue(factValue.getDimensionId(), columns[3]));
        else
            factValue.setValue(columns[3]);

        return factValue;
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

    private Long getFactParent(String[] columns) {
        if (columns.length > 4) {
            return getEntityIdByIdExt(columns[4], factMapper::parentByIdExt);
        }
        return null;
    }

    private Long getFactType(String extId) {
        Long dimensionValueIdExt = parseLong(extId);
        if (dimensionValueIdExt != null) {
            DimensionValueDb type = dimensionValueMapper.oneByDimensionCodeAndIdExt(DIMENSION_OF_FACTS_CODE, dimensionValueIdExt);
            if (type != null)
                return type.getId();
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

    private Long getDimensionValue(Long dimensionId, String idExt) {
        Long dimensionValueIdExt = parseLong(idExt);
        if (dimensionValueIdExt != null) {
            DimensionValueDb value = dimensionValueMapper.oneByDimensionIdAndIdExt(dimensionId, dimensionValueIdExt);
            if (value != null)
                return value.getId();
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
