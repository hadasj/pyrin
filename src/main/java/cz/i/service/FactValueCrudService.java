package cz.i.service;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.i.common.ValueType;
import cz.i.dao.FactValueMapper;
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

    public List<FactValue> readAllFactValues() {
        List<FactValueDb> dbValues = factValueMapper.all();
        List<FactValue> result = new ArrayList<>();

        for (FactValueDb factValueDb : dbValues) {
            result.add(mapFactValue(factValueDb));
        }
        return result;
    }

    private FactValue mapFactValue(FactValueDb factValueDb) {
        FactValue factValue = new FactValue();
        factValue.setId(factValueDb.getId().toString());
        factValue.setIdExternal(factValueDb.getIdExt());
        factValue.setCode(factValueDb.getCode());
        factValue.setAlias(factValueDb.getAlias());
        factValue.setDimension(dimensionCrudService.mapDimension(factValueDb.getDimension()));
        factValue.setValueType(factValueDb.getValueType());

        Object value;
        if (ValueType.DIMENSION_VALUE == factValueDb.getValueType())
            value = dimensionValueCrudService.mapDimensionValue(factValueDb.getDimensionValue());
        else
            value = factValueDb.getValue();
        factValue.setValues(asList(value));

        return factValue;
    }


}
