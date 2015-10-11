package com.marcussjolin.calhacks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetStuffActivity extends Activity {

    GridView mGrid;
    private GetStuffActivity mActivity;
    //private GridAdapter mAdapter;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_get_stuff);

        mGrid = (GridView) findViewById(R.id.grid);
        getItems();
        mAdapter = new ArrayAdapter<String>(this, R.layout.grid_item, R.id.item_title);
        mGrid.setAdapter(mAdapter);
        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setPostOnResponse(view);
            }
        });
        Log.d("TAG", "onCreate");

    }

    private void getItems() {
        RequestQueue queue = Volley.newRequestQueue(mActivity);
        StringBuilder builder = new StringBuilder();
        builder.append(CalHacksApplication.URL);
        builder.append("items/");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, builder.toString(), new JSONObject(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", "RESPONSE = " + response);
                        try {
                            JSONArray array = response.toJSONArray(response.names());
                            ArrayList<String> strings = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                strings.add(i, array.getJSONObject(i).getString("title"));
                            }
                            Log.d("TAG", "Strings = " + strings.toString());
                            mAdapter.addAll(strings);
                            mAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Log.d("TAG", "Exception = " + e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("TAG", "onErrorResponse " + error);
                    }
                });

        // Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest);

    }

    private void setPostOnResponse(View view) {
        RequestQueue queue = Volley.newRequestQueue(mActivity);
        GridAdapter.ViewHolder holder = (GridAdapter.ViewHolder) view.getTag();
        String itemId = holder.title.getText().toString();

        StringBuilder builder = new StringBuilder();
        builder.append(CalHacksApplication.URL);
        builder.append("items/");
        builder.append(itemId);
        builder.append("/quotedropoff");

        JSONObject object = new JSONObject();
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", "onResponse");
                Intent intent = new Intent(mActivity, ConfirmPostmatesActivity.class);
                intent.putExtra(ConfirmPostmatesActivity.ID, response.optString(Constants.PostmatesResponse.id));
                intent.putExtra(ConfirmPostmatesActivity.FEE, response.optString(Constants.PostmatesResponse.fee));
                intent.putExtra(ConfirmPostmatesActivity.CURRENCY, response.optString(Constants.PostmatesResponse.currency));
                intent.putExtra(ConfirmPostmatesActivity.DURATION, response.optString(Constants.PostmatesResponse.duration));
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
