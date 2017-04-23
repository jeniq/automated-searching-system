package com.hryshchenko.controller;

import com.hryshchenko.model.dto.SearchDTO;
import com.hryshchenko.service.course.CourseService;
import com.hryshchenko.service.course.CourseViewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.hryshchenko.controller.CourseController.JSON_MEDIA_TYPE;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    static final String JSON_MEDIA_TYPE = "application/json;";
    private CourseService courseService;
    private CourseViewsService courseViewsService;

    @Autowired
    public CourseController(CourseService courseService, CourseViewsService courseViewsService) {
        this.courseService = courseService;
        this.courseViewsService = courseViewsService;
    }

    @GetMapping(value = "/{id}", produces = JSON_MEDIA_TYPE)
    public ResponseEntity<?> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourse(id));
    }

    @Deprecated
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

    @PostMapping(value = "/watched", produces = JSON_MEDIA_TYPE)
    public ResponseEntity<?> appendNewView(@RequestParam(name = "course") Long courseId) {
        courseService.appendView(courseId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/statistic", produces = JSON_MEDIA_TYPE)
    public ResponseEntity<?> getStatistic() {
        return ResponseEntity.ok(courseViewsService.getTopCourses());
    }
}
