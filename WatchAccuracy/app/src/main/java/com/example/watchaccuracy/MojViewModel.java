package com.example.watchaccuracy;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MojViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Korisnik>> sviKorisnici;
    private MutableLiveData<Korisnik> ulogovaniKorisnik;
    public static Context context;

    public LiveData<ArrayList<Korisnik>> getSviKorisnici(){
        if(ulogovaniKorisnik == null){
            ulogovaniKorisnik = new MutableLiveData<>();
            setSviKorisnici(null);
        }
        return sviKorisnici;
    }

    public void setSviKorisnici(ArrayList<Korisnik> lista){
        if(lista == null){
            lista = new ArrayList<Korisnik>();

            ArrayList<Korisnik> listaKorisnika = new ArrayList<Korisnik>();
            RequestQueue queue = Volley.newRequestQueue();
            
            //this.sviKorisnici.setValue();

        }


//        public void dobaviFakultete() {
//            fakulteti = new ArrayList<>();
//            naziv = findViewById(R.id.labelaNaziv);
//            adresa = findViewById(R.id.labelaAdresa);
//
//            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());                                   //kreiramo red za izvrsavanje requestova
//
//            queue.getCache().remove("http://192.168.0.31:5000/json");                               //mora se izbaciti iz kesa adresa, jer iz nekog razloga volley svaki put kesira adresu i nece svaki put da posalje zahtev
//
//            String url = "http://192.168.0.31:5000/json";                                                           //lokalna adresa iz cmd-a, jer je 127.0.0.1:5000 rezervisano za virt. uredjaj (mobilni telefon)
//            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {        //koja je metoda, odakle uzima (eventualno ako je post mopze imati podatke dodatne) i callback za on response i za on error
//                @Override
//                public void onResponse(String response) {                                                           //response su svi fakulteti, treba da strpamo u listu i da je isparsiramo, prvo u json array pa u array listu fakulteta, ili rucno parsiramo jedan po jedan ili sa gson
//                    System.out.println(response);
//
//                    try {
//                        JSONArray jsonNiz = new JSONArray(response);                                    //ceo json fajl je niz, svaki element je jedan fakultet
//
//                        for(int i = 0; i < jsonNiz.length(); i++){
//                            JSONObject jsonFakultet = jsonNiz.getJSONObject(i);                                  //vadimo po jedan fakultet
//                            Fakultet ff = new Fakultet();
//
//                            if(jsonFakultet.has("naziv")) {                                 //ako fakultet koji smo dohvatili iz json fajla ima kljuc naziv, u nas objekat ff setujemo naziv na vrednost iz fajla (tj. iz njegovog json objekta)
//                                ff.setNaziv(jsonFakultet.getString("naziv"));
//                            }
//                            if(jsonFakultet.has("adresa")) {
//                                ff.setAdresa(jsonFakultet.getString("adresa"));
//                            }
//                            if(jsonFakultet.has("predmeti")) {
//                                ArrayList<String> nizPredmeta = new ArrayList<>();                      //niz predmeta se svakako dodaje, pa makar i prazan bio, tj. makar se dalje u toku programa ne napuni
//                                JSONArray jsonNizPredmeta = jsonFakultet.getJSONArray("predmeti");         //vadimo predmete
//
//                                for(int z = 0; z < jsonNizPredmeta.length(); z++){
//                                    JSONObject predmet = jsonNizPredmeta.getJSONObject(z);              //ovde dohvatam po jedan predmet
//                                    nizPredmeta.add(predmet.getString("naziv_predmeta"));           //pravimo listu stringova predmeti
//                                }
//                                ff.setPredmeti(nizPredmeta);                                             //setujemo listu stringova
//                            }
//                            fakulteti.add(ff);                                                           //dodajemo u listu za prikaz fakultet koji je upravo napravljen
//                        }
//                    } catch (JSONException e) {
//                        System.out.println("Greska prilikom konvertovanja u JSON tip podataka");
//                        e.printStackTrace();
//                    }
//                    //kako bi se ovo odradilo sa Gson() ?????????????
//                    //ovako ne moze zato sto ocekuje objekat ali je u stvari niz?                          //umesto fakultet.class mora ici fakultet[].class ako vadim json array
//                    //Fakultet zz =  new Gson().fromJson(response, Fakultet.class);                            //mora se dodati zavisnost u build gradle
//                    //ArrayList<JSONArray> jsonarr = new ArrayList<>();                                         //ne znam kako gson radi za ovaj primer
//                    //jsonarr =  new Gson().fromJson(response, Fakultet.class);
//
//                    //na kraju se obavezno poziva prikazivanje podataka
//                    generisiPrikazFakulteta();
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    System.out.println(error);
//                }
//            });
//
//            queue.add(request);
//            //queue.getCache().remove("http://192.168.0.31:5000/json");
//        }




    }


}
