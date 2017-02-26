package com.wenyu.network;

import android.content.Context;
import android.text.TextUtils;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CsrfTokenInterceptor implements Interceptor {

	private Context mContext;
	private String mDomain;

	public CsrfTokenInterceptor(Context context, String domain) {
		mContext = context;
		mDomain = domain;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request original = chain.request();
		String csrfToken = getCsrfToken();
		if (TextUtils.isEmpty(csrfToken)) {
			return chain.proceed(original);
		}

		Request request = original.newBuilder()
				.header("X-CSRFToken", csrfToken)
				.build();
		return chain.proceed(request);
	}

	private String getCsrfToken() {
		String csrf = "";

		PersistentCookieStore cookieStore = PersistentCookieStore.getIntance(mContext);
		List<YLiveCookie> cookieList = cookieStore.get(URI.create(mDomain));
		for (YLiveCookie cookie : cookieList) {
			if (cookie != null && TextUtils.equals(cookie.name(), "csrftoken")) {
				csrf = cookie.value();
				break;
			}
		}

		return csrf;
	}
}