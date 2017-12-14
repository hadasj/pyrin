package cz.i.entity.db.fact;

import cz.i.entity.db.CodedEntityDb;

/**
 * @author jan.hadas@i.cz
 */
public class FactDb extends CodedEntityDb {
    private String name;
    private Long parentId;
    private Long factTypeId;

    /** children - FK PARENT_ID*/
    private FactDb metadata;

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

    public FactDb getMetadata() {
        return metadata;
    }

    public void setMetadata(FactDb metadata) {
        this.metadata = metadata;
    }
}
