package com.gadgeteer.efelunte.emotionmingle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class InteractionsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactions);

        Spinner spinnerContacts = (Spinner)findViewById(R.id.spinner_contacts);

        ArrayAdapter<CharSequence> adapter_contacts = ArrayAdapter.createFromResource(this,
                R.array.contacts_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_contacts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerContacts.setAdapter(adapter_contacts);


        Spinner spinnerLeafs = (Spinner)findViewById(R.id.spinner_leafs);

        ArrayAdapter<CharSequence> adapter_leafs = ArrayAdapter.createFromResource(this,
                R.array.leafs_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_leafs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerLeafs.setAdapter(adapter_leafs);

        Button buttonSaveRelation = (Button)findViewById(R.id.button_save_relation);

        buttonSaveRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveRelationDialog dialog = new SaveRelationDialog();
                dialog.show(getSupportFragmentManager(), "missiles");
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_interactions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
