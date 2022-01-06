package com.daniil.courses.services;

import com.daniil.courses.models.StoreItem;
import com.daniil.courses.repositories.StoreItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilterServiceIml implements FilterService {

    StoreItemRepository storeItemRepository;

    @Autowired
    public FilterServiceIml(StoreItemRepository storeItemRepository) {
        this.storeItemRepository = storeItemRepository;
    }


    @Override
    public List<StoreItem> getAllItemsWithType(String type) {
        return storeItemRepository.findAll().stream()
                .filter(storeItem -> storeItem.getItem().getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public List<StoreItem> getAllItemsWithDriverConfiguration(String configuration) {
        return storeItemRepository.findAll().stream()
                .filter(storeItem -> storeItem.getItem().getDriverConfiguration().equals(configuration))
                .collect(Collectors.toList());
    }

    @Override
    public List<StoreItem> getAllItemsWithCPU(String CPU) {
        return storeItemRepository.findAll().stream()
                .filter(storeItem -> storeItem.getItem().getCPU().equals(CPU))
                .collect(Collectors.toList());
    }



    @Override
    public List<StoreItem> getAllWithReleaseDate(Date date) {
        return storeItemRepository.findAll().stream()
                .filter(storeItem -> storeItem.getItem().getReleaseDate().compareTo(date) > 0)
                .collect(Collectors.toList());
    }
}
