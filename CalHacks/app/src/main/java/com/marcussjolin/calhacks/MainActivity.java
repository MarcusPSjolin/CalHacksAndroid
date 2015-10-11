package com.marcussjolin.calhacks;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;


public class MainActivity extends Activity {

    private Context mContext;

    public static String NEW_USER = "is_new_user";
    public static String CURRENT_ITEM = "current_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        // Hide action bar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setContentView(R.layout.activity_main);

        RelativeLayout background = (RelativeLayout) findViewById(R.id.main_background);
        AnimationDrawable animation = (AnimationDrawable) background.getBackground();
        animation.start();

        setStoreButton();
        setGetButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideStatusBar();
    }

    private void hideStatusBar() {
        // Hide status bar
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void setStoreButton() {
        Button storeButton = (Button) findViewById(R.id.store_button);

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StoreStuffActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setGetButton() {
        Button storeButton = (Button) findViewById(R.id.get_button);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isNewUser = sharedPrefs.getBoolean(NEW_USER, true);

        if (isNewUser) {
            storeButton.setVisibility(View.GONE);
            sharedPrefs.edit().putBoolean(NEW_USER, false).apply();
        } else {
            storeButton.setVisibility(View.VISIBLE);
            storeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GetStuffActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
