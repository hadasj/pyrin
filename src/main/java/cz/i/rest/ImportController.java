package cz.i.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.i.service.ImportService;

/**
 * @author jan.hadas@i.cz
 */
@RestController
public class ImportController {
    private static final Logger LOG = LoggerFactory.getLogger(ImportController.class);

    @Autowired
    private ImportService importService;

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public void importData(@RequestBody String csv) {
        LOG.debug("Import csv..");

        importService.importData(csv);
    }
}
