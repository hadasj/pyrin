package cz.i.entity.fact;

import cz.i.entity.Entity;
import cz.i.entity.dimension.Dimension;

/**
 * @author jan.hadas@i.cz
 */
public class FactValue extends Entity {

    private Dimension dimension;

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }
}
