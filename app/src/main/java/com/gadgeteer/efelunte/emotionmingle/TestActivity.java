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


public class TestActivity extends ActionBarActivity
{

    private final static String DEVICE_NAME = "EmotionMingle";
    private final static String DEVICE_PIN_CODE = "5678";


    private static final int REQUEST_ENABLE_BT = 1;

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mBluetoothSocket;
    OutputStream mOutputStream;

    Spinner spinnerLeafs;
    TextView textviewLeafValue;
    SeekBar seekBarLeafValue;


    private final BroadcastReceiver mReceiver = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();

            Log.i(EmotionMingle.TAG, "Action: " + action);

            if (action.equals(BluetoothDevice.ACTION_FOUND))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if(device == null){
                    return;
                }

                String deviceName = device.getName();

                if(deviceName == null){
                    return;
                }

                if(deviceName.equals(DEVICE_NAME))
                {
                    device.createBond();
                }
            }

            if (action.equals(BluetoothDevice.ACTION_PAIRING_REQUEST))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if(device == null){
                    return;
                }

                device.setPin(DEVICE_PIN_CODE.getBytes());

            }

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
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

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
                Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);
            }
            else
            {
                Log.i(EmotionMingle.TAG, "Bluetooth is already on");

                BluetoothDevice bondedDevice = getBondedDevice(DEVICE_NAME);

                if(bondedDevice == null)
                {
                    Log.i(EmotionMingle.TAG, "BondedDevice es nulo!!");

                    mBluetoothAdapter.startDiscovery();

                }
                else
                {
                    try
                    {

                        BluetoothSocket mBluetoothSocket = bondedDevice.createRfcommSocketToServiceRecord(MY_UUID);

                        if(mBluetoothSocket != null)
                        {
                            mBluetoothAdapter.cancelDiscovery();

                            mBluetoothSocket.connect();

                            mOutputStream = mBluetoothSocket.getOutputStream();

                            if(mOutputStream != null)
                            {
                                String cmd = "hoja:2:34\n";
                                byte[] b = cmd.getBytes("UTF-8");
                                mOutputStream.write(b);
                            }
                            else
                            {
                                Log.i(EmotionMingle.TAG, "OutputStream es nulo!!");
                           }




                        }
                        else
                        {
                            Log.i(EmotionMingle.TAG, "BluetoothSocket es nulo!!");
                        }
                    }
                    catch (Exception e)
                    {
                        Log.i(EmotionMingle.TAG, "Exception: " + e.getLocalizedMessage());

                        try
                        {
                            mBluetoothSocket.close();
                        }
                        catch (Exception e2)
                        {
                            Log.i(EmotionMingle.TAG, "Exception: " + e2.getLocalizedMessage());
                        }
                    }
                }
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

                int progress = seekBar.getProgress();



            }
        });

        Button buttonSearchDevices = (Button) findViewById(R.id.button_search_devices);

        buttonSearchDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBluetoothAdapter.startDiscovery();
            }
        });


        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy

        IntentFilter filter2 = new IntentFilter(BluetoothDevice.ACTION_PAIRING_REQUEST);
        registerReceiver(mReceiver, filter2); // Don't forget to unregister during onDestroy

        IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mReceiver, filter3); // Don't forget to unregister during onDestroy


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);

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
                Toast.makeText(getApplicationContext(), "onActivityResult: Bluetooth turned on", Toast.LENGTH_LONG).show();

                mBluetoothAdapter.startDiscovery();


            }
            else
            {
                Toast.makeText(getApplicationContext(), "onActivityResult: Bluetooth is not turned on!!", Toast.LENGTH_LONG).show();
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

        return super.onOptionsItemSelected(item);
    }

    public void manageConnectedSocket(BluetoothSocket socket)
    {
        Log.i(EmotionMingle.TAG, "manageConnectedSocket!!");

    }





}
