package com.marcussjolin.calhacks;

import android.app.Application;

public class CalHacksApplication extends Application {

    public static CalHacksApplication INSTANCE;

    public static String URL = "http://calhacks.paulashbourne.com/api/";
    public static String USER_ID = "5619a118729fe0da6e035c2d";
    public static String FACILITY = "5619d121107d677233f4a51d";
    public static String PASSWORD = "password";
    public static String IMAGE_FILES = "image_files";

    @Override
    public void onCreate() {
        super.onCreate();

        CalHacksApplication.INSTANCE = this;
    }

}
