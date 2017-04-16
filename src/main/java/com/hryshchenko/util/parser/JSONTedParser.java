package com.hryshchenko.util.parser;

import com.hryshchenko.model.dto.CourseDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.hryshchenko.service.sourceAPI.TedAPI.COURSE_LINK;
import static com.hryshchenko.util.parser.JSONCourseraParser.DESCRIPTION_LENGTH;

@Component
public class JSONTedParser implements Parsable {
    @Override
    public List<CourseDTO> parseCourseJSON(JSONObject object) {
        List<CourseDTO> courses = new ArrayList<>();
        CourseDTO courseDTO;

        JSONArray array = object.getJSONArray("elements");

        for (int i = 0; i < array.length(); i++) {
            courseDTO = new CourseDTO();
            courseDTO.setName(array.getJSONObject(i).getString("title"));
            String description = array.getJSONObject(i).getString("description");
            description = description.length() > DESCRIPTION_LENGTH ? description.substring(0, 300) + "..." : description;
            courseDTO.setDescription(description);
            courseDTO.setPictureUrl(array.getJSONObject(i).getString("image"));
            courseDTO.setCourseSourceId(array.getJSONObject(i).getString("source_id"));
            courseDTO.setCourseLink(COURSE_LINK + array.getJSONObject(i).getString("link"));
            courses.add(courseDTO);
        }

        return courses;
    }
}
