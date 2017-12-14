package cz.i.entity.db;

/**
 * @author jan.hadas@i.cz
 */
public class CodedEntityDb extends EntityDb {
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
