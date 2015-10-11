package com.marcussjolin.calhacks;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StoreStuffSizeFragment extends Fragment {

    public static final String STORE_STUFF_SIZE_FRAGMENT = "store_stuff_size_fragment";

    private StoreStuffActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = (StoreStuffActivity) getActivity();
        setToolBarTitle(mActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_stuff_size, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setToolBarTitle(Activity activity) {
        String title = getString(R.string.pick_size);
        activity.setTitle(title);
    }

}
