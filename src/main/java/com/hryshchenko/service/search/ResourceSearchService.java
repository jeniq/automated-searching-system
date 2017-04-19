package com.hryshchenko.service.search;

import com.hryshchenko.model.dto.SearchDTO;
import com.hryshchenko.repository.course.CourseRepository;
import com.hryshchenko.repository.language.LanguageRepository;
import com.hryshchenko.repository.source.SourceRepository;
import com.hryshchenko.service.sourceAPI.Searchable;
import com.hryshchenko.util.parser.Parsable;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class ResourceSearchService {

    private CourseRepository courseRepository;
    private LanguageRepository languageRepository;
    private SourceRepository sourceRepository;

    @Autowired
    public ResourceSearchService(CourseRepository courseRepository, LanguageRepository languageRepository, SourceRepository sourceRepository) {
        this.courseRepository = courseRepository;
        this.languageRepository = languageRepository;
        this.sourceRepository = sourceRepository;
    }

    @Async
    public Future<Boolean> searchValue(SearchDTO searchDTO, Long sourceId, Parsable parser, Searchable sourceAPI) {
        for (String request : searchDTO.getRequest().split(" ")) {
            Optional<JSONObject> jsonObject = Optional.ofNullable(sourceAPI.find(request));
            if (jsonObject.isPresent()) { // Avoid repeating courses in database
                courseRepository.saveAll(parser.parseCourseJSON(jsonObject.get())
                        .stream()
                        .filter(c -> c != null)
                        .filter(c -> courseRepository.getCourseBySourceId(c.getCourseSourceId()) == null)
                        .peek(c -> c.setSource(sourceRepository.getById(sourceId)))
                        .collect(Collectors.toList())
                );
            }
        }
        return new AsyncResult<>(true); // indicate that method is done
    }
}
