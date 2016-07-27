package System;

/**
 *
 * @author Stefano Cortellessa
 */
public class Utente {
    
    public String nome;
    public String cognome;
    public String email;
    public int gruppo;
    public int modifiche;
    int id;

    public Utente(String email, String nome, String cognome, int gruppo, int modifiche) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.gruppo = gruppo;
        this.modifiche = modifiche;
    }
    
    public Utente(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }
    
    public Utente(String nome, String cognome, int modifiche) {
        this.nome = nome;
        this.cognome = cognome;
        this.modifiche = modifiche;
    }
    
    public Utente(String email, String nome, String cognome, int gruppo){
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.gruppo = gruppo;
    }

    public Utente() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String ermail) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getGruppo() {
        return gruppo;
    }

    public void setGruppo(int gruppo) {
        this.gruppo = gruppo;
    }
    
    public int getModifica(int modifiche) {
        return modifiche;
    }
    
    public void setModifica(int modifiche) {
        this.modifiche = modifiche;
    }

}
