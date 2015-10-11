package com.marcussjolin.calhacks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmActivity extends Activity {

    private TextView mFirstName;
    private TextView mLastName;
    private TextView mAddress;
    private TextView mState;
    private TextView mCity;
    private TextView mPhoneNumber;
    private Button mEditButton;
    private Button mConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirm);

        mFirstName = (TextView) findViewById(R.id.first_name);
        mLastName = (TextView) findViewById(R.id.last_name);
        mAddress = (TextView) findViewById(R.id.address);
        mState = (TextView) findViewById(R.id.state);
        mCity = (TextView) findViewById(R.id.city);
        mPhoneNumber = (TextView) findViewById(R.id.phone_number);

        mEditButton = (Button) findViewById(R.id.btn_edit);
        mConfirmButton = (Button) findViewById(R.id.btn_confirm);

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
