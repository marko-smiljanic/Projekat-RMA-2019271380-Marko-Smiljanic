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

Na pocetnoj stranici aplikacije imamo meni koji ima stavke: nalog (vodi na ekran koji daje informacije o nasem nalogu
i funkcionalnost da se kupi ful verzija aplikacije, tj. samo simulacija tako necega. Salje se post zahtev gde se 
proverava da vec nema kupljenu ful verziju, i ako nema onda je uspesno kupio), o aplikaciji (vodi na prikaz about dela
gde je objasnjeno kako aplikacija treba da radi i koja joj je svrha), odjava (omogucuje korisniku odjavu i vraca ga na 
ekran dobrodoslice. Odjava je realizovana tako sto se iz shared preference izbaci ulogovan korisnik i ulogovan setuje na nije)












