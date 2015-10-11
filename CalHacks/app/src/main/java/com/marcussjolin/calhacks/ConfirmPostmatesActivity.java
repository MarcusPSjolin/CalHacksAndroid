package com.marcussjolin.calhacks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class ConfirmPostmatesActivity extends Activity {

    private ConfirmPostmatesActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;

        setContentView(R.layout.activity_confirm_postmates);
        setConfirmButton();
    }

    private void setConfirmButton() {
        Button button = (Button) findViewById(R.id.looks_good);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
                boolean isNewUser = sharedPrefs.getBoolean(MainActivity.NEW_USER, true);
                if (isNewUser) {
                    Intent intent = new Intent(mActivity, SettingsActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mActivity, PickupInformationActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}
