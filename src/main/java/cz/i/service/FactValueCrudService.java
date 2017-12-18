package cz.i.service;

import static cz.i.Util.parseLong;
import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.i.dao.DimensionValueMapper;
import cz.i.dao.FactValueMapper;
import cz.i.entity.db.dimension.DimensionValueDb;
import cz.i.entity.db.fact.FactValueDb;
import cz.i.entity.model.fact.FactValue;


/**
 * @author jan.hadas@i.cz
 */
@Component
public class FactValueCrudService {

    @Autowired
    private FactValueMapper factValueMapper;

    @Autowired
    private DimensionCrudService dimensionCrudService;

    @Autowired
    private DimensionValueCrudService dimensionValueCrudService;

    @Autowired
    private DimensionValueMapper dimensionValueMapper;

    public List<FactValue> readAllFactValues() {
        return mapFactValues(factValueMapper.all());
    }

    public FactValue readOneFactValue(Long id) {
        FactValueDb factValueDb = factValueMapper.oneById(id);
        if (factValueDb == null)
            return null;
        return mapFactValue(factValueDb);
    }

    public List<FactValue> mapFactValues(List<FactValueDb> dbValues) {
        List<FactValue> result = new ArrayList<>();

        for (FactValueDb factValueDb : dbValues) {
            result.add(mapFactValue(factValueDb));
        }
        return result;
    }

    public void insert(FactValue factValue, Long factId) {
        FactValueDb factValueDb = new FactValueDb();
        factValueDb.setIdExt(factValue.getIdExternal());
        factValueDb.setAlias(factValue.getAlias());
        factValueDb.setCode(factValue.getCode());
        if (factValue.getDimension() != null)
            factValueDb.setDimensionId(parseLong(factValue.getDimension().getId()));
        factValueDb.setFactId(factId);
        factValueDb.setValueType(factValue.getValueType());
        if (factValue.getValues() != null && !factValue.getValues().isEmpty())
            setFactValue(factValueDb, factValue.getValues().get(0).toString());

        factValueMapper.insert(factValueDb);
    }

    private FactValue mapFactValue(FactValueDb factValueDb) {
        FactValue factValue = new FactValue();
        factValue.setId(factValueDb.getId().toString());
        factValue.setIdExternal(factValueDb.getIdExt());
        factValue.setCode(factValueDb.getCode());
        factValue.setAlias(factValueDb.getAlias());
        factValue.setDimension(dimensionCrudService.mapDimension(factValueDb.getDimension()));
        factValue.setValueType(factValueDb.getValueType());
        factValue.setValues(asList(getFactValue(factValueDb)));

        return factValue;
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

    public void setFactValue(FactValueDb factValue, String value) {
        switch (factValue.getValueType()) {
            case DIMENSION_VALUE:
                factValue.setDimensionValueId(getDimensionValue(factValue.getDimensionId(), value));
                break;
            case TIMESTAMP:
                factValue.setValueTimestamp(ZonedDateTime.parse(value));
                break;
            case STRING:
                factValue.setValueString(value);
                break;
            case INT:
                factValue.setValueInt(Integer.parseInt(value));
                break;
            case LONG:
                factValue.setValueLong(Long.parseLong(value));
                break;
            case DOUBLE:
                factValue.setValueDouble(Double.parseDouble(value));
                break;
            case BIG_DECIMAL:
                factValue.setValueBigdecimal(new BigDecimal(value));
                break;
        }
    }

    public Object getFactValue(FactValueDb factValue) {
        switch (factValue.getValueType()) {
            case DIMENSION_VALUE:
                return factValue.getDimensionValue();
            case TIMESTAMP:
                return factValue.getValueTimestamp().withZoneSameInstant(ZoneOffset.UTC);
            case STRING:
                return factValue.getValueString();
            case INT:
                return factValue.getValueInt();
            case LONG:
                return factValue.getValueLong();
            case DOUBLE:
                return factValue.getValueDouble();
            case BIG_DECIMAL:
                return factValue.getValueBigdecimal();
            default:
                throw new IllegalArgumentException("Unknown value type: " + factValue.getValueType());
        }
    }


}
