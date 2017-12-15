package cz.i.entity.db.fact;

import java.util.List;

import cz.i.entity.db.CodedEntityDb;
import cz.i.entity.db.dimension.DimensionValueDb;

/**
 * @author jan.hadas@i.cz
 */
public class FactDb extends CodedEntityDb {
    private String name;
    private Long parentId;
    private Long factTypeId;

    private List<FactDb> children;
    private List<FactValueDb> values;
    private DimensionValueDb factType;

    public Long getFactTypeId() {
        return factTypeId;
    }

    public void setFactTypeId(Long factTypeId) {
        this.factTypeId = factTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<FactDb> getChildren() {
        return children;
    }

    public void setChildren(List<FactDb> children) {
        this.children = children;
    }

    public List<FactValueDb> getValues() {
        return values;
    }

    public void setValues(List<FactValueDb> values) {
        this.values = values;
    }

    public DimensionValueDb getFactType() {
        return factType;
    }

    public void setFactType(DimensionValueDb factType) {
        this.factType = factType;
    }
}
