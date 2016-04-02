/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regex.regex;

import domain.*;
import java.util.Scanner;
import sovelluslogiikka.Notaationmuuntaja;
import sovelluslogiikka.Parametrikasittelija;

/**
 *
 * @author mikkomaa
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // testikoodia
        Tila t = new Tila('\0', true, null, null);
        Jono<Character> p = new Jono<>();
        p.lisaa('1');
        p.lisaa('2');
        p.lisaa('\\');
        p.lisaa('3');
        Notaationmuuntaja m = new Notaationmuuntaja(p);
        System.out.println(m.lisaaPisteet().toString());
        
//        for (String arg : args) {
//            System.out.println(arg);
//        }
//        System.out.println(args.length);
//        
//        Parametrikasittelija k = new Parametrikasittelija(args);
//        Scanner sc = k.avaaTiedosto();
//        System.out.println(k);
    }
    
}
