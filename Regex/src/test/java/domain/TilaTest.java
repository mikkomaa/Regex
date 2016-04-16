package domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TilaTest {

    Tila tila, tila2;

    @Before
    public void setUp() {
        tila = new Tila('x', true, null, null);
        tila2 = new Tila('\0', false, null, tila);
    }

    @Test
    public void konstruktoriToimiiOikein() {
        assertEquals("x true null null", tila.toString());
        assertEquals("'' false null -->", tila2.toString());
    }

    @Test
    public void getMerkkiToimiiOikein() {
        assertEquals('x', tila.getMerkki());
        assertEquals('\0', tila2.getMerkki());
    }

    @Test
    public void setMerkkiToimiiOikein() {
        tila.setMerkki('m');
        assertEquals("m true null null", tila.toString());
    }

    @Test
    public void isLopputilaToimiiOikein() {
        assertEquals(true, tila.isLopputila());
    }

    @Test
    public void setLopputilaToimiiOikein() {
        tila.setLopputila(false);
        assertEquals(false, tila.isLopputila());
    }

    @Test
    public void getUlos1ToimiiOikein() {
        assertEquals(null, tila.getUlos1());
    }

    @Test
    public void setUlos1ToimiiOikein() {
        tila.setUlos1(tila2);
        assertEquals("x true --> null", tila.toString());
        assertEquals("'' false null -->", tila.getUlos1().toString());
    }

    @Test
    public void getUlos2ToimiiOikein() {
        assertEquals(null, tila.getUlos2());
    }

    @Test
    public void setUlos2ToimiiOikein() {
        tila.setUlos2(tila);
        assertEquals("x true null -->", tila.getUlos2().toString());
    }

}
