package com.example.watchaccuracy;

public class Korisnik {
    private String korisnickoIme;
    private String lozinka;
    private String email;
    private boolean platioFulVerzijuAplikacije = false;
    private boolean dodatPrviSat = false;

    public Korisnik() {
    }

    public Korisnik(String korisnickoIme, String lozinka, String email) {
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.email = email;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void placanjeFulVerzijeAplikacije(){
        this.platioFulVerzijuAplikacije = true;
    }



}
