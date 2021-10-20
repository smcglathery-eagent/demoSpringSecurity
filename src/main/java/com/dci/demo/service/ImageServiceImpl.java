package com.dci.demo.service;

import com.dci.demo.dao.ImageDao;
import com.dci.demo.model.Image;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

@Service
public class ImageServiceImpl implements ImageService {

    private final static Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Autowired
    ImageDao imageDao;

    public int addImage(MultipartFile multipartFile) throws IOException {

        String filename = multipartFile.getOriginalFilename();
        log.debug("Attempting to upload file '" + filename + "'");

        InputStream inputStream = new BufferedInputStream(multipartFile.getInputStream());

        String contentType = URLConnection.guessContentTypeFromStream(inputStream);
        log.debug("'" + filename + "' contentType: " + contentType);

        int fileUploaded = imageDao.addImage(multipartFile, contentType);
        log.debug("Successfully uploaded file? " + BooleanUtils.toBoolean(fileUploaded));

        return fileUploaded;
    }

    public ResponseEntity<byte[]> getImageByName(String fileName) {

        log.debug("Attempting to get file by name: '" + fileName + "'");

        Image image = imageDao.getImageByName(fileName);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.valueOf(image.getContentType()));
        responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + image.getFileName().replace(" ", "_"));
        responseHeaders.setContentLength(image.getContents().length);

        return new ResponseEntity<byte[]>(image.getContents(), responseHeaders, HttpStatus.OK);
    }
}