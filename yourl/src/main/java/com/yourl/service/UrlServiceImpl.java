package com.yourl.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.yourl.controller.dto.ShortUrlResponse;
import com.yourl.controller.dto.UrlInfo;

@Service
public class UrlServiceImpl implements UrlService {
	
	 @Autowired
	    private IUrlStoreService urlStoreService;

	@Override
	public ShortUrlResponse shortenUrl(String url, String redirectionType) {
		
		ShortUrlResponse res=new ShortUrlResponse();
		
		try{
//TODO set the account to list f URLINFO maapping
        final String id = Hashing.murmur3_32()
            .hashString(url, StandardCharsets.UTF_8).toString();
        UrlInfo urlInfo=urlStoreService.findUrlById(id);
        if(urlInfo==null){
        	urlInfo=new UrlInfo();
        	urlInfo.setCount(1);
        	urlInfo.setUrl(url);
        	 urlStoreService.storeUrl(id, urlInfo);
        }else{
        urlInfo.setCount(urlInfo.getCount()+1);
        }
        
        StringBuilder sb=new StringBuilder();
        String urlFinal=sb.append("http://").append("urlShort/").append(id).toString();
//        String requestUrl = url.toString();
//        String prefix = requestUrl.substring(0, requestUrl.indexOf(url,
//            "http://".length()));
//        System.out.println(prefix);
        urlStoreService.storeUrl(urlFinal, urlInfo);
        res.setShortUrl(urlFinal);
        
        
		}catch(Exception ex){
			
		}
		
		return res;
	}



	
}
