package com.dci.demo.controller;

import com.dci.demo.model.Message;
import com.dci.demo.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@Api(value="Demo Controller")
@RequestMapping(value = "/demo")
public class DemoController extends BaseController{

    @Autowired
    DemoService demoService;

    @ApiOperation(value = "Return Message", consumes = "application/json")
    @RequestMapping(path="/message", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String demo(@Valid @RequestBody Message message){

        return demoService.demo(message);
    }
}