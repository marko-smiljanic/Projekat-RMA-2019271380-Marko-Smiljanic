package com.example.watchaccuracy;

import android.database.sqlite.SQLiteDatabase;
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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentPocetniEkran extends Fragment {  //ovo je u stvari pocetni ekran
    private MojViewModel viewModel;

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

        //TODO: zbog cega mi ne radi dugme kad prvi put pokrenem aplikaciju?
        //TODO: zbog cega ne radi poziv set ulogovani?
        // rucno dohvatam podatke da bi mi prvi prikaz (pri prvom otvaranju aplikacije) bio odgovarajuci (azuriran)


        //viewModel.setSatovi();          //rucno i ovde pozivam za svaki slucaj
        viewModel.setUlogovani();

        viewModel.getUlogovani().observe(getViewLifecycleOwner(), new Observer<Korisnik>() {     //kad god se azurira korisnik treba pozvati funkciju draw data
            @Override
            public void onChanged(Korisnik korisnik) {
                iscrtajDugmeDodajSat(korisnik, l2);        //korisnik mi treba za proveru da li je platio ful verziju app da znamo da li da mu zaista omogucimo tu funkcionalnost, iscrtavanja treba razdvojiti u razlicite observer-e
            }
        });

        viewModel.getSatovi().observe(getViewLifecycleOwner(), new Observer<ArrayList<Sat>>() {
            @Override
            public void onChanged(ArrayList<Sat> satovi) {
                iscrtajSatove(l, satovi);                           //ici ce u observe za satove
            }
        });
        //Odradio sam iscrtavanje opcija menija. To je odradjeno u posebnoj metodi menija koja se redefinise i u onCreate fragmenta omogucimo njeno pozivanje
        //napravljena baza za satove i checkpoint-e
        //TODO: ostalo mi je da odradim na klijentu satove i njihovo prikazivanje i pravljenje checkpoint-a
        //ogranicenje koje moram imati kod kreiranja satova je: ako nije platio ful verziju setujem on click listener na neki toast koji ga obavestava (u pitanju je dugme dodaj novi sat)
        //a ako je platio ful verziju setujem funkcionalnost dodavanja sata
        //observe treba odraditi i za iscrtavanje satova
        //drawData(l, korisnickoIme, l2);  //treba dodati kad gode skorisnik azuririra ili baza da se iscrta odma ponovo

        return vv;
    }

    //TODO: iz nekog razloga ovo se ne izvrsi kad se pokrene aplikacija prvi put, observe se izvrsi i api zahtev, ali se ne prikaze nista
    private void iscrtajDugmeDodajSat(Korisnik korisnik, LinearLayout ll) {
        Button dodajSat = ll.findViewById(R.id.dugmeDodajNoviSat);                //dohvatanje dugmeta koje je u drugom layout-u
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
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragmentView, FragmentFormaDodajSat.newInstance(), "fragmentDodajSat");
                    ft.addToBackStack("fragmentDodajSat");  //u ovom slucaju mi ovo ne treba
                    ft.commit();
                }
            });
        }
    }

    //TODO: treba mi ovde ogranicenje da se prikaze samo jedan sat iz baze ako korisnik nema uplacenu ful verziju aplikacije
    //TODO: ne znam kako to da odradim jer su u pitanju dva razlicita observe-a
    //TODO: NAJJEDNOSTAVNIJE RESENJE JE DA UNUTAR ISCRTAVANJA SATOVA POZOVEM API ZAHTEV I DOHVATIM KORISNIKA I DA TU UVEDEM
    //TODO: OGRANICENJE U PRIKAZU
    private void iscrtajSatove(ViewGroup container, ArrayList<Sat> lista){
        container.removeAllViews();

        LayoutInflater inf = getLayoutInflater();
        for (Sat ss : lista){

            View red = inf.inflate(R.layout.layout_sat, null);
            red.findViewById(R.id.layoutSatRed);
            TextView labelaMarka = red.findViewById(R.id.labelaMarkaSat);
            TextView labelaModel = red.findViewById(R.id.labelaModelSat);
            TextView labelaPoslednjiCheck = red.findViewById(R.id.labelaPoslednjiCheck);
            Button izmeniSat = red.findViewById(R.id.dugmeIzmeniSat);
            Button obrisiSat = red.findViewById(R.id.dugmeObrisiSat);

            labelaMarka.setText(ss.getMarka());
            labelaModel.setText(ss.getModel());

            izmeniSat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel.setJedanSelektovanSat(ss);

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragmentView, FragmentFormaIzmeniSat.newInstance(), "fragmentIzmeniSat");
                    ft.addToBackStack("fragmentIzmeniSat");  //u ovom slucaju mi ovo ne treba
                    ft.commit();
                }
            });

            obrisiSat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Baza baza = new Baza(getActivity());
                    SQLiteDatabase db = baza.getWritableDatabase();
                    baza.onCreate(db);

                    BazaSat bazaSat = new BazaSat(baza);
                    bazaSat.deleteSat(ss.getId());
                    db.close();

                    viewModel.setSatovi();
                }
            });

            container.addView(red);
        }
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




}
