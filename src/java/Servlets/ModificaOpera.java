/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import System.Libro;
import Util.Database;
import Util.FreeMarker;
import Util.SecurityLayer;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
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
public class ModificaOpera extends HttpServlet {

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
            
            System.out.println("IDDDD: " + tit);
            Database.connect();
            ResultSet rs = Database.selectRecord("pubblicazioni", "id = " + tit);

            while (rs.next()) {
                String titolo = rs.getString("titolo");
                String autore = rs.getString("autore");
                String descrizione = rs.getString("descrizione");

                data.put("titolo", titolo);
                data.put("autore", autore);
                data.put("descrizione", descrizione);
            }

            FreeMarker.process("modificaopera.html", data, response, getServletContext());
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

        Map<String, Object> temp = new HashMap<String, Object>();
        /*Modifica Opera*/
        if (!isNull(request.getParameter("modifica"))) {
            System.out.println("titolo: ");
            try {
                String titolo = request.getParameter("titolo");
                String autore = request.getParameter("autore");
                String descrizione = request.getParameter("descrizione");

                /**
                 * **************************************************
                 * System.out.println("titolo: " + titolo);
                 * System.out.println("autore: " + autore);
                 * System.out.println("descrizione: " + descrizione);
                ****************************************************
                 */
                temp.put("titolo", titolo);
                temp.put("autore", autore);
                temp.put("descrizione", descrizione);

                Database.connect();
                Database.updateRecord("pubblicazioni", temp, "id = " + id);
                Database.close();
                System.out.println(temp);

            } catch (Exception ex) {
                Logger.getLogger(InserimentoOpera.class.getName()).log(Level.SEVERE, null, ex);
            }

            response.sendRedirect("ListaOpere");
        }
    }
}
