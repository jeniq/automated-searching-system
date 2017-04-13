package com.hryshchenko.service.sourceAPI;

import com.hryshchenko.util.URLexecutor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UdemyAPI implements Searchable {

    public static final Long SOURCE_ID = 3L;
    private static final String searchByValue = "https://www.udacity.com/public-api/v0/courses";

    private URLexecutor urlExecutor;

    @Autowired
    public UdemyAPI(URLexecutor urlExecutor) {
        this.urlExecutor = urlExecutor;
    }

    @Override
    public JSONObject find(String value) {
        try {
            return urlExecutor.askURL(searchByValue);
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
