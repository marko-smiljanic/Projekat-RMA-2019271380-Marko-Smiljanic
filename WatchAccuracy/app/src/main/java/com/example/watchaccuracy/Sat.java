package com.example.watchaccuracy;

import java.util.ArrayList;

public class Sat {
    //podaci za bazu
    public static final String TABLE_NAME = "sat";

    public static final String FIELD_ID = "id";             //obavezno
    public static final String FIELD_MARKA = "marka";       //obavezno
    public static final String FIELD_MODEL = "model";


    private int satId;
    private String marka;
    private String model;
    private ArrayList<Checkpoint> listaCheckpointa;      //ne cuvam u bazi sata, ucitava se iz baze checkpointa (pretraga po id-ju sata)


    public Sat(){}
    public Sat(int satId, String marka, String model) {
        this.satId = satId;
        this.marka = marka;
        this.model = model;
        this.listaCheckpointa = null;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ArrayList<Checkpoint> getListaCheckpointa() {
        return listaCheckpointa;
    }

    public void setListaCheckpointa(ArrayList<Checkpoint> listaCheckpointa) {
        this.listaCheckpointa = listaCheckpointa;
    }

    public int getSatId() {
        return satId;
    }

    public void setSatId(int satId) {
        this.satId = satId;
    }




}
