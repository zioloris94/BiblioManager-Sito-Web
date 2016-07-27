/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import System.Immagine;
import System.Libro;
import Util.Database;
import Util.FreeMarker;
import Util.SecurityLayer;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class ModificaRistampa extends HttpServlet {

    String username;
    int id = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> data = new HashMap<String, Object>();
        HttpSession s = SecurityLayer.checkSession(request);
        List<Libro> opere = null;

        if (s != null) {

            String user = ((String) s.getAttribute("username"));
            username = user;
            //System.out.println("USER: " + username);
            int tit = Integer.parseInt(request.getParameter("id"));
            id = tit;
            //System.out.println("IDDDD: " + tit);
            
            data.put("tit", tit);
            Database.connect();
            ResultSet rs = Database.selectRecord("ristampe", "identificatore = " + tit);

            while (rs.next()) {
                String editore = rs.getString("editore");
                String indice = rs.getString("indice");
                String uri = rs.getString("uri");
                String isbn = rs.getString("isbn");
                String lingua = rs.getString("lingua");
                String edizione = rs.getString("edizione");
                int npagina = rs.getInt("numeropagina");
                String inserita = rs.getString("inserita");
                int id_pubblicazioni = rs.getInt("id_pubblicazioni");
                int consigliato = rs.getInt("consigliato");
                String path = rs.getString("path");

                data.put("id_pubblicazioni", id_pubblicazioni);
                data.put("consigliato", consigliato);
                data.put("path", path);
                data.put("inserita", inserita);
                data.put("editore", editore);
                data.put("indice", indice);
                data.put("uri", uri);
                data.put("isbn", isbn);
                data.put("lingua", lingua);
                data.put("edizione", edizione);
                data.put("numeroPagina", npagina);
            }
            
            List<Immagine> imm = null;
            ResultSet gll = Database.selectRecord("galleria", "id_ristampa = " + tit);
            imm = new ArrayList<Immagine>();
            
            while (gll.next()) {
                        //System.out.println("DENTRO ");
                        int id = gll.getInt("chiave");
                        String img = gll.getString("img");
                        //System.out.println("ID: " + id);
                        //System.out.println("IMG: " + img);
                        
                        Immagine tt = new Immagine(id, img);
                        //System.out.println(tt);
                        imm.add(tt);
                    }
                    Database.close();
                    
                    data.put("galleria", imm);
                    

            FreeMarker.process("modificaristampa.html", data, response, getServletContext());
        } else {
            FreeMarker.process("login.html", data, response, getServletContext());
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> data = new HashMap<String, Object>();
        
        /*Modifica Ristampa*/
        if (!isNull(request.getParameter("modifica"))) {
            System.out.println("DENTRO");
            try {
                String editore = request.getParameter("editore");
                String indice = request.getParameter("indice");
                String uri = request.getParameter("uri");
                String isbn = request.getParameter("isbn");
                String lingua = request.getParameter("lingua");
                String edizione = request.getParameter("edizione");
                String inserita = request.getParameter("inserita");
                String path = request.getParameter("path");
                int npagina = Integer.parseInt(request.getParameter("npagina"));
                int id_pubblicazioni = Integer.parseInt(request.getParameter("id_pubblicazioni"));
                int consigliato = Integer.parseInt(request.getParameter("consigliato"));

                System.out.println("dopo");
                //System.out.println("cons: " + consigliato);
                //System.out.println("pub: " + id_pubblicazioni);
                //System.out.println("ins: " + inserita);
                /**
                 * **************************************************
                 * System.out.println("inserita: " + username);
                 * System.out.println("editore: " + editore);
                 * System.out.println("indice: " + indice);
                 * System.out.println("tipo: " + tipo); System.out.println("uri:
                 * " + uri); System.out.println("formato: " + formato);
                 * System.out.println("isbn: " + isbn);
                 * System.out.println("lingua: " + lingua);
                 * System.out.println("numeroPagine: " + npagina);
                 * ***************************************************
                 */

                data.put("modificata", username);
                data.put("id_pubblicazioni", id_pubblicazioni);
                data.put("consigliato", consigliato);
                data.put("inserita", inserita);
                data.put("editore", editore);
                data.put("indice", indice);
                data.put("uri", uri);
                data.put("isbn", isbn);
                data.put("lingua", lingua);
                data.put("edizione", edizione);
                data.put("numeroPagina", npagina);
                data.put("path", path);

                Database.connect();
                Database.updateRecord("ristampe", data, "identificatore = " + id);
                Database.close();
                System.out.println(data);

            } catch (Exception ex) {
                Logger.getLogger(InserimentoOpera.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("operasingle?id=" + id);
        }

        if (!isNull(request.getParameter("inserisci"))) {
            System.out.println("INSERISCI");
            response.sendRedirect("Upload?id=" + id);
        }
        
        if (!isNull(request.getParameter("insert"))) {
            System.out.println("INSERT");
            response.sendRedirect("UploadGalleria?id=" + id);
        }

        /*Elimina Ristampa*/
        if (!isNull(request.getParameter("eliminaR"))) {

            try {

                int idd = Integer.parseInt(request.getParameter("id"));
                System.out.println("IDDDDD = " + idd);

                Database.connect();
                Database.deleteRecord("consiglia", "pubblicazione = '" + idd + "'");
                Database.deleteRecord("ristampe", "identificatore = '" + idd + "'");
                Database.close();

            } catch (Exception ex) {
                Logger.getLogger(OperaSingle.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("ListaOpere");
        }
    }
}
