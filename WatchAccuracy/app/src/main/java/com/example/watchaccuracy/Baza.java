package com.example.watchaccuracy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Baza extends SQLiteOpenHelper {
    public static final String BAZA_IME = "watchAccuracyBaza.sqlite";

    public Baza (Context cont){                                    //konstruktor mora da se redefinise sa super konstruktorom
        super(cont, BAZA_IME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL1 = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT);",
                Sat.TABLE_NAME, Sat.FIELD_ID, Sat.FIELD_MARKA, Sat.FIELD_MODEL);

        String SQL2 = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s DATETIME, %s DATETIME, %s DATETIME, %s DATETIME, %s DATETIME, %s DATETIME, %s DATETIME, %s TEXT, %s INTEGER NOT NULL);",
                Checkpoint.TABLE_NAME, Checkpoint.FIELD_ID, Checkpoint.FIELD_PRVO_VREME_SISTEMSKO, Checkpoint.FIELD_PRVO_VREME_NA_SATU,
                Checkpoint.FIELD_PRVO_ODSTUPANJE, Checkpoint.FIELD_DRUGO_VREME_SISTEMSKO, Checkpoint.FIELD_DRUGO_VREME_NA_SATU, Checkpoint.FIELD_DRUGO_ODSTUPANJE,
                Checkpoint.FIELD_KONACNO_ODSTUPANJE, Checkpoint.FIELD_OPIS, Checkpoint.FIELD_SAT_ID);

        db.execSQL(SQL1);
        db.execSQL(SQL2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s;", Sat.TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s;", Checkpoint.TABLE_NAME));

        onCreate(db);
    }

    public void brisiSve(SQLiteDatabase db) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s;", Sat.TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s;", Checkpoint.TABLE_NAME));

        onCreate(db);                                       //on create obavezno moram pozvati na kraju ako brisem sadrzaj iz tabela!!!!!
    }



}
