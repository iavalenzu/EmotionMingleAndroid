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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


public class TestBarActivity extends ActionBarActivity
{

    private final static String DEVICE_NAME = "EmotionMingle";
    private final static String DEVICE_PIN_CODE = "5678";


    private static final int REQUEST_ENABLE_BT = 1;


    BluetoothAdapter mBluetoothAdapter;

    EmotionMingleHardware emotionMingleHardware;

    TextView textviewBarSadValue;
    SeekBar seekBarSadValue;

    TextView textviewBarTiredValue;
    SeekBar seekBarTiredValue;

    TextView textviewBarStressedValue;
    SeekBar seekBarStressedValue;

    TextView textviewBarAngryValue;
    SeekBar seekBarAngryValue;

    TextView textviewBarHappyValue;
    SeekBar seekBarHappyValue;

    TextView textviewBarEnergeticValue;
    SeekBar seekBarEnergecticValue;

    TextView textviewBarRelaxedValue;
    SeekBar seekBarRelaxedValue;

    TextView textviewBarCalmedValue;
    SeekBar seekBarCalmedValue;

    Button buttonTurnOff;

    private final BroadcastReceiver mReceiverFound = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            Log.i(EmotionMingle.TAG, "Action: " + action);

            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (device == null) {
                    return;
                }

                String deviceName = device.getName();

                if (deviceName == null) {
                    return;
                }

                if (deviceName.equals(DEVICE_NAME)) {
                    device.createBond();
                }
            }

        }
    };

    private final BroadcastReceiver mReceiverPairingRequest = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_PAIRING_REQUEST)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (device == null) {
                    return;
                }

                device.setPin(DEVICE_PIN_CODE.getBytes());

            }
        }
    };

    private final BroadcastReceiver mReceiverBondStateChanged = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if(device == null){
                    return;
                }

                String name = device.getName();

                if(name == null)
                {
                    return;
                }

                if(name.equals(DEVICE_NAME))
                {
                    int bondState = device.getBondState();

                    if(bondState == BluetoothDevice.BOND_BONDED)
                    {
                        Log.i(EmotionMingle.TAG, "El device " + name + " esta enlazado!!");

                        try {
                            emotionMingleHardware =  new EmotionMingleHardware(device);

                            Toast.makeText(TestBarActivity.this, "Estas conectado a EmotionMingle", Toast.LENGTH_LONG).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.i(EmotionMingle.TAG, "No pude establecer la conexion");
                            if(emotionMingleHardware != null) {
                                emotionMingleHardware.close();
                            }
                        }

                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_test);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null)
        {
            // Device does not support Bluetooth
            Toast.makeText(getBaseContext(), "onCreate: Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (!mBluetoothAdapter.isEnabled())
            {
                Log.i(EmotionMingle.TAG, "El bluetooth esta desactivado, se debe habilitar");

                Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);
            }
            else
            {
                BluetoothDevice bondedDevice = getBondedDevice(DEVICE_NAME);

                if(bondedDevice == null)
                {
                    Log.i(EmotionMingle.TAG, "No encuentro el dispositivo en los paired devices.");

                    mBluetoothAdapter.startDiscovery();

                }
                else
                {
                    try
                    {
                        bondedDevice.setPin(DEVICE_PIN_CODE.getBytes());
                        emotionMingleHardware =  new EmotionMingleHardware(bondedDevice);

                        Toast.makeText(TestBarActivity.this, "Estas conectado a EmotionMingle", Toast.LENGTH_LONG).show();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                        Log.i(EmotionMingle.TAG, "No pude establecer la conexion");

                        if(emotionMingleHardware != null) {
                            emotionMingleHardware.close();
                        }
                    }


                }
            }
        }


        textviewBarSadValue = (TextView) findViewById(R.id.textview_bar_sad_value);
        textviewBarSadValue.setText("0");

        seekBarSadValue = (SeekBar) findViewById(R.id.seekbar_bar_sad_value);
        seekBarSadValue.setProgress(0);
        seekBarSadValue.setMax(12);


        textviewBarTiredValue = (TextView) findViewById(R.id.textview_bar_tired_value);
        textviewBarTiredValue.setText("0");

        seekBarTiredValue = (SeekBar) findViewById(R.id.seekbar_bar_tired_value);
        seekBarTiredValue.setProgress(0);
        seekBarTiredValue.setMax(12);


        textviewBarStressedValue = (TextView) findViewById(R.id.textview_bar_stressed_value);
        textviewBarStressedValue.setText("0");

        seekBarStressedValue = (SeekBar) findViewById(R.id.seekbar_bar_stressed_value);
        seekBarStressedValue.setProgress(0);
        seekBarStressedValue.setMax(12);


        textviewBarAngryValue = (TextView) findViewById(R.id.textview_bar_angry_value);
        textviewBarAngryValue.setText("0");

        seekBarAngryValue = (SeekBar) findViewById(R.id.seekbar_bar_angry_value);
        seekBarAngryValue.setProgress(0);
        seekBarAngryValue.setMax(12);


        textviewBarHappyValue = (TextView) findViewById(R.id.textview_bar_happy_value);
        textviewBarHappyValue.setText("0");

        seekBarHappyValue = (SeekBar) findViewById(R.id.seekbar_bar_happy_value);
        seekBarHappyValue.setProgress(0);
        seekBarHappyValue.setMax(12);


        textviewBarEnergeticValue = (TextView) findViewById(R.id.textview_bar_energetic_value);
        textviewBarEnergeticValue.setText("0");

        seekBarEnergecticValue = (SeekBar) findViewById(R.id.seekbar_bar_energetic_value);
        seekBarEnergecticValue.setProgress(0);
        seekBarEnergecticValue.setMax(12);


        textviewBarRelaxedValue = (TextView) findViewById(R.id.textview_bar_relaxed_value);
        textviewBarRelaxedValue.setText("0");

        seekBarRelaxedValue = (SeekBar) findViewById(R.id.seekbar_bar_relaxed_value);
        seekBarRelaxedValue.setProgress(0);
        seekBarRelaxedValue.setMax(12);


        textviewBarCalmedValue = (TextView) findViewById(R.id.textview_bar_calmed_value);
        textviewBarCalmedValue.setText("0");

        seekBarCalmedValue = (SeekBar) findViewById(R.id.seekbar_bar_calmed_value);
        seekBarCalmedValue.setProgress(0);
        seekBarCalmedValue.setMax(12);

        buttonTurnOff = (Button) findViewById(R.id.button_turn_off);

        buttonTurnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(emotionMingleHardware != null)
                {
                    try
                    {
                        emotionMingleHardware.turnOff();
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


        seekBarSadValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textviewBarSadValue.setText(""+progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                changeBarEmotionMingle();
            }
        });

        seekBarTiredValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textviewBarTiredValue.setText(""+progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                changeBarEmotionMingle();
            }
        });

        seekBarStressedValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textviewBarStressedValue.setText(""+progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                changeBarEmotionMingle();
            }
        });

        seekBarAngryValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textviewBarAngryValue.setText(""+progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                changeBarEmotionMingle();
            }
        });

        seekBarHappyValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textviewBarHappyValue.setText(""+progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                changeBarEmotionMingle();
            }
        });


        seekBarEnergecticValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textviewBarEnergeticValue.setText(""+progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                changeBarEmotionMingle();
            }
        });


        seekBarRelaxedValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textviewBarRelaxedValue.setText(""+progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                changeBarEmotionMingle();
            }
        });


        seekBarCalmedValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textviewBarCalmedValue.setText(""+progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                changeBarEmotionMingle();
            }
        });




        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiverFound, filter);

        IntentFilter filter2 = new IntentFilter(BluetoothDevice.ACTION_PAIRING_REQUEST);
        registerReceiver(mReceiverPairingRequest, filter2);

        IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mReceiverBondStateChanged, filter3);


    }


    public void changeBarEmotionMingle()
    {
        if(emotionMingleHardware != null)
        {
            int sad = seekBarSadValue.getProgress();
            int tired = seekBarTiredValue.getProgress();
            int stressed = seekBarStressedValue.getProgress();
            int angry = seekBarAngryValue.getProgress();
            int happy = seekBarHappyValue.getProgress();
            int energetic = seekBarEnergecticValue.getProgress();
            int relaxed = seekBarRelaxedValue.getProgress();
            int calmed = seekBarCalmedValue.getProgress();

            try
            {
                emotionMingleHardware.changeBar(sad, tired, stressed, angry, happy, energetic, relaxed, calmed);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiverFound);
        unregisterReceiver(mReceiverPairingRequest);
        unregisterReceiver(mReceiverBondStateChanged);


        if(emotionMingleHardware != null)
        {
            emotionMingleHardware.close();
        }


    }

    public BluetoothDevice getBondedDevice(String name)
    {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                if(device.getName().equals(name)){
                    return device;
                }
            }
        }

        return null;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_ENABLE_BT)
        {
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

            mBluetoothAdapter.startDiscovery();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }





}
