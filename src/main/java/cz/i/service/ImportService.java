package cz.i.service;

import java.util.ArrayList;
import java.util.List;

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
import cz.i.entity.db.dimension.DimensionDb;
import cz.i.entity.db.dimension.DimensionValueDb;
import cz.i.entity.db.dimension.DimensionValueLinkDb;
import cz.i.entity.db.fact.FactDb;

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
    private DimensionMapper dimensionMapper;

    @Autowired
    private DimensionValueMapper dimensionValueMapper;

    @Autowired
    private DimensionValueLinkMapper dimensionValueLinkMapper;

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

            default:
                LOG.warn("Unknown entity: {}", entity);
       }
    }

    private DimensionDb createDimension(String[] columns) {
        DimensionDb dimension = new DimensionDb();
        dimension.setIdExt(Long.parseLong(columns[0]));
        dimension.setCode(columns[1]);
        dimension.setAlias(columns[2]);
        dimension.setStructure(DimensionStructure.valueOf(columns[3]));
        dimension.setMode(DimensionMode.valueOf(columns[4]));
        dimension.setType(ValueType.valueOf(columns[5]));

        return dimension;
    }

    private DimensionValueDb createDimensionValue(String[] columns) {
        DimensionValueDb dimensionValue = new DimensionValueDb();
        dimensionValue.setIdExt(Long.parseLong(columns[0]));
        dimensionValue.setCode(columns[1]);
        dimensionValue.setAlias(columns[2]);
        if (!StringUtils.isEmpty(columns[3]))
            dimensionValue.setDimensionId(Long.parseLong(columns[3]));
        if (!StringUtils.isEmpty(columns[4]))
            dimensionValue.setParentId(Long.parseLong(columns[4]));
        dimensionValue.setTextEn(columns[5]);
        dimensionValue.setTextCs(columns[6]);
        dimensionValue.setTextBg(columns[7]);


        return dimensionValue;
    }

    private List<DimensionValueLinkDb> createLinks(String[] columns, Long ownerId) {
        List<DimensionValueLinkDb> links = new ArrayList<>();
        for (int index = 8; index < columns.length; index +=2) {
            if (!StringUtils.isEmpty(columns[index]) && !StringUtils.isEmpty(columns[index+1])) {
                DimensionValueLinkDb link = new DimensionValueLinkDb();
                link.setOwnerId(ownerId);
                link.setDimensionId(Long.parseLong(columns[index]));
                link.setValueId(Long.parseLong(columns[index+1]));
            }
        }
        return links;
    }

    private FactDb createFact(String[] columns) {
        FactDb fact = new FactDb();
        //TODO:
        fact.setId(Long.parseLong(columns[0]));

        return fact;
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
