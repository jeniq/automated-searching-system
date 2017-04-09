package com.hryshchenko.controller;

import com.hryshchenko.model.dto.CategoryDTO;
import com.hryshchenko.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private static final String JSON_MEDIA_TYPE = "application/json;";
    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(value = "/all", produces = JSON_MEDIA_TYPE)
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping(value = "/category", produces = JSON_MEDIA_TYPE)
    public ResponseEntity<?> getCoursesByCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping(value = "/search", produces = JSON_MEDIA_TYPE)
    public ResponseEntity<?> searchCourse(@RequestParam String request, @RequestParam String source) {
        return ResponseEntity.ok(courseService.searchByValue(request, source));
    }
}