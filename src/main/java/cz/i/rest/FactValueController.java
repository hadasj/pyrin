package cz.i.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.i.dao.FactValueMapper;
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

    @Autowired
    private FactValueMapper factValueMapper;

    @RequestMapping(value = "/fact-value", method = RequestMethod.GET)
    public List<FactValue> findAll(@RequestParam(required = false) Long id, @RequestParam(required = false) String code) {
        List<FactValue> facts = null;
        /*
        if (id != null) {
            LOG.info("Searching factValue by id: {}", id);
            facts = asList(factValueCrudService.oneById(id));
        } else if (!StringUtils.isEmpty(code)) {
            LOG.info("Searching factValue by code: {}", code);
            facts = factValueCrudService.allByCode(code);
        } else {
            LOG.info("Searching all factValues..");
            facts = factValueCrudService.all();
        } */

        LOG.info("Found factValues: {}", facts);
        return facts;
    }

    @RequestMapping(value = "/fact-value", method = RequestMethod.POST)
    public void insertEntity(@RequestBody FactValue fact) {
        LOG.info("Insert new factValue: {}", fact);
       // factValueCrudService.insert(fact);
    }

    @RequestMapping(value = "/fact-value", method = RequestMethod.PUT)
    public void update(@RequestBody FactValue fact) {
        LOG.info("update factValue: {}", fact);
        //factValueCrudService.update(fact);
    }

    @RequestMapping(value = "/fact-value", method = RequestMethod.DELETE)
    public void delete(@RequestBody FactValue fact) {
        LOG.info("delete factValue: {}", fact);
        //factValueMapper.delete(fact);
    }
}
