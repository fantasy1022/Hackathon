package com.fantasy1022.hackathon.common;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by fantasy_apple on 2017/6/27.
 */

public class Dialog {
    public static void showInfoDialog(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(context.getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Do nothing
                            }
                        })
                .create();
        alertDialog.show();
    }


    public static void showHintDialog(Context context, String message, final OnOkClickListener listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onOkClick();
                    }
                })
                .setNegativeButton(context.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Do nothing
                    }
                })
                .create();
        alertDialog.show();
    }

    public static void showChoiceDialog(Context context, String title, String[] items,int initIndex, final OnItemClickListner itmeListner, final OnOkClickListener okClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setSingleChoiceItems(items, initIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface,  int position) {
                        itmeListner.onItemClick(position);
                    }
                })
                .setPositiveButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        okClickListener.onOkClick();
                    }
                })
                .create();
        alertDialog.show();
    }


    public interface OnOkClickListener {
        void onOkClick();
    }

    public interface OnItemClickListner {
        void onItemClick(int number);
    }
}
