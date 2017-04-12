package com.hryshchenko.service.source;

import com.hryshchenko.model.entity.Source;
import com.hryshchenko.repository.source.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceService {

    private SourceRepository sourceRepository;

    @Autowired
    public SourceService(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    public List<Source> getAll() {
        return sourceRepository.getAll();
    }
}
