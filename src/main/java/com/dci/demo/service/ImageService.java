package com.dci.demo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    int addImage(MultipartFile file) throws IOException;

    ResponseEntity<byte[]> getImageByName(String fileName);
}