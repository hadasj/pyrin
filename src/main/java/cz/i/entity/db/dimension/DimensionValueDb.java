package cz.i.entity.db.dimension;

import java.util.List;

import cz.i.entity.db.CodedEntityDb;

/**
 * @author jan.hadas@i.cz
 */
public class DimensionValueDb extends CodedEntityDb {
    // dimenze = definice ciselniku
    private Long dimensionId;

    // stromove nadrazena hodnota dimenze
    private Long parentId;

    // souvisejici hodnoty dimenzi - napr. kategorie organizace, region ve kterym org. sidli
    private List<DimensionValueDb> links;

    private List<DimensionValueDb> children;

    private DimensionDb dimension;

    private DimensionValueDb parentValue;

    private String textCs;

    private String textEn;

    private String textBg;

    public Long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(Long dimensionId) {
        this.dimensionId = dimensionId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<DimensionValueDb> getLinks() {
        return links;
    }

    public void setLinks(List<DimensionValueDb> links) {
        this.links = links;
    }

    public DimensionDb getDimension() {
        return dimension;
    }

    public void setDimension(DimensionDb dimension) {
        this.dimension = dimension;
    }

    public DimensionValueDb getParentValue() {
        return parentValue;
    }

    public void setParentValue(DimensionValueDb parentValue) {
        this.parentValue = parentValue;
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

    public List<DimensionValueDb> getChildren() {
        return children;
    }

    public void setChildren(List<DimensionValueDb> children) {
        this.children = children;
    }

    public void setTextBg(String textBg) {
        this.textBg = textBg;
    }

    @Override
    public String toString() {
        return "id=" + getId() + ", code=" + getCode() + ", alias=" + getAlias() +
            ", dimensionId=" + dimensionId + ", parentId=" + parentId + ", links=" + links + ", children=" + children +
            ", textCs=" + textCs + ", textEn=" + textEn + ", textBg=" + textBg;
    }
}
