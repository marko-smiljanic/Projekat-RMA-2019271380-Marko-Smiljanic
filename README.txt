Projektat je prilagodjen za minimalnu verziju androida API 16: Android 4.1 (Jelly Bean), sto znaci da podrzava 
100% android uredjaja.

Rad aplikacije:

Korisniku se prvo prikazuje ekran pocetni ekran (ekran dobrodoslice) gde je korisnik upitan da li ima vec postojeci nalog za koriscenje 
aplikacije ili nema. Ukoliko je korisnik vec ulogovan u aplikaciju sa nalogom onda mu se ne iscrtava prikaz ekrana 
dobrodoslice nego se direktno salje na pocetnu stranicu aplikacije gde su prikazani njegovi satovi.

Ukoliko korisnik odabere dugme "imam nalog" ono ga vodi na formu gde bi trebao da se uloguje sa postojecim nalogom.
Korisnik unosi samo korisnicko ime i lozinku. Provere na klijentu su samo da ne sme unos biti prazan. Provera da li 
postoji takav korisnik se radi na serveru. U zavisnosti od response-a servera ga obavestimo da uneti podaci nisu tacni 
i da pokusa ponovo, ako je unos ok, i takav korisnik postoji saljemo ga na pocetnu stranicu sa njegovim satovima.

Ukoliko korisnik odabere dugme "nemam nalog" ono ga vodi na formu gde je pitan za unos podataka za kreiranje novog naloga.
Klikom na dugme potvrdi se salje post zahtev na api gde se proverava autenticnost korisnickog imena i email-a.
Korisniku se nece dozvoliti da napravi nalog ukoliko takvo korisnicko ime ili email vec poseduju drugi nalozi.
Ove provere su odradjene na serveru. Znaci server salje odredjeni response u zavisnosti od rezultata provere 
autenticnosti unetih podataka i korisnik dobija jasna obavestenja ukoliko podaci za novi nalog nisu autenticni. Na klijentu 
su onsovne provere, da nije polje za unos prazno, da mejl sadrzi "@". Nakon sto pravilno unese podatke i dobije 
"zeleno svetlo" od servera korisnik ima napravljeni nalog (sa kojim je ujedno i ulogovan) i dalje se navigira 
na pocetnu stranicu aplikacije gde su mu prikazani njegovi satovi.

Na pocetnoj stranici aplikacije imamo meni koji ima stavke: 
-nalog (vodi na ekran koji daje informacije o nasem nalogu i funkcionalnost da se kupi ful verzija aplikacije, 
tj. samo simulacija tako necega. Salje se post zahtev gde se proverava da vec nema kupljenu ful verziju, i ako nema 
onda je uspesno kupio, i to je to), 
-o aplikaciji (vodi na prikaz about dela gde je objasnjeno kako aplikacija treba da radi i koja joj je svrha),
-odjava (omogucuje korisniku odjavu i vraca ga na ekran dobrodoslice. Odjava je realizovana tako sto se iz 
shared preference izbaci ulogovan korisnik i ulogovan se promeni takodje)

Dalje na pocetnoj stranici mozemo osim menija videti i satove. Pojedinacni sat moze da se izmeni ili obrise.
Opcija dodavanje sata je omogucena samo nalozima koji imaju uplaceno ful verziju aplikacije. Satovima upravlja lokalna baza.

****Trebalo bi dodati da kada se uloguje nalog koji nema ful verziju aplikacije da se iz baze samo iscita prvi sat. 
(jer satovima upravlja lokalna baza na uredjaju i mozda je vise satova ostavljeno od nekog prethodnog naloga koji je imao
ful verziju aplikacije, ali ovo i nema mnogo smisla jer jedan korisnik koji instalira aplikaciju, i ako plati ful verziju
aplikacije, male su sanse da ce napraviti novi nalog na tom uredjaju koji nece imati uplacenu ful verziju)****

Satovi imaju opciju pravljenja checkpointa. Svaki sat ima svoje checkpoint-e. Klikom na dugme check idemo na stranicu za 
pravljenje i prikaz checkpointa. Checkpoint-ima upravlja baza. Checkpointi se prikazuju samo za selektovani sat.
Azuriran je prikaz poslednjeg checkpointa u prikazu satova na pocetnom ekranu. 

Detaljnije u specifikaciji.

****Treba dodati da se odstupanje satova prikaze (preko proporcije) na 24h, a ne ukupno odstupanje kako je sada odradjenja.
****Treba dodati da se prozor za unosenje vremena na satu u aplikaciju ugasi posle 1 min (jer posle 1 min ne mogu da 
obradim unosenje sekundi)
****Treba dodati opomenu da ako korisnik odradi checkpoint ukoliko odradi npr.u razmacima manjim od 6h.






