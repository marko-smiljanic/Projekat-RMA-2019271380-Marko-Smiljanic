package com.example.watchaccuracy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FragmentNalog extends Fragment {  //ovo je u stvari pocetni ekran
    public MojViewModel viewModel;

    public FragmentNalog(){}

    public static FragmentNalog newInstance(){
        FragmentNalog fragment = new FragmentNalog();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vv = inflater.inflate(R.layout.fragment_nalog, container, false);
        ConstraintLayout l = vv.findViewById(R.id.mainlyt);
        //kad god se azurira lista oglasa treba iscrtati
        //ovaj observe odraditi za iscrtavanje satova

//        viewModel.getJedanSelektovani().observe(getViewLifecycleOwner(), new Observer<Oglas>() {
//            @Override
//            public void onChanged(Oglas oglas) {
//                drawData(oglas, l);
//            }
//        });



        drawData(l);

        return vv;
    }

    private void drawData(ConstraintLayout ll){
        TextView tv = ll.findViewById(R.id.labelaNalog);
        tv.setText("Ucitavanje uspelo !!!!!!!!!!");
        //TODO: stao sam kod iscrtavanja opcija spinner-a. U opcije dodati korisnicko ime naloga (ne moze da se klikne), About i odjava

    }





}
