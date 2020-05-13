package com.wxy.skin_library.base;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.wxy.skin_library.Inflater.SkinInflaterDelegate;
import com.wxy.skin_library.skinview.SkinView;
import com.wxy.skin_library.skinview.SkinViewManager;

public abstract class SkinBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new SkinInflaterDelegate(getWindow(),
                new SkinView(this.toString())).inflater(getLayoutInflater());
        super.onCreate(savedInstanceState);
        loadLayout();
        Log.e("========", "===id==" + this.toString());
    }

    public abstract void loadLayout();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinViewManager.getInstance().clearViews(this.toString());
    }
}
