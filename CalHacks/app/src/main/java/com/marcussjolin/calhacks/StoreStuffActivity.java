package com.marcussjolin.calhacks;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

public class StoreStuffActivity extends Activity {

    public static final String SIZE_SELECTION = "size_selection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_stuff);

        boolean isSizeSelection = getIntent().getBooleanExtra(SIZE_SELECTION, false);

        if (isSizeSelection) {
            StoreStuffSizeFragment fragment = new StoreStuffSizeFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(fragment, StoreStuffSizeFragment.STORE_STUFF_SIZE_FRAGMENT);
            fragmentTransaction.commit();
        }
    }
}
