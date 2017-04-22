package com.hryshchenko.util.parser;

import com.hryshchenko.model.dto.CourseDTO;
import com.hryshchenko.model.entity.Language;
import com.hryshchenko.repository.language.LanguageRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hryshchenko.service.sourceAPI.TedAPI.COURSE_LINK;
import static com.hryshchenko.util.parser.JSONCourseraParser.DESCRIPTION_LENGTH;

@Component
public class JSONCommonParser implements Parsable {

    private LanguageRepository languageRepository;

    @Autowired
    public JSONCommonParser(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

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
            courseDTO.setCourseLink(array.getJSONObject(i).getString("link"));
            String lang = array.getJSONObject(i).getString("language");
            if (lang != null) {
                Optional<Language> language = languageRepository.getLanguageByAbbr(lang);
                if (language.isPresent()) {
                    courseDTO.setLanguage(language.get());
                } else {
                    courseDTO.setLanguage(new Language(4L)); // Other language
                }
            }
            courses.add(courseDTO);
        }

        return courses;
    }
}
