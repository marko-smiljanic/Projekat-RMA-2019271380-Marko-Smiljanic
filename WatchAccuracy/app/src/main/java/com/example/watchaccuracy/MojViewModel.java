package com.example.watchaccuracy;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MojViewModel extends AndroidViewModel {               //da bih mogao da dobavim kontekst
    private MutableLiveData<ArrayList<String>> ulogovan;  //bool
    private final static String SHARED_PREFERENCES_PREFIX = "UlogovanSharedPreferences";
    private final static String SHARED_PREFERENCES_ULOGOVAN = "ulogovanUkljuc";

    private MutableLiveData<ArrayList<Korisnik>> sviKorisnici;
    private ArrayList<Korisnik> listaKorisnika;                    //sluzi samo da imamo globalni atribut, da bi smo setovali vrednost svim korisnicima
    private MutableLiveData<Korisnik> ulogovaniKorisnik;

    /////////////////////////////////////////////////////////////////////
    public MojViewModel(@NonNull Application application) {
        super(application);
    }


//    public LiveData<ArrayList<String>> getUlogovan() {
//        if(ulogovan == null){
//            ulogovan = new MutableLiveData<>();
//            setUlogovan(null);
//        }
//        return ulogovan;
//    }
//
//    public void setUlogovan(ArrayList<String> lista){
//        if(lista == null){
//            lista = new ArrayList<String>();
//
//            SharedPreferences sharedPreferences = getApplication().getSharedPreferences(SHARED_PREFERENCES_PREFIX, 0);  //getaplication mora da se pise jer se to u main activity-ju kada sam radio podrazumevalo a ovo je view model
//            String zz = sharedPreferences.getString(SHARED_PREFERENCES_ULOGOVAN, "nije");       //ako ne nadje nista bice postavljeno nije
//            if(zz.equals("nije")){  //ako je vrednost default-na
//                return;
//            }else{
//                lista.add(zz);
//                this.ulogovan.setValue(lista);
//            }
//        }
//    }


    public LiveData<ArrayList<Korisnik>> getSviKorisnici(){  //ako je null onda ga instanciraj i pozovi njegov seter
        if(ulogovaniKorisnik == null){
            ulogovaniKorisnik = new MutableLiveData<>();
            setSviKorisnici(null);
        }
        return sviKorisnici;
    }

    public void setSviKorisnici(ArrayList<Korisnik> lista){  //ovo bi bilo citanje iz baze ili api poziv
        if(lista == null){                              //ovo ce se izvrsiti samo ako je null
            lista = new ArrayList<Korisnik>();
            listaKorisnika = new ArrayList<Korisnik>();


            RequestQueue queue = Volley.newRequestQueue(getApplication().getApplicationContext());
            String url = "http://192.168.0.31:5000/dobaviSveKorisnike";
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println(response);

                    try {
                        JSONArray jsonNiz = new JSONArray(response);          //niz svih dobavljenih podataka iz apija

                        for(int i = 0; i < jsonNiz.length(); i++){
                            JSONObject jsonKorisnik = jsonNiz.getJSONObject(i);    //pojedinacni korisnik
                            Korisnik kk = new Korisnik();

                            if(jsonKorisnik.has("korisnicko_ime")) {
                                kk.setKorisnickoIme(jsonKorisnik.getString("korisnicko_ime"));
                            }
                            if(jsonKorisnik.has("lozinka")) {
                                kk.setLozinka(jsonKorisnik.getString("adresa"));
                            }
                            if(jsonKorisnik.has("email")) {
                                kk.setEmail(jsonKorisnik.getString("email"));
                            }
                            if(jsonKorisnik.has("platio_ful_verziju")) {
                                boolean platio;
                                if(jsonKorisnik.getString("platio_ful_verziju").equals("true")){
                                    platio = true;
                                }else{
                                    platio = false;
                                }
                                kk.setPlatioFulVerzijuAplikacije(platio);
                            }

                            listaKorisnika.add(kk);                                                           //dodajemo u listu za prikaz fakultet koji je upravo napravljen
                        }
                    } catch (JSONException e) {
                        System.out.println("Greska prilikom konvertovanja u JSON tip podataka");
                        e.printStackTrace();
                    }
                    //prikaz
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error);
                }
            });

            lista = listaKorisnika;
            listaKorisnika = null;

            queue.add(request);
            queue.getCache().remove("http://192.168.0.31:5000/json");

            this.sviKorisnici.setValue(lista);
        }
    }





}
