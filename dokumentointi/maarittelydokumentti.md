# Säännöllisten lausekkeiden tulkki
## Ratkaistava ongelma
Säännölliset lausekkeet ovat tehokas ja merkintätavaltaan tiivis tapa kuvata merkkijonoja. Lausekkeiden käytännön sovelluksia ovat muun muassa tekstinkäsittelyohjelmien etsi-toiminnot.

Tarkoituksena on laatia säännöllisten lausekkeiden tulkki. Käyttäjä voi etsiä antamansa lausekkeen avulla tiedostosta rivejä, joilla esiintyy tietty merkkijono.
## Ohjelman saamat syötteet ja niiden käyttö
Ohjelma toteutetaan Javalla, ja sitä voi käyttää komentoriviltä. Käyttäjä ajaa ohjelman antamalla parametreina tiedoston nimen ja säännöllisen lausekkeen, esimerkiksi komennolla "regex tiedosto.txt abc|de*". tiedosto.txt:n formaattia ei rajoiteta, mutta ohjelman käyttötarkoituksen vuoksi tiedosto on yleensä tekstimuotoinen. Parametrina annettavan lausekkeen syntaksi kuvataan jäljempänä.

Parametrit toimivat ohjelman syötteinä. Ohjelma etsii annetusta tiedostosta rivit, joilla säännöllisen lausekkeen kuvaama merkkijono esiintyy, ja tulostaa kyseiset rivit.

Ohjelman toiminnan vaiheet ovat seuraavat:

1. Ohjelma avaa parametrina saadun tiedoston. Jos avaus ei onnistu, ohjelma tulostaa virheilmoituksen, ja ohjelman suoritus päättyy.
2. Ohjelma muuntaa parametrina saadun säännöllisen lausekkeen char[]-tauluksi. Lauseke voi sisältää myös välilyöntejä, minkä vuoksi parametri voi ohjelman käynnistyessä olla teknisesti useassa osassa.
3. Ohjelma tarkistaa, että säännöllinen lauseke on syntaksin mukainen. Jos havaitaan virhe, ohjelma tulostaa virheilmoituksen, ja suoritus päättyy.
4. Parametrina saatu lauseke on infix-notaatiossa. Lauseke muunnetaan postfix-notaatioon.
5. Postfix-notaatiosta luodaan epädeterministinen äärellinen automaatti (nondeterministic finite automaton, nfa). Automaatti on linkitetty rakenne, jonka osia ovat yksittäiset tilat.
6. Ohjelma lukee syötteenä saadusta tiedostosta rivin kerrallaan muistiin. Rivi ajetaan merkki kerrallaan automaatin läpi. Jos automaatti hyväksyy rivin, rivin ajaminen päättyy ja rivi tulostetaan. Muutoin rivi ajetaan loppuun, ja siirrytään sen jälkeen käsittelemään seuraava rivi.
7. Kun tiedoston kaikki rivit on käsitelty, ohjelman suoritus päättyy.

## Säännöllisen lausekkeen syntaksi
Säännöllisten lausekkeiden kolme perusoperaatiota ovat tähti (*), yhdiste (|) ja katenaatio. Lisäksi voidaan määritellä muita operaatioita, ja käytännön sovelluksissa niin usein tehdäänkin. Lisäoperaatiot helpottavat säännöllisten lausekkeiden käyttöä, vaikka ne eivät lisää lausekkeiden teoreettista ilmaisuvoimaa.

Parametrina annettava säännöllinen lauseke voi sisältää seuraavia operaatioita:
- katenaatio: Esim. lauseke abc löytää rivit, joilla esiintyy merkkijono abc. Katenaatiolle ei siis tarvitse merkitä operaattoria näkyviin.
- tähti * : Edeltävä merkki esiintyy merkkijonossa nolla kertaa tai useammin. Esim. ab* löytää rivit, joilla esiintyy a, ab, abb, abbb jne.
- yhdiste | : Lauseke a|b löytää merkkijonot, joissa esiintyy merkki a tai b.
- kysymysmerkki ? : Kysymysmerkin paikalla voi olla mikä tahansa merkki. Esim. lauseke t??o löytää mm. rivit, joilla esiintyvät merkkijonot talo, tulo tai talous.
- Tämä lista saattaa täydentyä riippuen siitä, miten paljon ehdin toteuttaa.

Operaatioiden etusijajärjestys on sitovimmasta alkaen seuraava: *, katenaatio ja |. Kysymysmerkki kuvaa normaalia merkkiä eikä siten ole etusijajärjestyksessä mukana.

Suluilla () voi muuttaa normaalia etusijajärjestystä. Esim. lauseke ab|cd löytää rivit, joilla esiintyy merkkijonot ab tai cd, mutta lauseke a(b|c)d löytää rivit, joilla esiintyy merkkijonot abd tai acd.

Jos säännöllisessä lausekkeessa haluaa käyttää tavallisena merkkinä merkkejä *, |, ?, (, ) tai \, merkin eteen pitää kirjoittaa kenoviiva. Esim. merkkijonon Häh? löytää lausekkeella Häh\?.

## Algoritmit ja tietorakenteet
Algoritmien kannalta ohjelmassa on kolme toisistaan erottuvaa vaihetta:

1. Parametrina annetun säännöllisen lausekkeen esikäsittely: Lauseke muunnetaan yhdeksi char[]-tauluksi, ja lausekkeen oikeellisuus tarkistetaan. Jos syntaksi on oikea, lausekkeeseen lisätään .-merkit kuvaamaan katenaatioita. Lopuksi infix-muodossa oleva lauseke muunnetaan postfix-muotoon. Postfix-muunnokseen käytetään shunting-yard-algoritmia (tai jotakin vastaavaa). Muut vaiheen algoritmit kehitetään todennäköisesti itse.
2. Äärellisen automaatin luominen: postfix-notaatiossa olevasta lausekkeesta luodaan automaatti käyttäen Thompsonin algoritmia.
3. Parametrina saatua tiedostoa luetaan rivi kerrallaan, ja jokainen rivi ajetaan automaatin läpi merkki kerrallaan. Ajoalgoritmi on tarkoitus kirjoittaa itse.

Edellä mainituissa vaiheissa käytetään vastaavasti seuraavia tietorakenteita:

1. Säännöllistä lauseketta käsitellään char[]-tauluna. Shunting-yard-algoritmissa tarvitaan mahdollisesti pinoa.
2. Automaatin rakenneosia ovat tilat, joita varten määritellään tietorakenne. Automaatti on toisiinsa linkitetyistä tiloista muodostuva kokonaisuus. Thompsonin algoritmissa käytetään apuna pinoa.
3. Automaatin ajamisessa käytetään apuna pinoa. Syötetiedoston rivit luetaan muistiin käyttäen Javan valmiita välineitä.

## Tavoitteena olevat aika- ja tilavaativuudet
### Koko ohjelman vaativuudet
Ohjelman nopeuden kannalta on käytännössä ratkaisevaa se, mikä on automaatin ajamisen aikavaativuus. Kun syötetiedoston kokoluokka on vähintään tuhansia merkkejä, automaatin ajaminen dominoi ohjelman aikavaativuutta. Säännöllinen lauseke on tyypillisesti korkeintaan kymmeniä merkkejä. Vaikka lausekkeen käsittelyssä on useita vaiheita, siihen kuluva aika on valituilla algoritmeillä merkityksetön ohjelman toiminnan kannalta.

Koska syötetiedoston kokoluokka voi olla esimerkiksi miljoonia merkkejä, on olennaista, että automaattia voidaan ajaa lineaarisessa ajassa suhteessa syötetiedoston kokoon. Tämä on myös tavoitteena aikavaativuudessa. Siis koko ohjelman aikavaativuus olisi käytännössä O(n) suhteessa syötetiedoston pituuteen.

Koko ohjelman tilavaativuus puolestaan määräytyy syötetiedoston pisimmän rivin (koska tiedostoa luetaan muistiin rivi kerrallaan) ja säännöllisen lausekkeen pituuden perusteella. Tilavaativuus on lineaarinen kumpaankin nähden. Hieman karkeasti sanoen koko ohjelman tilavaativuus on siten O(n) suhteessa syötteen pituuteen.
### Yksittäisten algoritmien vaativuudet
Seuraavassa kuvataan tarkemmin yksittäisten algoritmien vaativuudet.

Lausekkeen char[]-tauluksi muuntavan algoritmin aika- ja tilavaatimus on O(n) säännöllisen lausekkeen pituuteen nähden.

Syntaksin oikeellisuuden tarkistava algoritmi käy lausekkeen läpi korkeintaan kerran ja käyttää muutamaa apumuuttujaa. Aikavaativuus on O(n) lausekkeen pituuteen nähden ja tilavaativuus O(1).

Katenaatioita kuvaavat .-merkit lisäävä algoritmi käy lausekkeen läpi kertaalleen. Tuloksena oleva lauseke on korkeintaan noin kolme kertaa alkuperäisen lausekkeen pituinen. Aika- ja tilavaatimus on O(n) lausekkeen pituuteen nähden.

Shunting-yard-algoritmi (tai vastaava) käy lausekkeen kertaalleen läpi ja käyttää mahdollisesti pinoa. Aikavaatimus on O(n) lausekkeen pituuteen nähden ja tilavaatimus korkeintaan O(n).

Thompsonin algoritmi käy lausekkeen läpi kertaalleen ja luo vastaavan automaatin. Automaatin tilojen määrä on lineaarinen suhteessa lausekkeen pituuteen. Aika- ja tilavaatimus on O(n) lausekkeen pituuteen nähden.

Automaattia ajava algoritmi käy yksittäisiä rivejä läpi merkki kerrallaan, jokaisen rivin korkeintaan kerran. Riviä ei käydä läpi kokonaan, jos ennen rivin loppua löytyy etsittävä merkkijono. Automaatin koko on lineaarinen suhteessa säännöllisen lausekkeen pituuteen, ja automaatti käyttää ajettaessa apuna pinoa, joka on kooltaan korkeintaan lineaarinen suhteessa automaatin kokoon. Joten algoritmin aikavaativuus on O(n) suhteessa syötetiedoston kokoon ja tilavaativuus O(n) suhteessa säännöllisen lausekkeen pituuteen.
## Lähteet
Lisätietoa äärellisistä automaateista ja säännöllisistä lausekkeista on laskennan mallit-kurssin luentokalvojen luvussa 1 <https://www.cs.helsinki.fi/u/jkivinen/opetus/lama/s11/luennot.pdf>

Wikipediassa on artikkeli shunting-yard-algoritmista <https://en.wikipedia.org/wiki/Shunting-yard_algorithm>
ja automaatin luomisesta Thompsonin algoritmilla [https://en.wikipedia.org/wiki/Thompson's_construction]

