package com.wenyu.media.media;

import android.content.Context;
import android.support.annotation.IntDef;

import com.wenyu.media.cview.IRenderView;
import com.wenyu.media.cview.SurfaceRenderView;
import com.wenyu.media.cview.TextureRenderView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by chan on 17/4/13.
 */

public class RenderBuilder {
    public static final int RENDER_NONE = 0;
    public static final int RENDER_SURFACE_VIEW = 1;
    public static final int RENDER_TEXTURE_VIEW = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {RENDER_NONE, RENDER_SURFACE_VIEW, RENDER_TEXTURE_VIEW})
    public @interface RenderType {

    }

    @RenderType
    private int mRenderType = RENDER_SURFACE_VIEW;
    private Context mContext;

    public RenderBuilder(Context context) {
        mContext = context;
    }

    public RenderBuilder() {
    }

    public RenderBuilder setRenderType(@RenderType int renderType) {
        mRenderType = renderType;
        return this;
    }

    public RenderBuilder setContext(Context context) {
        mContext = context;
        return this;
    }

    public IRenderView build() {
        switch (mRenderType) {
            case RENDER_NONE:
                return null;
            case RENDER_TEXTURE_VIEW:
                return new TextureRenderView(mContext);
            case RENDER_SURFACE_VIEW:
                return new SurfaceRenderView(mContext);
        }
        return null;
    }
}
