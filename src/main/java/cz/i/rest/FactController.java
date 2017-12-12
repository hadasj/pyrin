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

import cz.i.dao.FactMapper;
import cz.i.entity.fact.Fact;

/**
 * @author jan.hadas@i.cz
 */
@RestController
public class FactController {

    private static final Logger LOG = LoggerFactory.getLogger(FactController.class);

    @Autowired
    private FactMapper factMapper;

    @RequestMapping(value = "/fact", method = RequestMethod.GET)
    public List<Fact> findAll(@RequestParam(required = false) Long id, @RequestParam(required = false) String code) {
        List<Fact> facts = null;

        if (id != null) {
            LOG.info("Searching fact by id: {}", id);
            facts = asList(factMapper.oneById(id));
        } else if (!StringUtils.isEmpty(code)) {
            LOG.info("Searching fact by code: {}", code);
            facts = factMapper.allByCode(code);
        } else {
            LOG.info("Searching all facts..");
            facts = factMapper.all();
        }

        LOG.info("Found facts: {}", facts);
        return facts;
    }

    @RequestMapping(value = "/fact", method = RequestMethod.POST)
    public void insertEntity(@RequestBody Fact fact) {
        LOG.info("Insert new fact: {}", fact);
        factMapper.insert(fact);
    }

    @RequestMapping(value = "/fact", method = RequestMethod.PUT)
    public void update(@RequestBody Fact fact) {
        LOG.info("update fact: {}", fact);
        factMapper.update(fact);
    }

    @RequestMapping(value = "/fact", method = RequestMethod.DELETE)
    public void delete(@RequestBody Fact fact) {
        LOG.info("delete fact: {}", fact);
        factMapper.delete(fact);
    }
}
