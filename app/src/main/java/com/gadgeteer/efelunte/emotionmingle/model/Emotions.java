package com.gadgeteer.efelunte.emotionmingle.model;

import com.orm.SugarRecord;

/**
 * Created by ismaelvalenzuela on 03-05-15.
 */
public class Emotions extends SugarRecord<Emotions> {


    int sad = 0;
    int tired = 0;
    int stressed = 0;
    int angry = 0;
    int happy = 0;
    int energetic = 0;
    int relaxed = 0;
    int calmed = 0;

    String lastEmotion = "";

    public Emotions()
    {

    }

    public int getSad() {
        return sad;
    }

    public void setSad(int sad) {
        this.sad = sad;
    }

    public int getTired() {
        return tired;
    }

    public void setTired(int tired) {
        this.tired = tired;
    }

    public int getStressed() {
        return stressed;
    }

    public void setStressed(int stressed) {
        this.stressed = stressed;
    }

    public int getAngry() {
        return angry;
    }

    public void setAngry(int angry) {
        this.angry = angry;
    }

    public int getHappy() {
        return happy;
    }

    public void setHappy(int happy) {
        this.happy = happy;
    }

    public int getEnergetic() {
        return energetic;
    }

    public void setEnergetic(int energetic) {
        this.energetic = energetic;
    }

    public int getRelaxed() {
        return relaxed;
    }

    public void setRelaxed(int relaxed) {
        this.relaxed = relaxed;
    }

    public int getCalmed() {
        return calmed;
    }

    public void setCalmed(int calmed) {
        this.calmed = calmed;
    }

    public String getLastEmotion() {
        return lastEmotion;
    }

    public void setLastEmotion(String lastEmotion) {
        this.lastEmotion = lastEmotion;
    }
}
