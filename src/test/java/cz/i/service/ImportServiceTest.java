package cz.i.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.ibatis.io.Resources;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jan.hadas@i.cz
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ImportServiceTest {

    @Autowired
    private ImportService importService;

    @Before
    public void setUp() {
        // TODO clean DB
    }

    @Ignore
    @Test
    public void importFactsTest() throws IOException, URISyntaxException {
        URL dimensions = Resources.getResourceURL("import.csv");
        String csv = new String(Files.readAllBytes(Paths.get(dimensions.toURI())));

        importService.importData(csv);
    }

}
