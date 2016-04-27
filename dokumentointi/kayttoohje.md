# Ohjelman käyttöohje
## Mitä ohjelma tekee
Ohjelma etsii ja tulostaa antamasi tiedoston ne rivit, joilla esiintyy antamasi hakusana. Hakusanana on säännöllinen lauseke.

## Ohjelman ajaminen
Ajettava ohjelma on jar-pakettina. Ohjelman ajamiseksi koneellasi on oltava Java asennettuna.

Kopioi tiedosto Regex-1.0-SNAPSHOT.jar github-repositorion release-kansiosta, ja tallenna se koneellesi.

Ohjelma ajetaan komentoriviltä. Aja ohjelma komennolla
```
java -cp Regex-1.0-SNAPSHOT.jar regex.regex.Main tiedostonimi lauseke
```
tiedostonimi ja lauseke ovat haluamiasi parametreja. tiedostonimi on tiedosto, josta merkkijonoja etsitään. lauseke on säännöllinen lauseke, jonka perusteella merkkijonoja etsitään.

Tiedoston on oltava samassa hakemistossa jar-paketin kanssa. Tai voit antaa tiedostonimen yhteydessä tiedostopolun, esim. Linuxissa tyyliin hakemisto/hakemisto/tiedostonimi.

Jos sinulla esimerkiksi on jar-paketin kanssa samassa hakemistossa tiedosto 7veljesta.txt ja haluat etsiä siitä rivit, joilla esiintyy merkkijono Jukola, aja ohjelma komennolla 
```
java -cp Regex-1.0-SNAPSHOT.jar regex.regex.Main 7veljesta.txt Jukola
```

### Komentotulkin vaikutus ohjelman ajamiseen

Jos lausekkeessa tai tiedostopolussa/tiedostonimessä on käyttämäsi komentotulkin toimintaa ohjaavia merkkejä (esimerkiksi |, / tai -), tulkki ei välttämättä välitä parametreja ohjelmalle siinä muodossa, kuin tarkoitit. Komentotulkista riippuu, miten parametrit kannattaa tällöin kirjoittaa.

Esimerkiksi Unixin/Linuxin bash-tulkissa parametrit kannattaa kirjoittaa heittomerkkien sisään. Tosin tällöin et voi sisällyttää itse lausekkeeseen heittomerkkejä. Edellinen ajoesimerkki olisi heittomerkkien kanssa
```
java -cp Regex-1.0-SNAPSHOT.jar regex.regex.Main '7veljesta.txt' 'Jukola'
```
Lisätietoa komentotulkin vaikutuksesta saat käyttämäsi tulkin ohjeesta (esim. komennolla man bash).

### Säännöllisen lausekkeen syntaksi
Hakusanana oleva säännöllinen lauseke voi sisältää seuraavia operaatioita:
- katenaatio eli normaali merkkien kirjoittaminen peräkkäin: esim. lauseke 'iso talo' löytää rivit, joilla esiintyy merkkijono 'iso talo'.
- tähti * : Edeltävä merkki esiintyy merkkijonossa nolla kertaa tai useammin. Esim. 'talo*n' löytää mm. rivit, joilla esiintyy 'taln', 'talon' tai 'taloon'.
- yhdiste | : Lauseke 'a|b' löytää merkkijonot, joissa esiintyy merkki a tai b.
- kysymysmerkki ? : Kysymysmerkin paikalla voi olla mikä tahansa yksi merkki. Esim. lauseke 't??o' löytää mm. rivit, joilla esiintyy 'talo', 'tulo' tai 'talous'.
- merkkiväli [ ] : Merkkiväli löytää rivit, joilla esiintyy mikä tahansa väliin kuuluva merkki. Esim. [3-5] löytää rivit, joilla esiintyy numero 3, 4 tai 5, ja [m-p] rivit, joilla esiintyy kirjain m, n, o tai p. Sallittuja merkkivälejä ovat pienet kirjaimet [a-z], isot kirjaimet [A-Z] ja numerot [0-9]. Merkkivälejä ei voi sekoittaa keskenään. Merkkivälissä jälkimmäisen merkin on oltava "suurempi" kuin ensimmäinen.

Operaatioiden etusijajärjestys on sitovimmasta alkaen seuraava: *, katenaatio ja |. Kysymysmerkki kuvaa mitä tahansa normaalia merkkiä eikä siten ole etusijajärjestyksessä mukana. Merkkiväli tulkitaan samoin kuin vastaava sulkulauseke eli esim. [0-2] tarkoittaa samaa kuin (0|1|2).

Suluilla ( ) voi muuttaa normaalia etusijajärjestystä. Esim. lauseke 'ab|cd' löytää rivit, joilla esiintyy merkkijonot ab tai cd, mutta lauseke 'a(b|c)d' löytää rivit, joilla esiintyy merkkijonot abd tai acd.

Jos säännöllisessä lausekkeessa haluaa käyttää tavallisena merkkinä merkkejä *, |, ?, [, ], (, ) tai \, merkin eteen pitää kirjoittaa kenoviiva. Esim. merkkijonon 'Häh?' löytää lausekkeella 'Häh\?'.

## Suorituskykytestien ajaminen
Ohjelmassa on ominaisuus, jolla voit helposti testata ohjelman nopeutta eri lausekkeilla ja tiedostoilla. Voit ajaa valmiiksi määritellyt testit, kun ensin kopioit jar-paketin kanssa samaan kansioon viisi muuta tiedostoa: tiedostot.txt, lausekkeet.txt, 7veljesta.txt, 7veljestax5.txt ja 7veljestax10.txt. Nämä tiedostot löytyvät github-repositorion kansiosta dokumentointi/testidokumentit/suorituskykytestitiedostot.

Nyt voit ajaa testit antamalla ohjelmalle parametriksi pelkästään sanan testi, eli komennolla
```
java -cp Regex-1.0-SNAPSHOT.jar regex.regex.Main testi
```
Tällöin ohjelma ajaa testit ja kirjoittaa testiraportin samaan kansioon tiedostoon testiraportti.txt. Mahdollinen aiempi raportti ylikirjoitetaan.

Voit määritellä helposti omiakin testejä muokkaamalla tiedostoja tiedostot.txt ja lausekkeet.txt. Lisätietoa on testausdokumentin kohdassa Suorituskykytestaus. Testausdokumentti on github-repositorion kansiossa dokumentointi/testidokumentit.

