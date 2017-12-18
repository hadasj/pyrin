package cz.i.entity.db.fact;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import cz.i.common.ValueType;
import cz.i.entity.db.CodedEntityDb;
import cz.i.entity.db.dimension.DimensionDb;
import cz.i.entity.db.dimension.DimensionValueDb;

/**
 * @author jan.hadas@i.cz
 */
public class FactValueDb extends CodedEntityDb {
    private Long factId;
    private Long dimensionId;
    private Long dimensionValueId;
    private String valueString;
    private ZonedDateTime valueTimestamp;
    private Integer valueInt;
    private Long valueLong;
    private Double valueDouble;
    private BigDecimal valueBigdecimal;
    private ValueType valueType;
    private FactDb fact;
    private DimensionDb dimension;
    private DimensionValueDb dimensionValue;
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

    public Long getDimensionValueId() {
        return dimensionValueId;
    }

    public void setDimensionValueId(Long dimensionValueId) {
        this.dimensionValueId = dimensionValueId;
    }

    public FactDb getFact() {
        return fact;
    }

    public void setFact(FactDb fact) {
        this.fact = fact;
    }

    public DimensionDb getDimension() {
        return dimension;
    }

    public void setDimension(DimensionDb dimension) {
        this.dimension = dimension;
    }

    public DimensionValueDb getDimensionValue() {
        return dimensionValue;
    }

    public void setDimensionValue(DimensionValueDb dimensionValue) {
        this.dimensionValue = dimensionValue;
    }
}
