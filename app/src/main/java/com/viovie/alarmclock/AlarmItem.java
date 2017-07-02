package com.viovie.alarmclock;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class AlarmItem implements Parcelable {
    public Calendar datetime;
    public boolean isRepeat;
    public boolean[] weekRepeat;
    public String title;
    public String content;

    public AlarmItem() {
        datetime = Calendar.getInstance();
        isRepeat = false;
        weekRepeat = new boolean[7];
        title = "";
        content = "";
    }

    public static final Parcelable.Creator<AlarmItem> CREATOR = new Creator<AlarmItem>() {
        @Override
        public AlarmItem createFromParcel(Parcel source) {
            AlarmItem ai = new AlarmItem();
            ai.datetime = (Calendar) source.readSerializable();
            ai.isRepeat = source.readByte() == 1;
            source.readBooleanArray(ai.weekRepeat);
            ai.title = source.readString();
            ai.content = source.readString();
            return ai;
        }

        @Override
        public AlarmItem[] newArray(int size) {
            return new AlarmItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(datetime);
        dest.writeByte((byte) (isRepeat ? 1 : 0));
        dest.writeBooleanArray(weekRepeat);
        dest.writeString(title);
        dest.writeString(content);
    }

    public String getDate() {
        return String.format("%d-%d-%d",
                datetime.get(Calendar.YEAR),
                datetime.get(Calendar.MONTH) + 1,
                datetime.get(Calendar.DAY_OF_MONTH));
    }

    public String getTime() {
        return String.format("%d:%d",
                datetime.get(Calendar.HOUR_OF_DAY),
                datetime.get(Calendar.MINUTE));
    }
}
