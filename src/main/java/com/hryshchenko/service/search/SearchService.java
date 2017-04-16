package com.hryshchenko.service.search;

import com.hryshchenko.model.entity.Source;
import com.hryshchenko.repository.course.CourseRepository;
import com.hryshchenko.service.sourceAPI.CourseraAPI;
import com.hryshchenko.service.sourceAPI.EdxAPI;
import com.hryshchenko.service.sourceAPI.Searchable;
import com.hryshchenko.service.sourceAPI.TedAPI;
import com.hryshchenko.util.parser.JSONCourseraParser;
import com.hryshchenko.util.parser.JSONEdxParser;
import com.hryshchenko.util.parser.JSONTedParser;
import com.hryshchenko.util.parser.Parsable;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SearchService {
    public static final String SPACE = " ";
    private JSONCourseraParser jsonCourseraParser;
    private JSONEdxParser jsonEdxParser;
    private JSONTedParser jsonTedParser;
    private CourseraAPI courseraAPI;
    private EdxAPI edxAPI;
    private TedAPI tedAPI;
    private CourseRepository courseRepository;

    @Autowired
    public SearchService(JSONCourseraParser jsonCourseraParser, JSONEdxParser jsonEdxParser, JSONTedParser jsonTedParser,
                         CourseraAPI courseraAPI, EdxAPI edxAPI, TedAPI tedAPI,
                         CourseRepository courseRepository) {
        this.jsonCourseraParser = jsonCourseraParser;
        this.jsonEdxParser = jsonEdxParser;
        this.jsonTedParser = jsonTedParser;
        this.courseraAPI = courseraAPI;
        this.edxAPI = edxAPI;
        this.tedAPI = tedAPI;
        this.courseRepository = courseRepository;
    }

    // This method adds to cache search result
    @Async
    public void search(String value, String source) {
        String[] values;
        Searchable sourceAPI;
        Parsable parser;
        // TODO fix string searching
        if (value.startsWith("\"") & value.endsWith("\"")) {
            values = new String[]{value.replace("\"", "")}; // Search by full request string
        } else {
            values = value.split(SPACE); // Search by separate word
        }
        String[] sourceList = source.split(",");

        if (sourceList.length == 1) { // Interrupt search without selected source
            return;
        }

        for (String resource : sourceList) { // Cache to database each course from each selected resources
            if (resource.isEmpty() || resource.equals(",")) { // TODO fix
                continue;
            }
            sourceAPI = selectResource(Long.valueOf(resource));
            parser = selectParser(Long.valueOf(resource));
            for (String request : values) {
                Optional<JSONObject> jsonObject = Optional.ofNullable(sourceAPI.find(request));
                if (jsonObject.isPresent()) { // Avoid repeating courses in database
                    courseRepository.saveAll(parser.parseCourseJSON(jsonObject.get())
                            .stream()
                            .filter(c -> c != null)
                            .filter(c -> courseRepository.getCourseBySourceId(c.getCourseSourceId()) == null)
                            .peek(c -> c.setSource(new Source(Long.valueOf(resource))))
                            .collect(Collectors.toList())
                    );
                }
            }
        }
    }

    private Searchable selectResource(Long resourceId) {
        switch (resourceId.toString()) {
            case "1": // Coursera
                return courseraAPI;
            case "2": // edX
                return edxAPI;
            default:
                return tedAPI;
        }
    }

    private Parsable selectParser(Long resourceId) {
        switch (resourceId.toString()) {
            case "1": // Coursera
                return jsonCourseraParser;
            case "2": // edX
                return jsonEdxParser;
            default:
                return jsonTedParser;
        }
    }
}
