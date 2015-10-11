package com.marcussjolin.calhacks;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class GetStuffActivity extends Activity {

    GridView mGrid;
    private GetStuffActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_stuff);

        mGrid = (GridView) findViewById(R.id.grid);
        mGrid.setAdapter(new GridAdapter(this));
    }

    private void getItems() {
        RequestQueue queue = Volley.newRequestQueue(mActivity);
        StringBuilder builder = new StringBuilder();
        builder.append(CalHacksApplication.URL);
        builder.append("items/");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, builder.toString(), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        // Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest);
    }

    private void setPostOnResponse() {

    }


}
