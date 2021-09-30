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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@ActiveProfiles("test")
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpEntity<String> httpEntity;
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
    public void demo_messageTooShort() throws Exception {

        uri = "/demo/message";

        String requestJson = readFile(new ClassPathResource("json/messageTooShort.json").getFile());

        httpEntity = new HttpEntity<>(requestJson, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.POST, httpEntity, String.class);

        ErrorInfo errorInfo = new ObjectMapper().readValue(response.getBody(), ErrorInfo.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Message must be a minimum of 2 characters", errorInfo.getExceptionMessage());
    }

    @Test
    public void demo() throws Exception {

        uri = "/demo/message";

        String requestJson = readFile(new ClassPathResource("json/message.json").getFile());

        httpEntity = new HttpEntity<>(requestJson, httpHeaders);

        ResponseEntity<String> message = restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.POST, httpEntity, String.class);

        Assertions.assertEquals(HttpStatus.OK, message.getStatusCode());
        Assertions.assertNotNull(message);
    }

    private String readFile(File file) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder sb = new StringBuilder();

        while((line=bufferedReader.readLine())!= null){
            sb.append(line.trim());
        }

        return sb.toString();
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}