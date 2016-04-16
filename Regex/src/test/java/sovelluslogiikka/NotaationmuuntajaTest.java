package sovelluslogiikka;

import domain.Jono;
import org.junit.Test;
import static org.junit.Assert.*;

public class NotaationmuuntajaTest {

    Notaationmuuntaja n;
    Jono<Character> jono;

    String[] lyhyet = {"4", "a", ".", "4.", "a*b", "a*", "\\*", "\\||a", "a|b", "\\\\", "\\\\\\\\"};
    String[] lyhyetPistein = {"4", "a", "\\.", "4.\\.", "a*.b", "a*", "\\*", "\\||a", "a|b", "\\\\", "\\\\.\\\\"};
    String[] lyhyetPostfix = {"4", "a", "\\.", "4\\..", "a*b.", "a*", "\\*", "\\|a|", "ab|", "\\\\", "\\\\\\\\."};

    String[] tait = {"ab|c", "a(b|c)", "a|b|c|d", "aa|aa", "(ab)|(cd)", "ab|(c|d)", "(((a|b)))"};
    String[] taitPistein = {"a.b|c", "a.(b|c)", "a|b|c|d", "a.a|a.a", "(a.b)|(c.d)", "a.b|(c|d)", "(((a|b)))"};
    String[] taitPostfix = {"ab.c|", "abc|.", "ab|c|d|", "aa.aa.|", "ab.cd.|", "ab.cd||", "ab|"};

    String[] hakasulut = {"[a-c]", "[a-b]|a", "a|[y-z]", "[d-g]*a", "[a-c][1-2]"};
    String[] normaalisulut = {"(a|b|c)", "(a|b)|a", "a|(y|z)", "(d|e|f|g)*a", "(a|b|c)(1|2)"};
    
    String[] muut = {"ab*c", "\\)9\\*pöö", "a(ab)*", "(ab)|c", "12\\34((5)|6)7*8", "12\\\\3", "(a*)*"};
    String[] muutPistein = {"a.b*.c", "\\).9.\\*.p.ö.ö", "a.(a.b)*", "(a.b)|c", "1.2.\\3.4.((5)|6).7*.8", "1.2.\\\\.3", "(a*)*"};
    String[] muutPostfix = {"ab*.c.", "\\)9.\\*.p.ö.ö.", "aab.*.", "ab.c|", "12.\\3.4.56|.7*.8.", "12.\\\\.3.", "a**"};

    @Test
    public void konstruktoriToimiiOikein() {
        n = new Notaationmuuntaja(luoJono("ab|"));
        assertEquals("ab|", n.toString());
    }

    // Testataan poistaHakasulut()-metodia
    @Test
    public void poistaHakasulutToimii() {
        for (int i = 0; i < hakasulut.length; i++) {
            jono = luoJono(hakasulut[i]);
            n = new Notaationmuuntaja(jono);
            assertEquals(normaalisulut[i], n.poistaHakasulut().toString());
        }
    }
    
    // Testataan lisaaPisteet()-metodia
    @Test
    public void lisaaPisteetToimiiLyhyilla() {
        for (int i = 0; i < lyhyet.length; i++) {
            jono = luoJono(lyhyet[i]);
            n = new Notaationmuuntaja(jono);
            assertEquals(lyhyetPistein[i], n.lisaaPisteet().toString());
        }
    }

    @Test
    public void lisaaPisteetToimiiTailla() {
        for (int i = 0; i < tait.length; i++) {
            jono = luoJono(tait[i]);
            n = new Notaationmuuntaja(jono);
            assertEquals(taitPistein[i], n.lisaaPisteet().toString());
        }
    }

    @Test
    public void lisaaPisteetToimiiOikeilla() {
        for (int i = 0; i < muut.length; i++) {
            jono = luoJono(muut[i]);
            n = new Notaationmuuntaja(jono);
            assertEquals(muutPistein[i], n.lisaaPisteet().toString());
        }
    }

    // Testataan muutaPostfixiin()-metodia
    @Test
    public void muutaPostfixiinToimiiLyhyilla() {
        for (int i = 0; i < lyhyet.length; i++) {
            jono = luoJono(lyhyet[i]);
            n = new Notaationmuuntaja(jono);
            n.lisaaPisteet();
            assertEquals(lyhyetPostfix[i], n.muutaPostfixiin().toString());
        }
    }

    @Test
    public void muutaPostfixiinToimiiTailla() {
        for (int i = 0; i < tait.length; i++) {
            jono = luoJono(tait[i]);
            n = new Notaationmuuntaja(jono);
            n.lisaaPisteet();
            assertEquals(taitPostfix[i], n.muutaPostfixiin().toString());
        }
    }

    @Test
    public void muutaPostfixiinToimiiOikeilla() {
        for (int i = 0; i < muut.length; i++) {
            jono = luoJono(muut[i]);
            n = new Notaationmuuntaja(jono);
            n.lisaaPisteet();
            assertEquals(muutPostfix[i], n.muutaPostfixiin().toString());
        }
    }

    private Jono<Character> luoJono(String s) {
        Jono<Character> jono = new Jono<>();
        for (int i = 0; i < s.length(); i++) {
            jono.lisaa(s.charAt(i));
        }
        return jono;
    }
}
