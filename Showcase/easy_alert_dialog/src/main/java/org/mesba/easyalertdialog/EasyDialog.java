package org.mesba.easyalertdialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by mis on 10/23/2015.
 */
public class EasyDialog {
    private static final String TAG = EasyDialog.class.getSimpleName();

    private static AlertDialog.Builder builder;

    public EasyDialog() {
    }

    public static void showDialog(Context context, String title, String message, DialogTypes type, DialogInterface.OnClickListener listener){
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        switch (type){
            case OK_ONLY:
                builder.setPositiveButton(android.R.string.ok, listener);
                break;
            case OK_CANCEL:
                builder.setPositiveButton(android.R.string.ok, listener);
                builder.setNegativeButton(android.R.string.cancel, listener);
                break;
            case YES_NO:
                builder.setPositiveButton(android.R.string.yes, listener);
                builder.setNegativeButton(android.R.string.no, listener);
                break;
        }

        builder.create().show();
    }

    public static void showSingleChoiceDialog(Context context, String title, int arrayResource, DialogTypes type, DialogInterface.OnClickListener listener){
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        switch (type){
            case SINGLE_CHOICE:
                builder.setItems(arrayResource, listener);
                break;
            case SINGLE_CHOICE_RADIO:
                builder.setSingleChoiceItems(arrayResource, -1, listener);
                break;
        }

        builder.setPositiveButton(android.R.string.ok, listener);
        builder.setNegativeButton(android.R.string.cancel, listener);
    }

    public static void setCustomTitleView(View customView){
        builder.setCustomTitle(customView);
    }

}
