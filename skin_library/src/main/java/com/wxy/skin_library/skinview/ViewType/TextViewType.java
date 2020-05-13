package com.wxy.skin_library.skinview.ViewType;

import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wxy.skin_library.skinview.SkinAttrBean;
import com.wxy.skin_library.utils.SkinConfig;

public class TextViewType extends AbstractType {

    Type mType;

    public TextViewType(Type type) {
        mType = type;
    }

    @Override
    public SkinAttrBean[] checkType(View view, AttributeSet attr) {
        if (view instanceof TextView) {
            SkinAttrBean[] skbs = new SkinAttrBean[2];
            SkinAttrBean skBColor = new SkinAttrBean();
            int c = attr.getAttributeResourceValue(SkinConfig.NS_RESOURCES,"textColor",0);
            ((TextView) view).setTextColor(getTargetResources().getColor(buildSkinAttrsBean(skBColor,c)));
            skbs[0] = skBColor;
            skbs[1] = setBg(attr,view);
            return skbs;
        } else {
            return mType.checkType(view,attr);
        }
    }

    @Override
    public void checkType(View v, SkinAttrBean[] skBs) {
        if (v instanceof TextView){
            if (null != skBs[0]) {
                ((TextView) v).setTextColor(getTargetResources()
                        .getColor(getResourcesId(skBs[0].getEntryName(),skBs[0].getTypeName())));
            }
            if (null != skBs[1]){
                try {
                    ((TextView) v).setBackground(getTargetResources().getDrawable(getResourcesId(skBs[1].getEntryName(),
                            skBs[1].getTypeName())));
                } catch (Resources.NotFoundException e) {
                    Log.e("======", "==bbbbb===" + e);
                }
            }
        } else {
            mType.checkType(v,skBs);
        }
    }
}
