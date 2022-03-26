package com.example.watchaccuracy;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BazaSat {
    private Baza baza;

    public BazaSat(Baza db){
        this.baza = db;
    }

    public void addSat(String marka, String model){
        SQLiteDatabase db = baza.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Sat.FIELD_MARKA, marka);
        cv.put(Sat.FIELD_MODEL, model);

        db.insert(Sat.TABLE_NAME, null, cv);
    }

    public void editSat(int satId, String marka, String model){
        SQLiteDatabase db = baza.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Sat.FIELD_MARKA, marka);
        cv.put(Sat.FIELD_MODEL, model);

        db.update(Sat.TABLE_NAME, cv, Sat.FIELD_ID + "=?", new String[] {String.valueOf(satId)});
    }

    public int deleteSat(int satId){
        int numDeleted = 0;
        SQLiteDatabase db = baza.getWritableDatabase();

        numDeleted = db.delete(Sat.TABLE_NAME, Sat.FIELD_ID + "=?", new String[] {String.valueOf(satId)});
        return numDeleted;
    }

    //ne znam da li cemi ovo trebati
    public Sat getOneSat (int satId){
        SQLiteDatabase db = baza.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = ?", Sat.TABLE_NAME, Sat.FIELD_ID);
        Cursor result = db.rawQuery(query, new String[] {String.valueOf(satId)});

        if(result.moveToFirst()) {
            @SuppressLint("Range") String marka = result.getString(result.getColumnIndex(Sat.FIELD_MARKA));
            @SuppressLint("Range") String model = result.getString(result.getColumnIndex(Sat.FIELD_MODEL));
            return new Sat(satId, marka, model);
        } else{
            return null;
        }
    }

    public ArrayList<Sat> getAllSatovi(){
        SQLiteDatabase db = baza.getReadableDatabase();
        String query = String.format("SELECT * FROM %s", Sat.TABLE_NAME);
        Cursor result = db.rawQuery(query, null);
        result.moveToFirst();

        ArrayList<Sat> list = new ArrayList<Sat>(result.getCount());
        while(!result.isAfterLast()){
            @SuppressLint("Range") int satId = result.getInt(result.getColumnIndex(Sat.FIELD_ID));
            @SuppressLint("Range") String marka = result.getString(result.getColumnIndex(Sat.FIELD_MARKA));
            @SuppressLint("Range") String model = result.getString(result.getColumnIndex(Sat.FIELD_MODEL));

            list.add(new Sat(satId, marka, model));
            result.moveToNext();
        }
        return list;
    }

    public void brisiSve(){
        SQLiteDatabase db = baza.getWritableDatabase();

        String query = String.format("DELETE FROM %s", Sat.TABLE_NAME);
        db.execSQL(query);
    }



}
