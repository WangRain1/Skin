package com.wxy.skin_library.skinview.ViewType;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wxy.skin_library.mode.SkinManager;
import com.wxy.skin_library.skinview.SkinAttrBean;
import com.wxy.skin_library.utils.SkinConfig;

public abstract class AbstractType implements Type {

    Resources resources = SkinManager.getInstance().getResources();

    Context context = SkinManager.getInstance().getContext();

    public int buildSkinAttrsBean(SkinAttrBean skB, int c) {
        skB.setTypeName(context.getResources().getResourceTypeName(c));
        skB.setEntryName(context.getResources().getResourceEntryName(c));
        return getResourcesId(context.getResources().getResourceEntryName(c),
                context.getResources().getResourceTypeName(c));
    }

    public int getResourcesId(String entry, String type) {
        return getTargetResources().getIdentifier(entry, type, context.getPackageName());
    }

    public Resources getTargetResources() {
        return SkinManager.getInstance().isChangeSkin() ? resources : context.getResources();
    }

    public SkinAttrBean setBg(AttributeSet attr, View view) {
        SkinAttrBean skBg = new SkinAttrBean();
        int b = attr.getAttributeResourceValue(SkinConfig.NS_RESOURCES, "background", 0);
        try {
            Drawable drawable = getTargetResources().getDrawable(buildSkinAttrsBean(skBg, b));
            view.setBackground(drawable);
        } catch (Resources.NotFoundException e) {
            Log.e("======", "==bbbbb===" + e);
            return null;
        }
        return skBg;
    }

}
