package com.hryshchenko.service.sourceAPI;

import com.hryshchenko.util.URLexecutor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EdxAPI implements Searchable {

    public static final Long SOURCE_ID = 2L;
    public static final String COURSE_LINK = "https://www.edx.org/course/";
    private static final String searchByValue = "https://www.edx.org/api/v1/catalog/search?query=";

    private URLexecutor urlExecutor;

    @Autowired
    public EdxAPI(URLexecutor urlExecutor) {
        this.urlExecutor = urlExecutor;
    }

    @Override
    public JSONObject find(String value) {
        try {
            return urlExecutor.askURL(searchByValue + value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long getId() {
        return SOURCE_ID;
    }

}
