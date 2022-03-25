package com.example.watchaccuracy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FragmentPocetniEkran extends Fragment {  //ovo je u stvari pocetni ekran
    public MojViewModel viewModel;

    public FragmentPocetniEkran(){}

    public static FragmentPocetniEkran newInstance(){
        FragmentPocetniEkran fragment = new FragmentPocetniEkran();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                try{
                    return modelClass.newInstance();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).get(MojViewModel.class);

        setHasOptionsMenu(true);                                    //ovo moram da dodam da bih pozvao kreiranje menija
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vv = inflater.inflate(R.layout.fragment_pocetni_ekran, container, false);
        ConstraintLayout l = vv.findViewById(R.id.mainlyt);


        String korisnickoIme = LokalnoCuvanjeSharedPreferences.ucitajUlogovanogKorisnika(getActivity());
        if(korisnickoIme.equals("")){
            System.out.println("GRESKA");
            return null;
        }


        //kad god se azurira lista oglasa treba iscrtati
        //ovaj observe odraditi za iscrtavanje satova

//        viewModel.getJedanSelektovani().observe(getViewLifecycleOwner(), new Observer<Oglas>() {
//            @Override
//            public void onChanged(Oglas oglas) {
//                drawData(oglas, l);
//            }
//        });



        drawData(l, korisnickoIme);

        return vv;
    }

    private void drawData(ConstraintLayout ll, String korisnickoIme){
        TextView tv = ll.findViewById(R.id.labelaNalog);
        tv.setText("Ucitavanje uspelo !!!");
        //Odradio sam iscrtavanje opcija menija. To je odradjeno u posebnoj metodi menija koja se redefinise i u onCreate fragmenta omogucimo njeno pozivanje

        //TODO: ostalo mi je da odradim satove i njihovo prikazivanje i pravljenje checkpoint-a
        //TODO: ogranicenje koje moram imati kod kreiranja satova je: ako nije platio ful verziju setujem on click listener na neki toast koji ga obavestava (u pitanju je dugme dodaj novi sat), bukvalno prekopiram api kod iz fragmenta nalog
        //TODO: ako je platio ful verziju setujem funkcionalnost dodavanja sata



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {                             //kreiranje menija, poziva se sam ali u oncreate metodi fragmenta moram da imam
        inflater.inflate(R.menu.meni, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {                                       //kada se selektuje neki element iz menija, odnosno onclick za menu iteme
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
