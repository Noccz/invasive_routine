package com.noccz.invasive_routine;

import android.view.View;

public class Utils {
    public static <T extends View> T findViewById(View view, int id) {
        return view.getRootView().findViewById(id);
    }
}
