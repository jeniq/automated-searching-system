package com.hryshchenko.service.sourceAPI;

import com.hryshchenko.model.dto.SearchDTO;
import org.json.JSONObject;

public interface Searchable {
    JSONObject find(String value);
    JSONObject find(SearchDTO request);
    Long getId();
}
