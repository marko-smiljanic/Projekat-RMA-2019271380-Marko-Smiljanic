package com.example.watchaccuracy;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MojViewModel extends AndroidViewModel {               //da bih mogao da dobavim kontekst
    private MutableLiveData<Korisnik> ulogovaniKorisnik;
    private MutableLiveData<ArrayList<Sat>> satovi;
    private MutableLiveData<Sat> jedanSelektovaniSat;
    ///////////////

    public MojViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Korisnik> getUlogovani(){  //ako je null onda ga instanciraj i pozovi njegov seter
        if(ulogovaniKorisnik == null){
            ulogovaniKorisnik = new MutableLiveData<>();
            setUlogovani();
        }
        return ulogovaniKorisnik;
    }

    private void setujPolje(Korisnik kk){    //sluzi da bih unutar zahteva imao ulogovanog korisnika
        this.ulogovaniKorisnik = new MutableLiveData<>();
        this.ulogovaniKorisnik.setValue(kk);
    }

    public void setUlogovani(){   //api zahtev koji dohvata ulogovanog korisnika sa prosledjenim kor imenom (iz shared preferences) i setovanje atributa ulogovani u view modelu
        String korisnickoIme = LokalnoCuvanjeSharedPreferences.ucitajUlogovanogKorisnika(getApplication().getApplicationContext());

        RequestQueue queue = Volley.newRequestQueue(getApplication().getApplicationContext());
        queue.getCache().clear();
        String url = "http://192.168.0.32:5000/dobaviJednogKorisnika/" + korisnickoIme;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                if (response.equals("greska")) {
                    return;
                }

                try {
                    JSONObject jsonKorisnik = new JSONObject(response);
                    Korisnik kk = new Korisnik();

                    if (jsonKorisnik.has("korisnicko_ime")) {
                        kk.setKorisnickoIme(jsonKorisnik.getString("korisnicko_ime"));
                    }
                    if (jsonKorisnik.has("lozinka")) {
                        kk.setLozinka(jsonKorisnik.getString("lozinka"));
                    }
                    if (jsonKorisnik.has("email")) {
                        kk.setEmail(jsonKorisnik.getString("email"));
                    }
                    if (jsonKorisnik.has("platio_ful_verziju")) {
                        boolean platio;
                        if (jsonKorisnik.getString("platio_ful_verziju").equals("true")) {
                            platio = true;
                        } else {
                            platio = false;
                        }
                        kk.setPlatioFulVerzijuAplikacije(platio);
                    }

                    setujPolje(kk);   //***********moram ovako da setujem ulogovanog korisnika jer ne mogu da odavde pristupam njemu sa this jer unutar request-a!!!
                } catch (JSONException e) {
                    System.out.println("Greska prilikom konvertovanja u JSON tip podataka");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        queue.add(request);

        //this.ulogovaniKorisnik.setValue(privremeni);  //ovo smora da se odradi unutar response-a i da mu setujem objekat korisnik koji sam napravio od parametara iz response-a
    }

    //parametar korisnik kk mi sluzi da bih ga u fragmentu pocetni ekran preneo u funkciju koja ovo observe-uje
    public LiveData<ArrayList<Sat>> getSatovi(){  //ako je null onda ga instanciraj i pozovi njegov seter
        if(satovi == null){
            satovi = new MutableLiveData<>();
            setSatovi();
        }
        return satovi;
    }

    public void setSatovi(){                //dodavanje satova iz baze i setovanje atributa view modela
        Baza baza = new Baza(getApplication().getApplicationContext());
        SQLiteDatabase db = baza.getWritableDatabase();
        baza.onCreate(db);

        BazaSat bazaSat = new BazaSat(baza);

        ArrayList<Sat> lista = new ArrayList<>();
        lista = bazaSat.getAllSatovi();
        db.close();

        this.satovi.setValue(lista);
    }

    public LiveData<Sat> getJedanSelektovaniSat(){
        return this.jedanSelektovaniSat;
    }

    public void setJedanSelektovanSat(Sat sat){
        this.jedanSelektovaniSat = new MutableLiveData<>();        //u android view modelu moram imati deklaracije u setovanju!!!
        this.jedanSelektovaniSat.setValue(sat);
    }





}
