package com.gadgeteer.efelunte.emotionmingle.model;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ismaelvalenzuela on 19-05-15.
 */
public class Location extends SugarRecord<Location> {

    static public final String TYPE_RECREACIONAL = "Recreacional";
    static public final String TYPE_PERSONAL = "Personal";
    static public final String TYPE_DEPORTE = "Deporte";
    static public final String TYPE_CUIDADO = "Cuidado";


    public String description;
    public String type;
    public Date created;
    public float latitude;
    public float longitude;

    public User user;
    private boolean selected;

    public Location()
    {

    }

    public Location(User user, String description, String type, float latitude, float longitude)
    {
        this.user = user;
        this.description = description;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.created = new Date();
        this.selected = false;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }
}
