package cz.i.entity.fact;

import java.util.List;

import cz.i.entity.CodedEntity;

/**
 * @author jan.hadas@i.cz
 */
public class Fact extends CodedEntity {
    private String name;
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
}
