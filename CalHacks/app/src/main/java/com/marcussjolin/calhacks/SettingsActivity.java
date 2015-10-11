package com.marcussjolin.calhacks;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingsActivity extends Activity {

    public static final String FIRST_USE = "first_use";

    private boolean mIsFirstUse = false;

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

        mIsFirstUse = getIntent().getBooleanExtra(FIRST_USE, false);

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
                    RequestQueue queue = Volley.newRequestQueue(mActivity);

                    StringBuilder builder = new StringBuilder();
                    builder.append(CalHacksApplication.URL);
                    builder.append("users/");
                    builder.append(CalHacksApplication.USER_ID);

                    JSONObject object = new JSONObject();
                    try {
                        object.put(Constants.Address.first_name, mFirstName.getText().toString());
                        object.put(Constants.Address.last_name, mLastName.getText().toString());
                        object.put(Constants.Address.address, mAddress.getText().toString());
                        object.put(Constants.Address.state, mState.getText().toString());
                        object.put(Constants.Address.city, mCity.getText().toString());
                        object.put(Constants.Address.phone_number, mPhoneNumber.getText().toString());
                    } catch (JSONException e) {
                        Log.e("TAG", "JSONException " + e);
                    }

                    Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (mIsFirstUse) {
                                Intent resultIntent = new Intent(mActivity, ItemDescriptionActivity.class);
                                setResult(RESULT_OK, resultIntent);
                                finish();
                            } else {
                                onBackPressed();
                            }
                        }
                    };

                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("TAG", "onErrorResponse error = " + error);
                        }
                    };

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                            builder.toString(), object, responseListener, errorListener);

                    queue.add(jsonObjectRequest);
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
