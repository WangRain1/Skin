package com.wxy.skin_library.skinview.ViewType;

import android.util.AttributeSet;
import android.view.View;

import com.wxy.skin_library.skinview.SkinAttrBean;

public class CustomViewType extends AbstractType {

    @Override
    public SkinAttrBean[] checkType(View view, AttributeSet attr) {
//        if (view instanceof TextView) {
//
//        } else {
//            Log.e("view type","unknown view type " + view);
//        }
        return null;
    }

    @Override
    public void checkType(View v, SkinAttrBean[] skBs) {

    }
}
