package domain;

/**
 * Luokka toteuttaa dynaamisesti kasvavan pinon
 *
 * @param <T> Pinon alkioiden tyyppi
 */
public class Pino<T> implements Sailio<T> {
    private T[] alkiot; // taulu alkioiden tallentamiseen
    private int koko; // alkiotaulun koko
    private int maara; // tauluun talletettujen alkioiden määrä

    /**
     * Luokan konstruktori, joka alustaa muuttujat
     */
    public Pino() {
        koko = 8;
        alkiot = (T[]) new Object[koko];
        maara = 0;
    }

    @Override
    public void lisaa(T t) {
        if (maara == koko) {
            suurenna();
        }
        alkiot[maara++] = t;
        
    }

    @Override
    public T poista() {
        if (onkoTyhja()) {
            return null;
        }
        return alkiot[--maara];
    }

    @Override
    public boolean onkoTyhja() {
        return maara == 0;
    }
    
    @Override
    public T kurkista() {
        if (onkoTyhja()) {
            return null;
        }
        return alkiot[maara - 1];
    }
    
    /**
     * Metodi luo pinosta kopion (erillisen olion)
     * 
     * @return Pinon kopio
     */
//    public Pino<T> luoKopio() {
//        Pino<T> kopio = new Pino<>();
//        for (int i = 0; i < maara; i++) {
//            kopio.lisaa(alkiot[i]);
//            
//        }
//        return kopio;
//    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < maara; i++) {
            s += alkiot[i].toString();
        }
        return s;
    }

    // Apumetodi suurentaa alkiotaulua
    private void suurenna() {
        koko *= 2;
        T[] uusi = (T[]) new Object[koko];
        
        for (int i = 0; i < maara; i++) {
            uusi[i] = alkiot[i];
        }
        alkiot = uusi;
    }
    
}
