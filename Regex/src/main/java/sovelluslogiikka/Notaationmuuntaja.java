/*
 * Pakkaus sisältää ohjelman keskeiset sovelluslogiikkaluokat
 */
package sovelluslogiikka;

import domain.*;

/**
 * Luokka muuntaa infix-notaatiossa olevan, syntaksin mukaisen lausekkeen
 * postfix-muotoon
 */
public class Notaationmuuntaja {

    private Jono<Character> lauseke;

    /**
     * Luokan konstruktori
     *
     * @param infix Infix-notaatiossa oleva säännöllinen lauseke
     */
    public Notaationmuuntaja(final Jono<Character> infix) {
        this.lauseke = infix.luoKopio();
    }

    /**
     * Metodi muuntaa lausekkeen postfix-muotoon, josta voidaan luoda automaatti
     *
     * @return Palauttaa muunnetun lausekkeen
     */
    public Jono<Character> muunna() {
        poistaHakasulut();
        lisaaPisteet();
        muutaPostfixiin();
        return lauseke;
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

    /**
     * Metodi muuntaa lausekkeen hakasulut tavallisiksi suluiksi
     * 
     * @return Palauttaa lausekkeen ilman hakasulkuja
     */
    public Jono<Character> poistaHakasulut() {
        Jono uusi = new Jono<>();

        while (!lauseke.onkoTyhja()) {
            char c = lauseke.poista();
            switch (c) {
                case '\\':
                    uusi.lisaa(c);
                    uusi.lisaa(lauseke.poista());
                    break;
                case '[':
                    muutaNormaalisuluiksi(uusi);
                    break;
                default:
                    uusi.lisaa(c);
                    break;
            }
        }

        lauseke = uusi;
        return lauseke;
    }

    private void muutaNormaalisuluiksi(Jono<Character> uusi) {
        char alku = lauseke.poista();
        lauseke.poista(); // väliviiva
        char loppu = lauseke.poista();
        lauseke.poista(); // ']'

        uusi.lisaa('(');
        while (alku < loppu) {
            uusi.lisaa(alku++);
            uusi.lisaa('|');
        }
        uusi.lisaa(loppu);
        uusi.lisaa(')');
    }

    @Override
    public String toString() {
        return lauseke.toString();
    }

}
