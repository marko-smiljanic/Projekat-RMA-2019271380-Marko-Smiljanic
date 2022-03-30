package com.example.watchaccuracy;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

public class BazaCheckpoint {
    private Baza baza;

    //pretvaranje u format za datetime
//    java.util.Date date = new Date();
//    Object param = new java.sql.Timestamp(date.getTime());
//    // The JDBC driver knows what to do with a java.sql type:

    public BazaCheckpoint(Baza db){
        this.baza = db;
    }

    public void addCheckpoint(String prvoVremeSistemsko, String prvoVremeNaSatu, String prvoOdstupanje,
                              String drugoVremeSistemsko, String drugoVremeNaSatu, String drugoOdstupanje,
                              String konacnoOdstupanje, String opis, int satId){
        SQLiteDatabase db = baza.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Checkpoint.FIELD_PRVO_VREME_SISTEMSKO, prvoVremeSistemsko);
        cv.put(Checkpoint.FIELD_PRVO_VREME_NA_SATU, prvoVremeNaSatu);
        cv.put(Checkpoint.FIELD_PRVO_ODSTUPANJE, prvoOdstupanje);
        cv.put(Checkpoint.FIELD_DRUGO_VREME_SISTEMSKO, drugoVremeSistemsko);
        cv.put(Checkpoint.FIELD_DRUGO_VREME_NA_SATU, drugoVremeNaSatu);
        cv.put(Checkpoint.FIELD_DRUGO_ODSTUPANJE, drugoOdstupanje);
        cv.put(Checkpoint.FIELD_KONACNO_ODSTUPANJE, konacnoOdstupanje);
        cv.put(Checkpoint.FIELD_OPIS, opis);
        cv.put(Checkpoint.FIELD_SAT_ID, satId);

        db.insert(Checkpoint.TABLE_NAME, null, cv);
    }

    public void editCheckpoint(int checkpointId, String prvoVremeSistemsko, String prvoVremeNaSatu, String prvoOdstupanje,
                               String drugoVremeSistemsko, String drugoVremeNaSatu, String drugoOdstupanje,
                               String konacnoOdstupanje, String opis, int satId){
        SQLiteDatabase db = baza.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Checkpoint.FIELD_PRVO_VREME_SISTEMSKO, prvoVremeSistemsko);
        cv.put(Checkpoint.FIELD_PRVO_VREME_NA_SATU, prvoVremeNaSatu);
        cv.put(Checkpoint.FIELD_PRVO_ODSTUPANJE, prvoOdstupanje);
        cv.put(Checkpoint.FIELD_DRUGO_VREME_SISTEMSKO, drugoVremeSistemsko);
        cv.put(Checkpoint.FIELD_DRUGO_VREME_NA_SATU, drugoVremeNaSatu);
        cv.put(Checkpoint.FIELD_DRUGO_ODSTUPANJE, drugoOdstupanje);
        cv.put(Checkpoint.FIELD_KONACNO_ODSTUPANJE, konacnoOdstupanje);
        cv.put(Checkpoint.FIELD_OPIS, opis);
        cv.put(Checkpoint.FIELD_SAT_ID, satId);

        db.update(Checkpoint.TABLE_NAME, cv, Checkpoint.FIELD_ID + "=?", new String[] {String.valueOf(checkpointId)});
    }

    public int deleteCheckpoint(int checkpointId){
        int numDeleted = 0;
        SQLiteDatabase db = baza.getWritableDatabase();

        numDeleted = db.delete(Checkpoint.TABLE_NAME, Checkpoint.FIELD_ID + "=?", new String[] {String.valueOf(checkpointId)});
        return numDeleted;
    }

    public ArrayList<Checkpoint> getAllCheckpointi(int satId){   //dohvatanje svih ali po id-ju sata
        SQLiteDatabase db = baza.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = ?", Checkpoint.TABLE_NAME, Checkpoint.FIELD_SAT_ID);
        Cursor result = db.rawQuery(query, new String[] {String.valueOf(satId)});
        result.moveToFirst();

        ArrayList<Checkpoint> list = new ArrayList<Checkpoint>(result.getCount());
        while(!result.isAfterLast()){
            @SuppressLint("Range") int checkpointId = result.getInt(result.getColumnIndex(Checkpoint.FIELD_ID));
            @SuppressLint("Range") String prvoVremeSistemsko = result.getString(result.getColumnIndex(Checkpoint.FIELD_PRVO_VREME_SISTEMSKO));
            @SuppressLint("Range") String prvoVremeNaSatu = result.getString(result.getColumnIndex(Checkpoint.FIELD_PRVO_VREME_NA_SATU));
            @SuppressLint("Range") String prvoOdstupanje = result.getString(result.getColumnIndex(Checkpoint.FIELD_PRVO_ODSTUPANJE));
            @SuppressLint("Range") String drugoVremeSistemsko = result.getString(result.getColumnIndex(Checkpoint.FIELD_DRUGO_VREME_SISTEMSKO));
            @SuppressLint("Range") String drugoNaSatu = result.getString(result.getColumnIndex(Checkpoint.FIELD_DRUGO_VREME_NA_SATU));
            @SuppressLint("Range") String drugoOdstupanje = result.getString(result.getColumnIndex(Checkpoint.FIELD_DRUGO_ODSTUPANJE));
            @SuppressLint("Range") String konacnoOdstupanje = result.getString(result.getColumnIndex(Checkpoint.FIELD_KONACNO_ODSTUPANJE));
            @SuppressLint("Range") String opis = result.getString(result.getColumnIndex(Checkpoint.FIELD_OPIS));
            //sat id ne moram da dohvatam jer ga imam prosledjenog funkciji

            Checkpoint cc = new Checkpoint(checkpointId, prvoVremeSistemsko, prvoVremeNaSatu, prvoOdstupanje, drugoVremeSistemsko,
                                        drugoNaSatu, drugoOdstupanje, konacnoOdstupanje, opis, satId);
            list.add(cc);
            result.moveToNext();
        }
        return list;
    }

    public void brisiSve(){
        SQLiteDatabase db = baza.getWritableDatabase();

        String query = String.format("DELETE FROM %s", Checkpoint.TABLE_NAME);
        db.execSQL(query);
    }





}
