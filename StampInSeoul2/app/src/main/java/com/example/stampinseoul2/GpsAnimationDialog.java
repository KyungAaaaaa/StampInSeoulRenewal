package com.example.stampinseoul2;

import android.content.Context;
import android.view.Window;

public class GpsAnimationDialog {
    private Context context;

    public GpsAnimationDialog(Context context) {
        this.context = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    private void requestWindowFeature(int featureNoTitle) {
    }


}
