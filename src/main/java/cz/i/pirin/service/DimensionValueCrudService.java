package cz.i.pirin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.i.pirin.dao.DimensionValueDao;
import cz.i.pirin.dao.DimensionValueLinkDao;
import cz.i.pirin.entity.db.dimension.DimensionValueDb;
import cz.i.pirin.mapper.DimensionValueMapper;
import cz.i.pirin.model.entity.dimension.DimensionValue;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class DimensionValueCrudService {

    @Autowired
    private DimensionValueDao dimensionValueDao;

    @Autowired
    private DimensionValueLinkDao dimensionValueLinkDao;

    @Autowired
    private DimensionValueMapper dimensionValueMapper;

    public List<DimensionValue> readAllDimensionValuesFlat() {
        List<DimensionValueDb> dimensionValues = dimensionValueDao.all();
        return dimensionValueMapper.mapDimensionValues(dimensionValues);
    }

    public DimensionValue readDimensionValueWithChilds(Long id) {
        DimensionValueDb dimensionValueDb = dimensionValueDao.oneById(id);
        dimensionValueDb.setLinks(dimensionValueLinkDao.allByOwner(dimensionValueDb));
        dimensionValueDb.setChildren(dimensionValueDao.allByParent(dimensionValueDb));
        return dimensionValueMapper.mapDimensionValue(dimensionValueDb);
    }
}
