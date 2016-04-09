# Viikkoraportti 2
Tällä viikolla olen kirjoittanut koodin, joka luo äärellisen automaatin postfix-muotoisesta säännöllisestä lausekkeesta. Opin, miten Thompsonin algoritmin voi käytännössä toteuttaa. Sain myös kirjoitettua koodin, jolla automaattia voi ajaa.

Lisäksi kirjoitin luokille testit. Hankaluutta aiheutti testauksessa lähinnä se, miten luodun automaatin pystyy kuvaamaan niin, että luonnin oikeellisuudelle voi kirjoittaa järkevät testit. Päädyin tekemään automaatista melko yksinkertaisen String-esityksen. Se on hieman hankalasti hahmotettava mutta testien kannalta riittävä tapa kuvata automaattia. 

Pohdin, miten automaatin ajaminen kannattaa toteuttaa, jotta se olisi tehokasta. Tavoitteenahan on lineaarinen aikavaativuus suhteessa syötteen pituuteen. Päädyin ratkaisuun, jossa automaatti tarkastelee syötettä merkki kerrallaan. Kussakin askeleessa pidetään pinossa niitä automaatin tiloja, jotka voivat tulevaisuudessa edetä hyväksyvään tilaan. Näin tulee samalla hyötykäyttöä toteuttamalleni pinorakenteelle. Kun automaatti tutkii seuraavan merkin, tutkitaan yksitellen jokainen pinossa oleva tila, eli pääseekö siitä eteenpäin. Etenkin automaatin epädeterministisyyden vuoksi vaikutti selkeimmältä kirjoittaa etenemisalgoritmi rekursiiviseksi. Arvioin, ettei rekursio tässä kohtaa aiheuta liikaa tehottomuutta suhteessa ei-rekursiiviseen algoritmiin. Automaatin rakenteen vuoksi rekursion syvyys on yleensä korkeintaan muutama askel. Mielenkiintoista nähdä, millaisia tehokkuustestien tulokset tulevat olemaan.

Tässä vaiheessa ohjelma oli niin hyvässä mallissa, että kirjoitin Main-metodiin käyttöliittymäkoodia, jotta pääsen kokeilemaan ohjelmaa loppukäyttäjän näkökulmasta. Huomasin pian, että etenkin laajennettavuutta silmällä pitäen on tarpeen kirjoittaa käyttöliittymä omaksi luokakseen, vaikka tässä projektissa ohjelma on käyttöliittymältään yksinkertainen komentoriviltä ajettava. Joten kirjoitin erillisen käyttöliittymäluokan.

Pientä päänvaivaa aiheutti se, että käytettävä komentotulkki (esim. bash) ei välttämättä välitä erikoismerkkejä sisältäviä komentoriviparametreja sellaisenaan ohjelmalle. Asia ratkesi bashin tapauksessa tulkin ohjeita lukemalla. Asia ei vaikuta ohjelman koodiin, mutta on hyvä mainita käyttöohjeessa.

Nopean kokeilun perusteella ohjelma toimii kuten pitääkin. Ohjelma on edistynyt hyvin aikataulussa. Epäselviä asioita ei tällä hetkellä ole.

Seuraavaksi vuorossa on käyttöliittymän yksittötestaus ja ohjelman integraatio- ja järjestelmätestausta. Lisäksi ajattelin laajentaa ohjelmaa ainakin niin, että käyttäjä voi käyttää säännöllisessä lausekkeessa myös  numero- tai kirjainvälejä, esim. vuosiluvut 1970-1990 voisi löytää lausekkeella 19[7-9]0. On myös tarpeen alkaa miettiä, miten ohjelman tehokkuutta on järkevää testata.

Aikaa olen käyttänyt tällä viikolla noin 20 tuntia.

