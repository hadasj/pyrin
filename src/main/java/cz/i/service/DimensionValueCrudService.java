package cz.i.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.i.dao.DimensionValueLinkMapper;
import cz.i.dao.DimensionValueMapper;
import cz.i.entity.db.dimension.DimensionValueDb;
import cz.i.entity.model.dimension.DimensionValue;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class DimensionValueCrudService {

    @Autowired
    private DimensionValueMapper dimensionValueMapper;

    @Autowired
    private DimensionValueLinkMapper dimensionValueLinkMapper;

    @Autowired
    private DimensionCrudService dimensionCrudService;

    public List<DimensionValue> readAllDimensionValues() {
        List<DimensionValueDb> dimensionValues = dimensionValueMapper.all();
        List<DimensionValue> result = new ArrayList<>();

        for (DimensionValueDb dimensionValueDb : dimensionValues) {
            result.add(mapDimensionValueDeep(dimensionValueDb));
        }
        return result;
    }

    private DimensionValue mapDimensionValueDeep(DimensionValueDb dimensionValueDb) {
        DimensionValue dimensionValue = mapDimensionValue(dimensionValueDb);
        dimensionValue.setLinks(mapDimensionValues(dimensionValueLinkMapper.allByOwner(dimensionValueDb)));
        return dimensionValue;
    }

    private List<DimensionValue> mapDimensionValues(List<DimensionValueDb> links) {
        if (links == null)
            return null;

        List<DimensionValue> result = new ArrayList<>();
        for(DimensionValueDb linkDb : links) {
            result.add(mapDimensionValue(linkDb));
        }

        return result;
    }

    private DimensionValue mapDimensionValue(DimensionValueDb dimensionValueDb) {
        DimensionValue dimensionValue = new DimensionValue();
        dimensionValue.setId(dimensionValueDb.getId().toString());
        dimensionValue.setIdExternal(dimensionValueDb.getIdExt());
        dimensionValue.setCode(dimensionValueDb.getCode());
        dimensionValue.setAlias(dimensionValueDb.getAlias());
        dimensionValue.setDimension(dimensionCrudService.mapDimension(dimensionValueDb.getDimension()));

        return dimensionValue;
    }
}
