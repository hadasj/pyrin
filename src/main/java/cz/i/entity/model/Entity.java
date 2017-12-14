package cz.i.entity.model;

/**
 * @author jan.hadas@i.cz
 */
public class Entity {
    private String id;
    private Long idExternal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIdExternal() {
        return idExternal;
    }

    public void setIdExternal(Long idExternal) {
        this.idExternal = idExternal;
    }
}
