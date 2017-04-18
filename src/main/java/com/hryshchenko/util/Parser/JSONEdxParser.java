package com.hryshchenko.util.parser;

import com.hryshchenko.model.dto.CourseDTO;
import com.hryshchenko.model.entity.Language;
import com.hryshchenko.repository.language.LanguageRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.hryshchenko.util.parser.JSONCourseraParser.DESCRIPTION_LENGTH;

@Component
public class JSONEdxParser implements Parsable {

    private LanguageRepository languageRepository;

    @Autowired
    public JSONEdxParser(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

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
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(array.getJSONObject(i).getString("start"));
                courseDTO.setStartTime(new Timestamp(date.getTime()));
                date = sdf.parse(array.getJSONObject(i).getString("end"));
                courseDTO.setEndTime(
                        new Timestamp(date.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String lang = array.getJSONObject(i).getString("language");
            if (lang != null) {
                Optional<Language> language = languageRepository.getLanguageByAbbr(lang);
                if (language.isPresent()) {
                    courseDTO.setLanguage(language.get());
                } else {
                    courseDTO.setLanguage(new Language(4L)); // Other language
                }
            }
            courseDTO.setCourseSourceId(array.getJSONObject(i).getString("key"));
            courseDTO.setCourseLink(array.getJSONObject(i).getString("marketing_url"));
            courses.add(courseDTO);
        }

        return courses;
    }
}
