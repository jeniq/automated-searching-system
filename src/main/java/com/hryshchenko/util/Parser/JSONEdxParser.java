package com.hryshchenko.util.parser;

import com.hryshchenko.model.dto.CourseDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.hryshchenko.util.parser.JSONCourseraParser.DESCRIPTION_LENGTH;

@Component
public class JSONEdxParser implements Parsable {

    @Override
    public List<CourseDTO> parseCourseJSON(JSONObject object) {
        List<CourseDTO> courses = new ArrayList<>();
        CourseDTO courseDTO;

        JSONArray array = object.getJSONObject("objects").getJSONArray("results");

        for (int i = 0; i < array.length(); i++) {
            courseDTO = new CourseDTO();
            courseDTO.setName(array.getJSONObject(i).getString("title"));
            String description = array.getJSONObject(i).getString("short_description");
            description = description.length() > DESCRIPTION_LENGTH ? description.substring(0, 300) + "..." : description;
            courseDTO.setDescription(description);
            courseDTO.setPictureUrl(array.getJSONObject(i).getString("image_url"));
           /* courseDTO.setStartTime(
                    new Timestamp(array.getJSONObject(i).getString("start"))); // API return string date
            courseDTO.setEndTime(
                    new Timestamp(array.getJSONObject(i).getLong("end")));*/
            courseDTO.setCourseSourceId(array.getJSONObject(i).getString("key"));
            courseDTO.setCourseLink(array.getJSONObject(i).getString("marketing_url"));
            courses.add(courseDTO);
        }

        return courses;
    }
}
