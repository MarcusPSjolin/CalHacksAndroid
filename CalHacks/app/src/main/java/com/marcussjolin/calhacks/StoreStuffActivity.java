package com.marcussjolin.calhacks;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StoreStuffActivity extends Activity {

    private StoreStuffActivity mActivity;

    public enum LockerSize {
        SMALL,
        MEDIUM,
        LARGE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_store_stuff);
        setToolBarTitle();
        setButtons();
    }

    private void setToolBarTitle() {
        String title = getString(R.string.pick_size);
        setTitle(title);
    }

    private void setButtons() {
        Button smallButton = (Button) findViewById(R.id.button_small);
        Button mediumButton = (Button) findViewById(R.id.button_medium);
        Button largeButton = (Button) findViewById(R.id.button_large);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LockerSize size = LockerSize.SMALL;

                switch (v.getId()) {
                    case R.id.button_small:
                        size = LockerSize.SMALL;
                        break;
                    case R.id.button_medium:
                        size = LockerSize.MEDIUM;
                        break;
                    case R.id.button_large:
                        size = LockerSize.LARGE;
                        break;
                }

                Intent intent = new Intent(mActivity, ItemDescriptionActivity.class);
                intent.putExtra(ItemDescriptionActivity.LOCKER_SIZE, size);
                startActivity(intent);
            }
        };

        smallButton.setOnClickListener(onClickListener);
        mediumButton.setOnClickListener(onClickListener);
        largeButton.setOnClickListener(onClickListener);
    }
}
