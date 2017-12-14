package cz.i.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.ibatis.io.Resources;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jan.hadas@i.cz
 */
//@SpringBootTest
//@RunWith(SpringRunner.class)
public class ImportServiceTest {

    @Autowired
    private ImportService importService;

    @Before
    public void setUp() {
        // TODO clean DB
    }

    //@Test
    public void importDimensionsTest() throws IOException, URISyntaxException {
        URL dimensions = Resources.getResourceURL("dimension.csv");
        String csv = new String(Files.readAllBytes(Paths.get(dimensions.toURI())));

        importService.importData(csv);
    }

    //@Test
    public void importDimensionValuesTest() throws IOException, URISyntaxException {
        URL dimensions = Resources.getResourceURL("dimension-value.csv");
        String csv = new String(Files.readAllBytes(Paths.get(dimensions.toURI())));

        importService.importData(csv);
    }

}
