package com.example.watchaccuracy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ///////////////////////////////////////
        //ovo mi sluzi za potrebe testiranja u procesu razvoja (da ponistim neke prikaze u aplikaciji jer zavise od ovih vrednosti iz shared preferences, pa ih ovde rucno setujem na ono sta mi treba)
        //LokalnoCuvanjeSharedPreferences.sacuvajDaLiJeUlogovan(getApplicationContext(), "nije");  //npr u ovom slucaju hocu da mi uvek otvori pocetni ekran za logovanje
        //LokalnoCuvanjeSharedPreferences.sacuvajUlogovanogKorisnika(getApplicationContext(), "");
        //ovo je bukvalno realizovana odjava korisnika, samo sto bih se posle ovoga prebacio na fragment prvo pokretanje


        //testiranje baze
        //4.1, api 26 ili vecu mozemo da koristimo view-tool windows-app inspection, i tada mozemo da posmatramo kako izgleda nasa baza podataka i da pisemo upite, npr. select * da vidimo sadrzaj baze

        //kreiranje baze
//        Baza baza = new Baza(getApplicationContext());
//        SQLiteDatabase db = baza.getWritableDatabase();
//
//        baza.onCreate(db);                                                          //onCreate mozemo ostaviti jer u upitu stoji if not exist, tako da necep ponistiti podatke koje imamo
        //baza.onUpgrade(db, 0, 0);
        //baza.brisiSve(db);

//        BazaSat bazaSat = new BazaSat(baza);
//        BazaCheckpoint bazaCheckpoint = new BazaCheckpoint(baza);


        //***********TABELA SATOVI

        //DODAVANJE, RADI!!!
        //bazaSat.addSat("Orient", "Ray 2");

        //IZMENA, RADI!!!
        //bazaSat.editSat(1, "NOVA MARKA", "NOVI MODEL");

        //BRISANJE, RADI!!!
        //bazaSat.deleteSat(1);

        //DOHVATANJE, RADI!!
//        ArrayList<Sat> lista = new ArrayList<>();
//        lista = bazaSat.getAllSatovi();

        //dohvatanje jednog, RADI!!!
//        Sat ss = bazaSat.getOneSat(1);
//        System.out.println(ss.getId() + " " + ss.getMarka());

        //PRIKAZ
//        System.out.println("U TABELI SATOVI: ");
//        for (Sat s : lista){
//            System.out.println(s.getId() + " " + s.getMarka() + " " + s.getModel());
//        }

        //****************TABELA CHECKPOINTI

        //DODAVANJE, RADI!!!
//        bazaCheckpoint.addCheckpoint("2021-7-17 23:14:58", "2021-7-17 23:14:58", null,
//                "2021-7-17 23:14:58", null, "2021-7-17 23:14:58", "2021-7-17 23:14:58",
//                "Neki opisssssssssssssssssss", 5);

        //IZMENA, RADI!!!
//        bazaCheckpoint.editCheckpoint(1, "2021-7-17 23:14:58", "2021-7-17 23:14:58", "2021-7-17 23:14:58",
//                "2021-7-17 23:14:58", "2021-7-17 23:14:58", "2021-7-17 23:14:58", "2021-7-17 23:14:58",
//                "DRUGI opis SADA PISE IZMENJENOOOOO", 1);

        //BRISANJE, RADI!!!
        //bazaCheckpoint.deleteCheckpoint(1);

        //DOHVATANJE, RADI!!!
//        ArrayList<Checkpoint> lista2 = bazaCheckpoint.getAllCheckpointi(5);

        //PRIKAZ
//        System.out.println("U TABELI CHECKPOINTI: ");
//        for (Checkpoint c : lista2){
//            System.out.println(c.getPrvoVremeSistemsko() + " " + c.getPrvoVremeNaSatu() + " "
//                    + c.getPrvoOdstupanje() + " " + c.getDrugoVremeSistemsko() + " " + c.getDrugoVremeNaSatu() + " "
//                    + c.getDrugoOdstupanje() + " " + c.getKonacnoOdstupanje() + " " + c.getSatId() + " "
//                    + c.getOpis());
//        }


        //prvi (pocenti) fragment se dodaje sa add, ostali se dodaju sa replace, i ako stavimo add to back stack onda se sa prikazanog fragmenta vraca na prethodni
        //main activity treba samo da barata fragmentima i nista drugo
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentView, FragmentPrvoPokretanje.newInstance(), "fragmentPrvoPokretanje")
                .commit();


        //zbog cega su tipovi u bazi checkpoint ubaceni kao numeric kada sam hteo da budu datetime?
        //za sada nemam problema i sve radi
        //nasao sam odgovor. sql lite nema datetime tip i zbog toga on meni skladisti kao numericki, to nije bitno jer ja svakako

        //Kako da omogucim da aplikacija sama na pocetku radi dohvatanje iz api-ja i da sam radi npr. iscitavanje iz shared preferences ako radim sa view modelom
        //imam dva nacina da radim sa fragmentima. Nesto im direktno prosledim i oni sa tim rade (npr. iscrtavaju)
        //preko view modela se iscrtava kada se nesto promeni. Dakle fragment radi observe nad podacima iz view modela
        //gde radim prelazak sa jednog fragmenta na drugi? U fragmentu?

        //kako da odradim slanje zahteva i proveri pri iscrtavanju naloga korisnika?
        //u fragmentu forma nalog ili u view model-u pa da ga observe-uje fragment za nalog?
        //da jednostavno dohvatim sve korisnike i setujem ulogovanog korisnika kojeg cu cuvati u shared preferences?
        //treba da se odradi kreiranje naloga korisnika, odnosno prvo slanje post zahteva na api a onda prikaz, kako to odraditi?


    }

    //TODO: ostalo je da konacno odstupanje pretvorim u second per day jedinicu (spd)
    //TODO: prikazati samo jedan sat ako nema ful verziju, tj. ucitati samo jedan iz baze, nzm da li ima smisla
    //TODO: jer ako je korisnik kupio ful verziju aplikacije zasto bi se sa istog uredjaja ulogovao sa nalogom koji nema kupljenu ful verziju
    //TODO: izmeniti o aplikaciji
    //svaki toast u projektu staviti na length short
    //TODO: trebao sam jos raditi sa senzorom kamere da korisnik moze da slika sat i da moze da postavi tu sliku u prikazu satova
    //TODO: izmeniti u klasi checkpoint da se id ne zove checkpointId nego samo id, ali tu je vec lanac izmena svuda
    //TODO: Trebala bi se i dodati dodatna ogranicenja, npr. da ne sme da unese kor ime od 300 karaktera i lozinku manje od 8 karaktera,
    //TODO: da se u bazu ne sme dodati vise od npr. 100 satova, itd. To su sve finese i zahtevaju dodatni posao
    //TODO: problem je sto sa serverom npr. lozinku razmenjujem kao plain text
    //TODO: da bi smo znali koje sekunde da setujemo (PRI ODABIRU VREMENA NA SATU), jer od klika na dugme CHECK do odabira vreme isto prodje vreme koje su nam u stvari sekunde, ne verujem da ce neko za vise od minuta potrositi da upise vreme koje vidi na satu, ali moze da se ishendluje
    //TODO: popraviti opis check pointa, da se i on setuje pri kreiranju i da se prikaze u odvojenom ekranu

    //za diplomski
    //word!!!
    //istorijat androida, koji problem resava aplikacija? pojasnjenje sa sscreenshot kod (screenshot na emulatoru se radi na crtl + s)



}


