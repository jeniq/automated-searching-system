package com.hryshchenko.service.course;


import com.hryshchenko.model.dto.SearchDTO;
import com.hryshchenko.model.entity.Course;
import com.hryshchenko.model.event.SearchRequest;
import com.hryshchenko.repository.course.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

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

    public Course getCourse(Long id) {
        return courseRepository.getById(id);
    }

    public List<Course> getAllCourses(Integer pageSize) {
        return courseRepository.getAll(pageSize);
    }

    public List<Course> search(String request, String resource, Integer pageSize) {
        List<Course> courses = new ArrayList<>();

        // Activate search at original sources in new threads
        eventPublisher.publishEvent(new SearchRequest(request, resource));

        // Find cached results in database
        // TODO fix repeat code in search service
        String values[];
        if (request.startsWith("\"") & request.endsWith("\"")) {
            values = new String[]{request}; // Search by full request string
        } else {
            values = request.split(SPACE); // Search by separate word
        }

        String[] sources = resource.split(",");

        if (sources.length == 1) { // Interrupt search without selected source
            return courseRepository.getAll(pageSize);
        }
        // TODO fix when no one source selected, nothing returns
        for (String s : sources) {
            if (s.equals("") || s.equals(",")) {
                continue;
            }
            for (String value : values) {
                courses.addAll(
                        courseRepository.getCoursesByNameAndSource(new SearchDTO(Long.valueOf(s), value), pageSize));
            }
        }

        return courses;
    }

    public List<Course> search(SearchDTO searchDTO, Integer pageSize) {
        return null;
    }
}
