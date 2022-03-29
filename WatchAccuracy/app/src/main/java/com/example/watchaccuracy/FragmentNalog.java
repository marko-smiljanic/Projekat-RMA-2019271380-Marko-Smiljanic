package com.example.watchaccuracy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FragmentNalog extends Fragment {
    public MojViewModel viewModel;
    public FragmentNalog(){

    }
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
        ConstraintLayout l = vv.findViewById(R.id.nalogMainLyt);

        String korisnickoIme = LokalnoCuvanjeSharedPreferences.ucitajUlogovanogKorisnika(getActivity());   //sve pocinje ucitavanjem ulogovanog
        if (korisnickoIme.equals("")) {
            System.out.println("GRESKA");
            return null;
        }

        iscrtajPrikaz(l, korisnickoIme);     //PROSLEDJUJEMO KORISNICKO IME ULOGOVANOG KORISNIKA JER NAM TREBA ZA ISCRTAVANJE FRAGMENTA I SLANJE API ZAHTEVA

        return vv;
    }

    public void iscrtajPrikaz(ConstraintLayout ll, String korIme) {
        TextView korisnickoImeLabela = ll.findViewById(R.id.nalogKorIme);
        TextView emailLabela = ll.findViewById(R.id.nalogEmail);
        TextView platioLabela = ll.findViewById(R.id.nalogPlacenaFulVerzija);
        Button dugmeplati = ll.findViewById(R.id.dugmeKupiFulVerziju);

        //SIMULACIJA KUPOVINE FUL VERZIJE APLIKACIJE
        //U OVOM SLUCAJU SE ZAHTEV SALJE NA API I PROVERAVA SE DA LI VEC IMA KUPLJENO, AKO NEMA SETUJE MU SE NA KUPLJENO
        //I TO JE TO, PROSTA SIMULACIJA
        dugmeplati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.getCache().clear();
                String url = "http://192.168.0.32:5000/platiFulVerziju/" + korIme;
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        if (response.equals("greska")) {
                            return;
                        } else if (response.equals("korisnik je vec platio")) {
                            Toast.makeText(getActivity().getApplicationContext(), "Korisnik vec ima uplacenu punu verziju aplikacije!", Toast.LENGTH_LONG).show();
                            return;
                        } else if (response.equals("sve ok")) {
                            Toast.makeText(getActivity().getApplicationContext(), "Ful verzija uspesno uplacena!", Toast.LENGTH_LONG).show();
                            platioLabela.setText("Kupljena ful verzija");
                        }
                        viewModel.setUlogovani();         //ovde sam pozvao set ponovo, a observe-uje pocetni ekran
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
                queue.add(request);
            }
        });  //kraj onclick-a!!!!!!

        //SLANJE ZAHTEVA ZA DOHVATANJE I ISCRTAVANJE FRAGMENTA
        //NA APIJU PROVERA ZA DOHVATANJE KORISNIKA SA PROSLEDJENIM KORISNICKIM IMENOM
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.getCache().clear();
        String url = "http://192.168.0.32:5000/dobaviJednogKorisnika/" + korIme;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                if(response.equals("greska")) {
                    return;
                }

                try {
                    JSONObject jsonKorisnik = new JSONObject(response);
                    Korisnik kk = new Korisnik();

                    if (jsonKorisnik.has("korisnicko_ime")) {
                        kk.setKorisnickoIme(jsonKorisnik.getString("korisnicko_ime"));
                    }
                    if (jsonKorisnik.has("lozinka")) {
                        kk.setLozinka(jsonKorisnik.getString("lozinka"));
                    }
                    if (jsonKorisnik.has("email")) {
                        kk.setEmail(jsonKorisnik.getString("email"));
                    }
                    if (jsonKorisnik.has("platio_ful_verziju")) {
                        boolean platio;
                        if (jsonKorisnik.getString("platio_ful_verziju").equals("true")) {
                            platio = true;
                        } else {
                            platio = false;
                        }
                        kk.setPlatioFulVerzijuAplikacije(platio);
                    }
                    //prikaz
                    korisnickoImeLabela.setText(kk.getKorisnickoIme());
                    emailLabela.setText(kk.getEmail());
                    if(kk.isPlatioFulVerzijuAplikacije() == true) {
                        platioLabela.setText("Kupljena ful verzija");
                    }else if(kk.isPlatioFulVerzijuAplikacije() == false) {
                        platioLabela.setText("Nije kupljena ful verzija");
                    }
                    viewModel.setUlogovani();
                } catch (JSONException e) {
                    System.out.println("Greska prilikom konvertovanja u JSON tip podataka");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        queue.add(request);
    }







}
