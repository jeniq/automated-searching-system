package com.hryshchenko.util.weight;

import com.hryshchenko.model.dto.SearchDTO;
import com.hryshchenko.model.entity.Course;
import com.hryshchenko.model.weight.Language;
import com.hryshchenko.model.weight.Relevance;
import com.hryshchenko.model.weight.Source;
import com.hryshchenko.model.weight.View;
import com.hryshchenko.service.course.CourseViewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class Weight {

    private CourseViewsService courseViewsService;
    private Long maxViews;

    @Autowired
    public Weight(CourseViewsService courseViewsService) {
        this.courseViewsService = courseViewsService;
    }

    public List<Course> define(List<Course> courses, SearchDTO searchDTO) {
        maxViews = courseViewsService.getMaxViews();
        courses.forEach(course -> course.setWeight(calculate(searchDTO, course, courses.size())));
        courses.sort(Comparator.comparing(Course::getWeight).reversed()); // Descending sort
        return courses;
    }

    private double calculate(SearchDTO searchDTO, Course course, int size) {
        double languageValue = 0;
        double sourceValue = Source.valueOf(course.getSource().getName().toUpperCase()).getValue();
        if (course.getLanguage() != null) {
            languageValue =
                    searchDTO.getLocale().equals(course.getLanguage().getLanguage().substring(0, 2).toLowerCase()) ?
                            Language.MATCH.getValue() :
                            Language.valueOf(course.getLanguage().getLanguage().toUpperCase()).getValue();
        }
        double relevanceValue = relevance(course, searchDTO);
        double popularityValue = 0;
        if (maxViews != null && maxViews > 0){
            popularityValue = popularity(course, maxViews);
        }
        return (sourceValue + languageValue + relevanceValue + popularityValue) / size;
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

    private double popularity(Course course, Long maxViews){
        if (course.getCourseView() != null){
            return ((1.0 * course.getCourseView().getViews()) / maxViews) * View.COEFFICIENT.getValue();
        }
        return 0;
    }

}
