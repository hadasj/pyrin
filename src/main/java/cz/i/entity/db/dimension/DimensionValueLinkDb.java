package cz.i.entity.db.dimension;

import cz.i.entity.Entity;

/**
 * @author jan.hadas@i.cz
 */
public class DimensionValueLinkDb extends Entity {
    private Long ownerId;
    private Long dimensionId;
    private Long valueId;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(Long dimensionId) {
        this.dimensionId = dimensionId;
    }

    public Long getValueId() {
        return valueId;
    }

    public void setValueId(Long valueId) {
        this.valueId = valueId;
    }
}
