package com.hryshchenko.util.weight;

import com.hryshchenko.model.dto.SearchDTO;
import com.hryshchenko.model.entity.Course;
import com.hryshchenko.model.weight.Language;
import com.hryshchenko.model.weight.Relevance;
import com.hryshchenko.model.weight.Source;

import java.util.Comparator;
import java.util.List;

public class Weight {

    public List<Course> define(List<Course> courses, SearchDTO searchDTO) {
        courses.forEach(course -> course.setWeight(calculate(searchDTO, course, courses.size())));
        courses.sort(Comparator.comparing(Course::getWeight).reversed()); // Descending sort
        return courses;
    }

    private double calculate(SearchDTO searchDTO, Course course, int size) {
        double languageValue = 0;
        double sourceValue = Source.valueOf(course.getSource().getName().toUpperCase()).getValue();
        if (course.getLanguage() != null) {
            languageValue = Language.valueOf(course.getLanguage().getLanguage().toUpperCase()).getValue();
        }
        double relevanceValue = relevance(course, searchDTO);
        return (sourceValue + languageValue + relevanceValue) / size;
    }

    private double relevance(Course course, SearchDTO searchDTO) {
        double titleValue = 0;
        double descriptionValue = 0;

        for (String request : searchDTO.getRequest().split(" ")) {
            if (course.getName().contains(request)) {
                if (course.getName().matches(".\\s" + course.getName() + "\\s.")) { // Exact word in title
                    titleValue += Relevance.EXACT_TITLE.getValue();
                } else {
                    titleValue += Relevance.TITLE.getValue();
                }
            }
            if (course.getDescription().contains(request)) {
                if (course.getName().matches(".\\s" + course.getName() + "\\s.")) { // Exact word in title
                    descriptionValue += Relevance.EXACT_DESCRIPTION.getValue();
                } else {
                    descriptionValue += Relevance.DESCRIPTION.getValue();
                }
            }
        }
        return titleValue + descriptionValue;
    }
}
