package com.dbapplication.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("mongoemb")
@RestController
public class MongoDbEmbeddedController {

    @GetMapping
    public String index() {
        return "MongoDB embedded controller is running!";
    }
}
