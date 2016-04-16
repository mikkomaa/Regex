package domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PinoTest {

    Pino<Character> pino;

    public PinoTest() {
        pino = new Pino<>();
    }

    @Before
    public void setUp() {
        pino = new Pino<>();
    }

    @Test
    public void lisaaToimiiOikein() {
        lisaaAlkioita(10);
        assertEquals("abcdefghij", pino.toString());
    }

    @Test
    public void poistaToimiiOikeinKunPoistetaanKaikki() {
        lisaaAlkioita(30);
        poistaAlkioita(30);
        assertEquals("", pino.toString());
    }

    @Test
    public void poistaToimiiOikeinKunEiPoistetaKaikkia() {
        lisaaAlkioita(30);
        poistaAlkioita(28);
        assertEquals("ab", pino.toString());
    }
    
    @Test
    public void poistaPalauttaaNullKunPinoOnTyhja() {
        assertNull(pino.poista());
    }
    
    @Test
    public void poistaEiPalautaNullKunPinoEiOleTyhja() {
        lisaaAlkioita(1);
        assertNotNull(pino.poista());
    }

    @Test
    public void onkoTyhjaToimiiOikeinTyhjalla() {
        lisaaAlkioita(15);
        poistaAlkioita(15);
        assertEquals(true, pino.onkoTyhja());
    }

    @Test
    public void onkoTyhjaToimiiOikeinEiTyhjalla() {
        lisaaAlkioita(15);
        assertEquals(false, pino.onkoTyhja());
    }

    @Test
    public void kurkistaToimiiOikeinTyhjalla() {
        assertNull(pino.kurkista());
    }

    @Test
    public void kurkistaToimiiOikeinEiTyhjalla() {
        lisaaAlkioita(3);
        assertEquals('c', (char) pino.kurkista());
    }

    private void lisaaAlkioita(int maara) {
        for (int i = 0; i < maara; i++) {
            int c = 'a' + i % 26;
            pino.lisaa((char) c);
        }
    }

    private void poistaAlkioita(int maara) {
        while (maara-- > 0) {
            pino.poista();
        }
    }
}
