package com.gadgeteer.efelunte.emotionmingle.utils;

import com.gadgeteer.efelunte.emotionmingle.model.Session;
import com.gadgeteer.efelunte.emotionmingle.model.User;

import java.util.Iterator;

/**
 * Created by ismaelvalenzuela on 03-05-15.
 */
public class Util {

    static public Session getSession()
    {
        Iterator<Session> iterator = Session.findAll(Session.class);

        if(iterator.hasNext()){
            return iterator.next();
        }

        Session session = new Session();

        return session;
    }


    static public User login(String email, String pass)
    {
        Iterator<User> users = User.findAll(User.class);

        while(users.hasNext())
        {
            User user = users.next();

            if(user.getEmail().equals(email) && user.getPass().equals(pass))
            {
                return user;
            }
        }

        return null;
    }




}
