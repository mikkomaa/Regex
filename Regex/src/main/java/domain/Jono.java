/**
 * Pakkaus sisältää tilan, jonon ja pinon toteutuksen
 */
package domain;

/**
 * Luokka toteuttaa dynaamisesti kasvavan jonon
 *
 * @param <T> Jonon alkioiden tyyppi
 */
public class Jono<T> implements Sailio<T> {

    private T[] alkiot; // taulu alkioiden tallentamiseen
    private int koko; // alkiotaulun koko
    private int alku; // jonon 1. alkion paikka taulussa
    private int loppu; // seuraavaksi tallennettavan alkion paikka taulussa

    /**
     * Luokan konstruktori, joka alustaa muuttujat
     */
    public Jono() {
        koko = 8;
        alkiot = (T[]) new Object[koko];
        alku = loppu = 0;
    }

    @Override
    public void lisaa(T t) {
        if (onkoTaysi()) {
            suurenna();
        }
        alkiot[loppu++] = t;
        if (loppu == koko) {
            loppu = 0; // jono pyörähtää taulun alkuun
        }
    }

    @Override
    public T poista() {
        if (onkoTyhja()) {
            return null;
        }
        T pois = alkiot[alku++];
        if (alku == koko) {
            alku = 0;
        }
        return pois;
    }

    @Override
    public T kurkista() {
        if (onkoTyhja()) {
            return null;
        }
        return alkiot[alku];
    }

    @Override
    public boolean onkoTyhja() {
        return alku == loppu;
    }

    // Apumetodi tarkistaa, onko jono täysi
    private boolean onkoTaysi() {
        int loppuaSeuraava = (loppu + 1 == koko ? 0 : loppu + 1);
        return alku == loppuaSeuraava;
    }

    @Override
    public String toString() {
        String s = "";
        int temp = alku;
        while (temp != loppu) {
            s += alkiot[temp++].toString();
            if (temp == koko) {
                temp = 0;
            }
        }
        return s;
    }

    // Apumetodi suurentaa alkiotaulua
    private void suurenna() {
        koko *= 2;
        T[] uusi = (T[]) new Object[koko];

        int i = 0;
        while (!onkoTyhja()) {
            uusi[i++] = poista();
        }

        alku = 0;
        loppu = i;
        alkiot = uusi;
    }

}
