package System;

/**
 *
 * @author Stefano Cortellessa
 */
public class Recensione {

    private int id;
    private String titolo;
    private String testo;
    private String utente;
    private String utapprovante;

    public Recensione(int id, String titolo, String testo, String utente) {

        this.id = id;
        this.titolo = titolo;
        this.testo = testo;
        this.utente = utente;
    }

    public Recensione(String utente, String titolo, String testo, int id, String utapprovante) {

        this.utente = utente;
        this.testo = testo;
        this.id = id;
        this.utapprovante = utapprovante;
        this.titolo = titolo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getUtente() {
        return utente;
    }

    public void setUtente(String users) {
        this.utente = users;
    }
    
    public String getUtapprovante() {
        return utapprovante;
    }

}
