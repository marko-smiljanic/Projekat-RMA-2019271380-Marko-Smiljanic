package com.example.watchaccuracy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FragmentPrvoPokretanje extends Fragment {
    private MojViewModel mojViewModel;
    private String ulogovan;


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

        ulogovan = LokalnoCuvanjeSharedPreferences.ucitajDaLiJeUlogovan(getActivity());   //proveravamo da li je ulogovan ili nije jer nam od toga zavisi koji cemo prikaz iscrtati
        if(ulogovan.equals("nije")){
            iscrtajPrvoPokretanje(cl);  //iscrtaj ovaj fragment

        }else if(ulogovan.equals("jeste")){
            //iscrtaj pocetni ekran, tj. gde bi trebali biti satovi, dalje logiku radim tamo
            //mogao bih kroz new instance da prosledim ovo ulogovan jeste-nije u naredni fragment preko new instance kao parametar i unutar toga kroz seter setovati atribut klase tog fragmenta (u slucaju da ne radim sve preko shared preferences)

            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragmentView, FragmentPocetniEkran.newInstance(), "fragmentPocetniEkran");
            //ft.addToBackStack("fragmentKorisnik");  //u ovom slucaju mi ovo ne treba
            ft.commit();
        }
        return vv;
    }


    private void iscrtajPrvoPokretanje(ConstraintLayout cl){
        //na onclick se otvaraju dalje fragmenti. Fragment forma koji ce se prilagoditi u slucaju da je pritisnuto imam nalog ili nemam nalog (reseno preko shared preferences, tj. u tom fragmentu cu renderovati prikaz u zavisnosti na koje je dugme kliknuto-iscitam iz shared preferences)
        Button imamNalog = cl.findViewById(R.id.buttonImamNalog);
        Button nemamNalog = cl.findViewById(R.id.buttonNemamNalog);

        imamNalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LokalnoCuvanjeSharedPreferences.sacuvajKojeJeKliknutoDugme(getActivity(), "imamNalog");  //cuvamo u shared preferences na koje je dugme kliknuto jer nam od toga zavisi prikaz u fragmentu koji sledi. Da nisam hteo da mi se fragment koji sledi prikazuje genericki, jednostavno bi im naveo da predju na druge fragmente (bez shared preferences u ovom slucaju)

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragmentView, FragmentFormaNalog.newInstance(), "fragmentFormaNalog");
                ft.addToBackStack("fragmentFormaNalog");
                ft.commit();
            }
        });
        nemamNalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LokalnoCuvanjeSharedPreferences.sacuvajKojeJeKliknutoDugme(getActivity(), "nemamNalog");

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragmentView, FragmentFormaNalog.newInstance(), "fragmentFormaNalog");
                ft.addToBackStack("fragmentFormaNalog");
                ft.commit();
            }
        });

    }


}
