package com.example.watchaccuracy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.w3c.dom.Text;

public class FragmentFormaNalog extends Fragment {
    private MojViewModel mojViewModel;
    private String kliknutoDugme;
    private String ulogovan;


    public FragmentFormaNalog(){}

    public static FragmentFormaNalog newInstance(){
        FragmentFormaNalog fragment = new FragmentFormaNalog();
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
        View vv = inflater.inflate(R.layout.fragment_forma_nalog, container, false);
        ConstraintLayout cl = vv.findViewById(R.id.mainLayoutFormaNalog);

        kliknutoDugme = LokalnoCuvanjeSharedPreferences.ucitajKojeJeKliknutoDugme(getActivity());
        if(kliknutoDugme.equals("imamNalog")){
            iscrtajKadImaNalog(cl);

        }else if(kliknutoDugme.equals("nemamNalog")){
            iscrtajKadNemaNalog(cl);

        }




        return vv;
    }

    private void iscrtajKadNemaNalog(ConstraintLayout ll){
        TextView labelaKorIme = ll.findViewById(R.id.labelaKorIme);
        TextView labelaLozinka = ll.findViewById(R.id.labelaLozinka);
        TextView labelaEmail = ll.findViewById(R.id.labelaEmail);

        EditText inputKorIme = ll.findViewById(R.id.inputKorIme);
        EditText inputLozinka = ll.findViewById(R.id.inputLozinka);
        EditText inputEmail = ll.findViewById(R.id.inputEmail);

        //cisto ako je ostalo gone od prosli put
        labelaEmail.setVisibility(View.VISIBLE);
        inputEmail.setVisibility(View.VISIBLE);

        TextView naslov = ll.findViewById(R.id.textViewNaslov);
        naslov.setText("Unesite podatke da napravite nalog:");

        Button potvrdi = ll.findViewById(R.id.buttonPotvrdi);
        potvrdi.setOnClickListener(new View.OnClickListener() {                                     //kreiranje naloga korisnika, tj. slanje post zahteva sa unetim vrednostima
            @Override
            public void onClick(View view) {
                ulogovan = LokalnoCuvanjeSharedPreferences.ucitajDaLiJeUlogovan(getActivity());
                if(ulogovan.equals("jeste")){
                    return;                                                                         //ako je ulogovan necemo praviti nalog
                }else if(ulogovan.equals("nije")){
                    //TODO: treba da se odradi kreiranje naloga korisnika, odnosno prvo slanje POST zahteva na api, a nakon toga prikaz, kako to odraditi?
                    

                }

            }
        });

    }

    private void iscrtajKadImaNalog(ConstraintLayout ll){  //kad ima nalog nema potrebe da unosi email kada proveru radim po kor imenu i lozinci
        TextView labelaKorIme = ll.findViewById(R.id.labelaKorIme);
        TextView labelaLozinka = ll.findViewById(R.id.labelaLozinka);
        TextView labelaEmail = ll.findViewById(R.id.labelaEmail);

        EditText inputKorIme = ll.findViewById(R.id.inputKorIme);
        EditText inputLozinka = ll.findViewById(R.id.inputLozinka);
        EditText inputEmail = ll.findViewById(R.id.inputEmail);

        labelaEmail.setVisibility(View.GONE);
        inputEmail.setVisibility(View.GONE);

        TextView naslov = ll.findViewById(R.id.textViewNaslov);
        naslov.setText("Unesite podatke da se ulogujete sa svojim nalogom:");



        Button potvrdi = ll.findViewById(R.id.buttonPotvrdi);
        potvrdi.setOnClickListener(new View.OnClickListener() {    //slanje get zahteva i vracanje korisnika koji se poklapa sa unetim vrednostima
            @Override
            public void onClick(View view) {
                String korIme = inputKorIme.getText().toString();
                String lozinka = inputLozinka.getText().toString();

                ulogovan = LokalnoCuvanjeSharedPreferences.ucitajDaLiJeUlogovan(getActivity());
                if(ulogovan.equals("jeste")){
                    return;
                }else if(ulogovan.equals("nije")){

                }


            }
        });

    }




}
