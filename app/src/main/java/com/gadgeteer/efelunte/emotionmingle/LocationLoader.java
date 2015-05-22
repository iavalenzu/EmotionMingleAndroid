package com.gadgeteer.efelunte.emotionmingle;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.gadgeteer.efelunte.emotionmingle.model.Location;
import com.gadgeteer.efelunte.emotionmingle.model.Session;
import com.gadgeteer.efelunte.emotionmingle.model.User;
import com.gadgeteer.efelunte.emotionmingle.utils.Util;

import java.util.List;

/**
 * Created by ismaelvalenzuela on 19-05-15.
 */
public class LocationLoader extends AsyncTaskLoader<List<Location>> {

    public LocationLoader(Context context)
    {
        super(context);
    }

    @Override
    public List<Location> loadInBackground() {


        Session session = Util.getSession();

        if(session != null)
        {
            User loggedUser = session.getUser();

            if (loggedUser != null)
            {
                return loggedUser.getLocations();

            }
        }

        return null;

    }
}
