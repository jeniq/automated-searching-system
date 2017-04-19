package com.hryshchenko.controller;

import com.hryshchenko.model.dto.SearchDTO;
import com.hryshchenko.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    public static final String JSON_MEDIA_TYPE = "application/json;";
    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(value = "/{id}", produces = JSON_MEDIA_TYPE)
    public ResponseEntity<?> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourse(id));
    }

    @GetMapping(value = "/all", produces = JSON_MEDIA_TYPE)
    public ResponseEntity<?> getAllCourses(@RequestParam(name = "size") Integer pageSize) {
        return ResponseEntity.ok(courseService.getAllCourses(pageSize));
    }

    @PostMapping(value = "/all", produces = JSON_MEDIA_TYPE)
    public ResponseEntity<?> getAllCourses(@RequestBody SearchDTO searchDTO,
                                           @RequestParam(name = "size") Integer pageSize) {
        return ResponseEntity.ok(courseService.getAllCourses(searchDTO, pageSize));
    }

    // Use POST because in this method we also write data to the database
    @PostMapping(value = "/search", produces = JSON_MEDIA_TYPE)
    public ResponseEntity<?> searchCourse(@RequestBody SearchDTO searchDTO,
                                          @RequestParam(name = "size") Integer pageSize) {
        return ResponseEntity.ok(courseService.getCourses(searchDTO, pageSize));
    }
}
