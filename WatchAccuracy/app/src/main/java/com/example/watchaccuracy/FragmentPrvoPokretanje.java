package com.example.watchaccuracy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

public class FragmentPrvoPokretanje extends Fragment {
    public MojViewModel mojViewModel;

    private final static String SHARED_PREFERENCES_PREFIX = "SharedPreferences";
    private final static String SHARED_PREFERENCES_ULOGOVAN = "ulogovanUkljuc";
    private String ulogovan;

    /////////////////////////////////////////

    public FragmentPrvoPokretanje(){}

    public static FragmentPrvoPokretanje newInstance(){
        FragmentPrvoPokretanje fragment = new FragmentPrvoPokretanje();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mojViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.Factory() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vv = inflater.inflate(R.layout.fragment_prvo_pokretanje, container, false);
        ConstraintLayout cl = vv.findViewById(R.id.mainLayoutPrvoPokretanje);

        ucitajDaLiJeUlogovan();  //setujemo ulogovan
        if(ulogovan.equals("nije")){
            iscrtajPrvoPokretanje(cl);  //iscrtaj ovaj fragment

        }else if(ulogovan.equals("jeste")){
            //iscrtaj nesto drugo, npr. prikazi pocetni ekran gde bi trebali biti satovi, dalje logiku radim tamo
            //u ovom fragmentu stoji samo neki dummy tekst za sada jer mi je bilo bitno da li prikazi rade na nacin na koji ocekujem, tj. da unutar jednog fragmenta mogu da zovem prikaz drugog
            //mogao bih kroz new instance da prosledim ovo ulogovan jeste-nije u naredni fragment preko new instance kao parametar i unutar toga kroz seter setovati atribut klase tog fragmenta
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragmentView, FragmentKorisnik.newInstance(), "fragmentKorisnik");
            //ft.addToBackStack("fragmentKorisnik");  //u ovom slucaju mi ovo ne treba
            ft.commit();
        }

        return vv;
    }

    public void ucitajDaLiJeUlogovan(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_PREFIX, 0);  //getaplication mora da se pise jer se to u main activity-ju kada sam radio podrazumevalo a ovo je view model
        String zz = sharedPreferences.getString(SHARED_PREFERENCES_ULOGOVAN, "nije");       //ako ne nadje nista bice postavljeno nije
        if(zz.equals("nije")){  //ako je vrednost default-na
            ulogovan = "nije";
        }else{
            ulogovan = "jeste";
        }
    }

    private void iscrtajPrvoPokretanje(ConstraintLayout cl){
        //na onclick se otvaraju dalje fragmenti? Fragment za pravljenje naloga ili za ukucavanje podataka postojeceg?
        Button imamNalog = cl.findViewById(R.id.buttonImamNalog);
        Button nemamNalog = cl.findViewById(R.id.buttonNemamNalog);

        

    }



}
