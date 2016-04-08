package sovelluslogiikka;

import domain.*;
import static domain.Vakiot.*;

/**
 * Luokka luo postfix-notaatiossa olevasta säännöllisestä lausekkeesta
 * epädeterministisen äärellisen automaatin
 */
public class Automaatinluoja {

    private Pino<Tila> tilapino; // pinoon muodostuu automaatti

    /**
     * Metodi luo säännöllisestä lausekkeesta epädeterministisen äärellisen
     * automaatin (nfa), joka koostuu toisiinsa linkitetyistä tiloista
     *
     * @param lauseke Postfix-notaatiossa oleva säännöllinen lauseke
     *
     * @return Palauttaa viitteen äärellisen automaatin alkutilaan
     */
    public Tila luoAutomaatti(Jono<Character> lauseke) {
        tilapino = new Pino<>();

        while (!lauseke.onkoTyhja()) {
            char c = lauseke.poista();
            switch (c) {
                case '\\':
                    hoidaMerkki(lauseke.poista());
                    break;
                case '.':
                    hoidaPiste();
                    break;
                case '|':
                    hoidaTai();
                    break;
                case '*':
                    hoidaTahti();
                    break;
                case '?':
                    hoidaMerkki(KYSYMYS);
                    break;
                default: // tavallinen merkki
                    hoidaMerkki(c);
                    break;
            }
        }

        return tilapino.poista();
    }

    private void hoidaMerkki(char merkki) {
        Tila t2 = new Tila(TYHJA, true, null, null);
        Tila t1 = new Tila(merkki, false, t2, null);
        tilapino.lisaa(t1);
    }

    private void hoidaPiste() {
        Tila t2 = tilapino.poista();
        Tila t1 = tilapino.poista();

        Tila temp = etsiLoppu(t1);
        temp.setUlos1(t2);
        temp.setLopputila(false);
        tilapino.lisaa(t1);
    }

    private void hoidaTai() {
        Tila t2 = tilapino.poista();
        Tila t1 = tilapino.poista();
        Tila alku = new Tila(TYHJA, false, t1, t2);
        Tila loppu = new Tila(TYHJA, true, null, null);

        Tila temp = etsiLoppu(t1);
        temp.setUlos1(loppu);
        temp.setLopputila(false);

        temp = etsiLoppu(t2);
        temp.setUlos1(loppu);
        temp.setLopputila(false);

        tilapino.lisaa(alku);
    }

    private void hoidaTahti() {
        Tila t1 = tilapino.poista();
        Tila temp = etsiLoppu(t1);
        temp.setLopputila(false);
        temp.setUlos2(t1);

        Tila loppu = new Tila(TYHJA, true, null, null);
        temp.setUlos1(loppu);
        Tila alku = new Tila(TYHJA, false, t1, loppu);
        tilapino.lisaa(alku);
    }

    private Tila etsiLoppu(Tila t) {
        Tila temp = t;
        while (!temp.isLopputila()) {
            temp = temp.getUlos1(); // lopputilaan päästään aina Ulos1-linkeistä
        }
        return temp;
    }
}
