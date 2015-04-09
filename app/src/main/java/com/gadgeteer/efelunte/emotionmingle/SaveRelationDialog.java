package com.gadgeteer.efelunte.emotionmingle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Ismael on 12-01-15.
 */
public class SaveRelationDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("¿Asociar contacto con esa hoja?")
                .setTitle("Probar Hoja");

        // Add the buttons
        builder.setPositiveButton(R.string.asociar_contacto, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.setNegativeButton(R.string.no_asociar_contacto, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // Create the AlertDialog
        return builder.create();



    }
}