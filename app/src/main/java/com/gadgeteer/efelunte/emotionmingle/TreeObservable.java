package com.gadgeteer.efelunte.emotionmingle;

import java.util.Observable;

/**
 * Created by ismaelvalenzuela on 26-05-15.
 */
public class TreeObservable extends Observable {

    @Override
    public void notifyObservers()
    {
        this.setChanged();
        super.notifyObservers();
    }
}
