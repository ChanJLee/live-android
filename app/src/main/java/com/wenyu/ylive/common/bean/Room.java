package com.wenyu.ylive.common.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

/**
 * Created by chan on 17/4/5.
 */
@Keep
public class Room implements Parcelable {
    public long id;
    public String title;
    public String snapshot;
    public String anchor;
    public int audienceCount;
    public String liveUrl;
    public int anchorId;

    public Room() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.snapshot);
        dest.writeString(this.anchor);
        dest.writeInt(this.audienceCount);
        dest.writeString(this.liveUrl);
        dest.writeInt(this.anchorId);
    }

    protected Room(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.snapshot = in.readString();
        this.anchor = in.readString();
        this.audienceCount = in.readInt();
        this.liveUrl = in.readString();
        this.anchorId = in.readInt();
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel source) {
            return new Room(source);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };
}
