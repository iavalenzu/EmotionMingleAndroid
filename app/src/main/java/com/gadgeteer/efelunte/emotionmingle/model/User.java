package com.gadgeteer.efelunte.emotionmingle.model;

import com.orm.SugarRecord;

/**
 * Created by ismaelvalenzuela on 03-05-15.
 */
public class User extends SugarRecord<User> {

    String email;
    String firstname;
    String lastname;
    String pass;
    String birthday;

    Emotions emotions;

    public User(){

    }

    public User(String email, String firstname, String lastname, String birthday, String pass, Emotions emotions){
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.pass = pass;
        this.emotions = emotions;

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

    public Emotions getEmotions() {
        return emotions;
    }

    public void setEmotions(Emotions emotions) {
        this.emotions = emotions;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

}
