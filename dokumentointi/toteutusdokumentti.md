# Toteutusdokumentti
## Ohjelman yleisrakenne
Ohjelma on Javalla toteutettu säännöllisten lausekkeiden tulkki. Käyttäjä voi antaa komentoriviltä säännöllisen lausekkeen ja tiedoston, josta lausekkeen kuvaamaa merkkijonoa etsitään. Ohjelma tulostaa ruudulle tiedoston rivit, joista merkkijono löytyy.

Ohjelman yleinen rakenne on kolmiosainen. Sovelluslogiikkaluokat sisältävät ohjelman varsinaisen toimintalogiikan. Toinen osa muodostuu itse toteutetuista tietorakenteista, joita käytetään apuna ohjelmalogiikan toteutuksessa. Kolmas osa on ohjelman käyttöliittymä.

Sovelluslogiikkaluokat on laadittu toiminnallisesti itsenäisiksi, toisistaan erillisiksi osiksi. Luokat eivät kutsu toisiaan, vaan käytännössä käyttöliittymä suorittaa ne tietyssä järjestyksessä sitä mukaa, kun ohjelman suoritus etenee.

Omia tietorakenteita ohjelmassa ovat pino ja jono. Kummatkin kasvavat tarvittaessa dynaamisesti. Ne toteuttavat rajapinnan Sailio. Lisäksi on määritelty luokka Tila. Lausekkeita käsittelevä automaatti muodostetaan toisiinsa linkitetyistä Tila-olioista.

Ohjelmassa on tekstipohjainen käyttöliittymä.

### Luokkien väliset yhteydet
Luokkien väliset yhteydet näkyvät toteutusvaiheen luokkakaaviossa. Se on dokumenttikansiossa.

### Sovelluslogiikkaluokkien toiminnan yleispiirteet
Kun käyttäjä käynnistää ohjelman, sovelluslogiikkaluokat suoritetaan seuraavassa järjestyksessä:

1. Parametrikasittelija. Luokka avaa tiedoston, josta säännöllistä lauseketta etsitään, ja muuntaa lausekkeen yhdeksi jonoksi. Jos käyttäjän antamissa komentoriviparametreissä on virhe (muu kuin lausekkeen syntaksivirhe), ohjelman suoritus päättyy.
2. Notaationtarkistaja. Luokka tarkistaa, onko lausekkeen syntaksi oikein. Jos ei ole, ohjelman suoritus päättyy.
3. Notaationmuuntaja. Luokka muuntaa lausekkeen postfix-muotoon automaatin luomista varten.
4. Automaatinluoja. Luokka luo lausekkeesta äärellisen automaatin.
5. Automaatti. Luokan avulla tarkistetaan, löytyykö tiedoston riveiltä lausekkeen kuvaama merkkijono.

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
onkoLausekeOikein-metodi käy läpi konstruktorissa luodun lausekekopion tarkistaen, onko lauseke (teknisesti jono) syntaksin mukainen.

onkoLausekeOikein()
    while (lauseke ei ole tyhjä)
        char c = lauseke.poista()
        switch (c)
            case '\\'
                tee jotain
            case '('
                tee jotain
            ...

Metodin switch-lauseessa on useita case-kohtia, joista jokaisessa tehdään joitakin vakioaikaisia ja -tilaisia operaatioita. Lausekkeen yksittäinen merkki käsitellään jossakin case-kohdassa. Jos lauseke on syntaksin mukainen, koko lauseke käydään läpi. Jos lausekkeessa on virhe, metodin suoritus päättyy virheen kohdalla. Metodi käyttää muutamaa apumuuttujaa.

Siis metodin aikavaativuus on O(n) ja tilavaativuus O(1).

#### Notaationmuuntaja
Luokka muuntaa syntaksiltaan oikean, infix-muodossa olevan säännöllisen lausekkeen postfix-muotoon. Luokan käyttäjän näkökulmasta muunnos tapahtuu kutsumalla muunna-metodia. Luokan sisäisesti muuntaminen on selkeyden vuoksi jaettu kolmeen osaan eli poistaHakasulut-, lisaaPisteet- ja muutaPostfixiin-metodeiksi.

##### Konstruktori
Luokan konstruktori luo kopion argumenttina saamastaan lausekkeesta Jono-luokan luoKopio-metodilla. Konstruktorin aika- ja tilavaativuus on O(n).

##### poistaHakasulut-metodi
Metodi käy lausekkeen merkki merkiltä läpi tasan kerran. Jos lausekkeessa on hakasulkumerkintöjä, ne muutetaan toiminnaltaan vastaaviksi lausekkeen osiksi ilman hakasulkuja. Hakasulut tulkitaan siten, että esimerkiksi merkintä [c-f] tarkoittaa samaa kuin (c|d|e|f). Kun lauseke on käyty läpi, tuloksena on uusi, semantiikaltaan entistä vastaava lauseke ilman hakasulkuja.

poistaHakasulut()
    while (lauseke ei ole tyhjä)
        char c = lauseke.poista()
        switch (c)
            case '['
                muunna hakasulkulauseke normaalisulkuiseksi
            case 'muut merkit'
                lisää c uuteen lausekkeeseen sellaisenaan

Hakasuluissa voidaan käyttää kolmenlaisia merkkivälejä: [a-z], [A-Z] ja [0-9]. Koska aakkosia a-z on 26 kappaletta, yksittäisen viisi merkkiä pitkän hakasulkumerkinnän tilalle voi tulla enintään 26+25+2=53 merkkiä pitkä sulkulauseke. Tästä seuraa, että yksittäiset hakasulkujen poistot voidaan tehdä vakioajassa ja -tilassa.

Tuloksena oleva muunnettu säännöllinen lauseke on pituudeltaan alkuperäinen lauseke kerrottuna jollain vähintään yhden suuruisella kertoimella.

Siis metodin aika- ja tilavaativuus on O(n).

##### lisaaPisteet-metodi
Metodi lisää säännölliseen lausekkeeseen pisteet katenaatioiden merkiksi. Esim. käyttäjän antama lauseke abc muutetaan muotoon a.b.c.

lisaaPisteet()
    while (lauseke ei ole tyhjä)
        char c = lauseke.poista()
        if (c:n jälkeen tarvitaan katenaatio)
            lisää uuteen lausekkeeseen c
            lisää uuteen lausekkeeseen .
        else
            lisää uuteen lausekkeeseen c

Oikea metodi on teknisten yksityiskohtien vuoksi mutkikkaampi, mutta toimintaidea on pseudokoodin mukainen.

Metodi käy lausekkeen läpi kertaalleen merkki kerrallaan ja luo samalla uuden lausekkeen. Uusi lauseke on pisteiden lisäämisen vuoksi pituudeltaan alkuperäinen lauseke kerrottuna jollain vähintään yhden suuruisella kertoimella k, missä k on (tässä sivuutettujen teknisten seikkojen vuoksi) enintään noin kolme.

Metodin aika- ja tilavaativuus on O(n).

##### muutaPostfixiin-metodi
Metodi luo infix-notaatiossa olevasta lausekkeesta uuden, postfix-muodossa olevan lausekkeen. Metodi käyttää shunting-yard-algoritmia.

muutaPostfixiin()
    while (lauseke ei ole tyhjä)
        char c = lauseke.poista()
        switch (c)
            case '\\'
                tee jotain
            case '('
                tee jotain
            ...

Metodin switch-lauseessa on useita case-kohtia, joista jokaisessa suoritetaan joitakin vakioaikaisia ja -tilaisia operaatioita. Lausekkeen yksittäinen merkki käsitellään jossakin case-kohdassa. Metodi käyttää apuna pinoa, johon väliaikaisesti säilötään operaattoreita.

Koska metodi käy läpi koko lausekkeen ja luo samalla uuden, aika- ja tilavaativuus on O(n).

#### Automaatinluoja
täydentyy... Thompsonin algoritmi...

#### Automaatti
täydentyy...

### Omat tietorakenteet
#### Pino
Luokka Pino toteuttaa pinon ja siihen muutaman yksinkertaisen pino-operaation. Pinon sisällä alkiot talletetaan taulukkoon.

Konstruktori luo pinon, jossa on aluksi tilaa kahdeksalle alkiolle. Luonnin aika- ja tilavaativuudet ovat siis vakioita.

lisaa-metodi lisää alkion pinoon. Normaalisti metodin aika- ja tilavaativuus on selvästi vakio. Jos alkiotaulu on täynnä, metodi luo uuden, kooltaan kaksinkertaisen taulun ja kopioi entiset alkiot siihen. Tällöin aika- ja tilavaativuus on O(n), mutta tasoitetun vaativuusanalyysin perusteella vaativuudet säilyvät kokonaisuutena vakiona.

poista-metodi palauttaa pinon päällimmäisen alkion ja vähentää alkioiden määrää kuvaavaa muuttujaa yhdellä. Aika- ja tilavaativuus on vakio.

onkoTyhja-metodi palauttaa tiedon siitä, onko pino tyhjä. Aika- ja tilavaativuus on vakio.

kurkista-metodi palauttaa pinon päällimmäisen alkion poistamatta sitä pinosta. Aika- ja tilavaativuus on vakio.

Yhteenveto pinosta on, että yksittäisten metodien aika- ja tilavaativuudet ovat tasoitetun analyysin mielessä vakioita. Koko pinon tilavaativuus on O(n), sillä jokainen alkio vie yhden paikan alkiotaulusta. Pinoon ei toteutettu dynaamista alkiotaulun pienennystä, sillä se ei ollut tarpeen ohjelman kannalta; ohjelmassa käytettävät pinot ovat suurimmillaankin pieniä käytettävissä olevaan muistiin nähden.

#### Jono
Luokka Jono toteuttaa tavanomaisen jonon.

Jonon konstruktoriin ja metodeihin lisaa, poista, kurkista ja onkoTyhja pätee sama, mitä edellä sanottiin pinon metodeista. Toiminnallisena erona on vain se, että jonoa käsitellään kummastakin päästä. Jonossa on myös aika- ja tilavaativuudeltaan vakioarvoinen apumetodi onkoTaysi.

Lisäksi luokassa on luoKopio-metodi, joka luo jonosta kopion (eri olion). Kopiolla on samat alkiot kuin alkuperäisellä jonolla, joten kopion tekemisen aika- ja tilavaativuus on O(n). Kopioimismahdollisuus osoittautui koodin selkeyden kannalta hyödylliseksi parissa sovelluslogiikkaluokkien kohdassa.

Jonoonkaan ei ollut tarvetta toteuttaa alkiotaulun dynaamista piennnystä.

#### Tila
Luokka Tila on äärellisen automaatin rakennuspalikka. Se sisältää muutaman muuttujan. Luokassa ei ole muuta toiminnallisuutta kuin setterit ja getterit.

### Käyttöliittymä
Tekstikäyttöliittymä toteuttaa ohjelman käyttöliittymän. Luokka kutsuu sovelluslogiikkaluokkia edellä kerrotussa järjestyksessä. Jos käyttäjän antamien parametrien käsittelyssä ei ilmene virheitä, luokka välittää luettavan tiedoston rivi kerrallaan automaatille ja tarvittaessa tulostaa rivit.

Suurin osan luokan koodia on reagointia logiikkaluokkien paluuarvoihin, jotta käyttäjälle tulostetaan tarvittaessa järkevä virheilmoitus.

Ohjelman tavanomaisessa käytössä luettava tiedosto on selvästi pitempi kuin komentoriviparametrit. Tällöin käyttöliittymän aikavaativuus on O(n) suhteessa tiedoston pituuteen, sillä ohjelman suorituksen aikana koko tiedosto käydään läpi kerran (rivi kerrallaan).

Käyttöliittymän tilavaativuuden ilmaiseminen lyhyesti on hieman epämääräistä, sillä luokassa luodaan oliot sovelluslogiikkaluokista. Jos olioiden luonti jätetään huomiotta, tilavaativuus on O(n) suhteessa komentoriviparametrien pituuteen tai luettavan tiedoston pisimmän rivin pituuteen, kumpi sattuu olemaan pitempi. Tilavaativuus voi siis olla huomattavan suuri, jos luettavassa tiedostossa on vaikkapa vain yksi erittäin pitkä rivi.

## Suorituskyky
Tähän asiaa testien perusteella...

## Puutteet ja parannusehdotukset
Tiedossa ei ole bugeja.

## Lähteet

