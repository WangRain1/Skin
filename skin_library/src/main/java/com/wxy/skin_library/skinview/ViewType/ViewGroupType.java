package com.wxy.skin_library.skinview.ViewType;

import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wxy.skin_library.skinview.SkinAttrBean;

//TODO 当前 viewgroup 为null
public class ViewGroupType extends AbstractType{

    Type mType;

    public ViewGroupType(Type type) {
        mType = type;
    }

    @Override
    public SkinAttrBean[] checkType(View view, AttributeSet attr) {
        if (view instanceof ViewGroup) {
            SkinAttrBean[] skbs = new SkinAttrBean[1];
            skbs[0] = setBg(attr,view);
            return skbs;
        } else {
           return mType.checkType(view,attr);
        }
    }

    @Override
    public void checkType(View v, SkinAttrBean[] skBs) {
        if (v instanceof ViewGroup){
            if (null != skBs[0]) {
                ((LinearLayout) v).setBackground(getTargetResources().getDrawable(getResourcesId(skBs[0]
                        .getEntryName(),skBs[0].getTypeName())));
            }
        } else {
            mType.checkType(v,skBs);
        }
    }
}
