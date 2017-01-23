package com.yourl.controller;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yourl.controller.dto.ShortUrlResponse;
import com.yourl.controller.dto.UrlInfo;
import com.yourl.service.IUrlStoreService;
import com.yourl.service.UrlService;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/url")
public class UrlController {

	@Autowired
	private UrlService urlService;

	@Autowired
	private IUrlStoreService urlStoreService;

	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public void redirectToUrl(String id, HttpServletResponse resp) throws Exception {
		final UrlInfo url = urlStoreService.findUrlById(id);
		if (url != null) {
			resp.addHeader("Location", url.getUrl().toString());
			// resp.addHeader("Access-Control-Allow-Origin",
			// "http://localhost:8080/");
			resp.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with");
			resp.addHeader("Access-Control-Max-Age", "60");
			resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			resp.addHeader("Access-Control-Allow-Origin", "*");
			resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		} else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@RequestMapping(value = "/shorten/{redirectionType}", method = RequestMethod.POST)
	public ShortUrlResponse shortenUrl(String url, @PathVariable String redirectionType) {

		if (!isUrlValid(url)) {
			throw new RuntimeException("URL is not valid");
		}

		try {
			return urlService.shortenUrl(url, redirectionType);
		} catch (Exception ex) {
			throw new RuntimeException(
					ex.getMessage() != null ? ex.getMessage() : "Error occured in shortening the URL");
		}

	}

	private boolean isUrlValid(String url) {
		boolean valid = true;
		try {
			new URL(url);
		} catch (MalformedURLException e) {
			valid = false;
		}
		return valid;
	}
}
