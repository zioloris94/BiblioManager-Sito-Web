package System;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Stefano Cortellessa
 */
public class Libro {

    private int identificatore;
    private int edizione;
    private String titolo;
    private String autore;
    private String descrizione;
    private String editore;
    private String lingua;
    private String data;
    private String path;

    public Libro(int identificatore, String titolo, String descrizione, String editore, String lingua, String data) {

        this.identificatore = identificatore;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.editore = editore;
        this.lingua = lingua;
        this.data = data;
    }

    public Libro(String titolo, String descrizione, int identificatore, String editore, int edizione) {

        this.identificatore = identificatore;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.editore = editore;
        this.edizione = edizione;
    }

    public Libro(String titolo, String descrizione, int identificatore) {

        this.identificatore = identificatore;
        this.titolo = titolo;
        this.descrizione = descrizione;
    }
    
    public Libro(String titolo, String autore, String editore, int identificatore) {

        this.identificatore = identificatore;
        this.titolo = titolo;
        this.autore = autore;
        this.editore = editore;
    }
    
    public Libro(String titolo, String descrizione, int identificatore, String editore, int edizione, String path) {

        this.identificatore = identificatore;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.editore = editore;
        this.edizione = edizione;
        this.path = path;
    }

    public Libro(String titolo, String descrizione) {

        this.titolo = titolo;
        this.descrizione = descrizione;
    }

    public Libro(String titolo, int identificatore) {

        this.titolo = titolo;
        this.identificatore = identificatore;
    }


    public int getIdentificatore() {
        return identificatore;
    }

    public void setIdentificatore(int identificatore) {
        this.identificatore = identificatore;
    }
    
    public int getEdizione() {
        return edizione;
    }
    
    public void setEdizione(int edizione) {
        this.edizione = edizione;
    }

    public String getTitolo() {
        return titolo;
    }
    
    public String getPath() {
        return path;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }
    
    public void setAutore(String autore) {
        this.autore = autore;
    }
    
    public String getEditore() {
        return editore;
    }

    public void setEditore(String editore) {
        this.editore = editore;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getData() {
        return this.data;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

}
