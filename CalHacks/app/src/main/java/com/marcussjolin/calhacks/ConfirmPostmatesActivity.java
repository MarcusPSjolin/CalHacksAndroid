package com.marcussjolin.calhacks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ConfirmPostmatesActivity extends Activity {

    public static final String ID = "id";
    public static final String FEE = "fee";
    public static final String CURRENCY = "currency";
    public static final String DURATION = "duration";

    private static final String SPACE = " ";

    private ConfirmPostmatesActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;

        setContentView(R.layout.activity_confirm_postmates);
        setConfirmButton();

        Intent intent = getIntent();
        int fee = intent.getIntExtra(FEE, 0) + 10;
        String currency = intent.getStringExtra(CURRENCY);
        String duration = intent.getStringExtra(DURATION);

        TextView price = (TextView) findViewById(R.id.confirmation_price);
        TextView eta = (TextView) findViewById(R.id.confirmation_eta);

        String priceString = getString(R.string.price) + SPACE + fee + SPACE + currency;
        String etaString = getString(R.string.eta) + SPACE + duration + SPACE + getString(R.string.minutes);

        price.setText(priceString);
        eta.setText(etaString);
    }

    private void setConfirmButton() {
        Button button = (Button) findViewById(R.id.looks_good);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPost();
            }
        });
    }

    private void setPost() {
        RequestQueue queue = Volley.newRequestQueue(mActivity);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mActivity);

        StringBuilder builder = new StringBuilder();
        builder.append(CalHacksApplication.URL);
        builder.append("items/");
        builder.append(sharedPrefs.getString(MainActivity.CURRENT_ITEM, ""));
        builder.append("/pickup");

        JSONObject object = new JSONObject();
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Intent intent = new Intent(mActivity, PickupInformationActivity.class);
                startActivity(intent);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse error = " + error);
            }
        };

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                builder.toString(), object, responseListener, errorListener);

        queue.add(jsonObjectRequest);
    }

}
