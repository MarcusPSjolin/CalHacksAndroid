package com.marcussjolin.calhacks;

import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StoreStuffSizeFragment extends Fragment {

    public static final String STORE_STUFF_SIZE_FRAGMENT = "store_stuff_size_fragment";

    private StoreStuffActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = (StoreStuffActivity) getActivity();
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
        setSmallButton();
        setMediumButton();
        setLargeButton();
    }

    private void setSmallButton() {
        Button button = (Button) mActivity.findViewById(R.id.button_small);
        String title = getString(R.string.small);
        String subtitle = getString(R.string.small_subtext);
        button.setText(Html.fromHtml("<b><big>" + title + "</big></b>" + "<br />" +
                "<small>" + subtitle + "</small>" + "<br />"));
    }

    private void setMediumButton() {
        Button button = (Button) mActivity.findViewById(R.id.button_medium);
        String title = getString(R.string.medium);
        String subtitle = getString(R.string.medium_subtext);
        button.setText(Html.fromHtml("<b><big>" + title + "</big></b>" + "<br />" +
                "<small>" + subtitle + "</small>" + "<br />"));
    }

    private void setLargeButton() {
        Button button = (Button) mActivity.findViewById(R.id.button_large);
        String title = getString(R.string.large);
        String subtitle = getString(R.string.large_subtext);
        button.setText(Html.fromHtml("<b><big>" + title + "</big></b>" + "<br />" +
                "<small>" + subtitle + "</small>" + "<br />"));
    }

}
