package com.unaiamutxastegi.todolistfragment.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cursomovil on 23/03/15.
 */
public class ToDo implements Parcelable {

    private int mData;
    private String tarea;
    private Date created;


    public ToDo(String tarea) {
        this.tarea = tarea;
        this.created = new Date();
    }

    public String getTarea() {
        return tarea;
    }

    public Date getCreated() {
        return created;
    }

    public String getCreatedFormated(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(this.created);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tarea);
        dest.writeLong(created.getTime());
    }

    public static final Parcelable.Creator<ToDo> CREATOR
            = new Parcelable.Creator<ToDo>() {
        public ToDo createFromParcel(Parcel in) {
            return new ToDo(in);
        }

        public ToDo[] newArray(int size) {
            return new ToDo[size];
        }
    };

    private ToDo(Parcel in) {
        tarea = in.readString();
        created = new Date(in.readLong());
    }
}
