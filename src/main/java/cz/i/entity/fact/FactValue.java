package cz.i.entity.fact;

import cz.i.common.ValueType;
import cz.i.entity.CodedEntity;
import cz.i.entity.Entity;
import cz.i.entity.dimension.Dimension;
import cz.i.entity.dimension.DimensionValue;

/**
 * @author jan.hadas@i.cz
 */
public class FactValue extends CodedEntity {

    private Dimension dimension;

    private Fact fact;

    private DimensionValue dimensionValue;

    private Object value;

    private String valueValue;

    private ValueType type;

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getValueValue() {
        return valueValue;
    }

    public void setValueValue(String valueValue) {
        this.valueValue = valueValue;
    }

    public ValueType getType() {
        return type;
    }

    public void setType(ValueType type) {
        this.type = type;
    }

    public DimensionValue getDimensionValue() {
        return dimensionValue;
    }

    public void setDimensionValue(DimensionValue dimensionValue) {
        this.dimensionValue = dimensionValue;
    }

    public Fact getFact() {
        return fact;
    }

    public void setFact(Fact fact) {
        this.fact = fact;
    }
}
