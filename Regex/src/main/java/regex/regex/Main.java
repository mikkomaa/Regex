/*
 * Pakkaus sisältää main-luokan, joka huolehtii ohjelman ajamisesta
 */
package regex.regex;

import domain.*;
import java.util.Scanner;
import sovelluslogiikka.Automaatinluoja;
import sovelluslogiikka.Automaatti;
import sovelluslogiikka.Notaationmuuntaja;
import sovelluslogiikka.Parametrikasittelija;

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
        
        // TODO: refaktoroi alla oleva erilliseen käyttöliittymään
        
        Parametrikasittelija pk = new Parametrikasittelija(args);
        Scanner lukija = pk.avaaTiedosto();
        if (lukija == null) {
            System.out.println("hupsis");
            tulostaAvausvirhe();
            System.exit(0);
        }
        
        Jono<Character> lauseke = pk.taulukoiLauseke();
        if (lauseke == null) {
            tulostaPuuttuvaLausekeVirhe();
            System.exit(0);
        }
        
        Notaationmuuntaja nm = new Notaationmuuntaja(lauseke);
        char c = nm.onkoLausekeOikein();
        if (c != 'x') {
            tulostaLausekevirhe();
            System.exit(0);
        }
        nm.lisaaPisteet();
        Jono<Character> postfix = nm.muutaPostfixiin();
        
        Automaatinluoja luoja = new Automaatinluoja();
        Tila nfa = luoja.luoAutomaatti(postfix);
        
        Automaatti automaatti = new Automaatti(nfa);
        
        while (lukija.hasNextLine()) {
            String rivi = lukija.nextLine();
            if (automaatti.suorita(rivi)) {
                System.out.println(rivi);
            }
        }
        
    }
    
    private static void tulostaAvausvirhe() {
        //todo
    }
    
    private static void tulostaPuuttuvaLausekeVirhe() {
        //todo
    }
    
    private static void tulostaLausekevirhe() {
        //todo
    }
    
    private static void tulostaOhje() {
        //todo
    }
    
}
