package cz.i.entity.db.fact;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import cz.i.common.ValueType;
import cz.i.entity.db.dimension.DimensionValueDb;

/**
 * @author jan.hadas@i.cz
 */
public class ValueDb {
    private Long factValueId;
    private Long dimensionValueId;
    private String valueString;
    private ZonedDateTime valueTimestamp;
    private Integer valueInt;
    private Long valueLong;
    private Double valueDouble;
    private BigDecimal valueBigdecimal;
    private ValueType valueType;
    private DimensionValueDb dimensionValue;

    public Long getFactValueId() {
        return factValueId;
    }

    public void setFactValueId(Long factValueId) {
        this.factValueId = factValueId;
    }

    public Long getDimensionValueId() {
        return dimensionValueId;
    }

    public void setDimensionValueId(Long dimensionValueId) {
        this.dimensionValueId = dimensionValueId;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public ZonedDateTime getValueTimestamp() {
        return valueTimestamp;
    }

    public void setValueTimestamp(ZonedDateTime valueTimestamp) {
        this.valueTimestamp = valueTimestamp;
    }

    public Integer getValueInt() {
        return valueInt;
    }

    public void setValueInt(Integer valueInt) {
        this.valueInt = valueInt;
    }

    public Long getValueLong() {
        return valueLong;
    }

    public void setValueLong(Long valueLong) {
        this.valueLong = valueLong;
    }

    public Double getValueDouble() {
        return valueDouble;
    }

    public void setValueDouble(Double valueDouble) {
        this.valueDouble = valueDouble;
    }

    public BigDecimal getValueBigdecimal() {
        return valueBigdecimal;
    }

    public void setValueBigdecimal(BigDecimal valueBigdecimal) {
        this.valueBigdecimal = valueBigdecimal;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public DimensionValueDb getDimensionValue() {
        return dimensionValue;
    }

    public void setDimensionValue(DimensionValueDb dimensionValue) {
        this.dimensionValue = dimensionValue;
    }

    public void setValue(Object value, ValueType valueType) {
        this.valueType = valueType;
        String valueString = value.toString();

        switch (valueType) {
            case DIMENSION_VALUE:
                dimensionValueId = Long.valueOf(valueString);
                break;
            case TIMESTAMP:
                valueTimestamp = ZonedDateTime.parse(valueString);
                break;
            case STRING:
                this.valueString = valueString;
                break;
            case INT:
                valueInt = Integer.parseInt(valueString);
                break;
            case LONG:
                valueLong = Long.valueOf(valueString);
                break;
            case DOUBLE:
                valueDouble = Double.parseDouble(valueString);
                break;
            case BIG_DECIMAL:
                valueBigdecimal = new BigDecimal(valueString);
                break;
            default:
                throw new IllegalArgumentException("Unknown value type: " + valueType);
        }
    }

    public Object getValue() {
        switch (valueType) {
            case DIMENSION_VALUE:
                return dimensionValue;
            case TIMESTAMP:
                return valueTimestamp.withZoneSameInstant(ZoneOffset.UTC);
            case STRING:
                return valueString;
            case INT:
                return valueInt;
            case LONG:
                return valueLong;
            case DOUBLE:
                return valueDouble;
            case BIG_DECIMAL:
                return valueBigdecimal;
            default:
                throw new IllegalArgumentException("Unknown value type: " + valueType);
        }
    }
}
