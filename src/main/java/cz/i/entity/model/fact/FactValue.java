package cz.i.entity.model.fact;

import java.util.List;

import cz.i.common.ValueType;
import cz.i.entity.model.CodedEntity;
import cz.i.pirin.model.entity.dimension.Dimension;

/**
 * @author jan.hadas@i.cz
 */
public class FactValue extends CodedEntity {

    private Dimension dimension;

    private List<Object> values;

    private ValueType valueType;

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    @Override
    public String toString() {
        return  "id=" + getId() + ", idExternal=" + getIdExternal() + ", code=" + getCode() + ", alias=" + getAlias() +
            ", dimension=" + dimension + ", values=" + values + ", valuetype=" + valueType;
    }
}
