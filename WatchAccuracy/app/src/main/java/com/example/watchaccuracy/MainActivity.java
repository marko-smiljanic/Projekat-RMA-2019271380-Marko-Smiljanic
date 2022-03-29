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
//        System.out.println(ss.getMarka());

        //PRIKAZ
//        System.out.println("U TABELI SATOVI: ");
//        for (Sat s : lista){
//            System.out.println(s.getMarka() + " " + s.getModel());
//        }

        //****************TABELA CHECKPOINTI

        //DODAVANJE, RADI!!!
//        bazaCheckpoint.addCheckpoint("2021-7-17 23:14:58", "2021-7-17 23:14:58", "2021-7-17 23:14:58",
//                "2021-7-17 23:14:58", "2021-7-17 23:14:58", "2021-7-17 23:14:58", "2021-7-17 23:14:58",
//                "Neki opisssssssssssssssssss", 1);

        //IZMENA, RADI!!!
//        bazaCheckpoint.editCheckpoint(1, "2021-7-17 23:14:58", "2021-7-17 23:14:58", "2021-7-17 23:14:58",
//                "2021-7-17 23:14:58", "2021-7-17 23:14:58", "2021-7-17 23:14:58", "2021-7-17 23:14:58",
//                "DRUGI opis SADA PISE IZMENJENOOOOO", 1);

        //BRISANJE, RADI!!!
        //bazaCheckpoint.deleteCheckpoint(1);

        //DOHVATANJE, RADI!!!
//        ArrayList<Checkpoint> lista2 = bazaCheckpoint.getAllCheckpointi(2);

        //PRIKAZ
//        System.out.println("U TABELI CHECKPOINTI: ");
//        for (Checkpoint c : lista2){
//            System.out.println(c.getPrvoVremeSistemsko() + " " + c.getPrvoVremeNaSatu() + " "
//                    + c.getPrvoOdstupanje() + " " + c.getDrugoVremeSistemsko() + " " + c.getDrugoVremeNaSatu() + " "
//                    + c.getDrugoOdstupanje() + " " + c.getKonacnoOdstupanje() + " " + c.getSatId() + " "
//                    + c.getOpis());
//        }





        //zbog cega su tipovi u bazi checkpoint ubaceni kao numeric kada sam hteo da budu datetime?
        //za sada nemam problema i sve radi
        //nasao sam odgovor. sql lite nema datetime tip i zbog toga on meni skladisti kao numericki, to nije bitno jer ja svakako
        //pretvaram u string i radim sa njim kada citam iz baze
        //TODO: da li da ipak promenim tip na TEXT?




        //prvi (pocenti) fragment se dodaje sa add, ostali se dodaju sa replace, i ako stavimo add to back stack onda se sa prikazanog fragmenta vraca na prethodni
        //main activity treba samo da barata fragmentima i nista drugo
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentView, FragmentPrvoPokretanje.newInstance(), "fragmentPrvoPokretanje")
                .commit();


        //Kako da omogucim da aplikacija sama na pocetku radi dohvatanje iz api-ja i da sam radi npr. iscitavanje iz shared preferences ako radim sa view modelom
        //imam dva nacina da radim sa fragmentima. Nesto im direktno prosledim i oni sa tim rade (npr. iscrtavaju)
        //preko view modela se iscrtava kada se nesto promeni. Dakle fragment radi observe nad podacima iz view modela
        //gde radim prelazak sa jednog fragmenta na drugi? U fragmentu?

        //kako da odradim slanje zahteva i proveri pri iscrtavanju naloga korisnika?
        //u fragmentu forma nalog ili u view model-u pa da ga observe-uje fragment za nalog?
        //da jednostavno dohvatim sve korisnike i setujem ulogovanog korisnika kojeg cu cuvati u shared preferences?
        //treba da se odradi kreiranje naloga korisnika, odnosno prvo slanje post zahteva na api a onda prikaz, kako to odraditi?


    }



    //TODO: problem koji nije resen: Ako se prvo uloguje korisnik koji ima ful verziju aplikacije i napravi vise satova onda ce
    //todo: sledeci korisnik koji se bude ulogovao isto imati pristup tim satovima iako nije mozda platio ful verziju aplikacije
    //TODO: resenje je da se ove dve funkcije spoje u view modelu i da u jednoj funkciji odradim i citanje korisnika i na osnovu
    //TODO: rezultata citanja korisnika onda odradim citanje iz baze. Ako korisnik nema uplacenu ful verziju ne prikazuj vise od jednog sata
    //TODO: ili da napravim da se satovi prikazuju u odvojenom fragmentu

}


