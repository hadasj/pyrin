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

            case FACT_VALUE:
                FactValueDb factValue = createFactValue(columns);
                LOG.debug("insert fact value: {}", factValue);
                factValueMapper.insert(factValue);

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
        dimensionValue.setDimensionId(getEntityByIdExt(columns[3], dimensionMapper::oneByIdExt));
        dimensionValue.setParentId(getEntityByIdExt(columns[4], dimensionValueMapper::oneByIdExt));
        dimensionValue.setTextEn(columns[5]);
        dimensionValue.setTextCs(columns[6]);
        dimensionValue.setTextBg(columns[7]);

        return dimensionValue;
    }

    private List<DimensionValueLinkDb> createLinks(String[] columns, Long ownerId) {
        List<DimensionValueLinkDb> links = new ArrayList<>();
        for (int index = 8; index < columns.length; index +=2) {
            Long dimensionId = getEntityByIdExt(columns[index], dimensionMapper::oneByIdExt);
            Long valueId = getEntityByIdExt(columns[index + 1], dimensionValueMapper::oneByIdExt);

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
        fact.setFactType(findFactType(columns));
        fact.setAlias(columns[2]);
        fact.setName(columns[3]);
        fact.setParentId(getFactParent(columns));

        return fact;
    }

    private FactValueDb createFactValue(String[] columns) {
        FactValueDb factValue = new FactValueDb();
        factValue.setIdExt(parseLong(columns[0]));
        factValue.setAlias(columns[1]);
        factValue.setDimensionId(parseLong(columns[2]));
        factValue.setValue(columns[3]);

        return factValue;
    }

    private <E extends EntityDb>Long getEntityByIdExt(String idExt, Function<Long, E> entityByIdExt) {
        Long parsedIdExt = parseLong(idExt);
        if(parsedIdExt != null) {
            EntityDb entity = entityByIdExt.apply(parsedIdExt);
            if (entity != null)
                return entity.getId();
        }
        return null;
    }

    private Long getFactParent(String[] columns) {
        try {
            if (columns.length > 4 && !StringUtils.isEmpty(columns[4]))
                return Long.parseLong(columns[4]);
        } catch (NumberFormatException e) {
            LOG.warn("FactParentIdExt: {} is not number", columns[4]);
        }
        return null;
    }

    private String findFactType(String[] columns) {
        try {
            Long valueId = Long.valueOf(columns[1]);
            DimensionValueDb type = dimensionValueMapper.oneByDimensionAndIdExt(DIMENSION_OF_FACTS_CODE, valueId);
            if (type != null)
                return type.getCode();
        } catch (NumberFormatException e) {
            LOG.warn("DimensionValueIdExt: {} is not number", columns[1]);
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
