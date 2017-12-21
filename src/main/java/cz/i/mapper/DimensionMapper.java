package cz.i.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cz.i.entity.db.dimension.DimensionDb;
import cz.i.pirin.model.entity.dimension.Dimension;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class DimensionMapper {

    public Dimension mapDimension(DimensionDb dimensionDb) {
        if (dimensionDb == null)
            return null;

        String id = dimensionDb.getId().toString();
        return new Dimension(id, dimensionDb.getAlias(), dimensionDb.getCode(), dimensionDb.getStructure(), dimensionDb.getMode());
    }

    public List<Dimension> mapDimensions(List<DimensionDb> dimensionsDb) {
        List<Dimension> dimensionList = new ArrayList<>();

        if (dimensionsDb != null)
            for (DimensionDb dimensionDb : dimensionsDb) {
                Dimension dimension = mapDimension(dimensionDb);
                dimensionList.add(dimension);
            }

        return dimensionList;
    }
}
