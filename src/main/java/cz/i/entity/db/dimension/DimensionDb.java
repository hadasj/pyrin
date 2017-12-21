package cz.i.entity.db.dimension;

import cz.i.common.ValueType;
import cz.i.entity.db.CodedEntityDb;
import cz.i.pirin.model.entity.dimension.DimensionMode;
import cz.i.pirin.model.entity.dimension.DimensionStructure;

/**
 * @author jan.hadas@i.cz
 */
public class DimensionDb extends CodedEntityDb {

    private DimensionStructure structure;
    private DimensionMode mode;
    private ValueType type;
    private String textCs;
    private String textEn;
    private String textBg;

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

    public ValueType getType() {
        return type;
    }

    public void setType(ValueType valueType) {
        this.type = valueType;
    }

    public String getTextCs() {
        return textCs;
    }

    public void setTextCs(String textCs) {
        this.textCs = textCs;
    }

    public String getTextEn() {
        return textEn;
    }

    public void setTextEn(String textEn) {
        this.textEn = textEn;
    }

    public String getTextBg() {
        return textBg;
    }

    public void setTextBg(String textBg) {
        this.textBg = textBg;
    }

    @Override
    public String toString() {
        return "id=" + getId() + ", code=" + getCode() + ", alias=" + getAlias() + ", mode=" + mode + ", structure=" +
            structure + ", type=" + type + ", textCs=" + textCs + ", textEn=" + textEn + ", textBg=" + textBg;
    }
}
