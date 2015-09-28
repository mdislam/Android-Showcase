package org.mesba.customfont;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by mis on 9/9/2015.
 */
public class CustomFontHelper {
    private static Typeface customFont;

    public static final String TYPEFACE_AVENIR_ROMAN = "fonts/AvenirLTStd-Roman.otf";
    public static final String TYPEFACE_AVENIR_LIGHT = "fonts/AvenirLTStd-Light.otf";
    public static final String TYPEFACE_AVENIR_BLACK = "fonts/AvenirLTStd-Black.otf";

    public static Typeface applyAvenirRoman(Context context){
        customFont = Typeface.createFromAsset(context.getAssets(), TYPEFACE_AVENIR_ROMAN);
        return customFont;
    }

    public static Typeface applyAvenirLight(Context context){
        customFont = Typeface.createFromAsset(context.getAssets(), TYPEFACE_AVENIR_LIGHT);
        return customFont;
    }

    public static Typeface applyAvenirBlack(Context context){
        customFont = Typeface.createFromAsset(context.getAssets(), TYPEFACE_AVENIR_BLACK);
        return customFont;
    }
}
