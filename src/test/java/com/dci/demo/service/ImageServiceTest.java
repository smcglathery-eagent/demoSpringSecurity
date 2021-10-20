package com.dci.demo.service;

import com.dci.demo.dao.ImageDao;
import com.dci.demo.model.Image;
import org.easymock.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@ExtendWith(EasyMockExtension.class)
public class ImageServiceTest extends EasyMockSupport {

    @TestSubject
    ImageService imageService = new ImageServiceImpl();

    @Mock
    ImageDao imageDao;

    @Mock
    MultipartFile multipartFile;

    @Mock
    Image image;

    @Mock
    ResponseEntity expectedResponse;

    @Mock
    HttpHeaders responseHeaders;

    @BeforeEach
    public void setUp() {

        responseHeaders = new HttpHeaders();
    }

    @Test
    public void addImage() throws IOException {

        multipartFile.getOriginalFilename();
        EasyMock.expectLastCall().andReturn("imageName");

        String input = "my input";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        multipartFile.getInputStream();
        EasyMock.expectLastCall().andReturn(inputStream);

        imageDao.addImage(multipartFile, null);
        EasyMock.expectLastCall().andReturn(1);

        replayAll();

        int actualResult = imageService.addImage(multipartFile);
        Assertions.assertEquals(1, actualResult);

        verifyAll();

    }

    @Test
    public void getImageByName() {

        responseHeaders.setContentType(MediaType.valueOf("image/jpeg"));
        responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=imageName");
        responseHeaders.setContentLength(0);

        expectedResponse = new ResponseEntity<byte[]>(new byte[] {}, responseHeaders, HttpStatus.OK);

        imageDao.getImageByName("imageName");
        EasyMock.expectLastCall().andReturn(image);

        image.getContentType();
        EasyMock.expectLastCall().andReturn("image/jpeg");

        image.getFileName();
        EasyMock.expectLastCall().andReturn("imageName");

        image.getContents();
        EasyMock.expectLastCall().andReturn(new byte[] {}).times(2);

        replayAll();

        ResponseEntity actualResponse = imageService.getImageByName("imageName");
        Assertions.assertEquals(expectedResponse, actualResponse);

        verifyAll();
    }
}