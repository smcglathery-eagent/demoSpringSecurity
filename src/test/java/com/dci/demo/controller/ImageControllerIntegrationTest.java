package com.dci.demo.controller;

import com.dci.demo.DemoApplication;
import com.dci.demo.exception.ErrorInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;

import java.io.IOException;

@ActiveProfiles("test")
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImageControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity;
    private HttpHeaders httpHeaders;
    private ObjectMapper objectMapper;
    private String uri;

    @BeforeEach
    public void setup() {

        restTemplate = new TestRestTemplate();

        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        objectMapper = new ObjectMapper();
    }

    @Test
    public void addImage() {

        uri = "/image/add";

        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("file", new ClassPathResource("image/kitten.jpg"));

        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        httpEntity = new HttpEntity<>(parameters, httpHeaders);

        ResponseEntity<String> message = restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.POST, httpEntity, String.class);

        Assertions.assertEquals(HttpStatus.CREATED, message.getStatusCode());
        Assertions.assertNotNull(message);
    }

    @Test
    public void getImage_NOT_FOUND() throws IOException {

        uri = "/image/name/foo";

        httpEntity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.GET, httpEntity, byte[].class);

        ErrorInfo errorInfo = new ObjectMapper().readValue(responseEntity.getBody(), ErrorInfo.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertTrue(errorInfo.getExceptionMessage().contains("Incorrect result size"));
    }

    @Test
    public void getImage() {

        uri = "/image/name/kitten.jpg";

        httpEntity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<byte[]> message = restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.GET, httpEntity, byte[].class);

        Assertions.assertEquals(HttpStatus.OK, message.getStatusCode());
        Assertions.assertNotNull(message);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}