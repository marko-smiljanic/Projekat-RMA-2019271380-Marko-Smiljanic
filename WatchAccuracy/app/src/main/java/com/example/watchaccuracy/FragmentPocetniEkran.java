package com.example.watchaccuracy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        tv.setText("Ucitavanje uspelo !!!!!!!!!!");
        //TODO: stao sam kod iscrtavanja opcija menija. U opcije dodati korisnicko ime naloga (da moze da klikne i da pogleda informacije o nalogu), About i odjava

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {  //kreiranje menija, poziva se sam ali u oncreate metodi fragmenta moram da imam
        inflater.inflate(R.menu.meni, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {              //kada se selektuje neki element iz menija, odnosno onclick za menu iteme
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuItemNalog:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragmentView, FragmentNalog.newInstance(), "fragmentNalog");
                ft.addToBackStack("fragmentNalog");
                ft.commit();
                return true;
            case R.id.menuItemOAplikaciji:

                return true;
            case R.id.menuItemOdjava:

                return true;
            default:
                return super.onOptionsItemSelected(item);     //false
        }
    }




}
