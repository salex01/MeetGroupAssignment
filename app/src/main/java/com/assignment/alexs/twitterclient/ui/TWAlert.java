package com.assignment.alexs.twitterclient.ui;

/**
 * Created by alexschwartzman on 1/6/18.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;


public class TWAlert extends DialogFragment {
    private String message ="error";//default

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(context)

                .setTitle( "Error")
                .setMessage( message)
           .setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                getActivity().finish();
                            }
                        });

        return builder.create();
    }

}