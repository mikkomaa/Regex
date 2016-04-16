package sovelluslogiikka;

import domain.Jono;
import org.junit.Test;
import static org.junit.Assert.*;

public class NotaationtarkistajaTest {

    Notaationtarkistaja n;
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
    
    String[] hakasulutOikeat = {"[a-z]", "[a-b]", "[y-z]", "[d-g]", "[A-Z]", "[A-B]", "[Y-Z]", "[D-G]",
    "[0-9]", "[0-1]", "[8-9]", "[3-6]", "12[a-z]", "[a-b]ab", "a[y-z]b", "[d-g]*", "[a-d][1-2]"};
    
    String[] hakasulutVirheelliset = {"[", "[a-a]", "[b-a]", "[z-y]", "[g-d]", "[A-A]", "[B-A]", "[Z-Y]", "[G-D]",
    "[0-0]", "[1-0]", "[9-8]", "[6-3]", "12[a-z", "[a-bab", "[dg]*", "[a-f][1]", "[a-v",
    "[c-}]", "[<-f]", "[<-}]", "[&-d]", "[3-<]", "[<Xf]", "[<X}]", "[&Xd]", "[3X<]",
    "[aXc]", "[3X6]", "[AXF]", "[A-b]"};
    
    String[] jalkimmainenHakasulkuVirheellinen = {"[a-b]]", "][b-a]", "]", "g-d]", "]A-A[", "ab|]f"};

    String[] oikeat = {"ab*c", "\\)9\\*pöö", "a(ab)*", "(ab)|c", "12\\34((5)|6)7*8", "12\\\\3"};
    String[] oikeatPistein = {"a.b*.c", "\\).9.\\*.p.ö.ö", "a.(a.b)*", "(a.b)|c", "1.2.\\3.4.((5)|6).7*.8", "1.2.\\\\.3"};
    String[] oikeatPostfix = {"ab*.c.", "\\)9.\\*.p.ö.ö.", "aab.*.", "ab.c|", "12.\\3.4.56|.7*.8.", "12.\\\\.3."};

    @Test
    public void konstruktoriToimiiOikein() {
        n = new Notaationtarkistaja(luoJono("ab|"));
        assertEquals("ab|", n.toString());
    }

    // Testataan onkoLausekeOikein()-metodia
    @Test
    public void onkoLausekeOikeinToimiiLyhyillaVirheellisilla() {
        for (int i = 0; i < lyhyetVirheelliset.length; i++) {
            jono = luoJono(lyhyetVirheelliset[i]);
            n = new Notaationtarkistaja(jono);
            assertEquals(lyhyetVirheellisetVastaukset[i], n.onkoLausekeOikein());
        }
    }

    @Test
    public void onkoLausekeOikeinToimiiLyhyillaOikeilla() {
        for (int i = 0; i < lyhyetOikeat.length; i++) {
            jono = luoJono(lyhyetOikeat[i]);
            n = new Notaationtarkistaja(jono);
            assertEquals('x', n.onkoLausekeOikein());
        }
    }

    @Test
    public void onkoLausekeOikeinToimiiTaivirheilla() {
        for (int i = 0; i < taitVirheelliset.length; i++) {
            jono = luoJono(taitVirheelliset[i]);
            n = new Notaationtarkistaja(jono);
            assertEquals('|', n.onkoLausekeOikein());
        }
    }

    @Test
    public void onkoLausekeOikeinToimiiKunTaitOikein() {
        for (int i = 0; i < taitOikeat.length; i++) {
            jono = luoJono(taitOikeat[i]);
            n = new Notaationtarkistaja(jono);
            assertEquals('x', n.onkoLausekeOikein());
        }
    }

    @Test
    public void onkoLausekeOikeinToimiiKunTahtiVaarin() {
        for (int i = 0; i < tahdetVirheelliset.length; i++) {
            jono = luoJono(tahdetVirheelliset[i]);
            n = new Notaationtarkistaja(jono);
            assertEquals('*', n.onkoLausekeOikein());
        }
    }

    @Test
    public void onkoLausekeOikeinToimiiKunSulkuVaarin() {
        for (int i = 0; i < sulutVirheelliset.length; i++) {
            jono = luoJono(sulutVirheelliset[i]);
            n = new Notaationtarkistaja(jono);
            assertEquals(sulutVirheellisetVastaukset[i], n.onkoLausekeOikein());
        }
    }
    
    @Test
    public void onkoLausekeOikeinToimiiKunHakasulutOikein() {
        for (int i = 0; i < hakasulutOikeat.length; i++) {
            jono = luoJono(hakasulutOikeat[i]);
            n = new Notaationtarkistaja(jono);
            assertEquals('x', n.onkoLausekeOikein());
        }
    }
    
    @Test
    public void onkoLausekeOikeinToimiiKunHakasulutVaarin() {
        for (int i = 0; i < hakasulutVirheelliset.length; i++) {
            jono = luoJono(hakasulutVirheelliset[i]);
            n = new Notaationtarkistaja(jono);
            assertEquals('[', n.onkoLausekeOikein());
        }
    }
    
    @Test
    public void onkoLausekeOikeinToimiiKunJalkimmainenHakasulkuVaarin() {
        for (int i = 0; i < jalkimmainenHakasulkuVirheellinen.length; i++) {
            jono = luoJono(jalkimmainenHakasulkuVirheellinen[i]);
            n = new Notaationtarkistaja(jono);
            assertEquals(']', n.onkoLausekeOikein());
        }
    }

    @Test
    public void onkoLausekeOikeinToimiiOikeilla() {
        for (int i = 0; i < oikeat.length; i++) {
            jono = luoJono(oikeat[i]);
            n = new Notaationtarkistaja(jono);
            assertEquals('x', n.onkoLausekeOikein());
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
