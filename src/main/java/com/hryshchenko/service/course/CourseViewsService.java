package com.hryshchenko.service.course;

import com.hryshchenko.model.dto.CourseViewsDTO;
import com.hryshchenko.repository.course.CourseViewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseViewsService {

    private CourseViewsRepository courseViewsRepository;

    @Autowired
    public CourseViewsService(CourseViewsRepository courseViewsRepository) {
        this.courseViewsRepository = courseViewsRepository;
    }

    public List<CourseViewsDTO> getTopCourses() {
        List<CourseViewsDTO> courses = new ArrayList<>();
        courseViewsRepository.getTopCourses().forEach(view -> courses.add(
                new CourseViewsDTO(view.getCourse().getId(), view.getCourse().getName(), view.getViews())));
        return courses;
    }
}
