/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import System.Libro;
import System.Utente;
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
public class ListaOpere extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> data = new HashMap<String, Object>();
        HttpSession s = SecurityLayer.checkSession(request);
        List<Libro> opere = null;
        String username;

        if (s != null) {

            String user = ((String) s.getAttribute("username"));
            username = user;
            //System.out.println("USER: " + username);
            ArrayList<Libro> utenti = new ArrayList<Libro>();
            ArrayList<Libro> ristampe = new ArrayList<Libro>();
            ArrayList<Libro> rista = new ArrayList<Libro>();
            ArrayList<Libro> result = new ArrayList<Libro>();
            String LSearch = request.getParameter("LSearch");
            String titlee = null;
            //System.out.println("LSEARCH: " + LSearch);

            /*Ricerca Opera*/
            if (!isNull(request.getParameter("LSearch"))) {
                String Search = request.getParameter("LSearch");
                System.out.println(Search);
                Database.connect();
                String condition = " titolo LIKE '%" + Search + "%' OR autore LIKE '%" + Search + "%'";
                //System.out.println(condition);

                ResultSet record = Database.selectRecord("pubblicazioni ", condition);

                while (record.next()) {
                    String titolo = record.getString("titolo");
                    String autore = record.getString("autore");
                    int id = record.getInt("id");
                    String descrizione = record.getString("descrizione");

                    titlee = titolo;
                    Libro k = new Libro(titolo, autore, id);
                    result.add(k);

                }
                //System.out.println(search);
                data.put("search", result);
                //System.out.println("DATA: " + data.get("search"));
                //ristamp = DataUtil.searchRistampe()
                Database.close();
            }

            /*Elimina Opera*/
            if (!isNull(request.getParameter("elimina"))) {

                try {

                    int idd = Integer.parseInt(request.getParameter("id"));
                    System.out.println("IDDDDD = " + idd);

                    Database.connect();
                    Database.deleteRecord("ristampe", "id_pubblicazioni = '" + idd + "'");
                    Database.deleteRecord("pubblicazioni", "id = '" + idd + "'");
                    Database.close();

                } catch (Exception ex) {
                    Logger.getLogger(OperaSingle.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.sendRedirect("ListaOpere");
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

            /*Show dell'elenco delle opere*/
            Database.connect();
            ResultSet rs = Database.selectRecordOr("pubblicazioni", "`pubblicazioni`.`Titolo` ASC");
            utenti = new ArrayList<Libro>();
            while (rs.next()) {
                String titolo = rs.getString("titolo");
                String autore = rs.getString("autore");
                String descrizione = rs.getString("descrizione");
                int id = rs.getInt("id");
                //System.out.println("TITOLO: " + titolo);
                Libro tempLib = new Libro(titolo, autore, descrizione, id);
                //System.out.println(tempLib);
                utenti.add(tempLib);
                //System.out.println(utenti);
            }

            data.put("opere", utenti);

            /*Show dell'elenco delle ristampe associate alla pubblicazione cercata*/
            if (!isNull(request.getParameter("LSearch"))) {
                String Search = request.getParameter("LSearch");
                //System.out.println(Search);
                Database.connect();
                String condition = " titolo LIKE '%" + Search + "%' OR autore LIKE '%" + Search + "%'";
                //System.out.println(condition);

                ResultSet record = Database.selectJoinW("pubblicazioni AS p", "ristampe AS r", "p.id = r.id_pubblicazioni",
                        condition);

                while (record.next()) {
                    int id = record.getInt("identificatore");
                    String titolo = record.getString("titolo");
                    String editore = record.getString("editore");
                    int edizione = record.getInt("edizione");
                    String descrizione = record.getString("descrizione");
                    System.out.println("TITLE: " + titolo);
                    System.out.println("EDITORE: " + editore);
                    titlee = titolo;
                    Libro gg = new Libro(titolo, descrizione, id, editore, edizione);
                    ristampe.add(gg);

                }
                //System.out.println(search);
                data.put("ristampe", ristampe);
                //System.out.println("DATA: " + data.get("search"));
                //ristamp = DataUtil.searchRistampe()

            }

            /*Show dell'elenco delle ristampe*/
            rista = new ArrayList<Libro>();
            ResultSet risp = Database.selectJoin("pubblicazioni AS p", "ristampe AS r", "p.id = r.id_pubblicazioni",
                    "`p`.`titolo` ASC");

            while (risp.next()) {
                int id = risp.getInt("identificatore");
                String titolo = risp.getString("titolo");
                String editore = risp.getString("editore");
                int edizione = risp.getInt("edizione");
                String descrizione = risp.getString("descrizione");
                System.out.println("TITLE: " + titolo);
                System.out.println("EDITORE: " + editore);

                Libro ristat = new Libro(titolo, descrizione, id, editore, edizione);
                rista.add(ristat);
            }

            data.put("rista", rista);
            Database.close();

            FreeMarker.process("listaopere.html", data, response, getServletContext());
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
        try {
            processRequest(request, response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
