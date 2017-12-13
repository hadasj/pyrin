package cz.i.rest;

import static java.util.Arrays.asList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.i.dao.DimensionMapper;
import cz.i.entity.db.dimension.DimensionDb;

/**
 * @author jan.hadas@i.cz
 */
@RestController
public class DimensionController {

    private static final Logger LOG = LoggerFactory.getLogger(DimensionController.class);

    @Autowired
    private DimensionMapper dimensionMapper;

    @RequestMapping(value = "/dimension", method = RequestMethod.GET)
    public List<DimensionDb> findAll(@RequestParam(required = false) Long id, @RequestParam(required = false) String code) {
        List<DimensionDb> dimensions = null;

        if (id != null) {
            LOG.info("Searching dimension by id: {}", id);
            dimensions = asList(dimensionMapper.oneById(id));
        } else if (!StringUtils.isEmpty(code)) {
            LOG.info("Searching dimension by code: {}", code);
            dimensions = asList(dimensionMapper.oneByCode(code));
        } else {
            LOG.info("Searching all dimsions..");
            dimensions = dimensionMapper.all();
        }

        LOG.info("Found dimensions: {}", dimensions);
        return dimensions;
    }

    @RequestMapping(value = "/dimension", method = RequestMethod.POST)
    public void insertEntity(@RequestBody DimensionDb dimension) {
        LOG.info("Insert new dimension: {}", dimension);
        dimensionMapper.insert(dimension);
    }

    @RequestMapping(value = "/dimension", method = RequestMethod.PUT)
    public void update(@RequestBody DimensionDb dimension) {
        LOG.info("update dimension: {}", dimension);
        dimensionMapper.update(dimension);
    }

    @RequestMapping(value = "/dimension", method = RequestMethod.DELETE)
    public void delete(@RequestBody DimensionDb dimension) {
        LOG.info("delete dimension: {}", dimension);
        dimensionMapper.delete(dimension);
    }
}
