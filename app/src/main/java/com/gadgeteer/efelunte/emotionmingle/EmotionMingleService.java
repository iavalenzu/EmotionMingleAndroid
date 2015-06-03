package com.gadgeteer.efelunte.emotionmingle;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gadgeteer.efelunte.emotionmingle.model.Emotion;
import com.gadgeteer.efelunte.emotionmingle.model.Leafs;
import com.gadgeteer.efelunte.emotionmingle.model.Session;
import com.gadgeteer.efelunte.emotionmingle.model.User;
import com.gadgeteer.efelunte.emotionmingle.utils.Util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class EmotionMingleService extends Service {

    Disponibilidad disponibilidad = new Disponibilidad();

    private final BroadcastReceiver mReceiverFound = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            Log.i(EmotionMingle.TAG, "Action: " + action);

            if (action.equals(Intent.ACTION_SCREEN_ON))
            {

                /**
                 * Se verifica la fecha de la ultima emocion ingresada
                 */

                Date now = new Date();

                Session session = Util.getSession();

                if(session != null) {

                    User loggedUser = session.getUser();

                    if (loggedUser != null) {
                        Emotion lastEmotion = loggedUser.getLastEmotion();

                        if (lastEmotion != null) {
                            Date emotionDate = lastEmotion.getDate();

                            Log.i(EmotionMingle.TAG, "LastEmotion hace: " + (now.getTime() - emotionDate.getTime()));


                            if (now.getTime() - emotionDate.getTime() > Constants.INACTIVITY_MAX_TIME) {
                                if (!MainApp.isActivityVisible()) {
                                    Log.i(EmotionMingle.TAG, "La app NO esta visible!!");
                                    MainApp.showNotification(1, "EmotionMingle", "¿Como te sientes ahora?");
                                } else {
                                    Log.i(EmotionMingle.TAG, "La app esta visible!!");
                                }

                            }

                        } else {
                            Log.i(EmotionMingle.TAG, "LastEmotion is NULL");
                        }
                    } else {
                        Log.i(EmotionMingle.TAG, "LoggedUser is NULL");
                    }
                }


            }
            if (action.equals(Intent.ACTION_SCREEN_OFF))
            {
            }

        }
    };


    public EmotionMingleService() {
    }


    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        public EmotionMingleService getService() {
            return EmotionMingleService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    @Override
    public void onCreate()
    {
        Log.i(EmotionMingle.TAG, "EmotionMingleService > OnCreate");
        super.onCreate();

        IntentFilter filter2 = new IntentFilter(Intent.ACTION_SCREEN_ON);
        registerReceiver(mReceiverFound, filter2);

        IntentFilter filter3 = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiverFound, filter3);

        IntentFilter filter4 = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(mReceiverFound, filter4);



        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run()
            {
                Log.i(EmotionMingle.TAG, "scheduleAtFixedRate");


                Cursor lastSMSMessageInbox = disponibilidad.getLastSMSMessageSent();

                if(lastSMSMessageInbox != null) {

                    String body = lastSMSMessageInbox.getString(lastSMSMessageInbox.getColumnIndexOrThrow("body")).toString();
                    String number = lastSMSMessageInbox.getString(lastSMSMessageInbox.getColumnIndexOrThrow("address")).toString();
                    String date = lastSMSMessageInbox.getString(lastSMSMessageInbox.getColumnIndexOrThrow("date")).toString();
                    String type = lastSMSMessageInbox.getString(lastSMSMessageInbox.getColumnIndexOrThrow("type")).toString();

                    Log.i(EmotionMingle.TAG, "SMS Body: " + body);
                    Log.i(EmotionMingle.TAG, "SMS Type: " + type);
                    Log.i(EmotionMingle.TAG, "SMS Date: " + date);
                }else
                {
                    Log.i(EmotionMingle.TAG, "There isnt sent messages");
                }

                disponibilidad.getLastIncomingCallLog();
                disponibilidad.getLastOutgoingCallLog();


                Date now = new Date();

                Session session = Util.getSession();

                if(session != null)
                {
                    Leafs leafs = session.getLeafs();

                    if(leafs != null)
                    {
                        if(now.getTime() - leafs.getLastUpdate().getTime() > Constants.LEAFS_SERVICE_POLLING)
                        {
                            /**
                             * Si la ultima actualizacion de las hojas fue hace mas de 5 minutos se consulta
                             * al servicio y se actualiza el valor de las hojas
                             */

                            try {

                                HttpClient httpclient = new DefaultHttpClient();

                                HttpResponse response = httpclient.execute(new HttpGet(Constants.LEAFS_SERVICE_URL));
                                StatusLine statusLine = response.getStatusLine();

                                HttpEntity entity = response.getEntity();

                                if (statusLine.getStatusCode() == HttpStatus.SC_OK)
                                {
                                    String responseString = EntityUtils.toString(entity);

                                    JSONObject responseJson = new JSONObject(responseString);

                                    int leaf1 = responseJson.getInt("Leaf1");
                                    int leaf2 = responseJson.getInt("Leaf2");
                                    int leaf3 = responseJson.getInt("Leaf3");
                                    int leaf4 = responseJson.getInt("Leaf4");
                                    int leaf5 = responseJson.getInt("Leaf5");
                                    int leaf6 = responseJson.getInt("Leaf6");
                                    int leaf7 = responseJson.getInt("Leaf7");
                                    int leaf8 = responseJson.getInt("Leaf8");

                                    leafs.setLeaf1(leaf1);
                                    leafs.setLeaf2(leaf2);
                                    leafs.setLeaf3(leaf3);
                                    leafs.setLeaf4(leaf4);
                                    leafs.setLeaf5(leaf5);
                                    leafs.setLeaf6(leaf6);
                                    leafs.setLeaf7(leaf7);
                                    leafs.setLeaf8(leaf8);

                                    leafs.save();


                                    MainApp.updateLeafs();

                                    MainApp.notifyTreeObservers();

                                    Log.i(EmotionMingle.TAG, "Service Response: " + responseString);

                                }

                            }catch (IOException e0)
                            {
                                e0.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                    }
                    else
                    {
                        Log.i(EmotionMingle.TAG, "Leafs is NULL");
                    }

                }
                else
                {
                    Log.i(EmotionMingle.TAG, "Session is NULL");
                }


            }
        }, 0, Constants.SERVICE_TIMER_PERIOD);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiverFound);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.i(EmotionMingle.TAG, "EmotionMingleService > OnStartCommand");
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }




}
