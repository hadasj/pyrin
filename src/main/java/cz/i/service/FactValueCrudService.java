package cz.i.service;

import static cz.i.util.Util.parseLong;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cz.i.common.ValueType;
import cz.i.dao.FactValueDao;
import cz.i.entity.db.fact.FactValueDb;
import cz.i.entity.db.fact.ValueDb;
import cz.i.entity.model.fact.FactValue;
import cz.i.mapper.DimensionMapper;


/**
 * @author jan.hadas@i.cz
 */
@Component
public class FactValueCrudService {

    @Autowired
    private FactValueDao factValueDao;

    @Autowired
    private DimensionMapper dimensionMapper;

    @Autowired
    private DimensionValueCrudService dimensionValueCrudService;

    @Autowired
    private ValueCrudService valueCrudService;

    public List<FactValue> readAllFactValues() {
        return mapFactValues(factValueDao.all());
    }

    public FactValue readOneFactValue(Long id) {
        FactValueDb factValueDb = factValueDao.oneById(id);
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

    @Transactional
    public void insert(FactValue factValue, Long factId) {
        FactValueDb factValueDb = new FactValueDb();
        factValueDb.setIdExt(factValue.getIdExternal());
        factValueDb.setAlias(factValue.getAlias());
        factValueDb.setCode(factValue.getCode());
        if (factValue.getDimension() != null)
            factValueDb.setDimensionId(parseLong(factValue.getDimension().getId()));
        factValueDb.setFactId(factId);

        factValueDao.insert(factValueDb);
        for (Object value : factValue.getValues())
            valueCrudService.insert(value, factValue.getValueType(), factValueDb.getId());
    }

    private FactValue mapFactValue(FactValueDb factValueDb) {
        FactValue factValue = new FactValue();
        factValue.setId(factValueDb.getId().toString());
        factValue.setIdExternal(factValueDb.getIdExt());
        factValue.setCode(factValueDb.getCode());
        factValue.setAlias(factValueDb.getAlias());
        factValue.setDimension(dimensionMapper.mapDimension(factValueDb.getDimension()));

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
