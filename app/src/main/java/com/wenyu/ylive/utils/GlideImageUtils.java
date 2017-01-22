package com.wenyu.ylive.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

public class GlideImageUtils {

	public static void setRoundImage(Context context, ImageView view, String url, int placeHolderResId) {
		Glide.with(context).load(url).transform(new CircleTransform(context)).crossFade().placeholder(placeHolderResId).into(view);
	}

	public static void setRoundImage(Context context, ImageView view, String url) {
		Glide.with(context).load(url).transform(new CircleTransform(context)).crossFade().into(view);
	}

	public static void setRoundImage(Context context, ImageView view, Uri uri) {
		Glide.with(context).load(uri).transform(new CircleTransform(context)).crossFade().into(view);
	}

	public static void setRoundImage(Context context, ImageView view, String url, float radius, int placeHolderResId) {
		Glide.with(context).load(url).transform(new RoundTransform(context, radius)).crossFade().placeholder(placeHolderResId).into(view);
	}

	public static void setImage(Context context, ImageView view, String url, int placeHolderResId) {
		Glide.with(context).load(url).crossFade().placeholder(placeHolderResId).into(view);
	}

	public static void setImage(Context context, ImageView view, String url) {
		Glide.with(context).load(url).crossFade().into(view);
	}

	public static void setImage(Context context, ImageView view, List<String> urls) {
		if (urls != null && !urls.isEmpty()) {
			Glide.with(context).load(urls.get(0)).crossFade().into(view);
		}
	}

	public static void setImage(Context context, ImageView view, List<String> urls, int placeHolderResId) {
		if (urls != null && !urls.isEmpty()) {
			Glide.with(context).load(urls.get(0)).crossFade().placeholder(placeHolderResId).into(view);
		}
	}

	public static void setImageWithCache(Context context, ImageView view, List<String> urls, int placeHolderResId) {
		if (urls != null && !urls.isEmpty()) {
			Glide.with(context).load(urls.get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().placeholder(placeHolderResId).into(view);
		}
	}

	public static void setImage(Context context, ImageView view, List<String> urls, final OnResourceReadyListener listener) {
		if (urls != null && !urls.isEmpty()) {
			Glide.with(context).load(urls.get(0)).crossFade().into(new GlideDrawableImageViewTarget(view) {
				@Override
				public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
					super.onResourceReady(resource, animation);
					if (listener != null) {
						listener.onReady();
					}
				}
			});
		}
	}

	public static void setImage(Context context, ImageView view, List<String> urls, int placeHolderResId, final OnResourceReadyListener listener) {
		if (urls != null && !urls.isEmpty()) {
			Glide.with(context).load(urls.get(0)).crossFade().placeholder(placeHolderResId).into(new GlideDrawableImageViewTarget(view) {
				@Override
				public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
					super.onResourceReady(resource, animation);
					if (listener != null) {
						listener.onReady();
					}
				}
			});
		}
	}

	public static void setImage(Context context, ImageView view, Uri uri, int placeHolderResId) {
		Glide.with(context)
				.load(uri)
				.crossFade()
				.placeholder(placeHolderResId)
				.into(view);
	}

	public static void setImage(Context context, ImageView view, String url, boolean crossFade) {
		if (crossFade) {
			Glide.with(context).load(url).crossFade().into(view);
		} else {
			Glide.with(context).load(url).dontAnimate().into(view);
		}
	}

	public static void setImage(Context context, ImageView view, String url, BitmapTransformation transformation, boolean crossFade) {
		if (crossFade) {
			Glide.with(context).load(url).transform(transformation).into(view);
		} else {
			Glide.with(context).load(url).dontAnimate().transform(transformation).into(view);
		}
	}

	public static void setImage(Context context, ImageView view, String url, final Callback callback) {
		Glide.with(context).load(url).crossFade().into(new GlideDrawableImageViewTarget(view) {
			@Override
			public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
				super.onResourceReady(resource, animation);
				if (callback != null) {
					callback.onResourceReady();
				}
			}
		});
	}

	public static void loadBitmap(Context context, String url, int width, int height, final BitmapLoadCallback callback) {
		Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>(width, height) {
			@Override
			public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
				callback.onBitmapReady(resource);
			}
		});
	}

	public static void setBlurImage(Context context, ImageView view, String url, int placeHolderResId) {
		Glide.with(context).load(url).transform(new BlurTransform(context)).crossFade().placeholder(placeHolderResId).into(view);
	}

	public static void setBlurImage(Context context, ImageView view, String url) {
		Glide.with(context).load(url).transform(new BlurTransform(context)).crossFade().into(view);
	}

	public interface Callback {
		void onResourceReady();
	}

	// 异步下载图片时的回调
	public interface BitmapLoadCallback {
		void onBitmapReady(Bitmap bitmap);
	}

	private static class BlurTransform extends BitmapTransformation {

		public BlurTransform(Context context) {
			super(context);
		}

		public Bitmap blur(Bitmap source, BitmapPool bitmapPool) {
			if (source == null) {
				return null;
			}

			float scaleFactor = 1f;
			float radius = 10;

			Bitmap overlay = bitmapPool.get((int) (source.getWidth() / scaleFactor), (int) (source.getHeight() / scaleFactor),
					Bitmap.Config.ARGB_8888);
			if (overlay == null) {
				overlay = Bitmap.createBitmap((int) (source.getWidth() / scaleFactor), (int) (source.getHeight() / scaleFactor),
						Bitmap.Config.ARGB_8888);
			}

			Canvas canvas = new Canvas(overlay);
			canvas.scale(scaleFactor, scaleFactor);

			ColorMatrix cMatrix = new ColorMatrix();
			int brightness = 20;
			cMatrix.setSaturation(1.0f);
			cMatrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});

			Paint paint = new Paint();
			paint.setFlags(Paint.FILTER_BITMAP_FLAG);
			paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
			canvas.drawBitmap(source, 0, 0, paint);

			overlay = FastBlur.doBlur(overlay, (int) radius, true);

			return overlay;
		}

		@Override
		protected Bitmap transform(BitmapPool bitmapPool, Bitmap bitmap, int i, int i1) {
			return blur(bitmap, bitmapPool);
		}

		@Override
		public String getId() {
			return getClass().getName();
		}

	}

	public static class CircleTransform extends BitmapTransformation {
		public CircleTransform(Context context) {
			super(context);
		}

		private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
			if (source == null)
				return null;

			int size = Math.min(source.getWidth(), source.getHeight());
			int x = (source.getWidth() - size) / 2;
			int y = (source.getHeight() - size) / 2;

			// TODO this could be acquired from the pool too
			Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

			Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
			if (result == null) {
				result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
			}

			Canvas canvas = new Canvas(result);
			Paint paint = new Paint();
			paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
			paint.setAntiAlias(true);
			float r = size / 2f;
			canvas.drawCircle(r, r, r, paint);
			return result;
		}

		@Override
		protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
			return circleCrop(pool, toTransform);
		}

		@Override
		public String getId() {
			return getClass().getName();
		}
	}

	public static class BlackWhiteTransform extends BitmapTransformation {

		public BlackWhiteTransform(Context context) {
			super(context);
		}

		@Override
		protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
			return convertToBlackWhite(pool, toTransform);
		}

		public Bitmap convertToBlackWhite(BitmapPool pool, Bitmap source) {
			float scaleFactor = 1f;
			Bitmap overlay = pool.get((int) (source.getWidth() / scaleFactor), (int) (source.getHeight() / scaleFactor), Bitmap.Config.ARGB_8888);
			if (overlay == null) {
				overlay = Bitmap.createBitmap((int) (source.getWidth() / scaleFactor), (int) (source.getHeight() / scaleFactor),
						Bitmap.Config.ARGB_8888);
			}
			Canvas c = new Canvas(overlay);
			Paint paint = new Paint();
			ColorMatrix cm = new ColorMatrix();
			cm.setSaturation(0);
			ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
			paint.setColorFilter(f);
			c.drawBitmap(source, 0, 0, paint);
			return overlay;
		}

		@Override
		public String getId() {
			return getClass().getName();
		}
	}

	public interface OnResourceReadyListener {
		void onReady();
	}

	public static class RoundTransform extends BitmapTransformation {

		private static float radius = 0f;

		public RoundTransform(Context context, float radius) {
			super(context);
			this.radius = radius;
		}

		private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
			if (source == null)
				return null;

			Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
			if (result == null) {
				result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
			}

			Canvas canvas = new Canvas(result);
			Paint paint = new Paint();
			paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
			paint.setAntiAlias(true);
			RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
			canvas.drawRoundRect(rectF, radius, radius, paint);
			return result;
		}

		@Override
		protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
			return roundCrop(pool, toTransform);
		}

		@Override
		public String getId() {
			return getClass().getName();
		}
	}

	// ---------- safe load ----------

	public static void setRoundImage(RequestManager requestManager, Context context, ImageView imageView, String url) {
		requestManager.load(url).transform(new CircleTransform(context)).crossFade().into(imageView);
	}
}
