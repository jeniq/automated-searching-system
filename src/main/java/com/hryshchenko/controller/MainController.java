package com.hryshchenko.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = {"/*", "/online-course/*"}, produces = MediaType.TEXT_HTML_VALUE)
    public String index() {
        return "/static/index.html";
    }

}
