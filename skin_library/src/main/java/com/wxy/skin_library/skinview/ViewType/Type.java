package com.wxy.skin_library.skinview.ViewType;

import android.util.AttributeSet;
import android.view.View;

import com.wxy.skin_library.skinview.SkinAttrBean;

public interface Type {

    SkinAttrBean[] checkType(View view, AttributeSet attr);

    void checkType(View v, SkinAttrBean[] skBs);
}
