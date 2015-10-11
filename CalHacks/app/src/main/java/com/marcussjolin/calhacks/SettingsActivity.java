package com.marcussjolin.calhacks;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends Activity {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mAddress;
    private EditText mState;
    private EditText mCity;
    private EditText mPhoneNumber;
    private Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mFirstName = (EditText) findViewById(R.id.first_name);
        mLastName = (EditText) findViewById(R.id.last_name);
        mAddress = (EditText) findViewById(R.id.address);
        mState = (EditText) findViewById(R.id.state);
        mCity = (EditText) findViewById(R.id.city);
        mPhoneNumber = (EditText) findViewById(R.id.phone_number);
        mSaveButton = (Button) findViewById(R.id.btn_save);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}
