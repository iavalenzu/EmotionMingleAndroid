package com.gadgeteer.efelunte.emotionmingle;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gadgeteer.efelunte.emotionmingle.model.Emotion;
import com.gadgeteer.efelunte.emotionmingle.model.Session;
import com.gadgeteer.efelunte.emotionmingle.model.User;
import com.gadgeteer.efelunte.emotionmingle.utils.Util;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class EmotionMingleService extends Service {

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

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run()
            {

                Log.i(EmotionMingle.TAG, "scheduleAtFixedRate");


                if(MainApp.isActivityVisible())
                {
                    Log.i(EmotionMingle.TAG, "La app esta en foreground!!");
                    return;
                }

                Session session = Util.getSession();

                if(session != null)
                {
                    User loggedUser = session.getUser();

                    if(loggedUser != null)
                    {
                        Emotion lastEmotion = loggedUser.getLastEmotion();

                        if(lastEmotion != null)
                        {
                            Date emotionDate = lastEmotion.getDate();
                            Date now = new Date();

                            if(now.getTime() - emotionDate.getTime() > 15*60*1000)
                            {
                                MainApp.showNotification(1, "EmotionMingle", "¿Como te sientes ahora?");
                            }

                        }
                        else
                        {
                            Log.i(EmotionMingle.TAG, "LastEmotion is NULL");
                        }
                    }
                    else
                    {
                        Log.i(EmotionMingle.TAG, "LoggedUser is NULL");
                    }
                }
                else
                {
                    Log.i(EmotionMingle.TAG, "Session is NULL");
                }


            }
        }, 0, 1*60*1000);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.i(EmotionMingle.TAG, "EmotionMingleService > OnStartCommand");
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }




}
