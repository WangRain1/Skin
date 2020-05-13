package com.wxy.skin_library.skinview.ViewType;

import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.wxy.skin_library.skinview.SkinAttrBean;
import com.wxy.skin_library.utils.SkinConfig;

public class ImageViewType extends AbstractType {

    Type mType;

    public ImageViewType(Type type) {
        mType = type;
    }

    @Override
    public SkinAttrBean[] checkType(View view, AttributeSet attr) {
        if (view instanceof ImageView) {
            SkinAttrBean[] skbs = new SkinAttrBean[2];
            SkinAttrBean skBSrc = new SkinAttrBean();
            int src = attr.getAttributeResourceValue(SkinConfig.NS_RESOURCES, "src", 0);
            ((ImageView) view).setImageDrawable(getTargetResources().getDrawable(buildSkinAttrsBean(skBSrc, src)));
            skbs[0] = skBSrc;
            skbs[1] = setBg(attr,view);
            return skbs;

        } else {
            return mType.checkType(view, attr);
        }
    }

    @Override
    public void checkType(View v, SkinAttrBean[] skBs) {
        if (v instanceof ImageView){
            if (null != skBs[0]) {
                ((ImageView) v).setImageDrawable(getTargetResources().getDrawable(getResourcesId(skBs[0]
                        .getEntryName(),skBs[0].getTypeName())));
            }
            if (null != skBs[1]){
                ((ImageView) v).setBackground(getTargetResources().getDrawable(getResourcesId(skBs[1].getEntryName(),
                        skBs[1].getTypeName())));
            }
        } else {
            mType.checkType(v,skBs);
        }
    }
}
