package domain;

/**
 * Luokka on rajapinta dynaamisesti kasvavalle tietorakenteelle
 *
 * @param <T> Säilöttävien alkioiden tyyppi
 */
public interface Sailio<T> {

    /**
     * Metodi lisää alkion säiliöön
     *
     * @param t Lisättävä alkio
     */
    void lisaa(T t);

    /**
     * Metodi poistaa alkion säiliöstä ja palauttaa sen
     *
     * @return Palauttaa poistettavan alkion tai, jos säiliö on tyhjä, arvon
     * null
     */
    T poista();

    /**
     * Metodi palauttaa poistovuorossa olevan alkion poistamatta sitä säiliöstä
     *
     * @return Palauttaa seuraavana poistovuorossa olevan alkion tai, jos pino
     * on tyhjä, arvon null
     */
    public T kurkista();

    /**
     * Metodi tarkistaa, onko säiliö tyhjä
     *
     * @return Palauttaa true, jos säiliö on tyhjä, muuten false
     */
    boolean onkoTyhja();

}
