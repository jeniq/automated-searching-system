package com.hryshchenko.service.sourceAPI;

import org.json.JSONObject;

public interface Searchable {
    JSONObject find(String value);
    Long getId();
}
