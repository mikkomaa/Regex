# Toteutusdokumentti
## Ohjelman yleisrakenne
Ohjelma on Javalla toteutettu säännöllisten lausekkeiden tulkki. Käyttäjä voi antaa komentoriviltä säännöllisen lausekkeen ja tiedoston, josta lausekkeen kuvaamaa merkkijonoa etsitään. Ohjelma tulostaa ruudulle tiedoston rivit, joista merkkijono löytyy.

Ohjelman yleinen rakenne on kolmiosainen. Sovelluslogiikkaluokat sisältävät ohjelman varsinaisen toimintalogiikan. Toinen osa muodostuu itse toteutetuista tietorakenteista, joita käytetään apuna ohjelmalogiikan toteutuksessa. Kolmas osa on ohjelman käyttöliittymä.

Sovelluslogiikkaluokat on laadittu toiminnallisesti itsenäisiksi, toisistaan erillisiksi osiksi. Luokat eivät kutsu toisiaan, vaan käytännössä käyttöliittymä suorittaa ne tietyssä järjestyksessä sitä mukaa, kun ohjelman suoritus etenee.

Omia tietorakenteita ohjelmassa ovat pino ja jono. Kummatkin kasvavat tarvittaessa dynaamisesti. Ne toteuttavat rajapinnan Sailio. Lisäksi on määritelty luokka Tila. Lausekkeita käsittelevä automaatti muodostetaan toisiinsa linkitetyistä Tila-olioista.

Ohjelmassa on tekstipohjainen käyttöliittymä.

### Luokkien väliset yhteydet
Ohjelman arkkitehtuuri on kerrosmainen. Kolmiosainen rakenne voidaan nähdä kolmena kerroksena, jossa ylempi kerros tuntee alapuolellaan olevia luokkia, mutta ei päinvastoin. Luokkien väliset yhteydet näkyvät toteutusvaiheen luokkakaaviossa. Se on dokumenttikansiossa.

### Sovelluslogiikkaluokkien toiminnan yleispiirteet
Kun käyttäjä käynnistää ohjelman, sovelluslogiikkaluokat suoritetaan seuraavassa järjestyksessä:

1. Parametrikasittelija. Luokka avaa tiedoston, josta säännöllistä lauseketta etsitään, ja muuntaa lausekkeen yhdeksi jonoksi. Jos käyttäjän antamissa komentoriviparametreissä on virhe (muu kuin lausekkeen syntaksivirhe), ohjelman suoritus päättyy.
2. Notaationtarkistaja. Luokka tarkistaa, onko lausekkeen syntaksi oikein. Jos ei ole, ohjelman suoritus päättyy.
3. Notaationmuuntaja. Luokka muuntaa lausekkeen postfix-muotoon automaatin luomista varten.
4. Automaatinluoja. Luokka luo lausekkeesta äärellisen automaatin.
5. Automaatti. Luokan avulla tarkistetaan, löytyykö tiedoston riveiltä lausekkeen kuvaama merkkijono.

### Säännöllisen lausekkeen syntaksi
Hakusanana oleva säännöllinen lauseke voi sisältää seuraavia operaatioita:
- katenaatio eli normaali merkkien kirjoittaminen peräkkäin
- tähti * : edeltävä merkki esiintyy merkkijonossa nolla kertaa tai useammin
- yhdiste |
- kysymysmerkki ? : kysymysmerkin paikalla voi olla mikä tahansa yksi merkki
- merkkiväli [] : Merkkiväli löytää rivit, joilla esiintyy mikä tahansa väliin kuuluva merkki. Sallittuja merkkivälejä ovat pienet kirjaimet [a-z], isot kirjaimet [A-Z] ja numerot [0-9]. Merkkivälejä ei voi sekoittaa keskenään. Merkkivälissä jälkimmäisen merkin on oltava "suurempi" kuin ensimmäinen.

Operaatioiden etusijajärjestys on sitovimmasta alkaen seuraava: *, katenaatio ja |. Kysymysmerkki kuvaa mitä tahansa normaalia merkkiä eikä siten ole etusijajärjestyksessä mukana. Merkkiväli tulkitaan samoin kuin vastaava sulkulauseke eli esim. [0-2] tarkoittaa samaa kuin (0|1|2).

Suluilla () voi muuttaa normaalia etusijajärjestystä.

Jos säännöllisessä lausekkeessa haluaa käyttää tavallisena merkkinä merkkejä *, |, ?, [, ], (, ) tai \, merkin eteen pitää kirjoittaa kenoviiva.

## Saavutetut aika- ja tilavaativuudet
Jäljempänä olevat pseudokoodit kuvaavat metodeita sellaisella tarkkuudella, että metodien toimintaidea näkyy selkeästi. Luokkien metodeista käydään läpi ne, jotka ovat aika- tai tilavaativuudeltaan kiinnostavia ohjelman toiminnan kannalta.

Jos muuta ei ilmoiteta, aika- ja tilavaativuudella tarkoitetaan vaativuutta suhteessa säännöllisen lausekkeen pituuteen.

### Sovelluslogiikkaluokat
#### Parametrikasittelija
TaulukoiLauseke-metodi kopioi komentoriviparametrina saadun säännöllisen lausekkeen yhdeksi jonoksi. Koska lauseke voi siinä olevien välilyöntien vuoksi olla teknisesti useassa osassa, metodissa on kaksi sisäkkäistä for-silmukkaa. Tästä huolimatta metodi käy lausekkeen läpi vain kerran. Siis aika- ja tilavaativuus on O(n).

#### Notaationtarkistaja
##### Konstruktori
Luokan konstruktori luo kopion argumenttina saamastaan lausekkeesta Jono-luokan luoKopio-metodilla. Konstruktorin aika- ja tilavaativuus on O(n).

##### onkoLausekeOikein-metodi
onkoLausekeOikein-metodi käy läpi konstruktorissa luodun lausekekopion tarkistaen, onko lauseke (teknisesti jono) syntaksin mukainen. Metodi kertoo paluuarvollaan, onko lauseke oikein tai mikä oli 1. virheellinen merkki.
```
char onkoLausekeOikein()
    while (lauseke ei ole tyhjä)
        char c = lauseke.poista()
        switch (c)
            case '\\'
                tee jotain
            case '('
                tee jotain
            ...
```
Metodin switch-lauseessa on useita case-kohtia, joista jokaisessa tehdään joitakin vakioaikaisia ja -tilaisia operaatioita. Lausekkeen yksittäinen merkki käsitellään jossakin case-kohdassa. Jos lauseke on syntaksin mukainen, koko lauseke käydään läpi. Jos lausekkeessa on virhe, metodin suoritus päättyy virheen kohdalla. Metodi käyttää muutamaa apumuuttujaa.

Siis metodin aikavaativuus on O(n) ja tilavaativuus O(1).

#### Notaationmuuntaja
Luokka muuntaa syntaksiltaan oikean, infix-muodossa olevan säännöllisen lausekkeen postfix-muotoon. Luokan käyttäjän näkökulmasta muunnos tapahtuu kutsumalla muunna-metodia. Luokan sisäisesti muuntaminen on selkeyden vuoksi jaettu kolmeen osaan eli poistaHakasulut-, lisaaPisteet- ja muutaPostfixiin-metodeiksi.

##### Konstruktori
Luokan konstruktori luo kopion argumenttina saamastaan lausekkeesta Jono-luokan luoKopio-metodilla. Konstruktorin aika- ja tilavaativuus on O(n).

##### poistaHakasulut-metodi
Metodi käy lausekkeen merkki merkiltä läpi tasan kerran. Jos lausekkeessa on hakasulkumerkintöjä, ne muutetaan toiminnaltaan vastaaviksi lausekkeen osiksi ilman hakasulkuja. Hakasulut tulkitaan siten, että esimerkiksi merkintä [c-f] tarkoittaa samaa kuin (c|d|e|f). Kun lauseke on käyty läpi, tuloksena on uusi, semantiikaltaan entistä vastaava lauseke ilman hakasulkuja.
```
Jono poistaHakasulut()
    while (lauseke ei ole tyhjä)
        char c = lauseke.poista()
        switch (c)
            case '['
                muunna hakasulkulauseke normaalisulkuiseksi
            case 'muut merkit'
                lisää c uuteen lausekkeeseen sellaisenaan
```
Hakasuluissa voidaan käyttää kolmenlaisia merkkivälejä: [a-z], [A-Z] ja [0-9]. Koska aakkosia a-z on 26 kappaletta, yksittäisen viisi merkkiä pitkän hakasulkumerkinnän tilalle voi tulla enintään 26+25+2=53 merkkiä pitkä sulkulauseke. Tästä seuraa, että yksittäiset hakasulkujen poistot voidaan tehdä vakioajassa ja -tilassa.

Tuloksena oleva muunnettu säännöllinen lauseke on pituudeltaan alkuperäinen lauseke kerrottuna jollain vähintään yhden suuruisella kertoimella k. Toisaalta kerroin k on aina alle kymmenen.

Siis metodin aika- ja tilavaativuus on O(n).

##### lisaaPisteet-metodi
Metodi lisää säännölliseen lausekkeeseen pisteet katenaatioiden merkiksi. Esimerkiksi käyttäjän antama lauseke abc muutetaan muotoon a.b.c. Paluuarvo on jono, jossa on säännöllinen lauseke lisättynä pisteillä.
```
Jono lisaaPisteet()
    while (lauseke ei ole tyhjä)
        char c = lauseke.poista()
        if (c:n jälkeen tarvitaan katenaatio)
            lisää uuteen lausekkeeseen c
            lisää uuteen lausekkeeseen .
        else
            lisää uuteen lausekkeeseen c
```
Oikea metodi on teknisten yksityiskohtien vuoksi mutkikkaampi, mutta toimintaidea on pseudokoodin mukainen.

Metodi käy lausekkeen läpi kertaalleen merkki kerrallaan ja luo samalla uuden lausekkeen. Uusi lauseke on pisteiden lisäämisen vuoksi pituudeltaan alkuperäinen lauseke kerrottuna jollain vähintään yhden suuruisella kertoimella k. Kerroin k on (tässä sivuutettujen teknisten seikkojen vuoksi) enintään noin kolme.

Metodin aika- ja tilavaativuus on O(n).

##### muutaPostfixiin-metodi
Metodi luo infix-notaatiossa olevasta lausekkeesta uuden, postfix-muodossa olevan lausekkeen ja palauttaa sen. Metodi käyttää shunting-yard-algoritmia.
```
Jono muutaPostfixiin()
    while (lauseke ei ole tyhjä)
        char c = lauseke.poista()
        switch (c)
            case '\\'
                tee jotain
            case '('
                tee jotain
            ...
```
Metodin switch-lauseessa on useita case-kohtia, joista jokaisessa suoritetaan joitakin vakioaikaisia ja -tilaisia operaatioita. Lausekkeen yksittäinen merkki käsitellään jossakin case-kohdassa. Metodi käyttää apuna pinoa, johon väliaikaisesti säilötään operaattoreita.

Koska metodi käy läpi koko lausekkeen ja luo samalla uuden, aika- ja tilavaativuus on O(n).

#### Automaatinluoja
luoAutomaatti-metodi luo postfix-muotoisesta säännöllisestä lausekkeesta epädeterministisen äärellisen automaatin. Metodi käyttää Thompsonin algoritmia.

```
Tila luoAutomaatti(lauseke)
    pino = uusi pino
    while (lauseke ei ole tyhjä)
        char c = lauseke.poista()
        switch (c)
            case 'c on tavallinen merkki'
                luo merkkiä kuvaava tila ja lisää se pinoon
            case 'c on operaattori'
                ota pinosta 1 tai 2 tilaa ja yhdistä ne oikealla tavalla
```
Metodin suorituksen päätyttyä metodissa apuna käytetty pino sisältää vain yhden tilan, joka on toisiinsa linkitetyistä tiloista muodostetun automaatin alkutila. Metodi palauttaa arvonaan automaatin alkutilan eli käytännössä valmiin automaatin.

Metodissa tarkoitettuja operaattoreita ovat tässä vaiheessa käytännössä . (katenaatio), | ja *. Thompsonin algoritmia koskevassa Wikipedian artikkelissa on Rules-kohdassa kuvat siitä, miten merkkejä kuvaavia tiloja yhdistetään toisiinsa operaattoreita käsiteltäessä [https://en.wikipedia.org/wiki/Thompson's_construction].

Yllä oleva pseudokoodi kuvaa yleisellä tasolla algoritmin toimintaidean. Aika- ja tilavaativuuden selvittämiseksi on tarpeen tarkastella koodia tarkemmin.

Tarkastellaan ensin tilavaativuutta. Yksittäisen Tila-olion tilavaativuus on O(1). Kun tavallinen merkki muunnetaan tilaksi, luodaan kaksi Tila-oliota. Katenaatiota käsiteltäessä ei luoda uusia tiloja. |- ja *-operaatioissa luodaan kaksi uutta Tila-oliota kummassakin. Joten automaatin luomisen tilavaativuus on O(n) suhteessa lausekkeen pituuteen.

Metodin aikavaativuuden voisi äkkiseltään arvioida olevan samaa luokkaa tilavaativuuden kanssa. Mutta osoittautuu, että aikavaativuus on neliöinen eli O(n^2) suhteessa lausekkeen pituuteen. Tämä johtuu siitä, että operaattoreita käsittelevät metodit käyttävät apumetodia, joka etsii siihen mennessä rakennetusta automaatin osasta lopputilan. Se etsitään algoritmilla, joka käy läpi linkitettyä listaa alkaen osan alkutilasta, kunnes löytyy osan lopputila.

Ohjelman nopeuden kannalta neliöisellä aikavaativuudella ei ole käytännön merkitystä, ellei säännöllisen lausekkeen pituus ole kokoluokaltaan tuhansia merkkejä. Siksi koodi on tässä kohdassa kirjoitettu mahdollisimman yksinkertaiseksi, eikä aikavaativuutta ole pyritty enempää minimoimaan. Aikavaativuuden pystyisi laskemaan ainakin luokkaan O(nlogn), jos kunkin automaatin osan lopputiloja pitäisi rakennuksen aikana muistissa esimerkiksi hashmapissä.

#### Automaatti
##### suorita-metodi
Luokka toteuttaa koodin, jolla valmiin automaatin läpi voi ajaa merkkijonoja eli tässä ohjelmassa tiedostosta luettuja rivejä. Jos riviltä löytyy säännöllisen lausekkeen kuvaama merkkijono, automaatti hyväksyy rivin ja paluuarvo on true. Jos merkkijonoa ei löydy, paluuarvo on false.

Luokassa on yksi public-metodi:
```
boolean suorita(merkkijono)
    nykyisetAktiivitilat = uusi pino
    nykyisetAktiivitilat.lisaa(automaatin alkutila)
    hyvaksy = false

    for (i = 0, i < merkkijonon pituus, i = i + 1)
        paivitaTila(merkkijonon i. merkki)
        if (hyvaksy == true)
            return true
    return false
```
Metodi käy merkkijonon läpi merkki kerrallaan ja kutsuu jokaisen merkin kohdalla apumetodia paivitaTila. Apuna käytetään pinoa, jossa pidetään kunakin hetkenä tilat, jotka voivat myöhemmin johtaa hyväksyvään tilaan. Metodin aikavaativuus on O(n) suhteessa merkkijonon pituuteen. Tilavaativuus on O(n) suhteessa säännöllisen lausekkeen pituuteen, sillä edellä todettiin, että automaatin tilojen määrä on lineaarinen suhteessa lausekkeen pituuteen.

##### paivitaTila-metodi
Apumetodi paivitaTila on seuraava:
```
paivitaTila(merkki)
    uudetAktiivitilat = uusi pino
    uudetAktiivitilat.lisaa(automaatin alkutila)

    while (nykyisetAktiivitilat ei ole tyhja)
        tila = nykyisetAktiivitilat.poista()
        etene(tila, merkki, true, uudetAktiivitilat)
    nykyisetAktiivitilat = uudetAktiivitilat
```
Metodi käy läpi nykyiset aktiivitilat tila kerrallaan. Metodi kutsuu kunkin tilan kohdalla apumetodia etene, joka etenee kyseisestä automaatin tilasta yhden askeleen eteenpäin. Samalla päivitetään aktiivitilojen joukko.

Metodin aika- ja tilavaativuus on O(n) suhteessa säännöllisen lausekkeen pituuteen.

##### Etene-metodi
Apumetodi etene käyttää rekursiota ja on toimintaidealtaan seuraava:
```
etene(alkutila, merkki, jatketaanko, uudetAktiivitilat)
    if (alkutila on null)
        return
    else if (alkutila on hyväksyvä tila)
        hyvaksy = true
    else if (ollaan teknisessä välitilassa)
        etene alkutilan uloslinkkiin 1
        etene alkutilan uloslinkkiin 2
    else if (merkki ei täsmää eli tästä ei voida edetä hyväksyvään tilaan)
        return
    else if (ei ole vielä edetty yhtään aitoa askelta)
        etene seuraavaan tilaan
    else // on edetty yksi "oikea" askel
        uudetAktiivitilat(lisaa nykyinen tila)
```
Metodin toiminta-ajatus on yksinkertainen. Jos parametrina saatu alkutila on hyväksyvä tila, huomataan, että automaatti hyväksyy merkkijonon. Jos ei olla hyväksyvässä tilassa mutta alkutilan merkki täsmää parametrina saatuun merkkiin, edetään yksi askel eteenpäin ja lisätään aktiivitiloihin tila, johon saavuttiin.

Teknisesti metodia monimutkaistaa se, että automaatissa on välitiloja, jotka eivät kuvaa merkkitiloja. Ongelma on ratkaistu metodissa lyhyesti käyttämällä rekursiota, kunnes päästään yksi aito askel eteenpäin. Automaatin rakenteen vuoksi rekursiolla ei käytännössä edetä kuin korkeintaan muutama askel. Voi arvioida, ettei rekursio tässä muodostu ongelmaksi ohjelman tehokkuuden kannalta.

Metodin aika- ja tilavaativuus on O(1).

##### Koko automaatin aika- ja tilavaativuudet
Tarkastellaan kokonaisuutena luokan kolmen metodin aikavaativuutta. Olennaista on, että suorita-metodin aikavaativuus on O(n) suhteessa merkkijonon pituuteen ja metodi kutsuu jokaisen merkin kohdalla paivitaTila-metodia, jonka aikavaativuus on O(n) suhteessa säännöllisen lausekkeen pituuteen. Koska lausekkeen pituus on yleensä paljon pienempi kuin merkkijonon pituus, saadaan automaatin aikavaativuudeksi O(n) suhteessa merkkijonon pituuteen. Tällöin lausekkeen pituuden voi mieltää aikavaativuuden kannalta joksikin vakiokertoimeksi.

Tilavaativuudeksi saadaan vastaavalla päättelyllä neliöinen O(n^2) suhteessa säännöllisen lausekkeen pituuteen. Pahimman tapauksen toteutuminen tarkoittaisi hieman yksinkertaistaen sitä, että jossakin vaiheessa kaikki automaatin merkkitilat olisivat aktiivisia ja sama tilanne toteutuisi myös seuraavan askeleen aikana. On vaikeaa keksiä järkevää esimerkkiä niin pitkästä lausekkeesta, että asia aiheuttaisi käytännön ongelmia

### Omat tietorakenteet
#### Pino
Luokka Pino toteuttaa pinon ja siihen muutaman yksinkertaisen pino-operaation. Pinon sisällä alkiot talletetaan taulukkoon.

Konstruktori luo pinon, jossa on aluksi tilaa kahdeksalle alkiolle. Konstruktorin aika- ja tilavaativuus on siis O(1).

lisaa-metodi lisää alkion pinoon. Normaalisti metodin aika- ja tilavaativuus on selvästi O(1). Jos alkiotaulu on täynnä, metodi luo uuden, kooltaan kaksinkertaisen taulun ja kopioi entiset alkiot siihen. Tällöin aika- ja tilavaativuus on O(n) suhteessa pinossa olevien alkioiden määrään. Mutta tasoitetun vaativuusanalyysin perusteella vaativuudet säilyvät silti kokonaisuutena vakiona.

poista-metodi palauttaa pinon päällimmäisen alkion ja vähentää alkioiden määrää kuvaavaa muuttujaa yhdellä. Aika- ja tilavaativuus on O(1).

onkoTyhja-metodi palauttaa tiedon siitä, onko pino tyhjä. Aika- ja tilavaativuus on O(1).

kurkista-metodi palauttaa pinon päällimmäisen alkion poistamatta sitä pinosta. Aika- ja tilavaativuus on O(1).

Pinoon ei toteutettu dynaamista alkiotaulun pienennystä, sillä se ei ollut tarpeen ohjelman kannalta; ohjelmassa käytettävät pinot ovat suurimmillaankin pieniä käytettävissä olevaan muistiin nähden.

Yhteenveto pinosta on, että yksittäisten metodien aika- ja tilavaativuudet ovat tasoitetun analyysin mielessä vakioita. Koko pinon tilavaativuus on O(n) suhteessa pinossa olevien alkioiden määrään, sillä jokainen alkio vie yhden paikan alkiotaulusta.

#### Jono
Luokka Jono toteuttaa tavanomaisen jonon.

Jonon konstruktoriin ja metodeihin lisaa, poista, kurkista ja onkoTyhja pätee sama, mitä edellä sanottiin pinon metodeista. Toiminnallisena erona on vain se, että jonoa käsitellään kummastakin päästä. Jonossa on myös aika- ja tilavaativuudeltaan vakioarvoinen apumetodi onkoTaysi.

Lisäksi luokassa on luoKopio-metodi, joka luo jonosta kopion (eri olion). Kopiolla on samat alkiot kuin alkuperäisellä jonolla, joten kopion tekemisen aika- ja tilavaativuus on O(n). luoKopio-metodi osoittautui koodin selkeyden kannalta hyödylliseksi parissa sovelluslogiikkaluokkien kohdassa.

Jonoonkaan ei ollut tarvetta toteuttaa alkiotaulun dynaamista piennnystä.

#### Tila
Luokka Tila on äärellisen automaatin rakennuspalikka. Se sisältää muutaman muuttujan. Luokassa ei ole muuta toiminnallisuutta kuin setterit ja getterit.

### Käyttöliittymä
Tekstikayttoliittyma-luokka toteuttaa ohjelman käyttöliittymän. Luokka kutsuu sovelluslogiikkaluokkia alussa kerrotussa järjestyksessä. Jos käyttäjän antamien parametrien käsittelyssä ei ilmene virheitä, luokka välittää luettavan tiedoston rivi kerrallaan automaatille ja tarvittaessa tulostaa rivit.

Suurin osan luokan koodia on reagointia logiikkaluokkien paluuarvoihin, jotta käyttäjälle tulostetaan tarvittaessa järkevä virheilmoitus.

Kun ohjelmaa käytetään tavanomaisesti, luettava tiedosto on selvästi pitempi kuin komentoriviparametrit. Tällöin käyttöliittymän aikavaativuus on O(n) suhteessa tiedoston pituuteen, sillä ohjelman suorituksen aikana koko tiedosto käydään läpi kerran (rivi kerrallaan).

Käyttöliittymän tilavaativuuden ilmaiseminen lyhyesti on hieman epämääräistä, sillä luokassa luodaan oliot sovelluslogiikkaluokista. Jos olioiden luonti jätetään huomiotta, tilavaativuus on O(n) suhteessa komentoriviparametrien pituuteen tai luettavan tiedoston pisimmän rivin pituuteen, kumpi sattuu olemaan pitempi. Tilavaativuus voi siis olla huomattavan suuri, jos luettavassa tiedostossa on vaikkapa vain yksi erittäin pitkä rivi.

## Suorituskyky
Tähän asiaa yllä olevan analyysiin ja testien perusteella...

## Puutteet ja parannusehdotukset
Tiedossa ei ole bugeja. Parannusehdotus merkkivälien kuvaaminen automaatissa yhdellä tilalla. täydentyy...

## Lähteet
täydentyy...?
