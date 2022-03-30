package com.example.watchaccuracy;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FragmentCheckpoint extends Fragment {
    MojViewModel viewModel;
    public int selektovaniSat;      //selektovano iz timepicker-a
    public int selektovaniMinut;

    public Date sistemskoVreme;
    public Date vremeNaSatu;
    //public Date odstupanje;
    public DateFormat dateFormat;

    public FragmentCheckpoint() {
    }

    public static FragmentCheckpoint newInstance() {
        FragmentCheckpoint fragment = new FragmentCheckpoint();
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
        View vv = inflater.inflate(R.layout.fragment_checkpoint, container, false);
        LinearLayout l = vv.findViewById(R.id.mainLytCheckpoint);
        LinearLayout l2 = vv.findViewById(R.id.mainLytCheckpoint2);



        viewModel.getJedanSelektovaniSat().observe(getViewLifecycleOwner(), new Observer<Sat>() {
            @Override
            public void onChanged(Sat sat) {
                iscrtajDodavanjeCheckpointa(sat, l2, l);
                iscrtajCheckpointe(sat, l);
            }
        });

        return vv;
    }

    private void iscrtajDodavanjeCheckpointa(Sat sat, LinearLayout ll, ViewGroup container){
        TextView tv = ll.findViewById(R.id.tv1);
        EditText inputOpis = ll.findViewById(R.id.inputOpisCheckpointa);
        Button kreirajCheck = ll.findViewById(R.id.dugmeKreirajCheck);

        //kada korisnik pritisne ovo dugme to znaci da je sekundna kazaljka dosegla 12, i mi prvo treba da uzmemo
        //sistemsko vreme (trenutno tacno) i da sacuvamo. Nakon toga od korisnika trazimo unos tacnog vremena na njegovom
        //satu da bi smo mogli dalje da pravimo checkpoint
        kreirajCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");           //   2022/03/29 23:54:24


                sistemskoVreme = new Date();   //dohvata trenutno sistemsko vreme

                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {  // i je sat, i1 minut
                        Date d = new Date();        //ponovo uzimamo trenutno vreme, jer razlika izmedju ovog i gore sistemskog vremena su sekunde koje treba da setujemo u vreme koje je na satu
                        long rezultat = d.getTime() - sistemskoVreme.getTime();
                        long rezultatUSekundama = (rezultat / (1000)) % 60;
                        //int sekundaInt = Integer.parseInt(String.valueOf(sekunda));      //TODO: da bi smo znali koje sekunde da setujemo, jer od klika na dugme do odabira vreme isto prodje vreme koje su nam u stvari sekunde, ne verujem da ce neko za vise od minuta potrositi da upise vreme koje vidi na satu, ali moze da se ishendluje

                        DateFormat dateFormat3 = new SimpleDateFormat("yyyy/MM/dd");   //odavde mi treba samo datum
                        String datumStr = dateFormat3.format(d);
                        String selektovaniSatStr = String.valueOf(i);
                        String selektovaniMinutStr = String.valueOf(i1);
                        String sekundeStr = String.valueOf(rezultatUSekundama);

                        //2022/03/29 23:54:24
                        String vremeStr = datumStr + " " + selektovaniSatStr + ":" + selektovaniMinutStr + ":" + sekundeStr;
                        try {
                            vremeNaSatu = dateFormat.parse(vremeStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        ///////////////////////////////////


                        Baza baza = new Baza(getActivity());
                        SQLiteDatabase db = baza.getWritableDatabase();
                        baza.onCreate(db);
                        BazaCheckpoint bazaCheckpoint = new BazaCheckpoint(baza);

                        ArrayList<Checkpoint> lista = new ArrayList<>();
                        lista = bazaCheckpoint.getAllCheckpointi(sat.getId());
                        //db.close();

                        //proveravam samo poslednjeg ucitanog iz liste jer ne moze se praviti novi checkpoint ako sposlednji nema setovano konacno odstupanje
                        long odstupanje = sistemskoVreme.getTime() - vremeNaSatu.getTime();

                        long razlikaUSekundama = (odstupanje / (1000)) % 60;
                        long razlikaUMinutima = (odstupanje / (1000 * 60)) % 60;
                        long razlikaUSatima = (odstupanje / (1000 * 60 * 60)) % 24;
                        //long razlikaUGodinama = (odstupanje / (1000l * 60 * 60 * 24 * 365));  //ne bi bas trebalo da se checkpoint uradi za godinu dana haha
                        long razlikaUDanima = (odstupanje / (1000 * 60 * 60 * 24)) % 365;

                        long razlikaUMinutimaUSekunde = razlikaUMinutima * 60;
                        long razlikaUSatimaUSekunde = razlikaUSatima * 3600;
                        //long razlikaUGodinamaUSekunde = razlikaUGodinama * 31536000;
                        long razlikaUDanimaUSekunde = razlikaUDanima * 86400;

                        long ukupneSekunde = razlikaUSekundama + razlikaUMinutimaUSekunde + razlikaUSatimaUSekunde + razlikaUDanimaUSekunde;

                        String rezultatUSekundamaStr = String.valueOf(ukupneSekunde);

                        //ovde bih trebao dalje da vrsim operacije izmedju datuma i kreiranje checkpointa u bazu
                        //prvo moram dohvatiti checkpointe i na poslednjem proveriti da li su neke od vrednostri null da bih znao sta da setujem
                        Checkpoint poslednji;
                        if(!lista.isEmpty()){     //ako lista nije prazna
                            poslednji = lista.get(lista.size() - 1);
                            if(poslednji.getKonacnoOdstupanje() == null){                   //ako poslednji nema konacno odstupanje, onda mu setujemo prvo vreme sistemsko i prvo vreme na satu i guramo u bazu, ostalo na null
                                int prvoOdstupanjeInt = Integer.parseInt(poslednji.getPrvoOdstupanje());
                                int drugoOdstupanje = Integer.parseInt(rezultatUSekundamaStr);
                                int konacno = prvoOdstupanjeInt - drugoOdstupanje;
                                bazaCheckpoint.editCheckpoint(poslednji.getCheckpointId(), poslednji.getPrvoVremeSistemsko(), poslednji.getPrvoVremeNaSatu(),
                                        poslednji.getPrvoOdstupanje(), dateFormat.format(sistemskoVreme), dateFormat.format(vremeNaSatu),
                                        rezultatUSekundamaStr, String.valueOf(konacno), null, sat.getId());

                            }else{                                               //ako poslednji ima konacno odstupanje onda dodajemo novi objekat sa pocetnim vrednostima
                                bazaCheckpoint.addCheckpoint(dateFormat.format(sistemskoVreme), dateFormat.format(vremeNaSatu), rezultatUSekundamaStr,
                                        null, null, null, null, null, sat.getId());
                            }
                        }else{            //ako je lista null, tj. i poslednji == null, znaci da u bazi nemamo nista i automatski dodajemo novog
                            bazaCheckpoint.addCheckpoint(dateFormat.format(sistemskoVreme), dateFormat.format(vremeNaSatu), rezultatUSekundamaStr,
                                    null, null, null, null, null, sat.getId());
                        }

                        iscrtajCheckpointe(sat, container);   //posle operacije sa bazom ponovo pozivam iscrtavanje checkpoint-a
                        db.close();
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT ,onTimeSetListener, selektovaniSat, selektovaniMinut, true);
                timePickerDialog.setTitle("Odaberite trenutno vreme na vasem satu:");
                timePickerDialog.show();

                //TODO: opis nisam setovao u bazu, nije mi ni bitan sad

            }
        });


    }

    private void iscrtajCheckpointe(Sat sat, ViewGroup container){
        container.removeAllViews();

        Baza baza = new Baza(getActivity());
        SQLiteDatabase db = baza.getWritableDatabase();
        baza.onCreate(db);
        BazaCheckpoint bazaCheckpoint = new BazaCheckpoint(baza);

        ArrayList<Checkpoint> lista = new ArrayList<>();
        lista = bazaCheckpoint.getAllCheckpointi(sat.getId());

        //db.close();

        LayoutInflater inf = getLayoutInflater();
        for(Checkpoint cc : lista){
            View red = inf.inflate(R.layout.layout_checkpoint, null);
            red.findViewById(R.id.layoutCheckpointRed);

            TextView labelaPrviCheckVreme = red.findViewById(R.id.labelaPrviCheck);
            TextView labelaDrugiCheckVreme = red.findViewById(R.id.labelaDrugiCheck);
            TextView labelaKonacnoOdstupanje = red.findViewById(R.id.labelaOdstupanje);
            ImageView slikaObrisi = red.findViewById(R.id.slikaObrisiCheckpoint);

            labelaPrviCheckVreme.setText(cc.getPrvoVremeSistemsko());   //ovo ne bi trebalo biti null nikad

            if(cc.getDrugoVremeSistemsko() == null){
                labelaDrugiCheckVreme.setText("");
            }else{
                labelaDrugiCheckVreme.setText(cc.getDrugoVremeSistemsko());
            }

            if(cc.getKonacnoOdstupanje() == null){
                labelaKonacnoOdstupanje.setText("");
            }else{
                labelaKonacnoOdstupanje.setText(cc.getKonacnoOdstupanje() + "s");
            }
            slikaObrisi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bazaCheckpoint.deleteCheckpoint(cc.getCheckpointId());
                    iscrtajCheckpointe(sat, container);
                }
            });

            container.addView(red);
        }
        db.close();


    }

//    Baza baza = new Baza(getActivity());
//    SQLiteDatabase db = baza.getWritableDatabase();
//                        baza.onCreate(db);
//    BazaCheckpoint bazaCheckpoint = new BazaCheckpoint(baza);






//                DateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");                    //    23:54:24
//                Date d2 = new Date(Long.parseLong("1648595277136"));  //dobije se zaista datum
//                Date d2 = null;
//                try {
//                    d2 = dateFormat.parse("2022/03/30 02:10:00");
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                Date d3 = null;
//                try {
//                    d3 = dateFormat.parse("2022/03/30 02:00:00");
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                long rezultat = d3.getTime() - d2.getTime();
//
//                long rezultatUMinutama = (rezultat / (1000 * 60)) % 60;

    //long difference_In_Seconds = (difference_In_Time / 1000) % 60;


    //System.out.println("DATUUUUUUUUUUUUUUUUUUUUUUM    " + rezultatUMinutama);
}
