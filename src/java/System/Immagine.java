/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

/**
 *
 * @author Stefano Cortellessa
 */
public class Immagine {
    
    private int id;
    private String img;
    
    public Immagine(int id, String img){
        this.id = id;
        this.img = img;
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getImg(){
        return img;
    }
    public void setId(String img){
        this.img = img;
    }
}
