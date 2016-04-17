/*
 * Pakkaus sisältää main-luokan
 */
package regex.regex;

import java.util.Scanner;
import kayttoliittyma.*;

/**
 * Pääohjelmaluokka, joka käynnistää ohjelman
 */
public class Main {

    /**
     * Metodi käynnistää ohjelman
     *
     * @param args Käyttäjän antamat komentoriviparametrit
     */
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("testi")) { // ajetaan testit
            Kayttoliittyma k = new Suorituskykytesti();
            k.kaynnista();
        } else { // normaali suoritus
            Kayttoliittyma k = new Tekstikayttoliittyma(args, new Scanner(System.in));
            k.kaynnista();
        }
    }

}
