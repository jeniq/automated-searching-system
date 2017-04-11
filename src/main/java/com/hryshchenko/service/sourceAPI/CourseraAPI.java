package com.hryshchenko.service.sourceAPI;

import com.hryshchenko.util.URLexecutor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CourseraAPI implements Searchable {

    @Autowired
    private URLexecutor urlExecutor;

    public CourseraAPI(URLexecutor urlExecutor) {
        this.urlExecutor = urlExecutor;
    }

    private static final String searchByValue = "https://api.coursera.org/api/courses.v1?q=search&query=";

    @Override
    public JSONObject find(String value) {
        try {
            return urlExecutor.askURL(buildSearchLink(value));
        } catch (IOException e) {
            new RuntimeException(e);
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

}
