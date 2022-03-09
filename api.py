import email
from types import MethodDescriptorType
import flask, json
from flask import Flask, request
import json

app = Flask('__main__', template_folder="", static_folder="", root_path="", static_url_path="")
msgs = []


@app.route('/')
def index_page():
    return ("Hello")

@app.route('/dobaviJednogKorisnika/<string:korisnickoIme>', methods=["GET"])       #moram obezbediti da korisnicko ime bude jedinstveno!!!!
def prikaz_jednog_korisnika(korisnickoIme):
    try:
        with open("korisnici.json") as f:
            data = json.load(f)
            for kk in data:
                if kk["korisnicko_ime"] == korisnickoIme:
                    return str(kk)
    except(Exception):
        return "Greška"

@app.route('/dobaviSveKorisnike', methods=["GET"])
def prikaz_svih():
    return flask.send_file("korisnici.json")

@app.route('/dodajJednogKorisnika', methods=["POST"])
def dodaj_korisnika():
    recnik = dict(flask.request.form)

    recnik2 = {                                                 #ovde sam morao da pravim novi recnik i da prepisujem vrednosti iz recnika response-a jer su vrednosti iz nekog razloga obrutim redosledom u njemu
        "korisnicko_ime": recnik["korisnicko_ime"],
        "lozinka": recnik["lozinka"],
        "email": recnik["email"],
        "platio_ful_verziju": recnik["platio_ful_verziju"]
    }                                  

    with open("korisnici.json", "r") as ff:                     #otvorimo json fajl
        json_data = json.load(ff)
    json_data = list(json_data)                                 #pretvorimo u listu jer je tako upisan u json-u, tj. pocinje i zavrsava se sa [ ]
    json_data.append(recnik2)                                   #dodamo recnik iz response-a

    with open ("korisnici.json", "w") as zz:                    #prepisemo izmenjeni fajl na postojeci, tj. novi samo sacuvamo preko starog (jer tako se cuva fajl, obrisi sve sto je bilo i dodaj novi fajl)
        json.dump(json_data, zz, indent=2)                      #ovde bi mogao dodati indent da mi json fajl zapise u preglednijoj strukturi a ne sve u jednoj liniji

    return "", 201





app.run("0.0.0.0", 5000)

