package com.wxy.skin_library.skinview;

import android.util.AttributeSet;
import android.view.View;

import com.wxy.skin_library.skinview.ViewType.CustomViewType;
import com.wxy.skin_library.skinview.ViewType.ImageViewType;
import com.wxy.skin_library.skinview.ViewType.TextViewType;
import com.wxy.skin_library.skinview.ViewType.ViewGroupType;

import java.util.HashMap;
import java.util.Map;

public class SkinView {

    private Map<View, SkinAttrBean[]> views = new HashMap<>();
    //activity id
    private String id;

    public SkinView(String id) {
        this.id = id;
        init();
    }

    public String getId() {
        return id;
    }

    private TextViewType textType;
    /**
     * 责任连模式==》减少 if else 判断
     */
    private void init() {
        /**
         *  检测view类型顺序：Text -> image -> group -> custom
         */
        CustomViewType customViewType = new CustomViewType();
        ViewGroupType groupType = new ViewGroupType(customViewType);
        ImageViewType imageType = new ImageViewType(groupType);
        textType = new TextViewType(imageType);
    }

    public void addSkinView(View view, AttributeSet attr) {
        if (null != view) {
            SkinAttrBean[] skBs = textType.checkType(view,attr);
            views.put(view,skBs);
        }
    }

    public void updateViews() {
        for (View v : views.keySet()){
            SkinAttrBean[] skBs = views.get(v);
            textType.checkType(v,skBs);
        }
    }
}
