package com.hryshchenko.service.sourceAPI;

import com.hryshchenko.model.dto.SearchDTO;
import com.hryshchenko.util.URLexecutor;
import com.hryshchenko.util.parser.html.HTMLPrometheusParser;
import com.hryshchenko.util.parser.html.HTMLtedParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrometheusAPI implements Searchable{

    public static final Long SOURCE_ID = 4L;
    public static final String COURSE_LINK = "https://prometheus.org.ua/";
    private static final String searchByValue = "https://prometheus.org.ua/courses/";

    private HTMLPrometheusParser htmlPrometheusParser;

    @Autowired
    public PrometheusAPI(HTMLPrometheusParser htmlPrometheusParser) {
        this.htmlPrometheusParser = htmlPrometheusParser;
    }

    @Override
    public JSONObject find(String value) {
            return htmlPrometheusParser.parseHtml(searchByValue, value);
    }

    @Override
    public JSONObject find(SearchDTO request) {
        return null;
    }

    @Override
    public Long getId() {
        return SOURCE_ID;
    }
}
