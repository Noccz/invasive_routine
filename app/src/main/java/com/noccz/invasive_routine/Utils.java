package com.noccz.invasive_routine;

import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utils {
    private static Gson gson;

    public static <T extends View> T findViewById(View view, int id) {
        return view.getRootView().findViewById(id);
    }

    public static Gson getGsonParser() {
        if(gson == null) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }
}
