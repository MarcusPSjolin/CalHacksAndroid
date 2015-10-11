package com.marcussjolin.calhacks;

import android.os.Bundle;
import android.app.Activity;
import android.widget.GridView;

public class GetStuffActivity extends Activity {

    GridView mGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_stuff);

        mGrid = (GridView) findViewById(R.id.grid);
        mGrid.setAdapter(new GridAdapter(this));
    }

}
