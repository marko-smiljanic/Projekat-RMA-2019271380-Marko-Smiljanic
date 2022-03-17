package com.example.watchaccuracy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ///////////////////////////////////////
        //prvi (pocenti) fragment se dodaje sa add, ostali se dodaju sa replace, i ako stavimo add to back stack onda se sa prikazanog fragmenta vraca na prethodni
        //main activity treba samo da barata fragmentima i nista drugo
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentView, FragmentPrvoPokretanje.newInstance(), "fragmentPrvoPokretanje")
                .commit();



        //Kako da omogucim da aplikacija sama na pocetku radi dohvatanje iz api-ja i da sam radi npr. iscitavanje iz shared preferences
        //ako radim sa view modelom????
        //i zbog cega ne radi shared preferences, ovo sam nasao, u fragmentu moram sa getactivity, u view modelu sa getapplication
        //gde bih implementirao setovanje ulogovanog u shared preferences? Na kraju kada kreira nalog?
        //da li bih mogao da imam atribut ulogovan u fragmentu prvo pokretanje pa da tamo iscrtavam u zavinsosti od njegove vredosti

        //kako da se na onclick u fragment prvo pokretanje navigiram dalje na prikaze fragmenata?
        //ako budem brkao po fragmentima i tamo onda?



        //imam dva nacina da radim sa fragmentima. Nesto im direktno prosledim i oni sa tim rade (npr. iscrtavaju)
        //preko view modela se iscrtava kada se nesto promeni. Dakle fragment radi observe nad podacima iz view modela
        //gde radim prelazak sa jednog fragmenta na drugi? U fragmentu?


        //kako da odradim slanje zahteva i proveri pri iscrtavanju naloga korisnika?
        //u fragmentu forma nalog ili u view model-u pa da ga observe-uje fragment za nalog?
        //da jednostavno dohvatim sve korisnike i setujem ulogovanog korisnika kojeg cu cuvati u shared preferences?
        //treba da se odradi kreiranje naloga korisnika, odnosno prvo slanje post zahteva na api a onda prikaz, kako to odraditi?



    }

}


