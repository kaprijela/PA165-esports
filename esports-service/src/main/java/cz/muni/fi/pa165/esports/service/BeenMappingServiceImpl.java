package cz.muni.fi.pa165.esports.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.dozer.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 @author Elena Álvarez
 */
@Service
public class BeenMappingServiceImpl implements BeenMappingService {
    @Autowired
    private Mapper dozer;

    @Override
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(dozer.map(object, mapToClass));
        }
        return mappedCollection;
    }

    @Override
    public <T> T mapTo(Object u, Class<T> mapToClass) {
        return dozer.map(u,mapToClass);
    }

    @Override
    public Mapper getMapper() {
        return dozer;
    }
}