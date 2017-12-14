package cz.i.entity.db.fact;

import cz.i.common.ValueType;
import cz.i.entity.db.CodedEntityDb;

/**
 * @author jan.hadas@i.cz
 */
public class FactValueDb extends CodedEntityDb {
    private Long factId;
    private Long dimensionId;
    private Long dimensionValueId;
    private String value;
    private ValueType valueType;
    //TODO: MULTI -> list of values


    public Long getFactId() {
        return factId;
    }

    public void setFactId(Long factId) {
        this.factId = factId;
    }

    public Long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(Long dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public Long getDimensionValueId() {
        return dimensionValueId;
    }

    public void setDimensionValueId(Long dimensionValueId) {
        this.dimensionValueId = dimensionValueId;
    }
}
