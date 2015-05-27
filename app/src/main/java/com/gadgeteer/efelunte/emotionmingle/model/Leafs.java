package com.gadgeteer.efelunte.emotionmingle.model;

import android.util.Log;

import com.gadgeteer.efelunte.emotionmingle.EmotionMingle;
import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by ismaelvalenzuela on 05-05-15.
 */
public class Leafs extends SugarRecord<Leafs> {

    private int leaf1 = 0;
    private int leaf2 = 0;
    private int leaf3 = 0;
    private int leaf4 = 0;
    private int leaf5 = 0;
    private int leaf6 = 0;
    private int leaf7 = 0;
    private int leaf8 = 0;

    private Date lastUpdate = new Date();



    public Leafs()
    {
    }

    public int getLeaf1() {
        return leaf1;
    }

    public void setLeaf1(int leaf1) {
        this.leaf1 = leaf1;
    }

    public int getLeaf2() {
        return leaf2;
    }

    public void setLeaf2(int leaf2) {
        this.leaf2 = leaf2;
    }

    public int getLeaf3() {
        return leaf3;
    }

    public void setLeaf3(int leaf3) {
        this.leaf3 = leaf3;
    }

    public int getLeaf4() {
        return leaf4;
    }

    public void setLeaf4(int leaf4) {
        this.leaf4 = leaf4;
    }

    public int getLeaf5() {
        return leaf5;
    }

    public void setLeaf5(int leaf5) {
        this.leaf5 = leaf5;
    }

    public int getLeaf6() {
        return leaf6;
    }

    public void setLeaf6(int leaf6) {
        this.leaf6 = leaf6;
    }

    public int getLeaf7() {
        return leaf7;
    }

    public void setLeaf7(int leaf7) {
        this.leaf7 = leaf7;
    }

    public int getLeaf8() {
        return leaf8;
    }

    public void setLeaf8(int leaf8) {
        this.leaf8 = leaf8;
    }

    public void setLeafValue(int hoja, int value) {

        Log.i(EmotionMingle.TAG, "Leafs > setLeafValue(" + hoja + "," + value + ")");

        switch (hoja)
        {
            case 1:
                setLeaf1(value);
                break;
            case 2:
                setLeaf2(value);
                break;
            case 3:
                setLeaf3(value);
                break;
            case 4:
                setLeaf4(value);
                break;
            case 5:
                setLeaf5(value);
                break;
            case 6:
                setLeaf6(value);
                break;
            case 7:
                setLeaf7(value);
                break;
            case 8:
                setLeaf8(value);
                break;
        }

    }

    public int getLeafValue(int hoja)
    {
        switch (hoja)
        {
            case 1:
                return leaf1;
            case 2:
                return leaf2;
            case 3:
                return leaf3;
            case 4:
                return leaf4;
            case 5:
                return leaf5;
            case 6:
                return leaf6;
            case 7:
                return leaf7;
            case 8:
                return leaf8;
            default:
                return 0;
        }

    }

    @Override
    public void save() {

        this.lastUpdate = new Date();

        super.save();
    }

    public void reset()
    {
        leaf1 = 0;
        leaf2 = 0;
        leaf3 = 0;
        leaf4 = 0;
        leaf5 = 0;
        leaf6 = 0;
        leaf7 = 0;
        leaf8 = 0;
    }

    public Date getLastUpdate()
    {
        return this.lastUpdate;
    }

}
