package com.hryshchenko.util.Parser;

import com.hryshchenko.model.dto.CourseDTO;
import org.json.JSONObject;

import java.util.List;

public interface Parsable {

    public List<CourseDTO> parseCourseJSON(JSONObject object);
}
