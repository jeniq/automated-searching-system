package com.hryshchenko.controller;

import com.hryshchenko.repository.source.SourceRepository;
import com.hryshchenko.service.source.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hryshchenko.controller.CourseController.JSON_MEDIA_TYPE;

@RestController
@RequestMapping("/api/source")
public class SourceController {

    private SourceService sourceService;

    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @GetMapping(value = "/all", produces = JSON_MEDIA_TYPE)
    public ResponseEntity<?> getAllSources() {
        return ResponseEntity.ok(sourceService.getAll());
    }
}
