package Servlets;

import System.Libro;
import System.Utente;
import Util.DataUtil;
import Util.Database;
import Util.FreeMarker;
import Util.SecurityLayer;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Objects.isNull;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Stefano Cortellessa
 */
public class Promuovere extends HttpServlet {

    Map data = new HashMap();

    private void action_error(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //assumiamo che l'eccezione sia passata tramite gli attributi della request
        //we assume that the exception has been passed using the request attributes
        Exception exception = (Exception) request.getAttribute("exception");
        String message;
        if (exception != null && exception.getMessage() != null) {
            message = exception.getMessage();
        } else {
            message = "Unknown error";
        }
        data.put("errore", message);
        FreeMarker.process("404page.html", data, response, getServletContext());

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession s = SecurityLayer.checkSession(request);
        List<Utente> utenti = null;

        if (s != null) {

            ArrayList<Utente> user = null;
            Map<String, Object> data = new HashMap<String, Object>();
            String w = null;
            
            String LSearchBO = request.getParameter("LSearchBO");

            /*Ricerca nel Backoffice*/
            if (!isNull(LSearchBO)) {
                try {

                    Database.connect();
                    user = DataUtil.searchBO(LSearchBO);
                    System.out.println(user);

                    Database.close();

                    data.put("ricerca", user);

                } catch (Exception ex) {
                    Logger.getLogger(Promuovere.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            /*Show dell'elenco nomi utenti registrati*/
            utenti = new ArrayList<Utente>();

            try {
                Database.connect();
                ResultSet rs = Database.selectRecordOr("utenti", "`utenti`.`cognome` DESC");

                utenti = new ArrayList<Utente>();
                while (rs.next()) {
                    String email = rs.getString("email");
                    String nome = rs.getString("nome");
                    String cognome = rs.getString("cognome");
                    int id = rs.getInt("id");
                    System.out.println("ID: " + id);
                    //System.out.println(nome);
                    //System.out.println(cognome);
                    Utente tempLib = new Utente(nome, cognome, email);
                    System.out.println(tempLib);
                    utenti.add(tempLib);
                    System.out.println(utenti);
                }
                Database.close();

            } catch (NamingException e) {
            } catch (SQLException e) {
            } catch (Exception ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }

            data.put("utenti", utenti);

            
            /*Promozione di un utente ad Attivo*/
            if (!isNull(request.getParameter("attivo"))) {

                
                String email = request.getParameter("email");
                ///System.out.println("EMAIL: " + email);
                
                Database.connect();
                
                Map<String, Object> temp = new HashMap<String, Object>();
                temp.put("gruppo", '1');   //Come da accordo con DB
                Database.updateRecord("utenti", temp, "email = '" + email + "'");
                
                Database.close();
            }
            
            /*Promozione di un utente a Passivo*/
            if (!isNull(request.getParameter("passivo"))) {

                String email = request.getParameter("email");
                ///System.out.println("EMAIL: " + email);
                Database.connect();
                
                Map<String, Object> temp = new HashMap<String, Object>();
                temp.put("gruppo", '2');   //Come da accordo con DB
                Database.updateRecord("utenti", temp, "email = '" + email + "'");
                
                Database.close();
            }
            
            /*Eliminare un Utente*/
            if (!isNull(request.getParameter("elimina"))) {

                String email = request.getParameter("email");
                ///System.out.println("EMAIL: " + email);
                Database.connect();
                
                Map<String, Object> temp = new HashMap<String, Object>();
                Database.deleteRecord("utenti", "email = '" + email + "'");
                
                Database.close();
                response.sendRedirect("Promuovere");
            }

            FreeMarker.process("promuovereutente.html", data, response, getServletContext());
        } else {
            FreeMarker.process("login.html", data, response, getServletContext());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            processRequest(request, response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
}
