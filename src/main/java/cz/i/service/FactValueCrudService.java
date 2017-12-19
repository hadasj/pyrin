package cz.i.service;

import static cz.i.Util.parseLong;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.i.common.ValueType;
import cz.i.dao.FactValueMapper;
import cz.i.entity.db.fact.FactValueDb;
import cz.i.entity.db.fact.ValueDb;
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
    private ValueCrudService valueCrudService;

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

        factValueMapper.insert(factValueDb);
        for (Object value : factValue.getValues())
            valueCrudService.insert(value, factValue.getValueType(), factValueDb.getId());
    }

    private FactValue mapFactValue(FactValueDb factValueDb) {
        FactValue factValue = new FactValue();
        factValue.setId(factValueDb.getId().toString());
        factValue.setIdExternal(factValueDb.getIdExt());
        factValue.setCode(factValueDb.getCode());
        factValue.setAlias(factValueDb.getAlias());
        factValue.setDimension(dimensionCrudService.mapDimension(factValueDb.getDimension()));

        ValueType valueType = null;
        List<Object> values = new ArrayList<>();
        for(ValueDb valueDb : factValueDb.getValues()) {
            values.add(valueDb.getValue());
            if (valueType == null)
                valueType = valueDb.getValueType();
            else
                if (!valueDb.getValueType().equals(valueType))
                    throw new IllegalArgumentException("Different value types for FACT_VALUE: " + factValueDb.getId());
        }
        factValue.setValues(values);
        factValue.setValueType(valueType);

        return factValue;
    }

}
