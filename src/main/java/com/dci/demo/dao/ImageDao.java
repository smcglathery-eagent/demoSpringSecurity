package com.dci.demo.dao;

import com.dci.demo.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageDao {

    int addImage(MultipartFile multipartFile, String contentType) throws IOException;

    Image getImageByName(String fileName);
}