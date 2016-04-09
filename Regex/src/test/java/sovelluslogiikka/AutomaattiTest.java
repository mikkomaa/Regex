package sovelluslogiikka;

import domain.Jono;
import domain.Tila;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AutomaattiTest {

    String teksti = "Jukolan talo, eteläisessä Hämeessä, seisoo erään mäen pohjaisella "
            + "rinteellä, liki Toukolan kylää. Sen läheisin ympäristö on kivinen "
            + "tanner, mutta alempana alkaa pellot, joissa, ennenkuin talo oli häviöön "
            + "mennyt, aaltoili teräinen vilja. Peltojen alla on niittu, apilaäyräinen, "
            + "halkileikkaama monipolvisen ojan; ja runsaasti antoi se heiniä, "
            + "ennenkuin joutui laitumeksi kylän karjalle. "
            + "Ja vielä perään omia merkkejä: Mitäh? Omia merkkejä?";

    String[] loytyvatLausekkeet = {"u", "ei", " erä*n", "t(u|a)lo, ", "t(a|u)lo, ",
        "täh\\?", "täh?", "Juko", "Omia mer(kk)|(xx)ejä", "Omia mer(xx)|(kk)ejä"};
    String[] eiLoytyvatLausekkeet = {"x", "pöö", "?\\?:", "ennenkuinka", "jukola", "  "};

    @Test
    public void suoritaToimiiKunLausekeLoytyy() {
        for (int i = 0; i < loytyvatLausekkeet.length; i++) {
            Tila nfa = luoUusiAutomaatti(loytyvatLausekkeet[i]);
            Automaatti automaatti = new Automaatti(nfa);
            assertTrue(automaatti.suorita(teksti));
        }
    }
    
    @Test
    public void suoritaToimiiKunLausekeEiLoydy() {
        for (int i = 0; i < eiLoytyvatLausekkeet.length; i++) {
            Tila nfa = luoUusiAutomaatti(eiLoytyvatLausekkeet[i]);
            Automaatti automaatti = new Automaatti(nfa);
            assertFalse(automaatti.suorita(teksti));
        }
    }

    private Tila luoUusiAutomaatti(String lauseke) {
        Jono<Character> jono = luoJono(lauseke);
        Notaationmuuntaja muuntaja = new Notaationmuuntaja(jono);
        muuntaja.onkoLausekeOikein();
        Jono<Character> jono2 = muuntaja.lisaaPisteet();
        jono = muuntaja.muutaPostfixiin();

        Automaatinluoja luoja = new Automaatinluoja();
        Tila nfa = luoja.luoAutomaatti(jono);

        return nfa;
    }

    private Jono<Character> luoJono(String s) {
        Jono<Character> jono = new Jono<>();
        for (int i = 0; i < s.length(); i++) {
            jono.lisaa(s.charAt(i));
        }
        return jono;
    }
}
