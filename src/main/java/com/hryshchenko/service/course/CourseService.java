package com.hryshchenko.service.course;


import com.hryshchenko.model.entity.Course;
import com.hryshchenko.model.event.SearchRequest;
import com.hryshchenko.repository.course.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.hryshchenko.service.search.SearchService.SPACE;

@Service
public class CourseService {

    private CourseRepository courseRepository;
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public CourseService(CourseRepository courseRepository,
                         ApplicationEventPublisher eventPublisher) {
        this.courseRepository = courseRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public List<Course> getAllCourses() {
        return courseRepository.getAll();
    }

    @Transactional
    public List<Course> search(String request, String resource) {
        List<Course> courses = new ArrayList<>();

        // Activate search at resources in new threads
        eventPublisher.publishEvent(new SearchRequest(request, resource));

        // Find cached results in database
        // TODO fix repeat code in search service
        String values[];
        if (request.startsWith("\"") & request.endsWith("\"")) {
            values = new String[]{request}; // Search by full request string
        } else {
            values = request.split(SPACE); // Search by separate word
        }
        for (String value : values) {
            courses.addAll(courseRepository.getCourseByName(value));
        }

        return courses;
    }
}
