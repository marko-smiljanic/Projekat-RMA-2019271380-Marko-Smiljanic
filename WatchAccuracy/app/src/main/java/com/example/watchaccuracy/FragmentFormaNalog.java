package com.example.watchaccuracy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class FragmentFormaNalog extends Fragment {
    private MojViewModel mojViewModel;
    private String kliknutoDugme;
    private String ulogovan;


    public FragmentFormaNalog(){}

    public static FragmentFormaNalog newInstance(){
        FragmentFormaNalog fragment = new FragmentFormaNalog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mojViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.Factory() {
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
        View vv = inflater.inflate(R.layout.fragment_forma_nalog, container, false);
        ConstraintLayout cl = vv.findViewById(R.id.mainLayoutFormaNalog);

        kliknutoDugme = LokalnoCuvanjeSharedPreferences.ucitajKojeJeKliknutoDugme(getActivity());
        if(kliknutoDugme.equals("imamNalog")){
            iscrtajKadImaNalog(cl);

        }else if(kliknutoDugme.equals("nemamNalog")){
            iscrtajKadNemaNalog(cl);

        }
        return vv;
    }

    private void iscrtajKadNemaNalog(ConstraintLayout ll){
        TextView labelaKorIme = ll.findViewById(R.id.labelaKorIme);
        TextView labelaLozinka = ll.findViewById(R.id.labelaLozinka);
        TextView labelaEmail = ll.findViewById(R.id.labelaEmail);

        EditText inputKorIme = ll.findViewById(R.id.inputKorIme);
        EditText inputLozinka = ll.findViewById(R.id.inputLozinka);
        EditText inputEmail = ll.findViewById(R.id.inputEmail);

        //cisto ako je ostalo gone od prosli put
        labelaEmail.setVisibility(View.VISIBLE);
        inputEmail.setVisibility(View.VISIBLE);

        TextView naslov = ll.findViewById(R.id.textViewNaslov);
        naslov.setText("Unesite podatke da napravite nalog:");

        Button potvrdi = ll.findViewById(R.id.buttonPotvrdi);
        potvrdi.setOnClickListener(new View.OnClickListener() {                                     //kreiranje naloga korisnika, tj. slanje post zahteva sa unetim vrednostima
            @Override
            public void onClick(View view) {
                ulogovan = LokalnoCuvanjeSharedPreferences.ucitajDaLiJeUlogovan(getActivity());     //obavezno provera da li je ulogovan
                if(ulogovan.equals("jeste")){
                    System.out.println("Korisnik je vec ulogovan. Greska.");
                    return;                                                                         //ako je ulogovan necemo praviti nalog
                }else if(ulogovan.equals("nije")){
                    while(inputKorIme.getText().toString().equals("") || inputLozinka.getText().toString().equals("") || inputEmail.getText().toString().equals("")) {
                        Toast.makeText(getActivity().getApplicationContext(), "Sva polja su obavezna!", Toast.LENGTH_LONG).show();
                        return;                                                                     //prekidamo izvrsavanje onclick-a i beskonacne while petlje
                    }
                    //iz nekog razloga ako se odradi split na  "@ada" (tj. pre @ nista) on iz nekog razloga posmatra kao da je razdelio na 2 elementa, necu ovaj izuzetak obraditi, nije bitno toliko
                    while((inputEmail.getText().toString()).split("@").length <= 1){                         //kad rastavimo string email po "@" i on ne vrati nista znaci nema @, ako ne vrati niz koji ima npr. 2 kraja nesto@nesto2 onda je pokusao da unese nesto@ ili @nesto i tu ga secemo, e sad ovde je problem jer ako je uneto @nesto on prepoznaje da je to niz od dva elementa jer se pre @ podrazumeva da stoji "
                        Toast.makeText(getActivity().getApplicationContext(), "Email adresa u neispravnom formatu!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    //Kada se uspesno odradi post zahtev sada se mozemo prebaciti na korisnikov nalog, tj. na neki pocetni ekran aplikacije
                    //takodje ako je sve sa post zahtevom proslo ok moram setovati ulogovan u shared preferences
                    //u ovom slucaju post zahteva moram proveriti sta je response i na osnovu njega odreagovati

                    RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    queue.getCache().clear();   //mozda da se kes cisti ovde umesto na kraju?
                    //queue.getCache().remove("http://192.168.0.31:5000/dodajJednogKorisnika");    //moramo da ocistimo kes da volley ne bi zabadao

                    String url = "http://192.168.0.31:5000/dodajJednogKorisnika";
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            if(response.equals("korisnicko ime vec postoji")){
                                Toast.makeText(getActivity().getApplicationContext(), "Nalog sa ovim korisnickim imenom vec postoji!", Toast.LENGTH_LONG).show();
                                return;
                            }else if(response.equals("email adresa vec postoji")){
                                Toast.makeText(getActivity().getApplicationContext(), "Nalog sa ovom email adresom vec postoji!", Toast.LENGTH_LONG).show();
                                return;
                            }else if(response.equals("sve ok")){
                                LokalnoCuvanjeSharedPreferences.sacuvajDaLiJeUlogovan(getActivity(), "jeste");                        //cuvamo status da je ulogovan
                                LokalnoCuvanjeSharedPreferences.sacuvajUlogovanogKorisnika(getActivity(), inputKorIme.getText().toString());      //cuvamo korisnicko ime ulogovanog korisnika

                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.fragmentView, FragmentNalog.newInstance(), "fragmentNalog");
                                //ft.addToBackStack("fragmentNalog");  //u ovom slucaju mi ovo ne treba
                                ft.commit();

                                Toast.makeText(getActivity().getApplicationContext(), "Uspesno ste se kreirali nalog!", Toast.LENGTH_LONG).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error);
                        }
                    }){
                        @Override
                        public Map<String, String> getParams(){                                            //trebalo bi da moze da se stavi object umesto string i da direktno saljem niz ali psoto ne moze tako da se odradi override onda moram da saljem niz u obliuku json array-a i da ga konvertujem u string
                            Map<String, String> params = new HashMap<String, String>();
                            //proveru za ispravnost formata unetih polja sam odradio gore izvan slanja zahteva

                            params.put("korisnicko_ime", inputKorIme.getText().toString());
                            //System.out.println(inputKorIme.getText().toString());
                            params.put("lozinka", inputLozinka.getText().toString());
                            params.put("email", inputEmail.getText().toString());
                            params.put("platio_ful_verziju", "false"); //kada kreira nalog po default-u stavljamo da nije platio ful verziju

                            return params;
                        }
                    };
                    queue.add(request);
                }
            }
        });
    }

    private void iscrtajKadImaNalog(ConstraintLayout ll){  //kad ima nalog nema potrebe da unosi email kada proveru radim po kor imenu i lozinci
        TextView labelaKorIme = ll.findViewById(R.id.labelaKorIme);
        TextView labelaLozinka = ll.findViewById(R.id.labelaLozinka);
        TextView labelaEmail = ll.findViewById(R.id.labelaEmail);

        EditText inputKorIme = ll.findViewById(R.id.inputKorIme);
        EditText inputLozinka = ll.findViewById(R.id.inputLozinka);
        EditText inputEmail = ll.findViewById(R.id.inputEmail);

        labelaEmail.setVisibility(View.GONE);
        inputEmail.setVisibility(View.GONE);

        TextView naslov = ll.findViewById(R.id.textViewNaslov);
        naslov.setText("Unesite podatke da se ulogujete sa svojim nalogom:");

        Button potvrdi = ll.findViewById(R.id.buttonPotvrdi);
        potvrdi.setOnClickListener(new View.OnClickListener() {    //slanje get zahteva i vracanje korisnika koji se poklapa sa unetim vrednostima
            @Override
            public void onClick(View view) {
                String korIme = inputKorIme.getText().toString();
                String lozinka = inputLozinka.getText().toString();

                ulogovan = LokalnoCuvanjeSharedPreferences.ucitajDaLiJeUlogovan(getActivity());
                if(ulogovan.equals("jeste")){
                    return;
                }else if(ulogovan.equals("nije")){
                    return;
                    //TODO: stao sam ovde, kod post zahteva i provere da li takav korisnik postoji, ako postoji ulogujem ga (shared pref. na polja ulogovan i ulogovani korisnik)
                    //TODO: ako ne postoji takav korisnik onda vratim response od servera i prikazem toast "neispravan unos". Provere se rade na serveru.
                    //TODO: kada ovo odradim ostaje da odradim dalje prikaz pocetnog ekrana sa satovima.
                }


            }
        });

    }




}
