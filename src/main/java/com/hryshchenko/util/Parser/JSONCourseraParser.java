package com.hryshchenko.util.Parser;

import com.hryshchenko.model.dto.CourseDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class JSONCourseraParser implements Parsable{

    // Coursera JSON
    public List<CourseDTO> parseCourseJSON(JSONObject object) {
        List<CourseDTO> courses = new ArrayList<>();
        CourseDTO courseDTO;

        JSONArray array = object.getJSONArray("elements");

        for (int i = 0; i < array.length(); i++) {
            courseDTO = new CourseDTO();
            courseDTO.setName(array.getJSONObject(i).getString("name"));
            // courseDTO.setDescription(array.getJSONObject(i).getString("description"));
            courseDTO.setDescription("Check for long description"); // TODO long description causes error in db
            courseDTO.setPictureUrl(array.getJSONObject(i).getString("photoUrl"));
            courseDTO.setStartTime(
                    new Timestamp(array.getJSONObject(i).getLong("startDate")));
            courses.add(courseDTO);
        }

        return courses;
    }
}
