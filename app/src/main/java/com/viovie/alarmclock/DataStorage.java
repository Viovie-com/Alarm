package com.viovie.alarmclock;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private static final String FILENAME = "alarm.items";

    public static void save(Context context, List<AlarmItem> list) {
        String data = new Gson().toJson(list);
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<AlarmItem> read(Context context) {
        List<AlarmItem> list = null;

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            String data = sb.toString();
            Type listType = new TypeToken<ArrayList<AlarmItem>>(){}.getType();
            list = new Gson().fromJson(data, listType);
        } catch (IOException e) {
            list = new ArrayList<>();
            e.printStackTrace();
        }

        return list;
    }

    /**
     *
     * @param context
     * @param item
     * @return position
     */
    public static int add(Context context, AlarmItem item) {
        List<AlarmItem> list = read(context);
        list.add(item);
        save(context, list);
        return list.size()-1;
    }

    public static void update(Context context, int position, AlarmItem item) {
        List<AlarmItem> list = read(context);
        if (position >= list.size()) return;

        list.set(position, item);
        save(context, list);
    }

    public static void delete(Context context, int position) {
        List<AlarmItem> list = read(context);
        if (position >= list.size()) return;

        list.remove(position);
        save(context, list);
    }
}
