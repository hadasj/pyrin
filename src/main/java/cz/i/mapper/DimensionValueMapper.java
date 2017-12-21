package cz.i.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.i.entity.db.dimension.DimensionValueDb;
import cz.i.pirin.model.entity.dimension.Dimension;
import cz.i.pirin.model.entity.dimension.DimensionValue;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class DimensionValueMapper {

    @Autowired
    private DimensionMapper dimensionMapper;

    public List<DimensionValue> mapDimensionValues(List<DimensionValueDb> dimensionValuesDb) {
        List<DimensionValue> dimensionValues = new ArrayList<>();
        if (dimensionValuesDb != null) {
            for (DimensionValueDb dimensionValueDb : dimensionValuesDb) {
                mapDimensionValue(dimensionValueDb);
            }
        }
        return dimensionValues;
    }

    public DimensionValue mapDimensionValue(DimensionValueDb dimensionValueDb) {
        String id = dimensionValueDb.getId().toString();
        Dimension dimension = dimensionMapper.mapDimension(dimensionValueDb.getDimension());

        DimensionValue dimensionValue = new DimensionValue(id, dimensionValueDb.getIdExt(), dimension, dimensionValueDb.getCode());
        dimensionValue.getChildren().addAll(mapDimensionValues(dimensionValueDb.getChildren()));
        dimensionValue.getLinks().addAll(mapDimensionValues(dimensionValueDb.getLinks()));

        return dimensionValue;
    }
}
