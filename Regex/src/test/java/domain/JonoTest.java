/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mikkomaa
 */
public class JonoTest {

    Jono<Character> jono;

    public JonoTest() {
        jono = new Jono<>();
    }

    @Before
    public void setUp() {
        jono = new Jono<>();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void lisaaToimiiOikeinKunAlkiotauluaSuurennetaan() {
        lisaaAlkioita(10);
        assertEquals("abcdefghij", jono.toString());
    }
    
    @Test
    public void lisaaToimiiOikeinKunAlkiotauluPyorahtaaAlkuun() {
        lisaaAlkioita(5);
        poistaAlkioita(4);
        lisaaAlkioita(5);
        assertEquals("eabcde", jono.toString());
    }
    
    @Test
    public void poistaPalauttaaNullKunJonoOnTyhja() {
        assertNull(jono.poista());
    }
    
    @Test
    public void poistaEiPalautaNullKunJonoEiOleTyhja() {
        lisaaAlkioita(1);
        assertNotNull(jono.poista());
    }
    
    @Test
    public void poistaToimiiOikeinKunPoistetaanKaikki() {
        lisaaAlkioita(30);
        poistaAlkioita(30);
        assertEquals("", jono.toString());
    }

    @Test
    public void poistaToimiiOikeinKunEiPoistetaKaikkia() {
        lisaaAlkioita(26);
        poistaAlkioita(24);
        assertEquals("yz", jono.toString());
    }
    
    @Test
    public void poistaToimiiOikeinKunAlkiotauluPyorahtaaAlkuun() {
        lisaaAlkioita(6);
        poistaAlkioita(5);
        lisaaAlkioita(3);
        poistaAlkioita(4);
        assertEquals("", jono.toString());
    }
    
    @Test
    public void kurkistaToimiiOikeinTyhjalla() {
        lisaaAlkioita(6);
        poistaAlkioita(5);
        lisaaAlkioita(3);
        poistaAlkioita(4);
        assertNull(jono.kurkista());
    }
    
    @Test
    public void kurkistaToimiiOikeinEiTyhjalla() {
        lisaaAlkioita(4);
        poistaAlkioita(2);
        assertEquals('c', (char) jono.kurkista());
    }
    
    @Test
    public void onkoTyhjaToimiiOikeinTyhjalla() {
        lisaaAlkioita(15);
        poistaAlkioita(15);
        assertEquals(true, jono.onkoTyhja());
    }

    @Test
    public void onkoTyhjaToimiiOikeinEiTyhjalla() {
        lisaaAlkioita(15);
        assertEquals(false, jono.onkoTyhja());
    }
    

    private void lisaaAlkioita(int maara) {
        for (int i = 0; i < maara; i++) {
            int c = 'a' + i % 26;
            jono.lisaa((char) c);
        }
    }

    private void poistaAlkioita(int maara) {
        while (maara-- > 0) {
            jono.poista();
        }
    }
}
