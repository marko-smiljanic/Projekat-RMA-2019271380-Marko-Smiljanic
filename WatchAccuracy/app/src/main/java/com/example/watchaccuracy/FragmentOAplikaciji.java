package com.example.watchaccuracy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class FragmentOAplikaciji extends Fragment {

    public FragmentOAplikaciji(){

    }
    public static FragmentOAplikaciji newInstance(){
        FragmentOAplikaciji fragment = new FragmentOAplikaciji();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vv = inflater.inflate(R.layout.fragment_o_aplikaciji, container, false);
        ConstraintLayout l = vv.findViewById(R.id.oAplikacijiMainLyt);

        iscrtaj(l);

        return vv;
    }

    private void iscrtaj(ConstraintLayout ll){
        TextView glavnaLabela = ll.findViewById(R.id.glavnaLabela);
        glavnaLabela.setText("Aplikacija je razvijena sa namerom da isprati odstupanja u radu mehanickih satova " +
                "kako bi korisnik imao evidenciju. Odstupanja kod mehanickih satova su veoma bitna jer nam to " +
                "govori kada im je vreme za servis. Aplikacija sluzi da procenimo opste stanje sata, ili u slucaju kada je sat " +
                "na servisu da casovnicar moze lakse da vodi evidenciju pri stelovanju odstupanja sata. " +
                "Ukoliko ne posedujete timegrapher ili neki drugi moderni uredjaj za pracenje odstupanja mehanickih satova ovakva " +
                "aplikaciija je verovatno najbolje moguce resenje. Samo merenje odstupanja radi tako sto prati izmereno vreme izmedju dva " +
                "checkpoint-a i uporedjuje sa srednje atomskim vremenom. Veoma je bitno da izmedju dva checkpoint-a prodje dovoljno " +
                "vremena (minimum 6 sati) da bi rezultat bio sigurniji. Odstupanje na satu u odredjenom vremenu se uporedjuje sa odstupanjem " +
                "iz srednje atomskog sata (sistemskog sata uredjaja) i na osnovu toga vidimo rezultat.");

    }

}
