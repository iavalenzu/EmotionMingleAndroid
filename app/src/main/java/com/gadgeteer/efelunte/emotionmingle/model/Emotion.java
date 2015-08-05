package com.gadgeteer.efelunte.emotionmingle.model;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by ismaelvalenzuela on 03-05-15.
 */
public class Emotion extends SugarRecord<Emotion> {


    static public final String SAD = "Sad";
    static public final String TIRED = "Tired";
    static public final String STRESSED = "Stressed";
    static public final String ANGRY = "Angry";
    static public final String HAPPY = "Happy";
    static public final String ENERGETIC = "Energetic";
    static public final String RELAXED = "Relaxed";
    static public final String CALMED = "Calmed";


    private String type;
    private Date created;
    private User user;

    public Emotion(){


    }

    public Emotion(String type, User user)
    {
        this.type = type;
        this.user = user;
        this.created = new Date();
    }


    public boolean isType(String type)
    {
        return this.type.equals(type);
    }

    public String getType() {
        return type;
    }

    public Date getDate() {
        return created;
    }
}
