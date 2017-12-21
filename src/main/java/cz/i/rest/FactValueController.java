package cz.i.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.i.entity.model.fact.FactValue;
import cz.i.service.FactValueCrudService;

/**
 * @author jan.hadas@i.cz
 */
@RestController
public class FactValueController {
    private static final Logger LOG = LoggerFactory.getLogger(FactValueController.class);

    @Autowired
    private FactValueCrudService factValueCrudService;

    @RequestMapping(value = "/fact-value", method = RequestMethod.GET)
    public List<FactValue> findAll(@RequestParam(required = false) Long id, @RequestParam(required = false) String code) {
        List<FactValue> factValues = new ArrayList<>();

        if (id != null) {
            LOG.info("Searching factValue by id: {}", id);
            FactValue factValue = factValueCrudService.readOneFactValue(id);
            if (factValue != null)
                factValues.add(factValue);
        } else {
            LOG.info("Searching all factValues..");
            factValues = factValueCrudService.readAllFactValues();
        }

        LOG.info("Found factValues: {}", factValues);
        return factValues;
    }

    @RequestMapping(value = "/fact-value", method = RequestMethod.POST)
    public void insertEntity(@RequestBody FactValue fact, @RequestParam Long factId) {
        LOG.info("Insert new factValue: {}, factId: {}", fact, factId);
        factValueCrudService.insert(fact, factId);
    }
}
