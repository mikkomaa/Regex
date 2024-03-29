# Testausdokumentti
## Yksikkötestit
Ohjelma on toteutettu Javalla. Ohjelman luokkien yhtenä toteutusperiaatteena oli, että ne ovat toiminnaltaan itsenäisiä ja toisistaan riippumattomia. Näin yksikkötestausta oli mahdollista hyödyntää tehokkaasti, ja kattavat testit vähensivät merkittävästi mahdollisuutta virheiden löytymiseen integraatiovaiheessa. Yksikkötestaus osoittautuikin hyödylliseksi, sillä integraatiovaiheessa toimintalogiikasta ei löytynyt yhtään bugia.

Ohjelmaa kirjoitettaessa luokille tehtiin yksikkötestejä seuraavasti.

### Sovelluslogiikka ja omat tietorakenteet
Kaikkiin sovelluslogiikkaluokkiin kirjoitettiin automaattiset testit JUnitilla. Sama koski luokkia, joilla toteutettiin omat tietorakenteet.

Kaikki näiden luokkien metodit testattiin. Yksikkötesteillä pyrittiin hyvään kattavuuteen erityisesti siltä osin, kuin ohjelman toimintalogiikka ei ole itsestään selvää ja logiikan toteutus vaati tarkkuutta. Tällaisia kohtia olivat etenkin säännöllisen lausekkeen oikeellisuuden tarkistaminen ja lausekkeen muuntaminen käyttäjän syöttämästä infix-muodosta postfix-muotoon.

Testien rivi- ja haarautumakattavuus selvitettiin Coberturalla. Rivi- ja haarautumakattavuus on suurimmassa osassa luokkia 100 prosenttia. Rivikattavuus on kaikissa luokissa yhteensä 99 prosenttia ja haarautumakattavuus yhteensä 98 prosenttia.

Mutaatiotestaukseen käytettiin Pitiä. Mutaatiokattavuus on puolessa luokista 100 prosenttia ja lopuissa luokissa vähintään 95 prosenttia jokaisessa. Testauskattavuus on näiltä osin pyritty saamaan niin korkeaksi, kuin se on järkevää ohjelman luonne huomioon ottaen. Parissa kohdassa 100 prosentin kattavuuden saavuttaminen olisi tehnyt koodin epätarkoituksenmukaiseksi tai vaikeaselkoiseksi ja siten muun muassa heikentänyt laajennettavuutta.

Cobertura- ja Pit-raportit ovat mukana testidokumenteissa.

### Käyttöliittymä
Ohjelmassa on tekstikäyttöliittymä, joka on erotettu sovelluslogiikasta. Käyttöliittymässä ei ole varsinaista interaktiivisuutta käyttäjän kanssa ohjelman suorituksen aikana. Jos käyttäjän antamat komentoriviparametrit ovat validit, käyttöliittymä tulostaa ruudulle etsityn merkkijonon sisältämät rivit, ja ohjelman suoritus päättyy. Jos parametreissa on virhe, ohjelma tulostaa oikean virheilmoituksen, ja ohjelman suoritus päättyy. Käyttöliittymän tulosteet riippuvat paluuarvoista, joita käyttöliittymä saa kutsumiltaan sovelluslogiikan metodeilta. Suurin osa käyttöliittymän koodista liittyy virheilmoitusten tulostamiseen.

Käyttöliittymälle ei kirjoitettu JUnit-testejä, vaan sen toiminta testattiin manuaalisesti ajamalla ohjelma erilaisilla parametrien arvoilla.

Ohjelmaa kokeiltiin kaikilla virheellisillä parametrikombinaatioilla (kumpikin parametri puuttuu, toinen parametri puuttuu, tiedoston avauksessa tapahtuu virhe, tai lauseke on väärin). Havaittiin, että ohjelma toimi tällöin, kuten pitääkin, ja että käyttäjän saamat virheilmoitukset ovat informatiiviset.

Ohjelmaa kokeiltiin manuaalisesti myös erilaisilla oikeilla parametreilla. Tällöinkin käyttöliittymä toimi suunnitellusti.

### Muuta yksikkötestauksesta
Main-luokalle ei tehty yksikkötestejä, sillä luokka vain käynnistää ohjelman.

Suorituskykytesti-luokalle ei tehty yksikkötestejä. Luokka ei ole osa ohjelman varsinaista toimintaa, vaan se toteuttaa jäljempänä kerrotun automaattisen suorituskykytestauksen.

## Integraatiotestaus
Ohjelma on rakenteeltaan yksinkertainen kerrosmainen, jossa ylempi kerros tuntee alemman luokkia, mutta ei toisin päin. Saman kerroksen luokilla ei ole riippuvuuksia.

Erillistä integraatiotestausta ei ole tehty, sillä käyttöliittymän testaus on samalla käytännössä testannut sitä, että ohjelma toimii kokonaisuutena.

Lisäksi suorituskykytestien yhteydessä osa testisyötteistä ajettiin myös normaaliin tapaan komentoriviltä. Ruudulle tulostuneilta riveiltä tarkistetiin, että jokaiselta riviltä löytyy haluttu merkkijono.

Näissä testeissä ei löytynyt vikoja ohjelmalogiikassa.

## Suorituskykytestaus
### Automaattiset testit
Ohjelmaan on kirjoitettu Suorituskykytesti-luokka, joka automatisoi suorituskykytestauksen. Automaattinen testaus simuloi ohjelman normaalia toimintaa. Ohjelma toimii lähes samalla tavalla kuin, jos käyttäjä olisi antanut lausekkeen ja tiedostonimen komentoriviltä.

Poikkeuksena on kuitenkin se, ettei testiympäristö tulosta rivejä ruudulle (eikä muuallekaan). Tähän on pari syytä. Suorituskykytestien tarkoituksena on testata ohjelman toteutusalgoritmien tehokkuutta, ei ruudulle tulostamista. Lisäksi tulostus on suhteellisen hidasta ja se saattaisi heikentää eri testikertojen tulosten vertailukelpoisuutta.

#### Automaattisten testien ajaminen
Käyttöohjeessa kerrotaan ohjelman normaalista käytöstä. Automaattiset testit voi ajaa helpoiten kopioimalla jar-paketin kanssa samaan kansioon viisi muuta tiedostoa: tiedostot.txt, lausekkeet.txt, 7veljesta.txt, 7veljestax5.txt ja 7veljestax10.txt. Nämä tiedostot löytyvät github-repositorion kansiosta dokumentointi/testidokumentit/suorituskykytestitiedostot.

Suorituskykytestit ajetaan, kun ohjelman käynnistää antamalla parametriksi pelkästään sanan testi, eli komennolla
```
java -cp Regex-1.0-SNAPSHOT.jar regex.regex.Main testi
```
Tällöin ohjelma käynnistää normaalin käyttöliittymän sijasta Suorituskykytesti-luokan ja ajaa testit.

Ajettavat testit määritellään tiedostoissa lausekkeet.txt ja tiedostot.txt. Jokainen tiedostot.txt-tiedostossa nimetty tiedosto testataan jokaisella lausekkeet.txt-tiedostossa mainitulla säännöllisellä lausekkeella. Jos esim. lausekkeet.txt sisältää kolme lauseketta ja tiedostot.txt kaksi tiedostonimeä, ajetaan yhteensä kuusi erilaista testiä.

Yksittäinen testi ajetaan kymmenen kertaa, ja testin suoritusaika on näistä keskiarvo. Esimerkiksi edellisen kappaleen tapauksessa jokainen kuudesta testistä ajetaan kymmenen kertaa.

Testitulokset kirjoitetaan testiraportit.txt-tiedostoon. Se tulee samaan kansioon, jossa muutkin tiedostot ovat. Jos tiedosto on olemassa ennen testausta, se ylikirjoitetaan.

### Omien testien tekeminen
Käyttäjä voi helposti lisätä omia suorituskykytestejä. Lausekkeita voi lisätä lausekkeet.txt-tiedostoon, yhdelle riville yksi lauseke. Tiedostonimiä voi lisätä tiedostot.txt-tiedostoon, yhdelle riville yksi nimi.

Testattavien tiedostojen on oltava määrittelytiedostojen kanssa samassa kansiossa tai sellaisessa muussa kansiossa, josta käyttäjän suoritusympäristö löytää ne.

### Suorituskykytestien tulokset
#### Testattavat tiedostot
Suorituskykytestauksessa käytettiin Project Gutenbergin sivuilta haettua Aleksis Kiven Seitsemän veljestä-kirjaa. Kirja oli tavallisena tekstitiedostona.

Tiedostolle ajetut testit ajettiin myös 5 ja 10 kertaa pidemmille tiedostoille, joihin oli kopioitu kirja peräkkäin 5 ja 10 kertaa. Tiedostojen koot olivat 0,68, 3,4 ja 6,8 megatavua. Näin voitiin vertailla tiedoston koon vaikutusta suoritusaikoihin. Tiedostot ovat mukana testidokumenteissa (7veljesta.txt, 7veljestax5.txt ja 7veljestax10.txt).

#### Testihavaintoja
Havaittiin, että ohjelman suoritusaika pysyy samalla tasolla kerrasta toiseen, kun samaa testiä ajetaan useasti (kokeiltiin viisi kertaa lausekkeella 'Jukola' ja viisi kertaa lausekkeella 's(u|a*)n'). Kahden pidemmän tiedoston kohdalla suoritusaika pysyi lähes täsmälleen samana kerrasta toiseen. Ohjelma näyttää siis toimivan joka kerta samalla tavalla, kun syöte on sama. Muu tulos olisikin varsin yllättävä.

Testejä toistettaessa havaittiin mielenkiintoinen seikka, että ensimmäisen testin suorituskerta vie 30-50 prosenttia enemmän aikaa kuin saman testin ajo muussa kohdassa samassa testiajossa. Toisen testin suoritusajassa näkyy sama ilmiö mutta heikompana ja kolmannessakin testissä ilmiö näkyy vielä hieman. Tulos ei riipu lausekkeesta tai tiedoston pituudesta. Ilmiölle ei keksitty toteutettuun ohjelmaan tai testien ajamiseen liittyvää syytä. Ennen jokaista testiä automaatti alustetaan alusta saakka samalla tavalla kuin ohjelman jokaisen normaalin suorituskerran yhteydessä. Ensimmäisen testin suoritusaikaan ei myöskään oteta mukaan itse ohjelman käynnistysaikaa. Ehkä ilmiö liittyy jotenkin Javan virtuaalikoneen sisäiseen toimintaan.

Kun yllä mainittu ilmiö ei näytä liittyvän itse ohjelman toimintaan, testituloksia on tulkittu ikään kuin ilmiötä ei olisi.

Testeissä havaittiin, että suoritusaika on lineaarinen suhteessa tiedoston kokoon. Jos testin suoritusaika oli lyhimmällä tiedostolla esimerkiksi 30 millisekuntia, niin suoritusaika samalla lausekkeella oli 5 kertaa pitemmällä tiedostolla viisinkertainen ja 10 kertaa pitemmällä tiedostolla kymmenkertainen. Tämä tulos päti riippumatta siitä, mikä oli testattava lauseke.

Havaittiin, että ohjelma on käyttökelpoinen megatavujenkin suuruisilla tiedostoilla. Pisimmällä eli 6,8 megatavun pituisella tiedostolla testien suoritusajat olivat 200 - 700 millisekuntia sen mukaan, mitä lauseketta testattiin.

Seuraavassa on erään testiajon tulokset eri kokoisilla 7veljesta-tiedostoilla eräillä etsityillä lausekkeilla. Ajat ovat millisekunteina. Samat tiedot ovat ensin taulukkona ja sitten kuvana.

Lauseke | 0,68 Mt (1x) | 3,4 Mt (5x) | 6,8 Mt (10x)
------- | ------------ | ----------- | -----------
Jukola | 22 | 116 | 227
s(u&#124;a*)n | 25 | 124 | 281
(A&#124;a)(A&#124;a)([m-p]&#124;P)(O&#124;u) | 27 | 138 | 271
t[a-z]lon va | 42 | 207 | 441
t[a-z][a-z][a-z][a-z] va | 72 | 360 | 730
((M&#124;m)aanantai)&#124;((T&#124;t)iistai)&#124;((K&#124;k)eskiviikko)&#124;((T&#124;t)orstai)&#124; | 68 | 345 | 702
((P&#124;p)erjantai)&#124;((L&#124;l)auantai)&#124;((S&#124;s)unnuntai)

![Suoritusaikakuva](https://github.com/mikkomaa/Regex/blob/master/dokumentointi/testidokumentit/suoritusajat.png)

Kuvasta huomataan, että ohjelman suoritusaika kasvaa lineaarisesti tiedostokoon kasvaessa. Kuvasta näkee myös, miten säännöllisen lausekkeen monimutkaisuus nostaa suoritusaikaa. Tämä johtuu siitä, että lausekkeen monimutkaisuus lisää myös suoritettavan automaatin monimutkaisuutta.

Lausekkeen monimutkaisuuden vaikutus ohjelman käytännön suoritusaikaan on tekijä, jota ei pysty mittaamaan yleispätevästi. On mahdollista kuvata tarkasti, montako solmua tietty lauseke aiheuttaa automaattiin ja millaisia yhteyksiä on solmujen välillä. Mutta riippuu täysin syötteenä olevasta tiedostosta, miten pitkälle automaattia ajetaan etsinnän aikana. Jos lauseke alkaa vaikkapa z-kirjaimella eikä tiedostossa ole yhtään z-kirjainta, monimutkainenkaan automaatti ei koskaan etene kuin yhden merkkitilan verran. Toisaalta huonoin mahdollinen suoritusaika saadaan silloin, kun automaatti on monimutkainen ja syötetiedosto sellainen, että mahdollisimman moni automaatin tila on jatkuvasti aktiivinen. Toteutusdokumentista löytyvän analyysin perusteella teoreettinen aikavaativuus pysyy tällöinkin yleensä lineaarisena suhteessa syötetiedoston pituuteen.

## Lähteet
Lähteet ovat samat kuin määrittelydokumentissa käytetyt.

