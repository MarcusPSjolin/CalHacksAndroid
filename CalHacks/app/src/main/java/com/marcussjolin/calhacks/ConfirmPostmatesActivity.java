package com.marcussjolin.calhacks;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class ConfirmPostmatesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_postmates);
        setConfirmButton();
    }

    private void setConfirmButton() {
        Button button = (Button) findViewById(R.id.looks_good);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
