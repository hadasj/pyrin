package cz.i.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.i.dao.DimensionDao;
import cz.i.entity.db.dimension.DimensionDb;
import cz.i.mapper.DimensionMapper;
import cz.i.pirin.model.entity.dimension.Dimension;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class DimensionCrudService {

    @Autowired
    private DimensionDao dimensionDao;

    @Autowired
    private DimensionMapper dimensionMapper;

    public List<Dimension> readAllDimensions() {
        List<DimensionDb> dimensions = dimensionDao.all();
        return dimensionMapper.mapDimensions(dimensions);
    }
}
