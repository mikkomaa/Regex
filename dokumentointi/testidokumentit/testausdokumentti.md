# Testausdokumentti
## Yksikkötestit
Ohjelma on toteutettu Javalla. Ohjelman luokkia kirjoitettaessa niille kirjoitettiin samalla yksikkötestit JUnitilla.

Pääsääntöisesti yksikkötestit toteutettiin kaikkiin luokkiin. Yksikkötestejä ei kuitenkaan ole Main-luokalle, joka pelkästään käynnistää ohjelman. Yksikkötestejä ei myöskään ole Suoritustesti-luokalle. Tämä luokka toteuttaa automaattisen suorituskykytestauksen eikä ole osa ohjelman varsinaista toiminnallisuutta. (Käyttöliittymän yksikkötestaus...?)

Yksikkötesteillä pyrittiin hyvää kattavuuteen erityisesti siltä osin, kuin ohjelman toimintalogiikka ei ole itsestään selvää ja logiikan toteutus vaati tarkkuutta. Tällaisia kohtia olivat etenkin säännöllisen lausekkeen oikeellisuuden tarkistaminen ja lausekkeen muuntaminen käyttäjän syöttämästä infix-muodosta postfix-muotoon. Etenkin näissä tapauksissa yksikkötestaus osoittautui hyödylliseksi.

Ohjelman luokkien yhtenä toteutusperiaatteena oli, että ne ovat toiminnaltaan itsenäisiä ja toisistaan riippumattomia. Tämä vähensi mahdollisuutta siihen, että integraatiovaiheessa havaittaisiin toimintaongelmia, ja korosti yksikkötestauksen painoarvoa. Ohjelman käyttöönotto olikin vaivatonta sen jälkeen, kun tarvittavat sovelluslogiikkaluokat olivat valmiit.

## Rivikattavuus ja mutaatiotestaus
Testauksen rivi- ja haarautumakattavuuden selvittämisen käytettiin Coberturaa. Mutaatiotestaukseen käytettiin Pitiä.

Rivikattavuus on kaikissa luokissa vähintään X prosenttia. Mutanteista tapettiin kaikissa luokissa vähintään X prosenttia. Testauskattavuus on näiltä osin pyritty saamaan niin korkeaksi, kuin se on järkevää ohjelman luonne huomioon ottaen. Parissa kohdassa esimerkiksi 100 prosentin haarautumakattavuus olisi tehnyt koodin vaikeaselkoisemmaksi tai lisännyt riskiä, että koodiin jäisi bugi mahdollisten tulevien laajennusten yhteydessä.

Cobertura- ja Pit-raportit ovat mukana testidokumenteissa.

## Suorituskykytestaus
### Automaattiset testit
Ohjelmaan on kirjoitettu Suoritustesti-luokka, joka automatisoi suorituskykytestauksen. Testit voidaan suorittaa ajamalla ohjelma komentoriviltä komennolla "ohjelmannimi testi". Tällöin ohjelma käynnistää normaalin käyttöliittymän sijasta Suoritustesti-luokan ja ajaa testit.

Testiaineisto määritellään tiedostoissa lausekkeet.txt ja tiedostot.txt. Jokainen tiedostot.txt-tiedostossa nimetty tiedosto testataan jokaisella lausekkeet.txt-tiedostossa mainitulla säännöllisellä lausekkeella. Jos esim. lausekkeet.txt sisältää kolme lauseketta ja tiedostot.txt kaksi tiedostonimeä, ajetaan yhteensä kuusi erilaista testiä. Jokainen kuudesta testistä ajetaan kymmenen kertaa. Yhden testin suoritusaika saadaan kymmenen testiajon keskiarvona.

Testitulokset kirjoitetaan testiraportit.txt-tiedostoon. Jos tiedosto on olemassa ennen testausta, se ylikirjoitetaan.

Automaattinen testaus simuloi ohjelman normaalia toimintaa. Testien aluksi ohjelma lukee lausekkeet.txt- ja tiedostot.txt-tiedostojen sisällöt muistiin yksittäisiksi String-merkkijonoiksi. Tästä eteenpäin ohjelma toimii lähes samalla tavalla kuin, jos käyttäjä olisi antanut lausekkeen ja tiedostonimen komentoriviltä.

Poikkeuksena on kuitenkin se, ettei testiympäristö tulosta rivejä ruudulle (eikä muuallekaan). Tähän on pari syytä. Ruudulle tulostus on ensinnäkin suhteellisen hidasta ja riippuu myös käyttöjärjestelmän toteutuksesta. Suorituskykytestien tarkoituksena on testata ohjelman toteutusalgoritmien tehokkuutta, ei ruudulle tulostamisen. Toiseksi tulostuksen hitaus aiheuttaisi sen, että ohjelman tehokkuuden vertailu eri lausekkeilla vaikeutuisi, koska ruudulle tulostuksia voisi olla eri lausekkeilla huomattavasti eri määrä. Ilman tulostusta saa paremman kuvan ohjelman suhteellisesta nopeudesta erilaisilla lausekkeilla.

### Omien testien tekeminen
Käyttäjä voi helposti lisätä omia suorituskykytestejä. Lausekkeita voi lisätä lausekkeet.txt-tiedostoon, yhdelle riville yksi lauseke. Tiedostonimiä voi lisätä tiedostot.txt-tiedostoon, yhdelle riville yksi nimi. Tiedostojen viimeisen rivin loppuun ei kannata laittaa rivinvaihtoa.

Testattavien tiedostojen on oltava määrittelytiedostojen kanssa samassa kansiossa tai sellaisessa muussa kansiossa, josta käyttäjän suoritusympäristö löytää ne.

### Testitulokset
täydentyy...

