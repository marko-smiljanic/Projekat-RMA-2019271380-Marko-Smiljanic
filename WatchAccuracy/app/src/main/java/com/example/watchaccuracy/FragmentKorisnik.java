package com.example.watchaccuracy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FragmentKorisnik extends Fragment {
    public MojViewModel viewModel;

    public FragmentKorisnik(){}

    public static FragmentKorisnik newInstance(){
        FragmentKorisnik fragment = new FragmentKorisnik();
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
        View vv = inflater.inflate(R.layout.fragment_korisnik, container, false);
        ConstraintLayout l = vv.findViewById(R.id.mainlyt);
        //kad god se azurira lista oglasa treba iscrtati

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
        TextView tv = ll.findViewById(R.id.tv1);
        tv.setText("Ucitavanje uspelo !!!!!!!!!!");
    }

}
