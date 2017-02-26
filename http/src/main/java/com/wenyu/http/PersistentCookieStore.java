package com.wenyu.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PersistentCookieStore {

	private static final String TAG = PersistentCookieStore.class.getSimpleName();

	// Persistence
	private static final String SP_COOKIE_STORE = "sb_cookie_store";
	private static final String SP_KEY_DELIMITER = "|"; // Unusual char in URL
	private static final String SP_KEY_DELIMITER_REGEX = "\\" + SP_KEY_DELIMITER;

	// In memory
	private Map<URI, Set<YLiveCookie>> allCookies;

	// In disk
	private SharedPreferences sharedPreferences;

	private static PersistentCookieStore instance;

	public static synchronized PersistentCookieStore getIntance(Context context) {
		if (instance == null) {
			instance = new PersistentCookieStore(context);
		}
		return instance;
	}

	private PersistentCookieStore(Context context) {
		sharedPreferences = context.getSharedPreferences(SP_COOKIE_STORE, Context.MODE_PRIVATE);
		loadAllFromPersistence();
	}

	private void loadAllFromPersistence() {
		allCookies = new HashMap<URI, Set<YLiveCookie>>();

		Map<String, ?> allPairs = sharedPreferences.getAll();
		for (Map.Entry<String, ?> entry : allPairs.entrySet()) {
			String[] uriAndName = entry.getKey().split(SP_KEY_DELIMITER_REGEX, 2);
			try {
				URI uri = new URI(uriAndName[0]);
				String encodedCookie = (String) entry.getValue();
				YLiveCookie cookie = new SerializableHttpCookie().decode(encodedCookie);

				Set<YLiveCookie> targetCookies = allCookies.get(uri);
				if (targetCookies == null) {
					targetCookies = new HashSet<>();
					allCookies.put(uri, targetCookies);
				}
				targetCookies.add(cookie);
			} catch (Exception e) {
				Log.w(TAG, e);
			}
		}
	}

	public synchronized void add(URI uri, YLiveCookie cookie) {
		if (uri == null || cookie == null || TextUtils.isEmpty(cookie.name()) || TextUtils.isEmpty(cookie.value())) {
			return;
		}

		uri = cookieUri(uri, cookie);
		Set<YLiveCookie> targetCookies = allCookies.get(uri);
		if (targetCookies == null) {
			targetCookies = new HashSet<>();
			allCookies.put(uri, targetCookies);
		}
		targetCookies.remove(cookie);
		targetCookies.add(cookie);

		saveToPersistence(uri, cookie);
	}

	private static URI cookieUri(URI uri, YLiveCookie cookie) {
		URI cookieUri = uri;
		if (cookie.domain() != null) {
			// Remove the starting dot character of the domain, if exists (e.g:.domain.com -> domain.com)
			String domain = cookie.domain();
			if (domain.charAt(0) == '.') {
				domain = domain.substring(1);
			}
			try {
				cookieUri = new URI(uri.getScheme() == null ? "http" : uri.getScheme(), domain, cookie.path() == null ? "/" : cookie.path(), null);
			} catch (Exception e) {
				Log.w(TAG, e);
			}
		}
		return cookieUri;
	}

	private void saveToPersistence(URI uri, YLiveCookie cookie) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(uri.toString() + SP_KEY_DELIMITER + cookie.name(), new SerializableHttpCookie().encode(cookie));
		editor.apply();
	}

	public synchronized List<YLiveCookie> get(URI uri) {
		return getValidCookies(uri);
	}

	private List<YLiveCookie> getValidCookies(URI uri) {
		List<YLiveCookie> targetCookies = new ArrayList<>();
		// If the stored URI does not have a path then it must match any URI in the same domain
		for (URI storedUri : allCookies.keySet()) {
			// Check ith the domains match according to RFC 6265
			if (checkDomainsMatch(storedUri.getHost(), uri.getHost())) {
				// Check if the paths match according to RFC 6265
				if (checkPathsMatch(storedUri.getPath(), uri.getPath())) {
					targetCookies.addAll(allCookies.get(storedUri));
				}
			}
		}

		// Check it there are expired cookies and remove them
		if (!targetCookies.isEmpty()) {
			List<YLiveCookie> cookiesToRemoveFromPersistence = new ArrayList<>();
			for (Iterator<YLiveCookie> it = targetCookies.iterator(); it.hasNext(); ) {
				YLiveCookie currentCookie = it.next();
				if (currentCookie != null && currentCookie.expiresAt() < System.currentTimeMillis()) {
					cookiesToRemoveFromPersistence.add(currentCookie);
					it.remove();
				}
			}

			if (!cookiesToRemoveFromPersistence.isEmpty()) {
				removeFromPersistence(uri, cookiesToRemoveFromPersistence);
			}
		}
		return targetCookies;
	}

	private boolean checkDomainsMatch(String cookieHost, String requestHost) {
		return requestHost.equals(cookieHost) || requestHost.endsWith("." + cookieHost);
	}

	private boolean checkPathsMatch(String cookiePath, String requestPath) {
		return requestPath.equals(cookiePath) || (requestPath.startsWith(cookiePath) && cookiePath.charAt(cookiePath.length() - 1) == '/')
				|| (requestPath.startsWith(cookiePath) && requestPath.substring(cookiePath.length()).charAt(0) == '/');
	}

	private void removeFromPersistence(URI uri, List<YLiveCookie> cookiesToRemove) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		for (YLiveCookie cookieToRemove : cookiesToRemove) {
			editor.remove(uri.toString() + SP_KEY_DELIMITER + cookieToRemove.name());
		}
		editor.apply();
	}

	public synchronized boolean remove(URI uri, YLiveCookie cookie) {
		Set<YLiveCookie> targetCookies = allCookies.get(uri);
		boolean cookieRemoved = (targetCookies != null && targetCookies.remove(cookie));
		if (cookieRemoved) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.remove(uri.toString() + SP_KEY_DELIMITER + cookie.name());
			editor.apply();
		}
		return cookieRemoved;
	}

	public synchronized boolean removeAll() {
		allCookies.clear();
		sharedPreferences.edit().clear().apply();
		return true;
	}

	public synchronized List<YLiveCookie> getCookies() {
		List<YLiveCookie> allValidCookies = new ArrayList<>();
		for (URI storedUri : allCookies.keySet()) {
			allValidCookies.addAll(getValidCookies(storedUri));
		}

		return allValidCookies;

	}
}