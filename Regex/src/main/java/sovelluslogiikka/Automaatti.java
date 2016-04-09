package sovelluslogiikka;

import domain.*;
import static domain.Vakiot.KYSYMYS;
import static domain.Vakiot.TYHJA;

/**
 * Luokka tarjoaa palvelun, jolla valmiin automaatin läpi voi ajaa merkkijonoja
 */
public class Automaatti {

    private final Tila nfa;
    // Aktiivitilat ovat tiloja, jotka voivat edetä hyväksyvään tilaan
    private Pino<Tila> aktiivitilat;
    private boolean hyvaksy;

    /**
     * Luokan konstruktori
     *
     * @param nfa Valmis automaatti (eli viite automaatin alkutilaan)
     */
    public Automaatti(Tila nfa) {
        this.nfa = nfa;
    }

    /**
     * Metodi ajaa merkkijonon automaatin läpi merkki kerrallaan
     *
     * @param mjono Ajettava merkkijono
     *
     * @return Palauttaa true, jos automaatti hyväksyy merkkijonon, muuten false
     */
    public boolean suorita(String mjono) {
        aktiivitilat = new Pino<>();
        aktiivitilat.lisaa(nfa); // alkutila pinoon
        hyvaksy = false;

        for (int i = 0; i < mjono.length(); i++) {
            char c = mjono.charAt(i);
            paivitaTila(c);

            if (hyvaksy == true) {
                return true;
            }
        }
        return false;
    }

    // Metodi päivittää automaatin tilan
    private void paivitaTila(char c) {
        Pino<Tila> uudet = new Pino<>(); // uudet aktiivitilat
        uudet.lisaa(nfa); // automaatin alkutila on aina aktiivinen

        while (!aktiivitilat.onkoTyhja()) {
            etene(aktiivitilat.poista(), c, true, uudet);
        }
        aktiivitilat = uudet;
    }

    // Rekursiivinen metodi, joka toteaa, onko parametrina saatu alku hyväksyvä
    // tila. Jos ei ole ja merkki täsmää, etenee seuraavaan merkkitilaan
    private void etene(Tila alku, char merkki, boolean jatka, Pino<Tila> uudet) {
        if (alku == null) {
            ; // rekursio tuli null-linkkiin
        } else if (alku.isLopputila()) {
            hyvaksy = true; // automaatti hyväksyy merkkijonon
        } else if (alku.getMerkki() == TYHJA) { // ollaan "välitilassa"
            etene(alku.getUlos1(), merkki, jatka, uudet);
            etene(alku.getUlos2(), merkki, jatka, uudet);
        } else if (jatka == true && alku.getMerkki() != merkki
                && alku.getMerkki() != KYSYMYS) {
            ; // polku päätyi umpikujaan väärän merkin vuoksi
        } else if (jatka == true) { // merkki täsmää tilan merkkiin
            etene(alku.getUlos1(), merkki, false, uudet);
            // merkkitilan 2. linkissä on aina null, ei tarvita rekursiokutsua
        } else { // päästiin seuraavaan tilaan, jossa on merkki
            uudet.lisaa(alku); // asetetaan tila aktiiviseksi
        }
    }

}
