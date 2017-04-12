package com.hryshchenko.service.sourceAPI;

import com.hryshchenko.util.URLexecutor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UdacityAPI implements Searchable {

    private static final String searchByValue = "https://www.udacity.com/public-api/v0/courses";

    private URLexecutor urlExecutor;

    @Autowired
    public UdacityAPI(URLexecutor urlExecutor) {
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
