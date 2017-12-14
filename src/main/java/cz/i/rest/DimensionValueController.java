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

import cz.i.entity.model.dimension.DimensionValue;
import cz.i.service.DimensionValueCrudService;

/**
 * @author jan.hadas@i.cz
 */
@RestController
public class DimensionValueController {
    private static final Logger LOG = LoggerFactory.getLogger(DimensionValueController.class);

    @Autowired
    private DimensionValueCrudService dimensionValueCrudService;

    @RequestMapping(value = "/dimension-value", method = RequestMethod.GET)
    public List<DimensionValue> findAll(@RequestParam(required = false) Long id,
                                        @RequestParam(required = false) String code,
                                        @RequestParam(required = false) Long dimensionId) {
        List<DimensionValue> dimensionValues = null;

        if (id != null) {
            LOG.info("Searching dimensionValue by id: {}", id);
            throw new IllegalStateException("Unimplemented");
        } else if (!StringUtils.isEmpty(code)) {
            LOG.info("Searching dimensionValue by code: {}", code);
            throw new IllegalStateException("Unimplemented");
        } else if (dimensionId != null) {
            LOG.info("Searching dimensionValues by dimension with id: {}", dimensionId);
            throw new IllegalStateException("Unimplemented");
        } else {
            LOG.info("Searching all dimensionValues..");
            dimensionValues = dimensionValueCrudService.readAllDimensionValues();
        }

        LOG.info("Found dimensionValues: {}", dimensionValues);
        return dimensionValues;
    }

    /*
    @RequestMapping(value = "dimension-value", method = RequestMethod.POST)
    public void insert(@RequestBody DimensionValueDb dimensionValue) {
        LOG.info("insert dimensionValue: {}", dimensionValue);
        dimensionValueMapper.insert(dimensionValue);
    }

    @RequestMapping(value = "dimension-value", method = RequestMethod.PUT)
    public void update(@RequestBody DimensionValueDb dimensionValue) {
        LOG.info("update dimensionValue: {}", dimensionValue);
        dimensionValueMapper.update(dimensionValue);
    }

    @RequestMapping(value = "dimension-value", method = RequestMethod.DELETE)
    public void delete(@RequestBody DimensionValueDb dimensionValue) {
        LOG.info("delete dimensionValue: {}", dimensionValue);
        dimensionValueMapper.delete(dimensionValue);
    }
    */
}
