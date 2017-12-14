package cz.i.entity.model.dimension;

import cz.i.common.DimensionMode;
import cz.i.common.DimensionStructure;
import cz.i.entity.model.CodedEntity;

/**
 * @author jan.hadas@i.cz
 */
public class Dimension extends CodedEntity {

    public DimensionStructure structure;
    public DimensionMode mode;

    public DimensionStructure getStructure() {
        return structure;
    }

    public void setStructure(DimensionStructure structure) {
        this.structure = structure;
    }

    public DimensionMode getMode() {
        return mode;
    }

    public void setMode(DimensionMode mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "id=" + getId() + ", code=" + getCode() + ", alias=" + getAlias() + ", mode=" + mode + ", structure=" +
            structure;
    }
}
