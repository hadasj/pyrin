package cz.i.entity.model.fact;

import java.util.List;

import cz.i.entity.model.CodedEntity;

/**
 * @author jan.hadas@i.cz
 */
public class Fact extends CodedEntity {
    private String name;
    // ? fact type??
    private Fact metadata;
    private List<FactValue> values;
    private List<Fact> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FactValue> getValues() {
        return values;
    }

    public void setValues(List<FactValue> values) {
        this.values = values;
    }

    public List<Fact> getChildren() {
        return children;
    }

    public void setChildren(List<Fact> children) {
        this.children = children;
    }

    public Fact getMetadata() {
        return metadata;
    }

    public void setMetadata(Fact metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return  "id=" + getId() + ", idExternal=" + getIdExternal() + ", code=" + getCode() + ", alias=" + getAlias() +
            ", name=" + name  + ", metadata=" + metadata + ", values=" + values + ", children=" + children;
    }
}
