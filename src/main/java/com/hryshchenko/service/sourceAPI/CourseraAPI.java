package com.hryshchenko.service.sourceAPI;

import com.hryshchenko.util.URLexecutor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CourseraAPI implements Searchable {

    public static final String COURSE_LINK = "https://www.coursera.org/";
    private static final Long SOURCE_ID = 1L;
    private static final String searchByValue = "https://api.coursera.org/api/courses.v1?q=search&query=";
    private URLexecutor urlExecutor;

    @Autowired
    public CourseraAPI(URLexecutor urlExecutor) {
        this.urlExecutor = urlExecutor;
    }

    @Override
    public JSONObject find(String value) {
        try {
            return urlExecutor.askURL(buildSearchLink(value));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String buildSearchLink(String request) {
        return addFields(searchByValue + request);
    }

    private String addFields(String link) {
        return new StringBuilder(link)
                .append("&limit=5") // TODO change limit
                .append("&fields=")
                .append("name,")
                .append("primaryLanguages,")
                .append("photoUrl,")
                .append("description,")
                .append("startDate")
                .toString();
    }

    @Override
    public Long getId() {
        return SOURCE_ID;
    }
}
