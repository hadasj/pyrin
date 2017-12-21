package cz.i.pirin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.i.pirin.dao.DimensionDao;
import cz.i.pirin.entity.db.dimension.DimensionDb;
import cz.i.pirin.mapper.DimensionMapper;
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
