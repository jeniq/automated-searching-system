package com.hryshchenko.service.course;


import com.hryshchenko.model.dto.SearchDTO;
import com.hryshchenko.model.entity.Course;
import com.hryshchenko.repository.course.CourseRepository;
import com.hryshchenko.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class CourseService {

    private CourseRepository courseRepository;
    private SearchService searchService;

    @Autowired
    public CourseService(CourseRepository courseRepository,
                         ApplicationEventPublisher eventPublisher,
                         SearchService searchService) {
        this.courseRepository = courseRepository;
        this.searchService = searchService;
    }

    public Course getCourse(Long id) {
        return courseRepository.getById(id);
    }

    public List<Course> getAllCourses(Integer pageSize) {
        return courseRepository.getAll(pageSize);
    }

    // TODO
    public List<Course> getAllCourses(SearchDTO searchDTO, Integer pageSize) {
        return null;
    }

    /**
     * This method gets courses. First, it starts checking out request in original sources
     * that selected by user at UI. It starts new thread for each source. In the same time,
     * it makes search at database. In case nothing found in database it waits for results
     * from original sources.
     *
     * @param searchDTO object with all necessary information for success searching.
     * @param pageSize  size of returning list.
     * @return list of courses founded at database
     */
    // TODO request in quotes
    public List<Course> getCourses(SearchDTO searchDTO, Integer pageSize) {
        Collection<Future<Boolean>> search = new ArrayList<>();

        // New thread for each source
        search.add(searchService.search(searchDTO));

        List<Course> result = courseRepository.getCourses(searchDTO, pageSize);

        if (result.size() == 0) {
            search.forEach(done -> {
                try {
                    done.get(); // Wait for all threads
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
            result = courseRepository.getCourses(searchDTO, pageSize);
        }

        return result;
    }
}
