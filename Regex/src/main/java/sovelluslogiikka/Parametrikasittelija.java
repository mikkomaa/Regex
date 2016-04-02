package sovelluslogiikka;

import domain.Jono;
import java.io.File;
import java.util.Scanner;

/**
 * Luokka avaa komentoriviparametrina saadun tiedoston ja muokkaa säännöllisen
 * lausekkeen yhdeksi jonoksi
 */
public class Parametrikasittelija {

    private String[] args;

    /**
     * Luokan konstruktori
     *
     * @param args Käyttäjän antamat komentoriviparametrit
     */
    public Parametrikasittelija(String[] args) {
        this.args = args;
    }

    /**
     * Metodi avaa parametrina saadun tiedoston
     *
     * @return Palauttaa Scanner-olion tiedostoon. Jos avaaminen ei onnistu,
     * palauttaa null
     */
    public Scanner avaaTiedosto() {
        try {
            File tiedosto = new File(args[0]);
            Scanner lukija = new Scanner(tiedosto);
            return lukija;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Metodi tallentaa säännöllisen lausekkeen jonoon
     *
     * @return Palauttaa säännöllisen lausekkeen yhtenä jonona
     */
    public Jono<Character> taulukoiLauseke() {
        if (args.length < 2) { // käyttäjä ei antanut lauseketta komentorivillä
            return null;
        }

        Jono<Character> jono = new Jono<>();
        for (int i = 1; i < args.length; i++) {
            for (int j = 0; j < args[i].length(); j++) {
                jono.lisaa(args[i].charAt(j));
            }
            if (i < args.length - 1) {
                jono.lisaa(' ');
            }
        }
        return jono;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < args.length; i++) {
            s += args[i];
            s += i < args.length - 1 ? " " : "";
        }
        return s;
    }

}
