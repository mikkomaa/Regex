/*
 * Pakkaus sisältää ohjelman keskeiset sovelluslogiikkaluokat
 */
package sovelluslogiikka;

import domain.*;

/**
 * Luokka tarkistaa, onko säännöllinen lauseke syntaksin mukainen, ja muuntaa
 * infix-notaatiossa olevan lausekkeen postfix-muotoon
 */
public class Notaationmuuntaja {

    private Jono<Character> lauseke;
    private char[] ops = {'(', ')', '|', '*', '?'};

    /**
     * Luokan konstruktori
     *
     * @param infix Infix-notaatiossa oleva säännöllinen lauseke
     */
    public Notaationmuuntaja(Jono<Character> infix) {
        this.lauseke = infix;
    }

    /**
     * Metodi tarkistaa, onko säännöllisen lausekkeen syntaksi oikein.
     *
     * @return Palauttaa 'x', jos syntaksi on oikein. Muuten palauttaa 1.
     * merkin, jossa havaitaan virhe
     */
    public char onkoLausekeOikein() {
        int sulkuja = 0; // avointen sulkujen määrä
        char edellinen = '|'; // 'x' tarkoittaa normaalia merkkiä
        Jono<Character> tarkistettu = new Jono<>();

        while (!lauseke.onkoTyhja()) {
            char c = lauseke.poista();
            switch (c) {
                case '\\':
                    if (lauseke.onkoTyhja()) {
                        return '\\'; // lopussa yksinäinen \-merkki
                    }
                    tarkistettu.lisaa(c);
                    c = lauseke.poista();
                    edellinen = 'x';
                    break;
                case '(':
                    sulkuja++;
                    edellinen = '(';
                    break;
                case ')':
                    if (edellinen == '|' || edellinen == '(' || --sulkuja < 0) {
                        return ')';
                    }
                    edellinen = ')';
                    break;
                case '|':
                    if (edellinen == '|' || edellinen == '(' || lauseke.onkoTyhja()) {
                        return '|';
                    }
                    edellinen = '|';
                    break;
                case '*':
                    if (edellinen == '|' || edellinen == '(' || edellinen == '*') {
                        return '*';
                    }
                    edellinen = '*';
                    break;
                default:
                    // tavallinen merkki
                    edellinen = 'x';
                    break;
            }
            tarkistettu.lisaa(c);
        }

        if (sulkuja > 0) {
            return '(';
        }
        lauseke = tarkistettu;
        return 'x';
    }

    /**
     * Metodi lisää infix-notaatiossa olevaan lausekkeeseen '.'-merkit
     * katenaatioiden kohdalle
     *
     * @return Palauttaa lausekkeen '.'-merkeillä lisättynä
     */
    public Jono<Character> lisaaPisteet() {
        Jono uusi = new Jono<>();

        while (!lauseke.onkoTyhja()) {
            // kopioidaan merkki
            char c = lauseke.poista();
            switch (c) {
                case '\\':
                    uusi.lisaa(c);
                    uusi.lisaa(lauseke.poista());
                    c = 'x'; // 'x' tarkoittaa normaalia merkkiä
                    break;
                case '.':
                    // lausekkeen . ei ole katenaatio
                    uusi.lisaa('\\');
                    uusi.lisaa(c);
                    c = 'x';
                    break;
                default:
                    uusi.lisaa(c);
                    break;
            }

            // lisätään tarvittaessa piste
            Character seur = lauseke.kurkista();
            if (c != '|' && c != '('
                    && seur != null && seur != '|' && seur != '*' && seur != ')') {
                uusi.lisaa('.');
            }
        }

        lauseke = uusi;
        return lauseke;
    }

    /**
     * Metodi muuttaa infix-notaatiossa olevan lausekkeen postfix-muotoon
     * shunting-yard-algoritmilla
     *
     * @return Palauttaa lausekkeen postfix-muodossa
     */
    public Jono<Character> muutaPostfixiin() {
        Jono postfix = new Jono<>();
        Pino<Character> ops = new Pino<>(); // operaattoreille

        while (!lauseke.onkoTyhja()) {
            char c = lauseke.poista();
            switch (c) {
                case '\\':
                    postfix.lisaa(c);
                    postfix.lisaa(lauseke.poista());
                    break;
                case '(':
                    ops.lisaa(c);
                    break;
                case ')':
                    while (ops.kurkista() != '(') {
                        postfix.lisaa(ops.poista());
                    }
                    ops.poista();
                    break;
                case '|':
                    while (ops.kurkista() != null && (ops.kurkista() == '*'
                            || ops.kurkista() == '.' || ops.kurkista() == '|')) {
                        postfix.lisaa(ops.poista());
                    }
                    ops.lisaa(c);
                    break;
                case '.':
                    while (ops.kurkista() != null && (ops.kurkista() == '*' || ops.kurkista() == '.')) {
                        postfix.lisaa(ops.poista());
                    }
                    ops.lisaa(c);
                    break;
                case '*':
                    while (ops.kurkista() != null && ops.kurkista() == '*') {
                        postfix.lisaa(ops.poista());
                    }
                    ops.lisaa(c);
                    break;
                default:
                    postfix.lisaa(c);
                    break;
            }
        }

        while (!ops.onkoTyhja()) {
            postfix.lisaa(ops.poista());
        }
        lauseke = postfix;
        return lauseke;
    }

//    private boolean onkoTaulussa(char[] taulu, char c) {
//        for (int i = 0; i < taulu.length; i++) {
//            if (taulu[i] == c) {
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public String toString() {
        return lauseke.toString();
    }

}
