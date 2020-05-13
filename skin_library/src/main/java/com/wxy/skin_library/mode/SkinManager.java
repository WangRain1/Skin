package com.wxy.skin_library.mode;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.wxy.skin_library.skinview.SkinViewManager;

import java.lang.reflect.Method;

public class SkinManager {

    private static final SkinManager MANAGER = new SkinManager();

    public static SkinManager getInstance() {
        return MANAGER;
    }

    Context mContext;

    Resources mResources;

    boolean isChangeSkin = false;

    public SkinManager from(Context context) {
        mContext = context.getApplicationContext();
        return this;
    }

    public void parserApk(String path) {
        if (null == mContext) {
            return;
        }
        AssetManager assets = null;
        try {
            assets = AssetManager.class.newInstance();
            Method m = AssetManager.class.getMethod("addAssetPath",String.class);
            m.invoke(assets,path);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mResources = new Resources(assets, mContext.getResources().getDisplayMetrics(), mContext.getResources().getConfiguration());
    }

    public void changeSkin(){
        setChangeSkin(!isChangeSkin());
        SkinViewManager.getInstance().updateViews();
    }

    public Resources getResources() {
        return mResources;
    }

    public Context getContext() {
        return mContext;
    }

    public boolean isChangeSkin() {
        return isChangeSkin;
    }

    public void setChangeSkin(boolean changeSkin) {
        isChangeSkin = changeSkin;
    }
}
