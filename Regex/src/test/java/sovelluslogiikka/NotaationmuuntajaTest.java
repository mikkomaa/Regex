/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

import domain.Jono;
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
public class NotaationmuuntajaTest {

    Notaationmuuntaja n;
    Jono<Character> jono;

    String[] lyhyetVirheelliset = {"\\", "(", ")", "|", "*", "a**", "a|", "b(", "()", "(*", "\\\\\\"};
    char[] lyhyetVirheellisetVastaukset = {'\\', '(', ')', '|', '*', '*', '|', '(', ')', '*', '\\'};

    String[] lyhyetOikeat = {"4", "a", ".", "4.", "a*b", "a*", "\\*", "\\||a", "a|b", "\\\\", "\\\\\\\\"};
    String[] lyhyetOikeatPistein = {"4", "a", "\\.", "4.\\.", "a*.b", "a*", "\\*", "\\||a", "a|b", "\\\\", "\\\\.\\\\"};
    String[] lyhyetOikeatPostfix = {"4", "a", "\\.", "4\\..", "a*b.", "a*", "\\*", "\\|a|", "ab|", "\\\\", "\\\\\\\\."};

    String[] taitVirheelliset = {"|bc", "a|c|", "ab|", "a||b", "(|c)", "b(|a)"};

    String[] taitOikeat = {"ab|c", "a(b|c)", "a|b|c|d", "aa|aa", "(ab)|(cd)", "ab|(c|d)", "(((a|b)))"};
    String[] taitOikeatPistein = {"a.b|c", "a.(b|c)", "a|b|c|d", "a.a|a.a", "(a.b)|(c.d)", "a.b|(c|d)", "(((a|b)))"};
    String[] taitOikeatPostfix = {"ab.c|", "abc|.", "ab|c|d|", "aa.aa.|", "ab.cd.|", "ab.cd||", "ab|"};

    String[] tahdetVirheelliset = {"a**", "a(*)", "a|*", "a|b**"};

    String[] sulutVirheelliset = {"a()", "ab)c", "a(ab", "(ab))"};
    char[] sulutVirheellisetVastaukset = {')', ')', '(', ')'};

    String[] oikeat = {"ab*c", "\\)9\\*pöö", "a(ab)*", "(ab)|c", "12\\34((5)|6)7*8", "12\\\\3"};
    String[] oikeatPistein = {"a.b*.c", "\\).9.\\*.p.ö.ö", "a.(a.b)*", "(a.b)|c", "1.2.\\3.4.((5)|6).7*.8", "1.2.\\\\.3"};
    String[] oikeatPostfix = {"ab*.c.", "\\)9.\\*.p.ö.ö.", "aab.*.", "ab.c|", "12.\\3.4.56|.7*.8.", "12.\\\\.3."};

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void konstruktoriToimiiOikein() {
        n = new Notaationmuuntaja(luoJono("ab|"));
        assertEquals("ab|", n.toString());
    }

    // Testataan onkoLausekeOikein()-metodia
    @Test
    public void onkoLausekeOikeinToimiiLyhyillaVirheellisilla() {
        for (int i = 0; i < lyhyetVirheelliset.length; i++) {
            jono = luoJono(lyhyetVirheelliset[i]);
            n = new Notaationmuuntaja(jono);
            assertEquals(lyhyetVirheellisetVastaukset[i], n.onkoLausekeOikein());
        }
    }

    @Test
    public void onkoLausekeOikeinToimiiLyhyillaOikeilla() {
        for (int i = 0; i < lyhyetOikeat.length; i++) {
            jono = luoJono(lyhyetOikeat[i]);
            n = new Notaationmuuntaja(jono);
            assertEquals('x', n.onkoLausekeOikein());
        }
    }

    @Test
    public void onkoLausekeOikeinToimiiTaivirheilla() {
        for (int i = 0; i < taitVirheelliset.length; i++) {
            jono = luoJono(taitVirheelliset[i]);
            n = new Notaationmuuntaja(jono);
            assertEquals('|', n.onkoLausekeOikein());
        }
    }

    @Test
    public void onkoLausekeOikeinToimiiKunTaitOikein() {
        for (int i = 0; i < taitOikeat.length; i++) {
            jono = luoJono(taitOikeat[i]);
            n = new Notaationmuuntaja(jono);
            assertEquals('x', n.onkoLausekeOikein());
        }
    }

    @Test
    public void onkoLausekeOikeinToimiiKunTahtiVaarin() {
        for (int i = 0; i < tahdetVirheelliset.length; i++) {
            jono = luoJono(tahdetVirheelliset[i]);
            n = new Notaationmuuntaja(jono);
            assertEquals('*', n.onkoLausekeOikein());
        }
    }

    @Test
    public void onkoLausekeOikeinToimiiKunSulkuVaarin() {
        for (int i = 0; i < sulutVirheelliset.length; i++) {
            jono = luoJono(sulutVirheelliset[i]);
            n = new Notaationmuuntaja(jono);
            assertEquals(sulutVirheellisetVastaukset[i], n.onkoLausekeOikein());
        }
    }

    @Test
    public void onkoLausekeOikeinToimiiOikeilla() {
        for (int i = 0; i < oikeat.length; i++) {
            jono = luoJono(oikeat[i]);
            n = new Notaationmuuntaja(jono);
            assertEquals('x', n.onkoLausekeOikein());
        }
    }

    // Testataan lisaaPisteet()-metodia
    @Test
    public void lisaaPisteetToimiiLyhyilla() {
        for (int i = 0; i < lyhyetOikeat.length; i++) {
            jono = luoJono(lyhyetOikeat[i]);
            n = new Notaationmuuntaja(jono);
            n.lisaaPisteet();
            assertEquals(lyhyetOikeatPistein[i], n.toString());
        }
    }

    @Test
    public void lisaaPisteetToimiiTailla() {
        for (int i = 0; i < taitOikeat.length; i++) {
            jono = luoJono(taitOikeat[i]);
            n = new Notaationmuuntaja(jono);
            n.lisaaPisteet();
            assertEquals(taitOikeatPistein[i], n.toString());
        }
    }

    @Test
    public void lisaaPisteetToimiiOikeilla() {
        for (int i = 0; i < oikeat.length; i++) {
            jono = luoJono(oikeat[i]);
            n = new Notaationmuuntaja(jono);
            n.lisaaPisteet();
            assertEquals(oikeatPistein[i], n.toString());
        }
    }

    // Testataan muutaPostfixiin()-metodia
    @Test
    public void muutaPostfixiinToimiiLyhyilla() {
        for (int i = 0; i < lyhyetOikeat.length; i++) {
            jono = luoJono(lyhyetOikeat[i]);
            n = new Notaationmuuntaja(jono);
            n.lisaaPisteet();
            assertEquals(lyhyetOikeatPostfix[i], n.muutaPostfixiin().toString());
        }
    }

    @Test
    public void muutaPostfixiinToimiiTailla() {
        for (int i = 0; i < taitOikeat.length; i++) {
            jono = luoJono(taitOikeat[i]);
            n = new Notaationmuuntaja(jono);
            n.lisaaPisteet();
            assertEquals(taitOikeatPostfix[i], n.muutaPostfixiin().toString());
        }
    }

    @Test
    public void muutaPostfixiinToimiiOikeilla() {
        for (int i = 0; i < oikeat.length; i++) {
            jono = luoJono(oikeat[i]);
            n = new Notaationmuuntaja(jono);
            n.lisaaPisteet();
            assertEquals(oikeatPostfix[i], n.muutaPostfixiin().toString());
        }
    }

//    // apumetodi
//    private char[] muunnaTauluksi(String s) {
//        char[] t = new char[s.length() + 1];
//        for (int i = 0; i < s.length(); i++) {
//            t[i] = s.charAt(i);
//        }
//        t[s.length()] = '\0';
//        return t;
//    }
//    
//    private String muunnaMerkkijonoksi(char[] t) {
//        String s = "";
//        for (int i = 0; t[i] != '\0'; i++) {
//            s += t[i];
//        }
//        return s;
//    }
    private Jono<Character> luoJono(String s) {
        Jono<Character> jono = new Jono<>();
        for (int i = 0; i < s.length(); i++) {
            jono.lisaa(s.charAt(i));
        }
        return jono;
    }
}
