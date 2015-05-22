package com.gadgeteer.efelunte.emotionmingle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gadgeteer.efelunte.emotionmingle.model.Leafs;
import com.gadgeteer.efelunte.emotionmingle.model.Session;
import com.gadgeteer.efelunte.emotionmingle.utils.Util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


public class TestLeafsActivity extends ActionBarActivity
{

    private static final int REQUEST_ENABLE_BT = 1;


    Spinner spinnerLeafs;
    TextView textviewLeafValue;
    SeekBar seekBarLeafValue;

    Button buttonTurnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        BluetoothAdapter bluetoothAdapter = MainApp.getBluetoothAdapter();

        if(bluetoothAdapter != null)
        {
            if(!bluetoothAdapter.isEnabled())
            {
                Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);
            }
        }


        spinnerLeafs = (Spinner)findViewById(R.id.spinner_leafs);

        ArrayAdapter<CharSequence> adapter_leafs = ArrayAdapter.createFromResource(this,
                R.array.leafs_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_leafs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerLeafs.setAdapter(adapter_leafs);


        textviewLeafValue = (TextView) findViewById(R.id.textview_leaf_value);
        textviewLeafValue.setText("0");


        seekBarLeafValue = (SeekBar) findViewById(R.id.seekbar_leaf_value);
        seekBarLeafValue.setProgress(0);
        seekBarLeafValue.setMax(12);


        spinnerLeafs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                Session session = Util.getSession();

                if(session != null)
                {
                    Leafs leafs = session.getLeafs();

                    if(leafs != null)
                    {
                        seekBarLeafValue.setProgress(leafs.getLeafValue(position+1));
                    }
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        seekBarLeafValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textviewLeafValue.setText(""+progress);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                EmotionMingleHardware emotionMingleHardware = MainApp.getEmotionMingleHardware();

                if(emotionMingleHardware != null)
                {
                    int position = spinnerLeafs.getSelectedItemPosition();

                    int hoja = position + 1;
                    int value = seekBar.getProgress();

                    Session session = Util.getSession();

                    if(session != null)
                    {
                        Leafs leafs = session.getLeafs();

                        if(leafs != null)
                        {
                            leafs.setLeafValue(hoja, value);
                            leafs.save();
                        }
                        else
                        {
                            Log.i(EmotionMingle.TAG, "TestLeafsActivity > Leafs is NULL");
                        }
                    }
                    else
                    {
                        Log.i(EmotionMingle.TAG, "TestLeafsActivity > Session is NULL");
                    }


                    try {
                        emotionMingleHardware.changeLeaf(hoja, value);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i(EmotionMingle.TAG, "Ocurrio un error al enviar el comando");
                        if(emotionMingleHardware != null) {
                            emotionMingleHardware.close();
                        }

                    }
                }



            }
        });


        buttonTurnOff = (Button) findViewById(R.id.button_turn_off);

        buttonTurnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                EmotionMingleHardware emotionMingleHardware = MainApp.getEmotionMingleHardware();

                if(emotionMingleHardware != null)
                {



                    try
                    {
                        emotionMingleHardware.turnOff();


                        Session session = Util.getSession();

                        if(session != null)
                        {
                            Leafs leafs = session.getLeafs();

                            if(leafs != null)
                            {
                                leafs.reset();
                                leafs.save();
                            }
                            else
                            {
                                Log.i(EmotionMingle.TAG, "TestLeafsActivity > Leafs is NULL");
                            }
                        }
                        else
                        {
                            Log.i(EmotionMingle.TAG, "TestLeafsActivity > Session is NULL");
                        }


                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                        Log.i(EmotionMingle.TAG, "Ocurrio un error al enviar el comando");
                        if(emotionMingleHardware != null) {
                            emotionMingleHardware.close();
                        }

                    }
                }

            }
        });




    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_ENABLE_BT)
        {
            BluetoothAdapter mBluetoothAdapter =  MainApp.getBluetoothAdapter();

            if(mBluetoothAdapter == null){
                return;
            }

            if(mBluetoothAdapter.isEnabled())
            {
                Log.i(EmotionMingle.TAG, "Bluetooth turned on!!!");
                mBluetoothAdapter.startDiscovery();
            }
            else
            {
                Log.i(EmotionMingle.TAG, "Bluetooth is NOT turned on!!!");
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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

        if (id == R.id.action_find_device) {

            BluetoothAdapter bluetoothAdapter = MainApp.getBluetoothAdapter();

            if(bluetoothAdapter != null)
            {
                bluetoothAdapter.startDiscovery();
            }


            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MainApp.activityResumed();

    }

    @Override
    protected void onPause() {
        super.onPause();
        MainApp.activityPaused();
    }



}
