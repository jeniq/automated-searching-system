package com.hryshchenko.util.Parser;

import com.hryshchenko.model.dto.CourseDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JSONUdacityParser implements Parsable{

    @Override
    public List<CourseDTO> parseCourseJSON(JSONObject object) {
        List<CourseDTO> courses = new ArrayList<>();
        CourseDTO courseDTO;

        JSONArray array = object.getJSONArray("courses");

        for (int i = 0; i < array.length(); i++) {
            courseDTO = new CourseDTO();
            courseDTO.setName(array.getJSONObject(i).getString("title"));
            // courseDTO.setDescription(array.getJSONObject(i).getString("summary"));
            courseDTO.setDescription("Check for long description"); // TODO long description causes error in db
            courseDTO.setPictureUrl(array.getJSONObject(i).getJSONArray("image").getJSONObject(1).getString("small"));
            courses.add(courseDTO);
        }

        return courses;
    }
}
