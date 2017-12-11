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
import cz.i.dao.DimensionValueMapper;
import cz.i.entity.dimension.Dimension;
import cz.i.entity.dimension.DimensionValue;

/**
 * @author jan.hadas@i.cz
 */
@RestController
public class DimensionValueController {
    private static final Logger LOG = LoggerFactory.getLogger(DimensionValueController.class);

    @Autowired
    private DimensionValueMapper dimensionValueMapper;

    @RequestMapping(value = "/dimension-value", method = RequestMethod.GET)
    public List<DimensionValue> findAll(@RequestParam(required = false) Long id,
                                        @RequestParam(required = false) String code,
                                        @RequestParam(required = false) Long dimensionId) {
        List<DimensionValue> dimensionValues = null;

        if (id != null) {
            LOG.info("Searching dimensionValue by id: {}", id);
            dimensionValues = asList(dimensionValueMapper.oneById(id));
        } else if (!StringUtils.isEmpty(code)) {
            LOG.info("Searching dimensionValue by code: {}", code);
            dimensionValues = asList(dimensionValueMapper.oneByCode(code));
        } else if (dimensionId != null) {
            LOG.info("Searching dimensionValues by dimension with id: {}", dimensionId);
            dimensionValues = dimensionValueMapper.allByDimension(dimensionId);
        } else {
            LOG.info("Searching all dimensionValues..");
            dimensionValues = dimensionValueMapper.all();
        }

        LOG.info("Found dimensionValues: {}", dimensionValues);
        return dimensionValues;
    }

    @RequestMapping(value = "dimension-value", method = RequestMethod.POST)
    public void insert(@RequestBody DimensionValue dimensionValue) {
        LOG.info("insert dimensionValue: {}", dimensionValue);
        dimensionValueMapper.insert(dimensionValue);
    }

    @RequestMapping(value = "dimension-value", method = RequestMethod.PUT)
    public void update(@RequestBody DimensionValue dimensionValue) {
        LOG.info("update dimensionValue: {}", dimensionValue);
        dimensionValueMapper.update(dimensionValue);
    }

    @RequestMapping(value = "dimension-value", method = RequestMethod.DELETE)
    public void delete(@RequestBody DimensionValue dimensionValue) {
        LOG.info("delete dimensionValue: {}", dimensionValue);
        dimensionValueMapper.delete(dimensionValue);
    }
}
