package sovelluslogiikka;

import domain.*;
import static domain.Vakiot.KYSYMYS;
import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AutomaatinluojaTest {
    
    Automaatinluoja luoja;
    Tila nfa; // valmis automaatti
    String automaatti; // tulostettu automaatti
    HashSet<Tila> tulostetut; // aputaulu automaatin tulostamista varten
    
    String[] postfix = {"4", "\\.", "4a.", "4a|", "4*", "?"};
    
    String[] postfixautomaatit = {"4 false --> null\n'' true null null\n",
    ". false --> null\n'' true null null\n",
    "4 false --> null\n'' false --> null\na false --> null\n'' true null null\n",
    "'' false --> -->\n4 false --> null\n'' false --> null\n'' true null null\na false --> null\n'' false --> null\n",
    "'' false --> -->\n4 false --> null\n'' false --> -->\n'' true null null\n",
    KYSYMYS + " false --> null\n'' true null null\n"};

    @Before
    public void setUp() {
        luoja = new Automaatinluoja();
        tulostetut = new HashSet<>();
        automaatti = "";
    }

    @Test
    public void luoAutomaattiToimiiOikein() {
        for (int i = 0; i < postfix.length; i++) {
            Jono<Character> jono = luoJono(postfix[i]);
            nfa = luoja.luoAutomaatti(jono);
            tulostaAutomaatti(nfa);
            assertEquals(postfixautomaatit[i], automaatti);
            
            alustaMuuttujat();
        }
    }

    private Jono<Character> luoJono(String s) {
        Jono<Character> jono = new Jono<>();
        for (int i = 0; i < s.length(); i++) {
            jono.lisaa(s.charAt(i));
        }
        return jono;
    }
    
    private void tulostaAutomaatti(Tila nfa) {
        if (nfa == null) {
            return;
        }
        if (tulostetut.add(nfa)) { // estetään silmukat
            automaatti += nfa.toString() + '\n';
            tulostaAutomaatti(nfa.getUlos1());
            tulostaAutomaatti(nfa.getUlos2());
        }
    }
    
    private void alustaMuuttujat() {
        luoja = new Automaatinluoja();
        tulostetut = new HashSet<>();
        automaatti = "";
    }
}
