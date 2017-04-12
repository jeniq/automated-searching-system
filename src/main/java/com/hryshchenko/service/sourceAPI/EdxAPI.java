package com.hryshchenko.service.sourceAPI;

import com.hryshchenko.util.URLexecutor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EdxAPI implements Searchable {

    private static final String searchByValue = "https://courses.edx.org/api/courses/v1/courses/";

    private URLexecutor urlExecutor;

    public EdxAPI(URLexecutor urlExecutor) {
        this.urlExecutor = urlExecutor;
    }

    @Override
    public JSONObject find(String value) {
        try {
            return urlExecutor.askURL(searchByValue);
        } catch (IOException e) {
            new RuntimeException(e);
        }
        return null;
    }

}
