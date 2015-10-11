package com.marcussjolin.calhacks;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends Activity {

    private SettingsActivity mActivity;
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
        mActivity = this;

        setContentView(R.layout.activity_settings);

        mFirstName = (EditText) findViewById(R.id.first_name);
        mLastName = (EditText) findViewById(R.id.last_name);
        mAddress = (EditText) findViewById(R.id.address);
        mState = (EditText) findViewById(R.id.state);
        mCity = (EditText) findViewById(R.id.city);
        mPhoneNumber = (EditText) findViewById(R.id.phone_number);
        mSaveButton = (Button) findViewById(R.id.btn_save);

        setSaveButton();
    }

    private void setSaveButton() {
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validFirst = checkFirstName();
                boolean validLast = checkLastName();
                boolean validAddress = checkAddress();
                boolean validState = checkState();
                boolean validCity = checkCity();
                boolean validPhone = checkPhoneNumber();

                boolean isValid = validFirst && validLast && validAddress && validState && validCity && validPhone;

                if (isValid) {
                    // TODO Save address.
                } else {
                    int message;
                    if (!validFirst) {
                        message = R.string.valid_first;
                    } else if (!validLast) {
                        message = R.string.valid_last;
                    } else if (!validAddress) {
                        message = R.string.valid_address;
                    } else if (!validState) {
                        message = R.string.valid_state;
                    } else if (!validCity) {
                        message = R.string.valid_city;
                    } else {
                        message = R.string.valid_phone;
                    }
                    Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean checkFirstName() {
        String first = mFirstName.getText().toString();
        return !first.equals("");
    }

    private boolean checkLastName() {
        String first = mLastName.getText().toString();
        return !first.equals("");
    }

    private boolean checkAddress() {
        String first = mAddress.getText().toString();
        return !first.equals("");
    }

    private boolean checkState() {
        String first = mState.getText().toString();
        return !first.equals("");
    }

    private boolean checkCity() {
        String first = mCity.getText().toString();
        return !first.equals("");
    }

    private boolean checkPhoneNumber() {
        String first = mPhoneNumber.getText().toString();
        return !first.equals("");
    }

}
