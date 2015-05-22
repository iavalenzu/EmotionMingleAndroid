package com.gadgeteer.efelunte.emotionmingle;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gadgeteer.efelunte.emotionmingle.model.Emotion;
import com.gadgeteer.efelunte.emotionmingle.model.Session;
import com.gadgeteer.efelunte.emotionmingle.model.User;
import com.gadgeteer.efelunte.emotionmingle.utils.Util;

import java.io.IOException;


public class TestBarActivity extends ActionBarActivity
{

    private final static String DEVICE_NAME = "EmotionMingle";
    private final static String DEVICE_PIN_CODE = "5678";


    private static final int REQUEST_ENABLE_BT = 1;


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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_test);

        BluetoothAdapter bluetoothAdapter = MainApp.getBluetoothAdapter();

        if(bluetoothAdapter != null)
        {
            if(!bluetoothAdapter.isEnabled())
            {
                Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);
            }
        }

        Session session = Util.getSession();

        User loggedUser = session.getUser();



        textviewBarSadValue = (TextView) findViewById(R.id.textview_bar_sad_value);

        seekBarSadValue = (SeekBar) findViewById(R.id.seekbar_bar_sad_value);

        if(loggedUser != null)
        {
            long sad = loggedUser.getEmotionCount(Emotion.SAD);
            textviewBarSadValue.setText("" + sad);
            seekBarSadValue.setProgress((int) sad);
        }

        seekBarSadValue.setMax(500);


        textviewBarTiredValue = (TextView) findViewById(R.id.textview_bar_tired_value);

        seekBarTiredValue = (SeekBar) findViewById(R.id.seekbar_bar_tired_value);

        if(loggedUser != null)
        {
            long tired = loggedUser.getEmotionCount(Emotion.TIRED);
            textviewBarTiredValue.setText("" + tired);
            seekBarTiredValue.setProgress((int) tired);
        }

        seekBarTiredValue.setMax(500);


        textviewBarStressedValue = (TextView) findViewById(R.id.textview_bar_stressed_value);

        seekBarStressedValue = (SeekBar) findViewById(R.id.seekbar_bar_stressed_value);

        if(loggedUser != null)
        {
            long stressed = loggedUser.getEmotionCount(Emotion.STRESSED);
            textviewBarStressedValue.setText("" + stressed);
            seekBarStressedValue.setProgress((int) stressed);
        }

        seekBarStressedValue.setMax(500);


        textviewBarAngryValue = (TextView) findViewById(R.id.textview_bar_angry_value);

        seekBarAngryValue = (SeekBar) findViewById(R.id.seekbar_bar_angry_value);

        if(loggedUser != null)
        {
            long angry = loggedUser.getEmotionCount(Emotion.ANGRY);
            textviewBarAngryValue.setText("" + angry);
            seekBarAngryValue.setProgress((int) angry);
        }

        seekBarAngryValue.setMax(500);


        textviewBarHappyValue = (TextView) findViewById(R.id.textview_bar_happy_value);

        seekBarHappyValue = (SeekBar) findViewById(R.id.seekbar_bar_happy_value);

        if(loggedUser != null)
        {
            long happy = loggedUser.getEmotionCount(Emotion.HAPPY);
            textviewBarHappyValue.setText("" + happy);
            seekBarHappyValue.setProgress((int) happy);
        }

        seekBarHappyValue.setMax(500);


        textviewBarEnergeticValue = (TextView) findViewById(R.id.textview_bar_energetic_value);

        seekBarEnergecticValue = (SeekBar) findViewById(R.id.seekbar_bar_energetic_value);

        if(loggedUser != null)
        {
            long energetic = loggedUser.getEmotionCount(Emotion.ENERGETIC);
            textviewBarEnergeticValue.setText("" + energetic);
            seekBarEnergecticValue.setProgress((int) energetic);
        }

        seekBarEnergecticValue.setMax(500);


        textviewBarRelaxedValue = (TextView) findViewById(R.id.textview_bar_relaxed_value);

        seekBarRelaxedValue = (SeekBar) findViewById(R.id.seekbar_bar_relaxed_value);

        if(loggedUser != null)
        {
            long relaxed = loggedUser.getEmotionCount(Emotion.RELAXED);
            textviewBarRelaxedValue.setText("" + relaxed);
            seekBarRelaxedValue.setProgress((int) relaxed);
        }

        seekBarRelaxedValue.setMax(500);


        textviewBarCalmedValue = (TextView) findViewById(R.id.textview_bar_calmed_value);

        seekBarCalmedValue = (SeekBar) findViewById(R.id.seekbar_bar_calmed_value);

        if(loggedUser != null)
        {
            long calmed = loggedUser.getEmotionCount(Emotion.CALMED);
            textviewBarCalmedValue.setText("" + calmed);
            seekBarCalmedValue.setProgress((int) calmed);
        }

        seekBarCalmedValue.setMax(500);

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


    }


    public void changeBarEmotionMingle()
    {
        EmotionMingleHardware emotionMingleHardware = MainApp.getEmotionMingleHardware();

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

            try {
                emotionMingleHardware.changeBar(sad, tired, stressed, angry, happy, energetic, relaxed, calmed);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_ENABLE_BT)
        {
            BluetoothAdapter bluetoothAdapter = MainApp.getBluetoothAdapter();

            if(bluetoothAdapter.isEnabled())
            {
                Log.i(EmotionMingle.TAG, "Bluetooth turned on!!!");
                bluetoothAdapter.startDiscovery();
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
