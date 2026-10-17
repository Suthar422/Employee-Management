package com.infyme.bookseat.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class CacheInspectionService {

    @Autowired
    private CacheManager cacheManager;

//    public CacheInspectionService(CacheManager cacheManager) {
//        this.cacheManager = cacheManager;
//    }

    public void inspectCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        log.info("Inspecting cache: " + cacheName);
        if (cache != null) {
            System.out.println("Cache name: " + cache.getName());
            System.out.println(Objects.requireNonNull(cache.getNativeCache()).toString());
        }
        else{
            System.out.println("Cache not found : " + cacheName);
        }
    }
}
