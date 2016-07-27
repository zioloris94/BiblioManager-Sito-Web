/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import System.Recensione;
import Util.DataUtil;
import Util.Database;
import Util.FreeMarker;
import Util.SecurityLayer;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Objects.isNull;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Stefano Cortellessa
 */
public class OperaRecensioni extends HttpServlet {

    String username = null;
    int id = 0;
    String nomeopera = null;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> data = new HashMap<String, Object>();
        HttpSession s = SecurityLayer.checkSession(request);

        if (s != null) {
            
            int group = DataUtil.getGroup((String) s.getAttribute("username"));
            data.put("group", group);
            
            String nome = DataUtil.getEmail((String) s.getAttribute("username"));
            String titolo = request.getParameter("recensioni");
            int idd = Integer.parseInt(request.getParameter("id"));
            nomeopera = titolo;
            id = idd;
            String user = ((String) s.getAttribute("username"));
            data.put("user", user);
            username = user;
            //System.out.println("email : " + user);
            //System.out.println("TITOLO RECENSIONE: " + titolo);
            //System.out.println("ID URL: " + idd);
            
            List<Recensione> rece = null;
            List<Recensione> recensionidaapprovare = null;
            
            data.put("nome", nome);
            data.put("titolo", titolo);
            data.put("id", idd);
            
            /*Show delle recensioni approvate*/
            rece = new ArrayList<Recensione>();
            ResultSet rec = Database.selectRecord("recensioni", "id_lib = " + idd + " AND approvato = 1 ");

            while (rec.next()) {
                
                int id = rec.getInt("id");
                String tito = rec.getString("titolo");
                String users = rec.getString("utente");
                String testo = rec.getString("testo");
                String utapp = rec.getString("utapprovante");
                //System.out.println("ID: " + id);
                //System.out.println("USERS: " + users);
                //System.out.println("TESTO: " + testo);
                //System.out.println("UTENTE APPROVANTE: " + utapp);
                Recensione tempRec = new Recensione(users,tito, testo, id, utapp);
                //System.out.println(tempRec);
                rece.add(tempRec);
                //System.out.println(rece);
            }

            data.put("recensioni", rece);
            //System.out.println(Recensione.utente);
            //System.out.println(Recensione.testo);
            
            /**************************************************************/
            
            /*Show delle recensioni da approvare*/
            recensionidaapprovare = new ArrayList<Recensione>();
            ResultSet r = Database.selectRecord("recensioni", "id_lib = " + idd + " AND approvato = 0");
            //System.out.println("R: " + r);
            while (r.next()) {
                //VARIABILI DI RECENSIONE
                int idi = r.getInt("id");
                String titol = r.getString("titolo");
                String ut = r.getString("utente");
                String te = r.getString("testo");
                String utappp = r.getString("utapprovante");
                //System.out.println("ID: " + idi);
                //System.out.println("USERS: " + ut);
                //System.out.println("TESTO: " + te);
                //System.out.println("UTENTE APPROVANTE: " + utappp);
                
                if(idi != 0){
                    data.put("controllo", 1);
                } else{
                    data.put("controllo", 0);
                }
                
                Recensione Rec = new Recensione(ut, titolo, te, idi,utappp);
                //System.out.println("REC: " + Rec);
                recensionidaapprovare.add(Rec);
                //System.out.println("RECENSIONI DA APPROVARE: " + recensionidaapprovare);
            }
            Database.close();
            data.put("recensionidaapprovare", recensionidaapprovare);
            //System.out.println(Recensione.utente);
            //System.out.println(Recensione.testo);
                        
           FreeMarker.process("operarecensioni.html", data, response, getServletContext());
        } else {
            FreeMarker.process("login.html", data, response, getServletContext());
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
        /*Approvare una recensione*/
        if (!isNull(request.getParameter("pubblica"))) {

            try {
                int idd = Integer.parseInt(request.getParameter("id"));
                //System.out.println("IDD = " + idd);
                Database.connect();
                Map<String, Object> temp = new HashMap<String, Object>();
                temp.put("approvato", '1');   //Come da accordo con DB
                temp.put("utapprovante", username);
                Database.updateRecord("recensioni", temp, "id = '" + idd + "'");
                Database.close();
            } catch (Exception ex) {
                Logger.getLogger(OperaSingle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*Eliminare una recensione*/
        if (!isNull(request.getParameter("elimina"))) {

            try {

                int idd = Integer.parseInt(request.getParameter("id"));
                //System.out.println("IDD = " + idd);

                Database.connect();
                Database.deleteRecord("recensioni", "id = '" + idd + "'");
                Database.close();

            } catch (Exception ex) {
                Logger.getLogger(OperaSingle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        response.sendRedirect("operarecensioni?id=" + id +"&recensioni=" + nomeopera);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
