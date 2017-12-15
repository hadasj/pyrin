package cz.i.entity.model.dimension;

import java.util.List;

import cz.i.entity.model.CodedEntity;

/**
 * @author jan.hadas@i.cz
 */
public class DimensionValue extends CodedEntity {

    private Dimension dimension;
    private List<DimensionValue> links;
    private List<DimensionValue> children;

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public List<DimensionValue> getLinks() {
        return links;
    }

    public void setLinks(List<DimensionValue> links) {
        this.links = links;
    }

    public List<DimensionValue> getChildren() {
        return children;
    }

    public void setChildren(List<DimensionValue> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return  "id=" + getId() + ", idExternal=" + getIdExternal() + ", code=" + getCode() + ", alias=" + getAlias() +
            ", children=" + children + ", dimension=" + dimension;
    }
}
