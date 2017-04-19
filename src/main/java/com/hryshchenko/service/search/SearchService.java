package com.hryshchenko.service.search;

import com.hryshchenko.model.dto.SearchDTO;
import com.hryshchenko.service.sourceAPI.CourseraAPI;
import com.hryshchenko.service.sourceAPI.EdxAPI;
import com.hryshchenko.service.sourceAPI.Searchable;
import com.hryshchenko.service.sourceAPI.TedAPI;
import com.hryshchenko.util.parser.JSONCourseraParser;
import com.hryshchenko.util.parser.JSONEdxParser;
import com.hryshchenko.util.parser.JSONTedParser;
import com.hryshchenko.util.parser.Parsable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class SearchService {
    private JSONCourseraParser jsonCourseraParser;
    private JSONEdxParser jsonEdxParser;
    private JSONTedParser jsonTedParser;
    private CourseraAPI courseraAPI;
    private EdxAPI edxAPI;
    private TedAPI tedAPI;
    private Searchable sourceAPI;
    private Parsable parser;
    private ResourceSearchService resourceSearchService;

    @Autowired
    public SearchService(JSONCourseraParser jsonCourseraParser, JSONEdxParser jsonEdxParser, JSONTedParser jsonTedParser,
                         CourseraAPI courseraAPI, EdxAPI edxAPI, TedAPI tedAPI,
                         ResourceSearchService resourceSearchService) {
        this.jsonCourseraParser = jsonCourseraParser;
        this.jsonEdxParser = jsonEdxParser;
        this.jsonTedParser = jsonTedParser;
        this.courseraAPI = courseraAPI;
        this.edxAPI = edxAPI;
        this.tedAPI = tedAPI;
        this.resourceSearchService = resourceSearchService;
    }

    @Async
    public Future<Boolean> search(SearchDTO searchDTO) {
        Collection<Future<Boolean>> search = new ArrayList<>();
        for (Long sourceId : searchDTO.getSources()) {
            selectResource(sourceId);
            search.add(resourceSearchService.searchValue(searchDTO, sourceId, parser, sourceAPI));
        }
        search.forEach(done -> {
            try {
                done.get(); // Wait for all threads
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        return new AsyncResult<>(true); // indicate that method is done
    }

    private synchronized Searchable selectResource(Long resourceId) {
        switch (resourceId.toString()) {
            case "1": // Coursera
                sourceAPI = courseraAPI;
                parser = jsonCourseraParser;
                return courseraAPI;
            case "2": // edX
                sourceAPI = edxAPI;
                parser = jsonEdxParser;
                return edxAPI;
            case "3":
                sourceAPI = tedAPI;
                parser = jsonTedParser;
                return tedAPI;
            default:
                throw new RuntimeException("Source not defined");
        }
    }

}
