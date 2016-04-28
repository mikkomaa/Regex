package kayttoliittyma;

import domain.Jono;
import domain.Tila;
import java.util.Scanner;
import sovelluslogiikka.*;

/**
 * Luokka toteuttaa tekstikäyttöliittymän ohjelman ajamiseksi komentoriviltä
 */
public class Tekstikayttoliittyma implements Kayttoliittyma {

    String[] args; // komentoriviparametrit
    Scanner lukija;

    public Tekstikayttoliittyma(String[] args, Scanner lukija) {
        this.args = args;
        this.lukija = lukija;
    }

    @Override
    public void kaynnista() {
        Jono<Character> lauseke = kasitteleParametrit();
        tarkistaLauseke(lauseke);
        lauseke = new Notaationmuuntaja(lauseke).muunna();
        Tila nfa = new Automaatinluoja().luoAutomaatti(lauseke);
        Automaatti automaatti = new Automaatti(nfa);

        while (lukija.hasNextLine()) {
            String rivi = lukija.nextLine();
            if (automaatti.suorita(rivi)) {
                System.out.println(rivi);
            }
        }
    }

    private Jono<Character> kasitteleParametrit() {
        Parametrikasittelija pk = new Parametrikasittelija(args);
        lukija = pk.avaaTiedosto();
        if (lukija == null) {
            tulostaAvausvirhe();
            System.exit(0);
        }

        Jono<Character> lauseke = pk.taulukoiLauseke();
        if (lauseke == null) {
            tulostaPuuttuvaLausekeVirhe();
            System.exit(0);
        }
        return lauseke;
    }

    private void tarkistaLauseke(Jono<Character> lauseke) {
        Notaationtarkistaja tarkistaja = new Notaationtarkistaja(lauseke);
        char c = tarkistaja.onkoLausekeOikein();
        if (c != 'x') {
            tulostaLausekevirhe(c);
            System.exit(0);
        }
    }

    private void tulostaAvausvirhe() {
        if (args == null || args.length < 1) {
            System.out.println("Virhe: et antanut luettavaa tiedostoa.");
        } else {
            System.out.println("Virhe: tiedostoa " + args[0] + " ei voinut avata.");
        }
        tulostaOhje();
    }

    private void tulostaPuuttuvaLausekeVirhe() {
        System.out.println("Virhe: et antanut säännöllistä lauseketta.");
        tulostaOhje();
    }

    private void tulostaLausekevirhe(char c) {
        System.out.println("Antamasi säännöllinen lauseke on virheellinen. "
                + "Virheen aiheutti merkki " + c + ".");
        tulostaOhje();
    }

    private void tulostaOhje() {
        System.out.println("Ohjelman käyttö: java -cp Regex-1.0-SNAPSHOT.jar "
                + "regex.regex.Main tiedosto lauseke");
        System.out.println("Tarkemmat ohjeet ovat käyttöohjeessa.");
    }

}
