package com.hryshchenko.service.sourceAPI;

import com.hryshchenko.model.dto.SearchDTO;
import com.hryshchenko.repository.language.LanguageRepository;
import com.hryshchenko.util.parser.html.HTMLtedParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TedAPI implements Searchable {

    public static final Long SOURCE_ID = 3L;
    public static final String COURSE_LINK = "https://www.ted.com";
    private static final String searchByValue = "https://www.ted.com/search?cat=talks&q=";
    private static final String searchTalks = "https://www.ted.com/talks?q=";
    private HTMLtedParser htmlTedParser;
    private LanguageRepository languageRepository;

    @Autowired
    public TedAPI(HTMLtedParser htmlTedParser, LanguageRepository languageRepository) {
        this.htmlTedParser = htmlTedParser;
        this.languageRepository = languageRepository;
    }

    @Override
    public JSONObject find(String value) {
        return htmlTedParser.parseHtml(searchByValue + value);
    }

    @Override
    public JSONObject find(SearchDTO request) {
        String requestString = searchByValue + request.getRequest();
        if (request.getLanguage() != null) {
            requestString = searchTalks + request.getRequest();
            requestString += "&language=" + languageRepository.getById(request.getLanguage()).getAbbr();
        }
        return htmlTedParser.parseHtml(requestString);
    }

    @Override
    public Long getId() {
        return SOURCE_ID;
    }
}
