/*
 * Pakkaus sisältää käyttöliittymän
 */
package kayttoliittyma;

import java.util.Scanner;

/**
 * Luokka on käyttöliittymän rajapinta
 */
public interface Kayttoliittyma {

    /**
     * Metodi aloittaa käyttöliittymän ja ohjelmalogiikan suorituksen
     *
     * @param args Käyttäjän mahdollisesti antamat ohjelman käynnistysparametrit
     */
    void kaynnista();
}
