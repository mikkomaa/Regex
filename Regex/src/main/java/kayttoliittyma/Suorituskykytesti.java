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
    FileWriter raportti;
    Automaatti automaatti;
    Scanner lukija;
    private final int KERTOJA = 1; // montako kertaa sama testi ajetaan

    @Override
    public void kaynnista() {
        System.out.println("Käynnistetään testit.");
        alustaTestitiedot();
        ajaTestit();
        hoidaLopputoimet();
        System.out.println("Testit onnistuivat. Testiraportti on tiedostossa "
                + "testiraportti.txt.");
    }

    private void alustaTestitiedot() {
        lausekkeet = new ArrayList<>();
        tiedostot = new ArrayList<>();
        lueAlustustiedostot("lausekkeet.txt", lausekkeet);
        lueAlustustiedostot("tiedostot.txt", tiedostot);

        try {
            raportti = new FileWriter("testiraportti.txt", false);
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
            lopeta();
        }
    }

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

    private Automaatti alustaTestiymparisto(String regex, String tiedosto) {
        String[] args = {tiedosto, regex};
        Jono<Character> lauseke = kasitteleParametrit(args);
        tarkistaLauseke(lauseke);
        lauseke = new Notaationmuuntaja(lauseke).muunna();
        Tila nfa = new Automaatinluoja().luoAutomaatti(lauseke);
        return new Automaatti(nfa);
    }

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

    private void ajaTestit() {
        try {
            raportti.write("Testataan jokainen tiedosto kaikilla lausekkeilla.\n"
                    + "Kukin suoritusaika on " + KERTOJA + " suorituskerran keskiarvo.\n\n");

            for (String tiedosto : tiedostot) {
                System.out.print("Testataan tiedostoa " + tiedosto + "... ");
                raportti.write("Tiedosto: " + tiedosto + "\n");

                for (String lauseke : lausekkeet) {
                    ajaTesti(lauseke, tiedosto);
                }

                raportti.write("\n");
                System.out.println("valmis.");
            }
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
            lopeta();
        }
    }

    private void ajaTesti(String regex, String tiedosto) {
        try {
            raportti.write("  lauseke: " + regex + ", suoritusaika: ");

            long aika = 0;
            for (int i = 0; i < KERTOJA; i++) {
                long aikaAlussa = System.currentTimeMillis();
                automaatti = alustaTestiymparisto(regex, tiedosto);
                while (lukija.hasNextLine()) {
                    String s = lukija.nextLine();
                    if (automaatti.suorita(s))
                        System.out.println(s);
                }
                long aikaLopussa = System.currentTimeMillis();

                aika += aikaLopussa - aikaAlussa;
                lukija.close();
            }
            raportti.write("" + (aika / KERTOJA) + " ms.\n");

        } catch (Exception e) {
            System.out.println("Tapahtui virhe: " + e.getMessage()
                    + "\ntestissä: lauseke " + regex + ", tiedosto " + tiedosto);
            lopeta();
        }
    }

    private void tarkistaLauseke(Jono<Character> lauseke) {
        Notaationtarkistaja tarkistaja = new Notaationtarkistaja(lauseke);
        char c = tarkistaja.onkoLausekeOikein();
        if (c != 'x') {
            System.out.println("\nVirhe. Lauseke on väärin: " + lauseke.toString());
            lopeta();
        }
    }

    private void hoidaLopputoimet() {
        try {
            raportti.close();
            lukija.close();
        } catch (Exception e) {
            System.out.println("Virhe tiedostoa testiraportti.txt suljettaessa.");
            lopeta();
        }
    }

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
