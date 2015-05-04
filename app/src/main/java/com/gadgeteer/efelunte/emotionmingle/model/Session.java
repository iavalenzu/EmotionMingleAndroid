package com.gadgeteer.efelunte.emotionmingle.model;

import com.orm.SugarRecord;

/**
 * Created by ismaelvalenzuela on 03-05-15.
 */
public class Session extends SugarRecord<Session> {

    User user;

    public Session(){

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
