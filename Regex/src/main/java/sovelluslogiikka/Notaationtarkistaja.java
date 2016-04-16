package sovelluslogiikka;

import domain.Jono;

/**
 * Luokka tarkistaa, onko säännöllinen lauseke syntaksin mukainen
 */
public class Notaationtarkistaja {

    private Jono<Character> lauseke;

    /**
     * Luokan konstruktori
     *
     * @param infix Infix-notaatiossa oleva tarkistettava lauseke
     */
    public Notaationtarkistaja(final Jono<Character> infix) {
        this.lauseke = infix.luoKopio();
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

        while (!lauseke.onkoTyhja()) {
            char c = lauseke.poista();
            switch (c) {
                case '\\':
                    if (lauseke.onkoTyhja()) {
                        return '\\'; // lopussa yksinäinen \-merkki
                    }
                    lauseke.poista();
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
                case '[':
                    if (!onkoHakasulutOikein()) {
                        return '[';
                    }
                    edellinen = ']';
                    break;
                case ']':
                    return ']';
                    // break;
                default:
                    // tavallinen merkki
                    edellinen = 'x';
                    break;
            }
        }

        if (sulkuja > 0) {
            return '(';
        }
        return 'x';
    }

    // Metodi tarkistaa, onko hakasulkuoperaatio oikein
    private boolean onkoHakasulutOikein() {
        Character alku = lauseke.poista();
        Character viiva = lauseke.poista();
        Character loppu = lauseke.poista();
        Character loppusulku = lauseke.poista();

        if (!onkoValiOikein(alku, loppu) || viiva == null || viiva != '-'
                || loppusulku == null || loppusulku != ']') {
            return false;
        }
        return true;
    }

    private boolean onkoValiOikein(Character alku, Character loppu) {
        if (alku == null || loppu == null || loppu <= alku) {
            return false;
        }

        if (alku >= 'a' && alku <= 'z' && loppu <= 'z') {
            return true;
        } else if (alku >= 'A' && alku <= 'Z' && loppu <= 'Z') {
            return true;
        } else if (alku >= '0' && alku <= '9' && loppu <= '9') {
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return lauseke.toString();
    }

}
