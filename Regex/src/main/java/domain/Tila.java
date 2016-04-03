package domain;

/**
 * Luokka toteuttaa tilan, joista äärellinen automaatti muodostuu
 */
public class Tila {

    private char merkki;
    private boolean lopputila;
    private Tila ulos1;
    private Tila ulos2;

    /**
     * Luokan konstruktori
     * 
     * @param merkki Merkki, jota tila kuvaa
     * @param lopputila Kertoo, onko tila automaatin lopputila
     * @param ulos1 Linkki seuraavaan tilaan
     * @param ulos2 Toinen linkki seuraavaan tilaan
     */
    public Tila(char merkki, boolean lopputila, Tila ulos1, Tila ulos2) {
        this.merkki = merkki;
        this.lopputila = lopputila;
        this.ulos1 = ulos1;
        this.ulos2 = ulos2;
    }

    /**
     * Metodi kertoo tilan merkin
     * 
     * @return Palauttaa tilan merkin
     */
    public char getMerkki() {
        return merkki;
    }

    /**
     * Metodi asettaa luokan merkin
     * 
     * @param merkki Merkin uusi arvo
     */
    public void setMerkki(char merkki) {
        this.merkki = merkki;
    }

    /**
     * Metodi kertoo, onko tila automaatin lopputila
     * 
     * @return Palauttaa true, jos tila on lopputila. Muuten palauttaa false
     */
    public boolean isLopputila() {
        return lopputila;
    }

    /**
     * Metodi asettaa lopputilan arvon
     * 
     * @param lopputila Lopputilan uusi arvo
     */
    public void setLopputila(boolean lopputila) {
        this.lopputila = lopputila;
    }

    /**
     * Metodi kertoo 1. ulos-linkin arvon
     * 
     * @return Palauttaa linkin arvon
     */
    public Tila getUlos1() {
        return ulos1;
    }

    /**
     * Metodi asettaa 1. ulos-linkin arvon
     * 
     * @param ulos1 Linkin uusi arvo
     */
    public void setUlos1(Tila ulos1) {
        this.ulos1 = ulos1;
    }

    /**
     * Metodi kertoo 2. ulos-linkin arvon
     * 
     * @return Palauttaa linkin arvon
     */
    public Tila getUlos2() {
        return ulos2;
    }

    /**
     * Metodi asettaa 2. ulos-linkin arvon
     * 
     * @param ulos2 Linkin uusi arvo
     */
    public void setUlos2(Tila ulos2) {
        this.ulos2 = ulos2;
    }

    @Override
    public String toString() {
        String s = merkki == '\0' ? "''" : "" + merkki;
        s += lopputila == true ? " true" : " false";
        s += ulos1 == null ? " null" : " -->";
        s += ulos2 == null ? " null" : " -->";
        return s;
    }

}
