package com.viovie.alarmclock;

import android.content.Context;

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

public class DataSorage {
    private static final String FILENAME = "alarm.items";

    public static void save(Context context, List<AlarmItem> list) {
        String data = new Gson().toJson(list);
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {

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
        }

        return list;
    }
}
