package com.hryshchenko.component;

import com.hryshchenko.model.event.SearchRequest;
import com.hryshchenko.service.search.SearchService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ServiceEventListener {

    private SearchService searchService;

    public ServiceEventListener(SearchService searchService) {
        this.searchService = searchService;
    }

    @EventListener
    public void handleSearchRequest(SearchRequest searchRequest) {
        searchService.search(searchRequest.getValue(), searchRequest.getSource());
    }

}
