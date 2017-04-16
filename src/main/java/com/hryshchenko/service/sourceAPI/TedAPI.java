package com.hryshchenko.service.sourceAPI;

import com.hryshchenko.util.parser.html.HTMLtedParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TedAPI implements Searchable {

    public static final Long SOURCE_ID = 3L;
    public static final String COURSE_LINK = "https://www.ted.com";
    private static final String searchByValue = "https://www.ted.com/search?cat=talks&q=";
    private HTMLtedParser htmlTedParser;

    @Autowired
    public TedAPI(HTMLtedParser htmlTedParser) {
        this.htmlTedParser = htmlTedParser;
    }

    @Override
    public JSONObject find(String value) {
        return htmlTedParser.parseHtml(searchByValue + value);
    }

    @Override
    public Long getId() {
        return SOURCE_ID;
    }
}
