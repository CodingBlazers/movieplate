package com.example.megha.movieplate.utility;

/**
 * Created by megha on 14/08/17.
 */

import android.os.Build;
import android.view.View;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This is a helper class which is used to create an id which can be assigned to views in the layouts programmatically
 * Source: http://stackoverflow.com/questions/6790623/programmatic-views-how-to-set-unique-ids
 * */
public class ViewIdGenerator {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int generateViewId() {

        if (Build.VERSION.SDK_INT < 17) {
            for (;;) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }
    }
}

