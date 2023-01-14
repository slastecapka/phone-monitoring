package com.example.sensors_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.sensors_android.DataBaseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DB {

    //удаление всех записей из базы данных
    public static void deleteAllRecords(Context context){
        DataBaseHelper dataBaseHelper;
        SQLiteDatabase bd;
        dataBaseHelper = new DataBaseHelper(context);
        try {
            dataBaseHelper.updateDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bd = dataBaseHelper.getReadableDatabase();
        bd.execSQL("DELETE FROM sensors_table");

    }


    //получим все данные из базы данных в виде списка
    public static List<String> getDataFromBD(Context context){
        DataBaseHelper dataBaseHelper;
        SQLiteDatabase bd;
        dataBaseHelper = new DataBaseHelper(context);
        try {
            dataBaseHelper.updateDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bd = dataBaseHelper.getReadableDatabase();
        Cursor cursor;
        cursor = bd.rawQuery("SELECT * FROM sensors_table",null);

        List<String> list = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            list.add(cursor.getString(1) + " (type " + cursor.getString(2) + ")");
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    //INSERT запрос для таблицы Сенсоров
    public static void addSensor(String sensor, int type, Context context){
        DataBaseHelper databaseHelper;
        SQLiteDatabase bd;
        databaseHelper = new DataBaseHelper(context);
        try {
            databaseHelper.updateDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bd = databaseHelper.getReadableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("sensor", sensor);
        newValues.put("type", type);
        bd.insert("sensors_table", null, newValues);
    }





}

