package com.example.megha.movieplate.utility.UI;

/**
 * Created by megha on 14/08/17.
 */

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * A helper class to convert unit values in dp, sp etc. to px for Java coding
 * */
public class UiUnitConverter {

    /**
     *typedValue denotes the unit of the value entered (Should be of the type: TypedValue.COMPLEX_UNIT_DIP etc.)
     */
    public static int topxConverter(float value, int typedValue)
    {
        int convertedValue = (int) TypedValue.applyDimension(typedValue, value, Resources.getSystem().getDisplayMetrics());
        return convertedValue;
    }

}

