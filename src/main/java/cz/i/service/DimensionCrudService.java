package cz.i.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.i.dao.DimensionMapper;
import cz.i.entity.db.dimension.DimensionDb;
import cz.i.entity.model.dimension.Dimension;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class DimensionCrudService {

    @Autowired
    private DimensionMapper dimensionMapper;

    public List<Dimension> readAllDimensions() {
        List<DimensionDb> dimensions = dimensionMapper.all();
        List<Dimension> result = new ArrayList<>();

        for (DimensionDb dimensionDb : dimensions) {
            result.add(mapDimension(dimensionDb));
        }

        return result;
    }

    public Dimension mapDimension(DimensionDb dimensionDb) {
        if (dimensionDb == null)
            return null;

        Dimension dimension = new Dimension();
        dimension.setId(dimensionDb.getId().toString());
        dimension.setIdExternal(dimensionDb.getIdExt());
        dimension.setMode(dimensionDb.getMode());
        dimension.setStructure(dimensionDb.getStructure());
        dimension.setAlias(dimensionDb.getAlias());
        dimension.setCode(dimensionDb.getCode());

        return dimension;
    }
}
