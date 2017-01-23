package com.yourl.service;

import org.springframework.stereotype.Service;

import com.yourl.controller.dto.UrlInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class InMemoryUrlStoreService implements IUrlStoreService{
    private Map<String, UrlInfo> urlByIdMap = new ConcurrentHashMap<String,UrlInfo>();

    @Override
    public UrlInfo findUrlById(String id) {
        return urlByIdMap.get(id);
    }

    @Override
    public void storeUrl(String id, UrlInfo url) {
        urlByIdMap.put(id, url);
    }
}
