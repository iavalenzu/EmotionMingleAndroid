package com.gadgeteer.efelunte.emotionmingle;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gadgeteer.efelunte.emotionmingle.model.Session;
import com.gadgeteer.efelunte.emotionmingle.model.User;
import com.gadgeteer.efelunte.emotionmingle.utils.Util;

import java.io.IOException;
import java.util.Set;


public class EmotionMingle extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {


    static final public String TAG = "EmotionMingleTag";

    private static final int REQUEST_ENABLE_BT = 1;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_mingle);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp( R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));


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

        if(session != null)
        {
            User loggedUser = session.getUser();

            if(loggedUser == null)
            {
                Intent loginIntent = new Intent(EmotionMingle.this, LoginActivity.class);
                startActivity(loginIntent);

            }
        }

        Intent intent = getIntent();

        if(intent != null)
        {
            if(intent.hasExtra(MainApp.SHOW_FRAGMENT))
            {
                int fragmentPos = intent.getIntExtra(MainApp.SHOW_FRAGMENT, 0);

                onNavigationDrawerItemSelected(fragmentPos);

            }
            else
            {
                Log.i(EmotionMingle.TAG, "Intent does not have SHOW_FRAGMENT extra!!");
            }
        }
        else
        {
            Log.i(EmotionMingle.TAG, "Intent is NULL");
        }




    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(EmotionMingle.TAG, "onNewIntent()");

        if(intent != null)
        {
            if(intent.hasExtra(MainApp.SHOW_FRAGMENT))
            {
                int fragmentPos = intent.getIntExtra(MainApp.SHOW_FRAGMENT, 0);

                Log.i(EmotionMingle.TAG, "FrgamentPos: " + fragmentPos);

                onNavigationDrawerItemSelected(fragmentPos);

            }
            else
            {
                Log.i(EmotionMingle.TAG, "Intent does not have SHOW_FRAGMENT extra!!");
            }
        }
        else
        {
            Log.i(EmotionMingle.TAG, "Intent is NULL");
        }


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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /*
        EmotionMingleHardware emotionMingleHardware = MainApp.getEmotionMingleHardware();

        if(emotionMingleHardware != null)
        {
            emotionMingleHardware.close();;
        }
        */

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
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, TreeFragment.newInstance())
                        .commit();
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ProfileFragment.newInstance())
                        .commit();
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, EmotionsFragment.newInstance())
                        .commit();
                break;
            case 3:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, TlatoqueFragment.newInstance())
                        .commit();
                break;

            case 4:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, LocationsFragment.newInstance())
                        .commit();
                break;

            case 5:

                Session session = Util.getSession();

                session.setUser(null);
                session.setLeafs(null);
                session.save();

                try {
                    MainApp.turnOff();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);


                break;
        }



    }

    public void onSectionAttached(int number)
    {
        switch (number) {
            case 0:
                mTitle = getString(R.string.title_section1);
                break;
            case 1:
                mTitle = getString(R.string.title_section2);
                break;
            case 2:
                mTitle = getString(R.string.title_section3);
                break;
            case 3:
                mTitle = getString(R.string.title_section4);
                break;
            case 4:
                mTitle = getString(R.string.title_section5);
                break;
        }
    }

    public void restoreActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (!mNavigationDrawerFragment.isDrawerOpen())
        {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.emotion_mingle, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
