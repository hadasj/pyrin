package cz.i.pirin.rest;

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

import cz.i.pirin.model.entity.fact.Fact;
import cz.i.pirin.service.FactCrudService;

/**
 * @author jan.hadas@i.cz
 */
@RestController
public class FactController {

    private static final Logger LOG = LoggerFactory.getLogger(FactController.class);

    @Autowired
    private FactCrudService factCrudService;

    @RequestMapping(value = "/fact", method = RequestMethod.GET)
    public List<Fact> findAll(@RequestParam(required = false) Long id, @RequestParam(required = false) String code) {
        List<Fact> facts = new ArrayList<>();

        if (id != null) {
            LOG.info("Searching fact by id: {}", id);
            Fact fact = factCrudService.readOneFact(id);
            if (fact != null)
                facts.add(fact);
        } else {
            LOG.info("Searching all facts..");
            facts = factCrudService.readAllFacts();
        }

        LOG.info("Found facts: {}", facts);
        return facts;
    }

    @RequestMapping(value = "/fact", method = RequestMethod.POST)
    public void insertEntity(@RequestBody Fact fact) {
        LOG.info("Insert new fact: {}", fact);
        factCrudService.insert(fact);
    }

}
