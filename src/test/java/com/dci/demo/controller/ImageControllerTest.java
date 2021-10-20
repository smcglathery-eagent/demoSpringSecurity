package com.dci.demo.controller;

import com.dci.demo.service.ImageService;
import org.easymock.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@ExtendWith(EasyMockExtension.class)
public class ImageControllerTest extends EasyMockSupport {

    @TestSubject
    ImageController imageController = new ImageController();

    @Mock
    ImageService imageService;

    @Mock
    MultipartFile multipartFile;

    @Mock
    ResponseEntity expectedResponse;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void addImage() throws IOException {

        imageService.addImage(multipartFile);
        EasyMock.expectLastCall().andReturn(1);

        replayAll();

        int actualResult = imageController.addImage(multipartFile);
        Assertions.assertEquals(1, actualResult);

        verifyAll();
    }

    @Test
    public void getImageByName() throws IOException {

        imageService.getImageByName("imageName");
        EasyMock.expectLastCall().andReturn(expectedResponse);

        replayAll();

        ResponseEntity actualResponse = imageController.getImageByName("imageName");
        Assertions.assertEquals(expectedResponse, actualResponse);

        verifyAll();
    }
}