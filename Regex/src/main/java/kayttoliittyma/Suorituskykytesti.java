package kayttoliittyma;

import domain.Jono;
import domain.Tila;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import sovelluslogiikka.*;

/**
 * Luokka toteuttaa automaattiset suorituskykytestit. Luokka ei ole osa ohjelman
 * varsinaista toiminnallisuutta
 */
public class Suorituskykytesti implements Kayttoliittyma {

    ArrayList<String> lausekkeet; // testattavat lausekkeet
    ArrayList<String> tiedostot; // testattavat tiedostot
    FileWriter raportti; // testiraportin kirjoittamiseen
    Automaatti automaatti;
    Scanner lukija;
    private final int KERTOJA = 10; // montako kertaa sama testi ajetaan

    @Override
    public void kaynnista() {
        System.out.println("Käynnistetään testit.");
        alustaTestitiedot();
        ajaTestit();
        hoidaLopputoimet();
        System.out.println("Testit onnistuivat. Testiraportti on tiedostossa "
                + "testiraportti.txt.");
    }

    // Luetaan testien tiedot tiedostoista ja avataan raporttitiedosto
    private void alustaTestitiedot() {
        lausekkeet = new ArrayList<>();
        tiedostot = new ArrayList<>();
        lueAlustustiedostot("lausekkeet.txt", lausekkeet);
        lueAlustustiedostot("tiedostot.txt", tiedostot);

        try {
            // jos tiedosto on olemassa, kirjoitetaan päälle
            raportti = new FileWriter("testiraportti.txt", false);
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
            lopeta();
        }
    }

    // Ajetaan testit yksi kerrallaan ja kirjoitetaan tulos raporttiin
    private void ajaTestit() {
        try {
            raportti.write("Testataan jokainen tiedosto kaikilla lausekkeilla.\n"
                    + "Kukin suoritusaika on " + KERTOJA + " testikerran keskiarvo.\n\n");

            for (String tiedosto : tiedostot) {
                if (tiedosto.isEmpty()) {
                    continue; // tiedostot.txt:ssä on tyhjä rivi
                }
                
                System.out.print("Testataan tiedostoa " + tiedosto + "... ");
                raportti.write("Tiedosto: " + tiedosto + "\n");

                for (String lauseke : lausekkeet) {
                    long aika = ajaTesti(lauseke, tiedosto); // testin ajo
                    raportti.write("  lauseke: " + lauseke + ", suoritusaika: "
                            + aika + " ms.\n");
                }

                raportti.write("\n");
                System.out.println("valmis.");
            }
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
            lopeta();
        }
    }

    // Ajetaan yksi testi KERTOJA kertaa. Palautetaan keskimääräinen suoritusaika
    private long ajaTesti(String regex, String tiedosto) {
        long suoritusaika = 0;
        
        try {
            for (int i = 0; i < KERTOJA; i++) {
                long aikaAlussa = System.currentTimeMillis();
                automaatti = alustaTestiymparisto(regex, tiedosto);
                while (lukija.hasNextLine()) {
                    String rivi = lukija.nextLine();
                    if (automaatti.suorita(rivi));
                        //System.out.println(rivi);
                }
                long aikaLopussa = System.currentTimeMillis();
                
                suoritusaika += aikaLopussa - aikaAlussa;
                lukija.close();
            }
        } catch (Exception e) {
            System.out.println("Tapahtui virhe: " + e.getMessage()
                    + "\ntestissä: lauseke " + regex + ", tiedosto " + tiedosto);
            lopeta();
        }
        return suoritusaika / KERTOJA;
    }
    
    // Luodaan automaatti yksittäistä testiä varten
    private Automaatti alustaTestiymparisto(String regex, String tiedosto) {
        String[] args = {tiedosto, regex};
        Jono<Character> lauseke = kasitteleParametrit(args);
        tarkistaLauseke(lauseke);
        lauseke = new Notaationmuuntaja(lauseke).muunna();
        Tila nfa = new Automaatinluoja().luoAutomaatti(lauseke);
        return new Automaatti(nfa);
    }
    
    // Apumetodi tiedoston lukemiseen
    private void lueAlustustiedostot(String tiedosto, ArrayList<String> taulu) {
        try {
            lukija = new Scanner(new File(tiedosto));
            while (lukija.hasNextLine()) {
                taulu.add(lukija.nextLine());
            }
            lukija.close();
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
            lopeta();
        }
    }
    
    // Apumetodi testattavan lausekkeen käsittelyyn
    private Jono<Character> kasitteleParametrit(String[] args) {
        Parametrikasittelija pk = new Parametrikasittelija(args);
        lukija = pk.avaaTiedosto();
        if (lukija == null) {
            System.out.println("\nVirhe: tiedostoa " + args[0]
                    + " ei voinut avata.");
            lopeta();
        }

        Jono<Character> lauseke = pk.taulukoiLauseke();
        if (lauseke == null) {
            System.out.println("Virhe lauseketta " + args[1]
                    + " käsiteltäessä.");
            lopeta();
        }
        return lauseke;
    }
    
    // Apumetodi säännöllisen lausekkeen oikeellisuuden tarkistamiseen
    private void tarkistaLauseke(Jono<Character> lauseke) {
        Notaationtarkistaja tarkistaja = new Notaationtarkistaja(lauseke);
        char c = tarkistaja.onkoLausekeOikein();
        if (c != 'x') {
            System.out.println("\nVirhe. Lauseke on väärin: " + lauseke.toString());
            lopeta();
        }
    }

    // Lopetetaan onnistuneiden testien jälkeen
    private void hoidaLopputoimet() {
        try {
            raportti.close();
            lukija.close();
        } catch (Exception e) {
            System.out.println("Virhe tiedostoa testiraportti.txt suljettaessa.");
            lopeta();
        }
    }

    // Lopetetaan hallitusti virheen sattuessa
    private void lopeta() {
        System.out.println("Lopetetaan.");
        try {
            raportti.close();
            lukija.close();
            System.exit(0);
        } catch (Exception e) {
            System.exit(0);
        }
    }

}
