package com.wenyu.ylive.net.core;

import android.text.TextUtils;

import okhttp3.Cookie;

public class YLiveCookie {

	private Cookie cookie;

	public YLiveCookie(Cookie cookie) {
		this.cookie = cookie;
	}

	public Cookie getCookie() {
		return cookie;
	}

	public String name() {
		return cookie.name();
	}

	public String domain() {
		return cookie.domain();
	}

	public String path() {
		return cookie.path();
	}

	public String value() {
		return cookie.value();
	}

	public long expiresAt() {
		return cookie.expiresAt();
	}

	public boolean secure() {
		return cookie.secure();
	}

	public boolean httpOnly() {
		return cookie.httpOnly();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}

		if (obj instanceof YLiveCookie) {
			YLiveCookie that = (YLiveCookie) obj;
			return TextUtils.equals(this.name(), that.name())
					&& TextUtils.equals(this.domain(), that.domain())
					&& TextUtils.equals(this.path(), that.path());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hash = 17;
		hash = hashCode(hash, this.name());
		hash = hashCode(hash, this.domain());
		hash = hashCode(hash, this.path());
		return hash;
	}

	private int hashCode(final int seed, final Object object) {
		return seed * 37 + (object != null ? object.hashCode() : 0);
	}
}
