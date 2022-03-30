package com.example.watchaccuracy;

public class Korisnik {
    private String korisnickoIme;
    private String lozinka;
    private String email;
    private boolean platioFulVerzijuAplikacije = false;

    public Korisnik() {
    }

    public Korisnik(String korisnickoIme, String lozinka, String email, boolean platioFulVerzijuAplikacije) {
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.email = email;
        this.platioFulVerzijuAplikacije = platioFulVerzijuAplikacije;
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

    public boolean isPlatioFulVerzijuAplikacije() {
        return platioFulVerzijuAplikacije;
    }

    public void setPlatioFulVerzijuAplikacije(boolean platioFulVerzijuAplikacije) {
        this.platioFulVerzijuAplikacije = platioFulVerzijuAplikacije;
    }


}
