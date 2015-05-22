package com.gadgeteer.efelunte.emotionmingle.model;

import com.orm.SugarRecord;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by ismaelvalenzuela on 03-05-15.
 */
public class User extends SugarRecord<User> {

    String email;
    String firstname;
    String lastname;
    String pass;
    String birthday;

    public User(){

    }

    public User(String email, String firstname, String lastname, String birthday, String pass){
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.pass = pass;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    public long getEmotionCount(String emotion)
    {
        return Emotion.count(Emotion.class, "type = ? and user = ?", new String[]{ emotion, String.valueOf(this.getId())});
    }

    public String getMaxCountEmotion()
    {

        Hashtable<String, Long> emotions = new Hashtable<String, Long>();

        emotions.put(Emotion.SAD, getEmotionCount(Emotion.SAD));
        emotions.put(Emotion.TIRED, getEmotionCount(Emotion.TIRED));
        emotions.put(Emotion.STRESSED, getEmotionCount(Emotion.STRESSED));
        emotions.put(Emotion.ANGRY, getEmotionCount(Emotion.ANGRY));
        emotions.put(Emotion.HAPPY, getEmotionCount(Emotion.HAPPY));
        emotions.put(Emotion.ENERGETIC, getEmotionCount(Emotion.ENERGETIC));
        emotions.put(Emotion.RELAXED, getEmotionCount(Emotion.RELAXED));
        emotions.put(Emotion.CALMED, getEmotionCount(Emotion.CALMED));


        Set<String> keys = emotions.keySet();
        int max = 0;
        String maxKey = null;

        for(String key: keys)
        {
            Long value = emotions.get(key);

            if(value.intValue() > max)
            {
                max = value.intValue();
                maxKey = key;

            }

        }

        return maxKey;

    }

    public Emotion getLastEmotion()
    {
        List<Emotion> emotions = Emotion.find(Emotion.class, "user = ?", new String[]{ String.valueOf(this.getId())}, null, "created DESC", "1");

        if(emotions == null)
        {
            return null;
        }

        if(emotions.isEmpty())
        {
            return null;
        }

        return emotions.get(0);

    }

    public Location getLastLocation()
    {
        List<Location> locations = Location.find(Location.class, "user = ?", new String[]{ String.valueOf(this.getId())}, null, "created DESC", "1");

        if(locations == null)
        {
            return null;
        }

        if(locations.isEmpty())
        {
            return null;
        }

        return locations.get(0);

    }

    public List<Location> getLocations()
    {
        List<Location> locations = Location.find(Location.class, "user = ?", new String[]{ String.valueOf(this.getId())}, null, "created DESC", null);

        return locations;
    }



    public void deselectAllLocations()
    {
        List<Location> locations = Location.find(Location.class, "user = ?", new String[]{String.valueOf(this.getId())}, null, null, null);

        if(locations == null || locations.isEmpty())
        {
            return;
        }

        Iterator<Location> iterator = locations.iterator();

        if(iterator == null)
        {
            return;
        }

        while(iterator.hasNext())
        {
            Location location = iterator.next();
            location.setSelected(false);
            location.save();
        }

    }

    public Location getSelectedLocation()
    {
        List<Location> locations = Location.find(Location.class, "user = ? AND selected = ?", new String[]{ String.valueOf(this.getId()), "1"}, null, null, "1");

        if(locations == null || locations.isEmpty())
        {
            return null;
        }

        return locations.get(0);

    }





}
