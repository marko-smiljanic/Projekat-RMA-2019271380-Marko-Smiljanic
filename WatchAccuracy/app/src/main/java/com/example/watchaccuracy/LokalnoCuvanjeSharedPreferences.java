package com.example.watchaccuracy;

import android.content.Context;
import android.content.SharedPreferences;

public class LokalnoCuvanjeSharedPreferences {                                                      //cisto da konstante imam na jednom mestu i da im pristupam iskljucivo preko ove klase

    public final static String SHARED_PREFERENCES_PREFIX = "SharedPreferences";                     //ime fajla (sme biti samo jedan fajl)
    //ime kljuceva pod kojima se cuvaju vrednosti
    public final static String SHARED_PREFERENCES_ULOGOVAN = "ulogovanUkljuc";
    public final static String SHARED_PREFERENCES_KLIKNUTO_DUGME = "kliknutoDugme";


    public static String ucitajDaLiJeUlogovan(Context context){                                     //context je za fragment klasu getActivity(), za view model getApplication()
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_PREFIX, 0);  //getaplication mora da se pise jer se to u main activity-ju kada sam radio podrazumevalo a ovo je view model
        String zz = sharedPreferences.getString(SHARED_PREFERENCES_ULOGOVAN, "nije");               //ako ne nadje nista bice postavljeno nije
        if(zz.equals("nije")){  //ako je vrednost default-na
            return "nije";
        }else{
            return "jeste";
        }
    }


    public static void sacuvajKojeJeKliknutoDugme(Context context, String kojeJeDugme){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_PREFIX, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_KLIKNUTO_DUGME, kojeJeDugme);
        editor.commit();
    }

    public static String ucitajKojeJeKliknutoDugme(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LokalnoCuvanjeSharedPreferences.SHARED_PREFERENCES_PREFIX, 0);
        String zz = sharedPreferences.getString(LokalnoCuvanjeSharedPreferences.SHARED_PREFERENCES_KLIKNUTO_DUGME, "prazno");

        if(zz.equals("prazno")){
            System.out.println("Greska u citanju iz shared preferences!!");
            return "prazno";
        }
       return zz;
    }





}