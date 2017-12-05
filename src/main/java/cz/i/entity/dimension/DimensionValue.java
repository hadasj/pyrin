package cz.i.entity.dimension;

import java.util.List;

import cz.i.entity.CodedEntity;

/**
 * @author jan.hadas@i.cz
 */
public class DimensionValue extends CodedEntity {

    private Dimension dimension;
    private List<DimensionValue> links;

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
}
