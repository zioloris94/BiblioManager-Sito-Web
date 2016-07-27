package Servlets;

import System.Libro;
import Util.DataUtil;
import Util.Database;
import Util.FreeMarker;
import Util.SecurityLayer;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class OperaMultiple extends HttpServlet {

    Map<String, Object> data = new HashMap<String, Object>();
    int start = 0;
    int max = 8;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession s = SecurityLayer.checkSession(request);

        if (s != null) {

            int group = DataUtil.getGroup((String) s.getAttribute("username"));
            data.put("group", group);

            String nome = DataUtil.getEmail((String) s.getAttribute("username"));
            data.put("nome", nome);
            String user = ((String) s.getAttribute("username"));
            data.put("user", user);

            List<Libro> lib = null;

            /*Pulsante Indietro*/
            if (!isNull(request.getParameter("avanti"))) {
                System.out.println("AVANTI");

                start += 8;
                max = 8;
            }
            
            /*Pulsante Avanti*/
            if (!isNull(request.getParameter("indietro"))) {
                System.out.println("INDIETRO");

                start -= 8;
                max = 8 ; 
            }
            
            /*Ordinamento per Data di Pubblicazione Decrescente*/
            if (!isNull(request.getParameter("data"))) {

                try {
                    Database.connect();
                    ResultSet rst = Database.selectJoinL("pubblicazioni AS p", "ristampe AS r", "p.id = r.id_pubblicazioni",
                            "`r`.`Data` DESC", start, max);

                    lib = new ArrayList<Libro>();

                    while (rst.next()) {
                        //System.out.println("DENTRO ");
                        int id = rst.getInt("identificatore");
                        String titolo = rst.getString("titolo");
                        String editore = rst.getString("editore");
                        String descrizione = rst.getString("descrizione");
                        String path = rst.getString("path");
                        int edizione = rst.getInt("edizione");
                        //System.out.println("ID: " + id);
                        //System.out.println("TITOLO: " + titolo);
                        //System.out.println("DESCRIZIONE: " + descrizione);
                        System.out.println("DESCRIZIONE: " + path);

                        Libro tt = new Libro(titolo, descrizione, id, editore, edizione, path);
                        //System.out.println(tempLib);
                        lib.add(tt);
                    }
                    Database.close();

                } catch (NamingException e) {
                } catch (SQLException e) {
                } catch (Exception ex) {
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                }

                data.put("libri", lib);
                //System.out.println(lib);
                FreeMarker.process("operamultiple.html", data, response, getServletContext());
            }

            /*Ordinamento per Autore Crescente*/
            if (!isNull(request.getParameter("autore"))) {

                try {
                    Database.connect();
                    ResultSet rst = Database.selectJoinL("pubblicazioni AS p", "ristampe AS r", "p.id = r.id_pubblicazioni",
                            "`p`.`autore` ASC", start, max);

                    lib = new ArrayList<Libro>();

                    while (rst.next()) {
                        System.out.println("DENTRO ");
                        int id = rst.getInt("identificatore");
                        String titolo = rst.getString("titolo");
                        String editore = rst.getString("editore");
                        String descrizione = rst.getString("descrizione");
                        int edizione = rst.getInt("edizione");
                        //System.out.println("ID: " + id);
                        //System.out.println("TITOLO: " + titolo);
                        //System.out.println("DESCRIZIONE: " + descrizione);
                        String path = rst.getString("path");
                        Libro tt = new Libro(titolo, descrizione, id, editore, edizione, path);
                        lib.add(tt);
                    }
                    Database.close();

                } catch (NamingException e) {
                } catch (SQLException e) {
                } catch (Exception ex) {
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                }

                data.put("libri", lib);
                //System.out.println(lib);
                FreeMarker.process("operamultiple.html", data, response, getServletContext());
            }

            /*Ordinamento per editore Crescente*/
            if (!isNull(request.getParameter("editore"))) {

                try {
                    Database.connect();
                    ResultSet rst = Database.selectJoinL("pubblicazioni AS p", "ristampe AS r", "p.id = r.id_pubblicazioni",
                            "`r`.`Editore` ASC", start, max);

                    lib = new ArrayList<Libro>();

                    while (rst.next()) {
                        System.out.println("DENTRO ");
                        int id = rst.getInt("identificatore");
                        String titolo = rst.getString("titolo");
                        String editore = rst.getString("editore");
                        String descrizione = rst.getString("descrizione");
                        int edizione = rst.getInt("edizione");
                        //System.out.println("ID: " + id);
                        //System.out.println("TITOLO: " + titolo);
                        //System.out.println("DESCRIZIONE: " + descrizione);
                        String path = rst.getString("path");
                        Libro tt = new Libro(titolo, descrizione, id, editore, edizione, path);
                        lib.add(tt);
                    }
                    Database.close();

                } catch (NamingException e) {
                } catch (SQLException e) {
                } catch (Exception ex) {
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                }

                data.put("libri", lib);
                //System.out.println(lib);
                FreeMarker.process("operamultiple.html", data, response, getServletContext());
            }

            /*Ordinamento per isbn Decrescente*/
            if (!isNull(request.getParameter("consigliato"))) {

                try {
                    Database.connect();
                    ResultSet rst = Database.selectJoinL("pubblicazioni AS p", "ristampe AS r", "p.id = r.id_pubblicazioni",
                            "`r`.`Consigliato` DESC", start, max);

                    lib = new ArrayList<Libro>();

                    while (rst.next()) {
                        System.out.println("DENTRO ");
                        int id = rst.getInt("identificatore");
                        String titolo = rst.getString("titolo");
                        String editore = rst.getString("editore");
                        String descrizione = rst.getString("descrizione");
                        int edizione = rst.getInt("edizione");
                        //System.out.println("ID: " + id);
                        //System.out.println("TITOLO: " + titolo);
                        //System.out.println("DESCRIZIONE: " + descrizione);
                        String path = rst.getString("path");
                        Libro tt = new Libro(titolo, descrizione, id, editore, edizione, path);
                        lib.add(tt);
                    }
                    Database.close();

                } catch (NamingException e) {
                } catch (SQLException e) {
                } catch (Exception ex) {
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                }

                data.put("libri", lib);
                //System.out.println(lib);
                FreeMarker.process("operamultiple.html", data, response, getServletContext());
            }

            /*Ordinamento per numero di pagine Crescente*/
            if (!isNull(request.getParameter("numeropagine"))) {

                try {
                    Database.connect();
                    ResultSet rst = Database.selectJoinL("pubblicazioni AS p", "ristampe AS r", "p.id = r.id_pubblicazioni",
                            "`r`.`NumeroPagina` ASC", start, max);

                    lib = new ArrayList<Libro>();

                    while (rst.next()) {
                        System.out.println("DENTRO ");
                        int id = rst.getInt("identificatore");
                        String titolo = rst.getString("titolo");
                        String editore = rst.getString("editore");
                        String descrizione = rst.getString("descrizione");
                        int edizione = rst.getInt("edizione");
                        //System.out.println("ID: " + id);
                        //System.out.println("TITOLO: " + titolo);
                        //System.out.println("DESCRIZIONE: " + descrizione);
                        String path = rst.getString("path");
                        Libro tt = new Libro(titolo, descrizione, id, editore, edizione, path);
                        lib.add(tt);
                    }
                    Database.close();

                } catch (NamingException e) {
                } catch (SQLException e) {
                } catch (Exception ex) {
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                }

                data.put("libri", lib);
                //System.out.println(lib);
                FreeMarker.process("operamultiple.html", data, response, getServletContext());
            }
            
            /*Ordinamento alfabetico crescente DEFAULT*/
            try {
                Database.connect();
                ResultSet rst = Database.selectJoinL("pubblicazioni AS p", "ristampe AS r", "p.id = r.id_pubblicazioni",
                        "`p`.`Titolo` ASC", start, max);

                lib = new ArrayList<Libro>();

                while (rst.next()) {

                    int id = rst.getInt("identificatore");
                    String titolo = rst.getString("titolo");
                    String editore = rst.getString("editore");
                    String descrizione = rst.getString("descrizione");
                    int edizione = rst.getInt("edizione");
                    //System.out.println("ID: " + id);
                    //System.out.println("TITOLO: " + titolo);
                    //System.out.println("DESCRIZIONE: " + descrizione);
                    String path = rst.getString("path");
                    Libro tt = new Libro(titolo, descrizione, id, editore, edizione, path);
                    lib.add(tt);
                }
                

            } catch (NamingException e) {
            } catch (SQLException e) {
            } catch (Exception ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }

            int count = Database.countRecord("ristampe", "identificatore");
            Database.close();
            System.out.println("COUNTTTT: " + count);
            
            data.put("count", count);
            data.put("start", start);
            data.put("max", max);
            data.put("libri", lib);

            FreeMarker.process("operamultiple.html", data, response, getServletContext());
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

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Descrizione OperaMultiple";
    }
}
