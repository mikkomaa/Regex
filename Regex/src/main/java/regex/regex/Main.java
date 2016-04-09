/*
 * Pakkaus sisältää main-luokan, joka huolehtii ohjelman ajamisesta
 */
package regex.regex;

import java.util.Scanner;
import kayttoliittyma.*;

/**
 * Pääohjelmaluokka, joka käynnistää ohjelman ja huolehtii ohjelman ajamisesta
 */
public class Main {

    /**
     * Metodi käynnistää ja ajaa ohjelman
     *
     * @param args Käyttäjän antamat komentoriviparametrit
     */
    public static void main(String[] args) {
        Kayttoliittyma k = new Tekstikayttoliittyma(args, new Scanner(System.in));
        k.kaynnista();
    }

}
