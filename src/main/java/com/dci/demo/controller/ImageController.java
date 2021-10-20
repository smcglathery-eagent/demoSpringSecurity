package com.dci.demo.controller;

import com.dci.demo.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Validated
@RestController
@Api(value="Image Controller")
@RequestMapping(value = "/image")
public class ImageController extends BaseController{

    @Autowired
    ImageService imageService;

    @PostMapping(path="/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add Image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public int addImage(
            @ApiParam(name = "file", value = "Select the image to upload", required = true)
            @RequestPart("file") MultipartFile multipartFile) throws IOException {

        return imageService.addImage(multipartFile);
    }

    @ApiOperation(value = "Get Image by Name", consumes = "application/json")
    @GetMapping(path="/name/{fileName}")
    public ResponseEntity<byte[]> getImageByName(@PathVariable("fileName") String fileName){

        return imageService.getImageByName(fileName);
    }
}