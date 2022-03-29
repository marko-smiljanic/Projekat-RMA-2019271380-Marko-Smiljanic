package com.example.watchaccuracy;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

public class FragmentFormaDodajSat extends Fragment {
    MojViewModel viewModel;


    public FragmentFormaDodajSat() {
    }


    public static FragmentFormaDodajSat newInstance() {
        FragmentFormaDodajSat fragment = new FragmentFormaDodajSat();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MojViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vv = inflater.inflate(R.layout.fragment_forma_dodaj_sat, container, false);
        ConstraintLayout l = vv.findViewById(R.id.mainLytDodajSat);

        iscrtaj(l);

        return vv;
    }

    private void iscrtaj(ConstraintLayout ll){
        EditText inputMarka = ll.findViewById(R.id.unosMarka);
        EditText inputModel = ll.findViewById(R.id.unosModel);
        Button dugmeDodaj  = ll.findViewById(R.id.dodajSatUBazu);

        dugmeDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputMarka.getText().toString().equals("") || inputModel.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Polja ne smeju biti prazna!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Baza baza = new Baza(getActivity());
                SQLiteDatabase db = baza.getWritableDatabase();
                baza.onCreate(db);

                BazaSat bazaSat = new BazaSat(baza);
                bazaSat.addSat(inputMarka.getText().toString(), inputModel.getText().toString());
                db.close();

                viewModel.setSatovi();                                     //pozivam rucno za ovde posle dodavanja u bazu jer moram biti siguran da je doslo do promene satova u view modelu

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragmentView, FragmentPocetniEkran.newInstance(), "fragmentPocetniEkran");
                //ft.addToBackStack("fragmentPocetniEkran");  //u ovom slucaju mi ovo ne treba
                ft.commit();
            }
        });

    }


}
