package com.example.watchaccuracy;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class FragmentFormaIzmeniSat extends Fragment {
    MojViewModel viewModel;


    public FragmentFormaIzmeniSat() {
    }


    public static FragmentFormaIzmeniSat newInstance() {
        FragmentFormaIzmeniSat fragment = new FragmentFormaIzmeniSat();
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
        View vv = inflater.inflate(R.layout.fragment_forma_izmeni_sat, container, false);
        ConstraintLayout l = vv.findViewById(R.id.mainLytIzmeniSat);


        viewModel.getJedanSelektovaniSat().observe(getViewLifecycleOwner(), new Observer<Sat>() {
            @Override
            public void onChanged(Sat sat) {
                iscrtaj(l, sat);
            }
        });

        return vv;
    }


    private void iscrtaj(ConstraintLayout ll, Sat sat){
        EditText inputMarka = ll.findViewById(R.id.inputIzmeniMarka);
        EditText inputModel = ll.findViewById(R.id.inputIzmeniModel);
        Button dugmeIzmeni = ll.findViewById(R.id.dugmeIzmeni);

        inputMarka.setText(sat.getMarka());
        inputModel.setText(sat.getModel());

        dugmeIzmeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputMarka.getText().toString().equals("") || inputModel.getText().toString().equals("")){  //ne dozvoljavam izmenu da stavi prazno
                    Toast.makeText(getActivity(), "Polja ne smeju biti prazna", Toast.LENGTH_SHORT).show();
                    return;
                }

                Baza baza = new Baza(getActivity());
                SQLiteDatabase db = baza.getWritableDatabase();
                baza.onCreate(db);

                BazaSat bazaSat = new BazaSat(baza);
                bazaSat.editSat(sat.getId(), inputMarka.getText().toString(), inputModel.getText().toString());
                db.close();

                viewModel.setSatovi();        //obavezno odradim ponovno dovlacenje iz baze, jer iscrtavanje sledeceg fragmenta na koji prelazim zavisi od tih podataka

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragmentView, FragmentPocetniEkran.newInstance(), "fragmentPocetniEkran");
                //ft.addToBackStack("fragmentPocetniEkran");      //u ovom slucaju mi ovo ne treba
                ft.commit();
            }
        });
    }


}
