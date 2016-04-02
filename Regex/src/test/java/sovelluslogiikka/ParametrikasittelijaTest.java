package sovelluslogiikka;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import domain.Jono;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sovelluslogiikka.Parametrikasittelija;

/**
 *
 * @author mikkomaa
 */
public class ParametrikasittelijaTest {
    
    Jono<Character> jono;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void konstruktoriToimiiOikeinKunParametriOnTyhja() {
        String s[] = {};
        Parametrikasittelija p = new Parametrikasittelija(s);
        assertEquals("", p.toString());
    }

    @Test
    public void konstruktoriToimiiOikeinKunOnParametreja() {
        String s[] = {"eka", "toka", "kolmas"};
        Parametrikasittelija p = new Parametrikasittelija(s);
        assertEquals("eka toka kolmas", p.toString());
    }

    @Test
    public void avaaTiedostoToimiiOikeinKunEiOleTiedostoa() {
        String s[] = {};
        Parametrikasittelija p = new Parametrikasittelija(s);
        assertEquals(null, p.avaaTiedosto());
    }

    @Test
    public void avaaTiedostoToimiiOikeinKunTiedostonNimiOnVaarin() {
        String s[] = {"olematon.txt"};
        Parametrikasittelija p = new Parametrikasittelija(s);
        assertEquals(null, p.avaaTiedosto());
    }

//    @Test
//    public void avaaTiedostoToimiiOikeinKunTiedostonNimiOnOikein() {
//        String s[] = {"testi.txt"};
//        Parametrikasittelija p = new Parametrikasittelija(s);
//        assertEquals(null, p.avaaTiedosto());
//    }
    @Test
    public void taulukoiLausekeToimiiOikeinIlmanLauseketta() {
        String s[] = {};
        Parametrikasittelija p = new Parametrikasittelija(s);
        jono = p.taulukoiLauseke();
        assertNull(jono);
    }
    
    @Test
    public void taulukoiLausekeToimiiOikeinLyhyellalausekkeella() {
        String s[] = {"tiedosto", "A"};
        Parametrikasittelija p = new Parametrikasittelija(s);
        jono = p.taulukoiLauseke();
        assertEquals("A", jono.toString());
    }
    
    @Test
    public void taulukoiLausekeToimiiOikeinHelpollalausekkeella() {
        String s[] = {"tiedosto", "abc|d(ef)*"};
        Parametrikasittelija p = new Parametrikasittelija(s);
        jono = p.taulukoiLauseke();
        assertEquals("abc|d(ef)*", jono.toString());
    }

    @Test
    public void taulukoiLausekeToimiiOikeinHeittomerkkilausekkeella() {
        String s[] = {"tiedosto", "\"ab|c hei\""};
        Parametrikasittelija p = new Parametrikasittelija(s);
        jono = p.taulukoiLauseke();
        assertEquals("\"ab|c hei\"", jono.toString());
    }
    
    @Test
    public void taulukoiLausekeToimiiOikeinPitkallalausekkeella() {
        String s[] = {"tiedosto", "\"\\\'''öb|c hei\" kissa kävelee*(mmm|fff)* "};
        Parametrikasittelija p = new Parametrikasittelija(s);
        jono = p.taulukoiLauseke();
        assertEquals("\"\\\'''öb|c hei\" kissa kävelee*(mmm|fff)* ", jono.toString());
    }
    
    @Test
    public void taulukoiLausekeToimiiOikeinMoniosaisella() {
        String s[] = {"tiedosto", "abc", "def\\g", "h", "ijk"};
        Parametrikasittelija p = new Parametrikasittelija(s);
        jono = p.taulukoiLauseke();
        assertEquals("abc def\\g h ijk", jono.toString());
    }

    private String tulostaTaulu(char[] taulu) {
        String s = "";
        int i = 0;
        while (taulu[i] != '\0') {
            s += taulu[i++];
        }
        return s;
    }
}
