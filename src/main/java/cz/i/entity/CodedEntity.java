package cz.i.entity;

/**
 * @author jan.hadas@i.cz
 */
public class CodedEntity extends Entity {
    private String alias;
    private String code;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
