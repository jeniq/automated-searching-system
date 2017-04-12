package com.hryshchenko.service.search;

import com.hryshchenko.repository.course.CourseRepository;
import com.hryshchenko.service.sourceAPI.CourseraAPI;
import com.hryshchenko.service.sourceAPI.EdxAPI;
import com.hryshchenko.service.sourceAPI.Searchable;
import com.hryshchenko.util.Parser.JSONCourseraParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    public static final String SPACE = " ";
    private JSONCourseraParser jsonCourseraParser;
    private CourseraAPI courseraAPI;
    private EdxAPI edxAPI;
    private CourseRepository courseRepository;

    @Autowired
    public SearchService(JSONCourseraParser jsonCourseraParser, CourseraAPI courseraAPI, EdxAPI edxAPI,
                         CourseRepository courseRepository) {
        this.jsonCourseraParser = jsonCourseraParser;
        this.courseraAPI = courseraAPI;
        this.edxAPI = edxAPI;
        this.courseRepository = courseRepository;
    }

    // This method adds to cache search result
    @Async
    public void search(String value, String source) {
        String[] values;
        Searchable sourceAPI;
        // TODO fix string searching
        if (value.startsWith("\"") & value.endsWith("\"")) {
            values = new String[]{value}; // Search by full request string
        } else {
            values = value.split(SPACE); // Search by separate word
        }
        String[] sourceList = source.split(",");

        // TODO if sourceList empty - search at all resources
        for (String resource : sourceList) { // Cache to database each course from each selected resources
            if (resource.isEmpty() || resource.equals(",")) { // TODO fix
                continue;
            }
            sourceAPI = selectResource(Long.valueOf(resource));
            for (String request : values) {
                courseRepository.saveAll(jsonCourseraParser.parseCourseJSON(sourceAPI.find(request)));
            }
        }
    }

    private Searchable selectResource(Long resourceId) {
        switch (resourceId.toString()) {
            case "1": // Coursera
                return courseraAPI;
            case "2": // edX
                return edxAPI;
            case "3": // edX
                return null; // TODO add Udacity
            default:
                return null;
        }
    }
}
