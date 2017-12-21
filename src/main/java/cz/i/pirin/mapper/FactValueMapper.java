package cz.i.pirin.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.i.pirin.entity.db.fact.FactValueDb;
import cz.i.pirin.entity.db.fact.ValueDb;
import cz.i.pirin.model.entity.dimension.Dimension;
import cz.i.pirin.model.entity.dimension.ValueType;
import cz.i.pirin.model.entity.fact.FactValue;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class FactValueMapper {

    @Autowired
    private DimensionMapper dimensionMapper;

    public List<FactValue> mapFactValues(List<FactValueDb> factValuesDb) {
        List<FactValue> factValues = new ArrayList<>();
        if (factValuesDb != null) {
            for (FactValueDb factValueDb : factValuesDb) {
                factValues.add(mapFactValue(factValueDb));
            }
        }
        return factValues;
    }

    public FactValue mapFactValue(FactValueDb factValueDb) {
        if (factValueDb == null)
            return null;

        String id = factValueDb.getId().toString();
        Dimension dimension = dimensionMapper.mapDimension(factValueDb.getDimension());

        List<ValueDb> valuesDb = factValueDb.getValues();
        boolean first = true;
        ValueType valueType = null;
        List<Object> values = new ArrayList<>();
        for(ValueDb valueDb : valuesDb) {
            if (first) {
                valueType = valueDb.getValueType();
                first = false;
            } else {
                if (valueDb.getValueType() != valueType)
                    throw new IllegalStateException("Fact value " + factValueDb.getId() + "with different value types!!");
            }
            values.add(valueDb.getValue());
        }


        return new FactValue(id, dimension, valueType, values);
    }
}
