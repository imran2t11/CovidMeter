package com.masterimran.covidmeter.Utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.masterimran.covidmeter.R;


public class LoaderDialog extends AlertDialog {
    public LoaderDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // getWindow().setLayout(20,20);
    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.custom_loader);
    }

    @Override
    public void cancel() {
        super.cancel();
    }

    public void stopLoader() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, StaticVaribles.LOADER_DELAY);
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
            Log.d("loaderexception", "exception:" + e);
        }
    }
}
