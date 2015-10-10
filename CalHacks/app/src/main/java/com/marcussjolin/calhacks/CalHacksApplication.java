package com.marcussjolin.calhacks;

import android.app.Application;

public class CalHacksApplication extends Application {

    public static CalHacksApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

        CalHacksApplication.INSTANCE = this;
    }

}
