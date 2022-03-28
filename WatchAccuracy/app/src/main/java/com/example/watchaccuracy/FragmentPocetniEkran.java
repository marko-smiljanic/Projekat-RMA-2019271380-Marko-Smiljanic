package com.example.watchaccuracy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentPocetniEkran extends Fragment {  //ovo je u stvari pocetni ekran
    public MojViewModel viewModel;
    private Korisnik ulogovaniKorisnik;

    public FragmentPocetniEkran() {
    }

    public static FragmentPocetniEkran newInstance() {
        FragmentPocetniEkran fragment = new FragmentPocetniEkran();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MojViewModel.class);

        setHasOptionsMenu(true);                                    //ovo moram da dodam da bih pozvao kreiranje menija
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vv = inflater.inflate(R.layout.fragment_pocetni_ekran, container, false);
        LinearLayout l = vv.findViewById(R.id.mainLytPocetniEkran);
        LinearLayout l2 = vv.findViewById(R.id.mainLyt2PocetniEkran);    //treba mi da bih mogao da nadjem dugme u draw data metodi, jer je dugme izvan mainLytPocetniEkran layouta!!!

        //ulogovaniKorisnik = null;    //na pocetku uvek stavimo da je null jer je znamo da li ce se api zahtev dobro izvrsiti i da li ce u njemu biti korisnik
        //String korisnickoIme = LokalnoCuvanjeSharedPreferences.ucitajUlogovanogKorisnika(getActivity());
//        String ulogovan = LokalnoCuvanjeSharedPreferences.ucitajDaLiJeUlogovan(getActivity());

//        if (korisnickoIme.equals("")) {      //cisto za svaki slucja provera ali ako sam menjao vrednosti u shared preferences kad god je trebalo onda nece biti problema nikakvih
//            System.out.println("GRESKA");
//            return null;
//        }
//        if(ulogovan.equals("nije")){
//            System.out.println("GRESKA");
//            return null;
//        }

//        ulogovaniKorisnik = new Korisnik();
//        dohvatiUlogovanogKorisnika(korisnickoIme);                //prosledjujemo korisnicko ime iz shared preferemces i radiumo api zahtev i time bi trebalo da setujemo atribut ulogovani korisnik
//
//        if(ulogovaniKorisnik == null){
//            System.out.println("GRESKA");
//            return null;
//        }

        //viewModel.setUlogovani();

        //get korisnik prosledjeno korisnicko ime iz shared preferences, a draw data prosledjeno isto korisnicko ime samo sam morao da iskoristim objekat koji je izmenjen u view modelu
        viewModel.setUlogovani();  //rucno dohvatam podatke da bi mi prvi prikaz (pri prvom otvaranju aplikacije) bio odgovarajuci (azuriran)

        viewModel.getKorisnik().observe(getViewLifecycleOwner(), new Observer<Korisnik>() {     //kad god se azurira korisnik treba pozvati funkciju draw data
            @Override
            public void onChanged(Korisnik korisnik) {
                drawData(l, korisnik, l2);
            }
        });

        //TODO: observe treba odraditi i za iscrtavanje satova
        //drawData(l, korisnickoIme, l2);  //treba dodati kad gode skorisnik azuririra ili baza da se iscrta odma ponovo

        return vv;
    }

    private void drawData(ViewGroup container, Korisnik korisnik, LinearLayout l2) {
        //Odradio sam iscrtavanje opcija menija. To je odradjeno u posebnoj metodi menija koja se redefinise i u onCreate fragmenta omogucimo njeno pozivanje

        //napravljena baza za satove i checkpoint-e
        //TODO: ostalo mi je da odradim na klijentu satove i njihovo prikazivanje i pravljenje checkpoint-a
        //TODO: ogranicenje koje moram imati kod kreiranja satova je: ako nije platio ful verziju setujem on click listener na neki toast koji ga obavestava (u pitanju je dugme dodaj novi sat), bukvalno prekopiram api kod iz fragmenta nalog
        //TODO: ako je platio ful verziju setujem funkcionalnost dodavanja sata

        container.removeAllViews();

        LayoutInflater inf = getLayoutInflater();
        View red = inf.inflate(R.layout.layout_sat, null);
        red.findViewById(R.id.layoutSatRed);

        Button izmeniSat = red.findViewById(R.id.dugmeIzmeniSat);
        Button obrisiSat = red.findViewById(R.id.dugmeObrisiSat);
        TextView labelaPoslednjiCheck = red.findViewById(R.id.labelaPoslednjiCheck);


        Button dodajSat = l2.findViewById(R.id.dugmeDodajNoviSat);                //dohvatanje dugmeta koje je u drugom layout-u
        if(korisnik.isPlatioFulVerzijuAplikacije() == false){
            dodajSat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity().getApplicationContext(), "Morate imati ful verziju aplikacije da bi ste dodali sat. Posetite vas nalog.", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            dodajSat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //nesto drugo radim ovde, dodavanje sata u bazu ili prelazak na fragment dodavanje
                    Toast.makeText(getActivity().getApplicationContext(), "RADI", Toast.LENGTH_SHORT).show();
                }
            });
        }









        container.addView(red);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {                             //kreiranje menija, poziva se sam ali u oncreate metodi fragmenta moram da imam
        inflater.inflate(R.menu.meni, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {                                       //za meni: kada se selektuje neki element iz menija, odnosno onclick za menu iteme
        // Handle item selection
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (item.getItemId()) {
            case R.id.menuItemNalog:
                ft.replace(R.id.fragmentView, FragmentNalog.newInstance(), "fragmentNalog");
                ft.addToBackStack("fragmentNalog");
                ft.commit();
                return true;
            case R.id.menuItemOAplikaciji:
                ft.replace(R.id.fragmentView, FragmentOAplikaciji.newInstance(), "fragmentOAplikaciji");
                ft.addToBackStack("fragmentOAplikaciji");
                ft.commit();
                return true;
            case R.id.menuItemOdjava:                                                           //odjava odradjena u main activity-ju kada mije za svako pokretanje aplikacije bilo potrebno da korisnik ne bude ulogovan, prekopirati
                LokalnoCuvanjeSharedPreferences.sacuvajDaLiJeUlogovan(getActivity().getApplicationContext(), "nije");
                LokalnoCuvanjeSharedPreferences.sacuvajUlogovanogKorisnika(getActivity().getApplicationContext(), "");
                //Da li sam trebao da ubacim neki podekran koji bi trebalo da pita da li ste sigurni da zelite da se odjavite?

                ft.replace(R.id.fragmentView, FragmentPrvoPokretanje.newInstance(), "fragmentPrvoPokretanje");
                //ft.addToBackStack("fragmentPrvoPokretanje");                                   //ovo ne treba jer zasto bi smo dozvolili korisniku mogucnost da se posle odjave vrati na ekran gde je potrebno da bude ulogovan
                ft.commit();
                Toast.makeText(getActivity().getApplicationContext(), "Uspesno ste se odjavili!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);  //false
        }
    }

    private void dohvatiUlogovanogKorisnika(String korisnickoIme) {  //ovo je samo api zahtev. Setuje atribut ulogovani korisnik, jer nam treba platio ful verziju aplikacije
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
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
                    ulogovaniKorisnik = kk;  // // // //
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
    }








}
