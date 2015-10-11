package com.marcussjolin.calhacks;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapMarker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

public class PickupInformationActivity extends Activity {

    private Map mMap = null;
    private MapFragment mMapFragment = null;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://calhacks.paulashbourne.com");
        } catch (URISyntaxException e) {
            // No-op... Don't really care
        }
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String name;
                    long lng;
                    long lat;
                    long pickupMillis;
                    long dropoffMillis;
                    try {
                        name = data.getJSONObject(Constants.PostmatesUpdates.courier)
                                .getString(Constants.PostmatesUpdates.courier);
                        lng = data.getLong(Constants.PostmatesUpdates.lng);
                        lat = data.getLong(Constants.PostmatesUpdates.lat);
                        pickupMillis = data.getLong(Constants.PostmatesUpdates.pickup_millis);
                        dropoffMillis = data.getLong(Constants.PostmatesUpdates.dropoff_millis);
                    } catch (JSONException e) {
                        return;
                    }

                    // add the message to view
                    updateView(name, pickupMillis, dropoffMillis);
                    updateMap(lat, lng);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSocket.on("new message", onNewMessage);
        mSocket.connect();

        setContentView(R.layout.confirmed_pickup_layout);

        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        mMapFragment.init(new OnEngineInitListener() {
            @Override
            public void onEngineInitializationCompleted(OnEngineInitListener.Error error) {
                if (error == OnEngineInitListener.Error.NONE) {
                    // retrieve a reference of the map from the map fragment
                    mMap = mMapFragment.getMap();
                    // Set the map center to the Vancouver region (no animation)
                    mMap.setCenter(new GeoCoordinate(49.196261, -123.004773, 0.0),
                            Map.Animation.NONE);
                    // Set the zoom level to the average between min and max
                    mMap.setZoomLevel((mMap.getMaxZoomLevel() + mMap.getMinZoomLevel()) / 2);
                } else {
                    System.out.println("ERROR: Cannot initialize Map Fragment");
                }
            }
        });

        mSocket.emit("new message", CalHacksApplication.USER_ID);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off("new message", onNewMessage);
    }

    private void updateView(String name, long pickupMillis, long dropoffMillis) {
        TextView updateText = (TextView) findViewById(R.id.update_text);
        String str = name + " is coming!";

        if (pickupMillis != 0) {
            str += " ETA for pickup is " + pickupMillis/60000.0 + " minutes.";
        }

        if (dropoffMillis != 0) {
            str += " ETA for dropoff is " + dropoffMillis/60000.0 + " minutes.";
        }

        updateText.setText(str);
    }

    private void updateMap(long lat, long lng) {
        Image image = new Image();
        try {
            image.setImageResource(R.drawable.ic_person_pin_black_24dp);
        } catch (IOException e) {
            Log.d("TAG", "IOException");
        }
        MapMarker marker = new MapMarker(new GeoCoordinate(lat, lng), image);
        mMap.addMapObject(marker);
        mMap.setCenter(new GeoCoordinate(lat, lng, 0.0), Map.Animation.NONE);
        mMap.setZoomLevel((mMap.getMaxZoomLevel() + mMap.getMinZoomLevel()) / 2);
    }
}
