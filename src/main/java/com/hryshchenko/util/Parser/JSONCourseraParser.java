package com.hryshchenko.util.parser;

import com.hryshchenko.model.dto.CourseDTO;
import com.hryshchenko.model.entity.Category;
import com.hryshchenko.model.entity.Language;
import com.hryshchenko.repository.category.CategoryRepository;
import com.hryshchenko.repository.language.LanguageRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hryshchenko.service.sourceAPI.CourseraAPI.COURSE_LINK;

@Component
public class JSONCourseraParser implements Parsable {

    public final static int DESCRIPTION_LENGTH = 300;
    private LanguageRepository languageRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public JSONCourseraParser(LanguageRepository languageRepository,
                              CategoryRepository categoryRepository) {
        this.languageRepository = languageRepository;
        this.categoryRepository = categoryRepository;
    }

    // Coursera JSON
    public List<CourseDTO> parseCourseJSON(JSONObject object) {
        List<CourseDTO> courses = new ArrayList<>();
        CourseDTO courseDTO;

        JSONArray array = object.getJSONArray("elements");

        for (int i = 0; i < array.length(); i++) {
            courseDTO = new CourseDTO();
            courseDTO.setName(array.getJSONObject(i).getString("name"));
            String description = array.getJSONObject(i).getString("description");
            description = description.length() > DESCRIPTION_LENGTH ? description.substring(0, 300) + "..." : description;
            courseDTO.setDescription(description);
            courseDTO.setPictureUrl(array.getJSONObject(i).getString("photoUrl"));
            courseDTO.setStartTime(
                    new Timestamp(array.getJSONObject(i).getLong("startDate")));
            courseDTO.setCourseSourceId(array.getJSONObject(i).getString("id"));
            String lang = array.getJSONObject(i).getJSONArray("primaryLanguages").getString(0);
            if (lang != null) {
                Optional<Language> language = languageRepository.getLanguageByAbbr(lang);
                if (language.isPresent()) {
                    courseDTO.setLanguage(language.get());
                } else {
                    courseDTO.setLanguage(new Language(4L)); // Other language
                }
            }
            String domainId = array.getJSONObject(i).getJSONArray("domainTypes").getJSONObject(0).getString("domainId");
            JSONArray domainArray = object.getJSONObject("paging").getJSONObject("facets").getJSONObject("domains").getJSONArray("facetEntries");
            for (int j = 0; j < domainArray.length(); j++) {
                if (domainArray.getJSONObject(j).getString("id").equals(domainId)) {
                    Optional<Category> category = categoryRepository.getCategoryByName(domainArray.getJSONObject(j).getString("name"));
                    if (category.isPresent()) {
                        courseDTO.setCategory(category.get());
                    } else {
                        courseDTO.setCategory(new Category(6L)); // Other categories
                    }
                    break;
                }
            }
            courseDTO.setCourseLink(COURSE_LINK + "learn/" + array.getJSONObject(i).getString("slug"));
            courses.add(courseDTO);
        }

        return courses;
    }
}
