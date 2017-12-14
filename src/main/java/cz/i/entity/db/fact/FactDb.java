package cz.i.entity.db.fact;

import cz.i.entity.db.CodedEntityDb;

/**
 * @author jan.hadas@i.cz
 */
public class FactDb extends CodedEntityDb {
    private String factType;
    private String name;
    private Long parentId;

    /** children - FK PARENT_ID*/
    private FactDb metadata;

    public String getFactType() {
        return factType;
    }

    public void setFactType(String factType) {
        this.factType = factType;
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

    public FactDb getMetadata() {
        return metadata;
    }

    public void setMetadata(FactDb metadata) {
        this.metadata = metadata;
    }
}
