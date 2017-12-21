package cz.i.pirin.entity.db.fact;

import java.util.List;

import cz.i.pirin.entity.db.CodedEntityDb;
import cz.i.pirin.entity.db.dimension.DimensionDb;

/**
 * @author jan.hadas@i.cz
 */
public class FactValueDb extends CodedEntityDb {
    private Long factId;
    private Long dimensionId;
    private Long dimensionValueId;
    List<ValueDb> values;
    private FactDb fact;
    private DimensionDb dimension;

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

    public List<ValueDb> getValues() {
        return values;
    }

    public void setValues(List<ValueDb> values) {
        this.values = values;
    }
}
