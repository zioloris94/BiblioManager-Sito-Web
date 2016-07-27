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
public class PaginaPersonale extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> data = new HashMap<String, Object>();
        HttpSession s = SecurityLayer.checkSession(request);

        if (s != null) {

            int group = DataUtil.getGroup((String) s.getAttribute("username"));
            data.put("group", group);
            
            String nome = DataUtil.getEmail((String) s.getAttribute("username"));
            data.put("nome", nome);

            String user = ((String) s.getAttribute("username"));
            data.put("user", user);
            String email = request.getParameter("username");
            System.out.println("USER: " + email);

            List<Libro> lib = null;

            /*Ordinamento alfabetico crescente DEFAULT*/
            try {
                Database.connect();
                ResultSet rst = Database.selectJoinW("pubblicazioni AS p", "ristampe AS r", "p.id = r.id_pubblicazioni",
                        "inserita = '" + email + "'");

                lib = new ArrayList<Libro>();

                while (rst.next()) {
                    
                    int id = rst.getInt("identificatore");
                    String titolo = rst.getString("titolo");
                    String editore = rst.getString("editore");
                    String descrizione = rst.getString("descrizione");
                    int edizione = rst.getInt("edizione");
                    String path = rst.getString("path");
                    //System.out.println("ID: " + id);
                    //System.out.println("TITOLO: " + titolo);
                    //System.out.println("DESCRIZIONE: " + descrizione);
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

            data.put("operepersonali", lib);

            FreeMarker.process("paginapersonale.html", data, response, getServletContext());
        } else {
            FreeMarker.process("login.html", data, response, getServletContext());
        }
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
