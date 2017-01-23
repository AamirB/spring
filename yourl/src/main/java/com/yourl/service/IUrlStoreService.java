package com.yourl.service;

import com.yourl.controller.dto.UrlInfo;

public interface IUrlStoreService {
	UrlInfo findUrlById(String id);

    void storeUrl(String id, UrlInfo url);
}
