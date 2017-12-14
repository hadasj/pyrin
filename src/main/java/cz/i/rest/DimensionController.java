package cz.i.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.i.entity.model.dimension.Dimension;
import cz.i.service.DimensionCrudService;

/**
 * @author jan.hadas@i.cz
 */
@RestController
public class DimensionController {

    private static final Logger LOG = LoggerFactory.getLogger(DimensionController.class);

    @Autowired
    private DimensionCrudService dimensionCrudService;

    @RequestMapping(value = "/dimension", method = RequestMethod.GET)
    public List<Dimension> findAll(@RequestParam(required = false) Long id, @RequestParam(required = false) String code) {
        List<Dimension> dimensions = null;

        if (id != null) {
            LOG.info("Searching dimension by id: {}", id);
            throw new IllegalStateException("Unimplemented");
        } else if (!StringUtils.isEmpty(code)) {
            LOG.info("Searching dimension by code: {}", code);
            throw new IllegalStateException("Unimplemented");
        } else {
            LOG.info("Searching all dimsions..");
            dimensions = dimensionCrudService.readAllDimensions();
        }

        LOG.info("Found dimensions: {}", dimensions);
        return dimensions;
    }
   /*
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
    }*/
}
