package com.hryshchenko.util.parser;

import com.hryshchenko.model.dto.CourseDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.hryshchenko.service.sourceAPI.CourseraAPI.COURSE_LINK;

@Component
public class JSONCourseraParser implements Parsable {

    private final static int DESCRIPTION_LENGTH = 300;

    // Coursera JSON
    public List<CourseDTO> parseCourseJSON(JSONObject object) {
        List<CourseDTO> courses = new ArrayList<>();
        CourseDTO courseDTO;

        JSONArray array = object.getJSONArray("elements");

        for (int i = 0; i < array.length(); i++) {
            courseDTO = new CourseDTO();
            courseDTO.setName(array.getJSONObject(i).getString("name"));
            String description = array.getJSONObject(i).getString("description");
            description = description.length() > DESCRIPTION_LENGTH ? description.substring(0,300) + "..." : description;
            courseDTO.setDescription(description);
            //courseDTO.setDescription("Check for long description"); // TODO long description causes error in db
            courseDTO.setPictureUrl(array.getJSONObject(i).getString("photoUrl"));
            courseDTO.setStartTime(
                    new Timestamp(array.getJSONObject(i).getLong("startDate")));
            courseDTO.setCourseSourceId(array.getJSONObject(i).getString("id"));
            courseDTO.setCourseLink(COURSE_LINK + "learn/" + array.getJSONObject(i).getString("slug"));
            courses.add(courseDTO);
        }

        return courses;
    }
}
