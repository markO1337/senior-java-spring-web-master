package hu.ponte.hr.function;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/function/image/image_test_data.sql")
@Sql(scripts = "/function/image/clean_up.sql", executionPhase = AFTER_TEST_METHOD)
public class ImageIT {

    private static String ENDPOINT_URL;
    private static final String RESOURCE_LOCATION = "classpath:function/image/";

    @LocalServerPort
    private int serverPort;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void init() {
        ENDPOINT_URL = String.format("http://localhost:%d/api/images", serverPort);
    }

    @Test
    public void testGetAllMetadata() throws JSONException, IOException {
        // When
        ResponseEntity<String> response = testRestTemplate.getForEntity(ENDPOINT_URL + "/meta", String.class);

        // Then
        JSONAssert.assertEquals(readJsonFile("getAllMetadataResponse.json"), response.getBody(), JSONCompareMode.LENIENT);
    }

    @Test
    public void testGetImageById() throws IOException {
        // When
        ResponseEntity<byte[]> response = testRestTemplate.getForEntity(ENDPOINT_URL + "/1/preview", byte[].class);

        // Then
        byte[] excepted = FileUtils.readFileToByteArray(resourceLoader.getResource(RESOURCE_LOCATION + "responseImage.jpeg").getFile());
        Assert.assertArrayEquals(excepted, response.getBody());
    }

    private String readJsonFile(String fileName) throws IOException {
        return Files.readString(Path.of(resourceLoader.getResource(RESOURCE_LOCATION + fileName).getFile().getPath()));
    }

}
