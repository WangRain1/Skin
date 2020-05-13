package com.wxy.skin_library.skinview;

import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class SkinViewManager {

    private static final SkinViewManager MANAGER = new SkinViewManager();

    private Map<String, SkinView> views = new HashMap<>();

    public static SkinViewManager getInstance() {
        return MANAGER;
    }

    public void addSkinView(View view, AttributeSet attr, SkinView skinView) {
        if (null != view) {
            // 封装 skin view
            skinView.addSkinView(view,attr);
            views.put(skinView.getId(),skinView);
        }
    }

    public void updateViews() {
        for (String key : views.keySet()){
            views.get(key).updateViews();
        }
    }

    public void clearViews(String id){
        views.remove(id);
    }
}
