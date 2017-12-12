package cz.i.converter;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

import cz.i.common.ValueType;
import cz.i.entity.dimension.Dimension;
import cz.i.entity.fact.FactValue;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class FactValueConverter {

    public FactValue convertDbValue(FactValue factValue) {
        if (factValue == null)
            return null;
        Object value = readValue(factValue.getValueValue(), factValue.getDimension(), factValue.getType());
        factValue.setValue(value);
        return factValue;
    }

    public FactValue convertExternalValue(FactValue factValue) {
        String valueValue;
        if (factValue.getValue() != null) {
            valueValue = writeValue(factValue.getValue());
        } else  {
            valueValue = factValue.getValueValue();
        }
        factValue.setValueValue(valueValue);
        return factValue;
    }

    private Object readValue(String string, Dimension dimension, ValueType valueType) {
        if (string != null)
            switch (valueType) {
                case STRING:
                case TIMESTAMP:
                    return string;
                case INT:
                    return Integer.valueOf(string);
                case LONG:
                    return Long.valueOf(string);
                case DOUBLE:
                    return Double.valueOf(string);
                case BIG_DECIMAL:
                    return new BigDecimal(string);
                case DIMENSION_VALUE:
                    if (dimension != null)
                        return dimension.getAlias();
            }
        return null;
    }

    public String writeValue(Object value) {
        return value.toString();
    }
}
