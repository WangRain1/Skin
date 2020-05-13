package com.example.myapplication;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.wxy.skin_library.base.SkinBaseActivity;
import com.wxy.skin_library.mode.SkinManager;

public class Main extends SkinBaseActivity {

    @Override
    public void loadLayout() {
        setContentView(R.layout.main);
        ImageView view = findViewById(R.id.imge);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("====onClick===","====text===" + v.getId());
                SkinManager.getInstance().changeSkin();
            }
        });

    }
}
