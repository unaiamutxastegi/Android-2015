package com.unaiamutxastegi.earthquakes.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by cursomovil on 25/03/15.
 */
public class EarthQuake {

    private Coordinate coords;
    private double magnitude;
    private Date time;
    private String _id;
    private String place;
    private String url;

    public EarthQuake(){

    }

    public EarthQuake(Coordinate coords, double magnitude, Date time, String _id) {
        this.coords = coords;
        this.magnitude = magnitude;
        this.time = time;
        this._id = _id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setCoords(Coordinate coords) {
        this.coords = coords;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setTime(long time) {
        this.time = new Date(time);
    }

    public void set_id(String _id) {
        this._id = _id;
    }
    public String getUrl() {
        return url;
    }
    public String getPlace() {

        return place;
    }

    @Override
    public String toString() {
        return this.getPlace();
    }

    public Coordinate getCoords() {

        return coords;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public Date getTime() {
        return time;
    }

    public String get_id() {
        return _id;
    }

}
