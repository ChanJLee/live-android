package com.wenyu.ylive.net.core;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class YLiveCookieJar implements CookieJar {

	private final PersistentCookieStore cookieStore;

	public YLiveCookieJar(PersistentCookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}

	@Override
	public void saveFromResponse(HttpUrl httpUrl, List<Cookie> cookieList) {
		if (cookieList != null && cookieList.size() > 0) {
			for (Cookie cookie : cookieList) {
				Log.d("chan_debug", cookie.toString());
				cookieStore.add(httpUrl.uri(), new YLiveCookie(cookie));
			}
		}
	}

	@Override
	public List<Cookie> loadForRequest(HttpUrl httpUrl) {
		List<Cookie> targetCookies = new ArrayList<>();
		for (YLiveCookie cookie : cookieStore.get(httpUrl.uri())) {
			targetCookies.add(cookie.getCookie());
		}
		return targetCookies;
	}
}
