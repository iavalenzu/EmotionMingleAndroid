package com.gadgeteer.efelunte.emotionmingle;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.gadgeteer.efelunte.emotionmingle.model.Emotion;
import com.gadgeteer.efelunte.emotionmingle.model.Leafs;
import com.gadgeteer.efelunte.emotionmingle.model.Session;
import com.gadgeteer.efelunte.emotionmingle.model.User;
import com.gadgeteer.efelunte.emotionmingle.utils.Util;
import com.orm.SugarApp;

import java.io.IOException;
import java.util.Set;

/**
 * Created by ismaelvalenzuela on 05-05-15.
 */
public class MainApp extends SugarApp implements ServiceConnection {

    private final static String DEVICE_NAME = "EmotionMingle";
    private final static String DEVICE_PIN_CODE = "5678";

    public final static int NOTIFICATION_LAST_EMOTION = 1;
    public final static String SHOW_FRAGMENT = "SHOW_FRAGMENT";

    static Context context;

    static public Session session;

    static public User loggedUser;

    static public BluetoothAdapter mBluetoothAdapter;

    static public EmotionMingleHardware emotionMingleHardware;


    private final BroadcastReceiver mReceiverFound = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            Log.i(EmotionMingle.TAG, "Action: " + action);

            if (action.equals(BluetoothDevice.ACTION_FOUND))
            {
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

                            Toast.makeText(getApplicationContext(), "Estas conectado a EmotionMingle", Toast.LENGTH_LONG).show();

                            turnOff();
                            Thread.sleep(1000);

                            clearLeafs();
                            updateBar();



                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.i(EmotionMingle.TAG, "No pude establecer la conexion");
                            Toast.makeText(getApplicationContext(), "No pude establecer la conexion", Toast.LENGTH_LONG).show();

                            if(emotionMingleHardware != null) {
                                emotionMingleHardware.close();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    };

    private EmotionMingleService emotionMingleService;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(EmotionMingle.TAG, "MainApp > onCreate");

        context = getApplicationContext();

        //loadSession();

        loadServices();

        settingBluetooth();

        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiverFound, filter);

        IntentFilter filter2 = new IntentFilter(BluetoothDevice.ACTION_PAIRING_REQUEST);
        registerReceiver(mReceiverPairingRequest, filter2);

        IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mReceiverBondStateChanged, filter3);


    }




    @Override
    public void onTerminate()
    {
        super.onTerminate();
        Log.i(EmotionMingle.TAG, "MainApp > onTerminate");

        unregisterReceiver(mReceiverFound);
        unregisterReceiver(mReceiverPairingRequest);
        unregisterReceiver(mReceiverBondStateChanged);

    }

    public void loadSession()
    {

        session = Util.getSession();

        if(session != null)
        {
            loggedUser = session.getUser();

            if(loggedUser == null)
            {
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);

            }
        }

    }

    public void loadServices()
    {
        Intent intent = new Intent(this, EmotionMingleService.class);
        startService(intent);

        boolean result  = bindService(intent, this, Context.BIND_AUTO_CREATE);

        Log.i(EmotionMingle.TAG, "BindService result: " + result);

    }

    public void settingBluetooth()
    {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null)
        {
            // Device does not support Bluetooth
            Toast.makeText(getBaseContext(), "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (mBluetoothAdapter.isEnabled())
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

                        Toast.makeText(getApplicationContext(), "Estas conectado a EmotionMingle", Toast.LENGTH_LONG).show();

                        turnOff();
                        Thread.sleep(1000);

                        clearLeafs();
                        updateBar();


                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                        Log.i(EmotionMingle.TAG, "No pude establecer la conexion");

                        if(emotionMingleHardware != null){
                            emotionMingleHardware.close();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        }


    }


    public static void updateBar() throws IOException
    {
        Session session = Util.getSession();

        if(session != null)
        {
            User loggedUser = session.getUser();

            if (loggedUser != null)
            {
                long sad = loggedUser.getEmotionCount(Emotion.SAD);
                long tired = loggedUser.getEmotionCount(Emotion.TIRED);
                long stressed = loggedUser.getEmotionCount(Emotion.STRESSED);
                long angry = loggedUser.getEmotionCount(Emotion.ANGRY);
                long happy = loggedUser.getEmotionCount(Emotion.HAPPY);
                long energetic = loggedUser.getEmotionCount(Emotion.ENERGETIC);
                long relaxed = loggedUser.getEmotionCount(Emotion.RELAXED);
                long calmed = loggedUser.getEmotionCount(Emotion.CALMED);

                if(emotionMingleHardware != null)
                {
                    emotionMingleHardware.changeBar(sad, tired, stressed, angry, happy, energetic, relaxed, calmed);
                }
            }
        }

    }


    static public void clearLeafs() throws IOException {


        Session session = Util.getSession();

        if(session != null)
        {

            Leafs leafs = session.getLeafs();

            if(leafs != null)
            {
                leafs.reset();
                leafs.save();
            }

        }



    }

    public BluetoothDevice getBondedDevice(String name)
    {
        if(mBluetoothAdapter == null)
        {
            return null;
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0)
        {
            for (BluetoothDevice device : pairedDevices)
            {
                if(device.getName().equals(name))
                {
                    return device;
                }
            }
        }

        return null;

    }

    public static BluetoothAdapter getBluetoothAdapter()
    {
        return mBluetoothAdapter;
    }

    public static EmotionMingleHardware getEmotionMingleHardware() {
        return emotionMingleHardware;
    }

    public static User getLoggedUser()
    {
        return loggedUser;
    }

    public static Session getSession()
    {
        return session;
    }


    public static void turnOff() throws IOException
    {
        if(emotionMingleHardware != null)
        {
            emotionMingleHardware.turnOff();
        }

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service)
    {
        Log.i(EmotionMingle.TAG, "IoTApplication > onServiceConnected");

        emotionMingleService = ((EmotionMingleService.LocalBinder)service).getService();

    }

    @Override
    public void onServiceDisconnected(ComponentName name)
    {
        Log.i(EmotionMingle.TAG, "IoTApplication > onServiceDisconnected");
        emotionMingleService = null;
    }

    /**
     * Show a notification while this service is running.
     */
    static public void showNotification(int notificationId, String title, String message) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(message);


        Intent resultIntent = new Intent(context, EmotionMingle.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        if(notificationId == NOTIFICATION_LAST_EMOTION)
        {
            resultIntent.putExtra(SHOW_FRAGMENT, 2);
        }


        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );


        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        Notification notification = mBuilder.build();

        notificationManager.notify(notificationId, notification);

    }

    private static boolean activityVisible = false;

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }



}
