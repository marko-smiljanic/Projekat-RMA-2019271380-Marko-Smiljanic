package com.example.watchaccuracy;

import java.util.Date;

public class Checkpoint {
    //podaci za bazu
    public static final String TABLE_NAME = "checkpoint";

    public static final String FIELD_ID = "id";                                                 //obavezno
    public static final String FIELD_PRVO_VREME_SISTEMSKO = "prvo_vreme_sistemsko";             //obavezno
    public static final String FIELD_PRVO_VREME_NA_SATU = "prvo_vreme_na_satu";                 //obavezno
    public static final String FIELD_PRVO_ODSTUPANJE = "prvo_odstupanje";                       //obavezo
    public static final String FIELD_DRUGO_VREME_SISTEMSKO = "drugo_vreme_sistemsko";
    public static final String FIELD_DRUGO_VREME_NA_SATU = "drugo_vreme_na_satu";
    public static final String FIELD_DRUGO_ODSTUPANJE = "drugo_odstupanje";
    public static final String FIELD_KONACNO_ODSTUPANJE = "konacno_odstupanje";
    public static final String FIELD_OPIS = "opis";
    public static final String FIELD_SAT_ID = "sat_id";                                         //obavezno


    //znaci da bih dobio odstupanje sata korisnik ce svaki put morati da unosi koje je vreme na satu
    //necu da pravim odnose izmedju razlicitih checkpoint-a jer bi dosta zakoplikovao sve
    //znaci jedan ckeckpoint ima jedno merenje odstupanja. Za sledece merenje odstupanja se pravi
    //novi checkpoint i sve iz pocetka
    private int checkpointId;
    private String prvoVremeSistemsko;                                                //pocetno, tj. prvo vreme (sistemsko vreme koje je bilo na satu u trenutku kreiranja checkpointa)
    private String prvoVremeNaSatu;
    private String prvoOdstupanje;                                                    //razlika izmedju prvo vreme sistemsko i prvo vreme checkirano na satu
    private String drugoVremeSistemsko;
    private String drugoVremeNaSatu;
    private String drugoOdstupanje;                                                   //isto samo za drugo
    private String konacnoOdstupanje;                                                 //odnos izmedju prvog odstupanja i drugog daje konacno odstupanje u radu
    private String opis;
    private int satId;


    public Checkpoint(){}

    public Checkpoint(int checkpointId, String prvoVremeSistemsko, String prvoVremeNaSatu, String prvoOdstupanje,
                      String drugoVremeSistemsko, String drugoVremeNaSatu, String drugoOdstupanje,
                      String konacnoOdstupanje, String opis, int satId) {
        this.checkpointId = checkpointId;
        this.prvoVremeSistemsko = prvoVremeSistemsko;
        this.prvoVremeNaSatu = prvoVremeNaSatu;
        this.prvoOdstupanje = prvoOdstupanje;
        this.opis = opis;
        this.drugoVremeSistemsko = drugoVremeSistemsko;
        this.drugoVremeNaSatu = drugoVremeNaSatu;
        this.drugoOdstupanje = drugoOdstupanje;
        this.konacnoOdstupanje = konacnoOdstupanje;
        this.satId = satId;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public int getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(int checkpointId) {
        this.checkpointId = checkpointId;
    }

    public String getPrvoVremeSistemsko() {
        return prvoVremeSistemsko;
    }

    public void setPrvoVremeSistemsko(String prvoVremeSistemsko) {
        this.prvoVremeSistemsko = prvoVremeSistemsko;
    }

    public String getPrvoVremeNaSatu() {
        return prvoVremeNaSatu;
    }

    public void setPrvoVremeNaSatu(String prvoVremeNaSatu) {
        this.prvoVremeNaSatu = prvoVremeNaSatu;
    }

    public String getPrvoOdstupanje() {
        return prvoOdstupanje;
    }

    public void setPrvoOdstupanje(String prvoOdstupanje) {
        this.prvoOdstupanje = prvoOdstupanje;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getDrugoVremeSistemsko() {
        return drugoVremeSistemsko;
    }

    public void setDrugoVremeSistemsko(String drugoVremeSistemsko) {
        this.drugoVremeSistemsko = drugoVremeSistemsko;
    }

    public String getDrugoVremeNaSatu() {
        return drugoVremeNaSatu;
    }

    public void setDrugoVremeNaSatu(String drugoVremeNaSatu) {
        this.drugoVremeNaSatu = drugoVremeNaSatu;
    }

    public String getDrugoOdstupanje() {
        return drugoOdstupanje;
    }

    public void setDrugoOdstupanje(String drugoOdstupanje) {
        this.drugoOdstupanje = drugoOdstupanje;
    }

    public String getKonacnoOdstupanje() {
        return konacnoOdstupanje;
    }

    public void setKonacnoOdstupanje(String konacnoOdstupanje) {
        this.konacnoOdstupanje = konacnoOdstupanje;
    }

    public int getSatId() {
        return satId;
    }

    public void setSatId(int satId) {
        this.satId = satId;
    }

    public void izracunajKonacnoOdstupanje(Date prvoOdstupanje, Date drugoOdstupanje) {  //ovde bi trebalo da prima prvo i drugo odstupanje i da na onsovu njega racuna i setuje konacno

        //naravno ovo ovako ne moze moram videti kako se racuna i radi sa date objektima
        //this.konacnoOdstupanje = prvoOdstupanje - drugoOdstupanje;
    }





}
