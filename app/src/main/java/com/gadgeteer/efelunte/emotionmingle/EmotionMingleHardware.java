package com.gadgeteer.efelunte.emotionmingle;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by ismaelvalenzuela on 09-04-15.
 */
public class EmotionMingleHardware {

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    private BluetoothDevice device;
    private BluetoothSocket mBluetoothSocket;
    private OutputStream mOutputStream;


    public EmotionMingleHardware(BluetoothDevice device) throws IOException
    {
        this.device = device;

        this.mBluetoothSocket = this.device.createRfcommSocketToServiceRecord(MY_UUID);

        if(this.mBluetoothSocket == null){
            throw new IllegalStateException("El bluetooth socket es nulo.");
        }

        this.mBluetoothSocket.connect();

        this.mOutputStream = this.mBluetoothSocket.getOutputStream();

        if(this.mOutputStream == null)
        {
            throw  new IllegalStateException("El output stream es nulo.");
        }

        Log.i(EmotionMingle.TAG, "Estas conectado a EmotionMingle");

    }

    public void changeLeaf(int hoja, int valor) throws IOException
    {

        String message = "hoja" + ":" + hoja + ":" + valor + "\n";

        if(this.mOutputStream != null)
        {
            this.mOutputStream.write(message.getBytes());
            Log.i(EmotionMingle.TAG, "Enviaste EmotionMingle: " + message);

        }

    }

    public void changeBar(int sad, int tired, int stressed, int angry, int happy, int energetic, int relaxed, int calmed) throws IOException {

        String message = "barra" + ":" + sad + ":" + tired + ":" + stressed + ":" + angry + ":" + happy + ":" + energetic + ":" + relaxed + ":" + calmed + "\n";

        if(this.mOutputStream != null)
        {
            this.mOutputStream.write(message.getBytes());
            Log.i(EmotionMingle.TAG, "Enviaste EmotionMingle: " + message);

        }




    }


    public void close()
    {
        if(this.mBluetoothSocket != null)
        {
            try {
                this.mBluetoothSocket.close();
                Log.i(EmotionMingle.TAG, "Cerraste las conexion con EmotionMingle.");
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(EmotionMingle.TAG, "Ocurrio un error al cerrar el Socket!");
            }
        }

    }

    public void turnOff() throws IOException
    {
        String message = "off" + ":" + "\n";

        if(this.mOutputStream != null)
        {
            this.mOutputStream.write(message.getBytes());
            Log.i(EmotionMingle.TAG, "Enviaste EmotionMingle: " + message);

        }

    }
}
