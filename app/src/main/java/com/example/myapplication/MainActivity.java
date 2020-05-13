package com.example.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.wxy.skin_library.base.SkinBaseActivity;
import com.wxy.skin_library.mode.SkinManager;

public class MainActivity extends SkinBaseActivity {

    @Override
    public void loadLayout() {
        setContentView(R.layout.activity_main);

        findViewById(R.id.text2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main.class);
                startActivity(intent);
            }
        });
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
