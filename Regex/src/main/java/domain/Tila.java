/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author mikkomaa
 */
public class Tila {

    private char merkki;
    private boolean lopputila;
    private Tila ulos1;
    private Tila ulos2;

    public Tila(char merkki, boolean lopputila, Tila ulos1, Tila ulos2) {
        this.merkki = merkki;
        this.lopputila = lopputila;
        this.ulos1 = ulos1;
        this.ulos2 = ulos2;
    }

    public char getMerkki() {
        return merkki;
    }

    public void setMerkki(char merkki) {
        this.merkki = merkki;
    }

    public boolean isLopputila() {
        return lopputila;
    }

    public void setLopputila(boolean lopputila) {
        this.lopputila = lopputila;
    }

    public Tila getUlos1() {
        return ulos1;
    }

    public void setUlos1(Tila ulos1) {
        this.ulos1 = ulos1;
    }

    public Tila getUlos2() {
        return ulos2;
    }

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
