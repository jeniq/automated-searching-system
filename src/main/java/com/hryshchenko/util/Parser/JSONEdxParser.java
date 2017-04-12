package com.hryshchenko.util.Parser;

import com.hryshchenko.model.dto.CourseDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JSONEdxParser implements Parsable {

    @Override
    public List<CourseDTO> parseCourseJSON(JSONObject object) {
        List<CourseDTO> courses = new ArrayList<>();
        CourseDTO courseDTO;

        JSONArray array = object.getJSONArray("results");

        for (int i = 0; i < array.length(); i++) {
            courseDTO = new CourseDTO();
            courseDTO.setName(array.getJSONObject(i).getString("name"));
            // courseDTO.setDescription(array.getJSONObject(i).getString("short_description"));
            courseDTO.setDescription("Check for long description"); // TODO long description causes error in db
            courseDTO.setPictureUrl(array.getJSONObject(i).getJSONArray("image").getJSONObject(1).getString("small"));
           /* courseDTO.setStartTime(
                    new Timestamp(array.getJSONObject(i).getString("start")));*/
            courses.add(courseDTO);
        }

        return courses;
    }
}
