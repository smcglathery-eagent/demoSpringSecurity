package com.dci.demo.controller;

import com.dci.demo.DemoApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders httpHeaders;
    private String uri;

    @BeforeEach
    public void setup() {

        restTemplate = new TestRestTemplate();

        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void defaultHandlerTest() {

        String expected = "{\"url\":\"http://localhost:" + port + "/demo/foo\",\"errorMessage\":\"The path does not exist.\",\"exceptionMessage\":\"The resource[/demo/foo] does not exist in the Demo Service\"}";

        uri = "/demo/foo";

        HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.GET, entity, String.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(expected, response.getBody());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}