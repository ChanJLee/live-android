package com.wenyu.network;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import okhttp3.Cookie;

public class SerializableHttpCookie implements Serializable {

	private static final String TAG = SerializableHttpCookie.class.getSimpleName();
	private static final long serialVersionUID = 6374381323722046732L;

	private transient YLiveCookie cookie;

	public SerializableHttpCookie() {
	}

	public String encode(YLiveCookie cookie) {
		this.cookie = cookie;

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(os);
			outputStream.writeObject(this);
		} catch (IOException e) {
			Log.d(TAG, "IOException in encodeCookie", e);
			return null;
		}

		return byteArrayToHexString(os.toByteArray());
	}

	public YLiveCookie decode(String encodedCookie) {
		byte[] bytes = hexStringToByteArray(encodedCookie);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
		YLiveCookie cookie = null;
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			cookie = ((SerializableHttpCookie) objectInputStream.readObject()).cookie;
		} catch (IOException e) {
			Log.d(TAG, "IOException in decodeCookie", e);
		} catch (ClassNotFoundException e) {
			Log.d(TAG, "ClassNotFoundException in decodeCookie", e);
		}

		return cookie;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(cookie.name());
		out.writeObject(cookie.value());
		out.writeObject(cookie.domain());
		out.writeLong(cookie.expiresAt());
		out.writeObject(cookie.path());
		out.writeBoolean(cookie.secure());
		out.writeBoolean(cookie.httpOnly());
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		String name = (String) in.readObject();
		String value = (String) in.readObject();
		String domain = (String) in.readObject();
		long expiresAt = in.readLong();
		String path = (String) in.readObject();
		boolean secure = in.readBoolean();
		boolean httpOnly = in.readBoolean();

		Cookie.Builder builder = new Cookie.Builder();
		builder = builder.name(name);
		builder = builder.value(value);
		builder = builder.domain(domain);
		builder = builder.expiresAt(expiresAt);
		builder = builder.path(path);
		builder = httpOnly ? builder.httpOnly() : builder;
		builder = secure ? builder.secure() : builder;
		cookie = new YLiveCookie(builder.build());
	}

	/**
	 * Using some super basic byte array &lt;-&gt; hex conversions so we don't
	 * have to rely on any large Base64 libraries. Can be overridden if you
	 * like!
	 *
	 * @param bytes byte array to be converted
	 * @return string containing hex values
	 */
	private String byteArrayToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte element : bytes) {
			int v = element & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString();
	}

	/**
	 * Converts hex values from strings to byte array
	 *
	 * @param hexString string of hex-encoded values
	 * @return decoded byte array
	 */
	private byte[] hexStringToByteArray(String hexString) {
		int len = hexString.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
		}
		return data;
	}
}
