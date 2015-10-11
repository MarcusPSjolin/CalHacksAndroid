package com.marcussjolin.calhacks;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;

public class PickupInformationActivity extends Activity {

    private Map mMap = null;
    private MapFragment mMapFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                    Log.d("TAG", "Exception - " + error);
                }
            }
        });
    }
}
