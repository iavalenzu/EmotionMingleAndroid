package com.gadgeteer.efelunte.emotionmingle;

import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.Telephony;
import android.util.Log;

import com.gadgeteer.efelunte.emotionmingle.model.Emotion;

import java.util.Date;

/**
 * Created by ismaelvalenzuela on 26-05-15.
 */
public class Disponibilidad {

    public Disponibilidad()
    {

    }

    public Cursor getLastSMSMessageInbox()
    {
        Uri uri = Uri.parse("content://sms");
        Cursor cursor = MainApp.getAppContentResolver().query(Telephony.Sms.CONTENT_URI, null, Telephony.TextBasedSmsColumns.TYPE + " = ?", new String[]{String.valueOf(Telephony.TextBasedSmsColumns.MESSAGE_TYPE_INBOX)}, Telephony.TextBasedSmsColumns.DATE + " DESC");

        if (cursor.moveToFirst())
        {
            return cursor;
        }

        return null;
    }

    public Cursor getLastSMSMessageSent()
    {
        Cursor cursor = MainApp.getAppContentResolver().query(Telephony.Sms.CONTENT_URI, null, Telephony.TextBasedSmsColumns.TYPE + " = ?", new String[]{String.valueOf(Telephony.TextBasedSmsColumns.MESSAGE_TYPE_SENT)}, Telephony.TextBasedSmsColumns.DATE + " DESC");

        if (cursor.moveToFirst())
        {
            return cursor;
        }

        return null;
    }

    public Cursor getLastIncomingCallLog()
    {
        Cursor cursor = MainApp.getAppContentResolver().query(CallLog.Calls.CONTENT_URI, null, CallLog.Calls.TYPE + " = ?", new String[]{String.valueOf(CallLog.Calls.INCOMING_TYPE)}, CallLog.Calls.DATE + " DESC");

        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);


        if(cursor.moveToNext())
        {

            String phNumber = cursor.getString(number);
            String callType = cursor.getString(type);
            String callDate = cursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = cursor.getString(duration);

            Log.i(EmotionMingle.TAG, "phNumber: " + phNumber );
            Log.i(EmotionMingle.TAG, "callDuration: " + callDuration );


            return cursor;
        }

        return null;

    }

    public Cursor getLastOutgoingCallLog()
    {
        Cursor cursor = MainApp.getAppContentResolver().query(CallLog.Calls.CONTENT_URI, null, CallLog.Calls.TYPE + " = ?", new String[]{String.valueOf(CallLog.Calls.OUTGOING_TYPE)}, CallLog.Calls.DATE + " DESC");

        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);


        if(cursor.moveToNext())
        {
            String phNumber = cursor.getString(number);
            String callType = cursor.getString(type);
            String callDate = cursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = cursor.getString(duration);

            Log.i(EmotionMingle.TAG, "phNumber: " + phNumber );
            Log.i(EmotionMingle.TAG, "callDuration: " + callDuration );


            return cursor;
        }

        return null;

    }


}
