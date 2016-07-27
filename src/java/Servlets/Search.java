package Servlets;

import System.Libro;
import System.Recensione;
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
public class Search extends HttpServlet {

    Map data = new HashMap();
    int start = 0;
    int max = 0;

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
        Map<String, Object> data = new HashMap<String, Object>();
        
        if (s != null) {
            
            int group = DataUtil.getGroup((String) s.getAttribute("username"));
            data.put("group", group);
            
            String nome = DataUtil.getEmail((String) s.getAttribute("username"));
            
            ArrayList<Libro> search = null;
            String w = null;
            String LSearch = request.getParameter("LSearch");
            data.put("nome", nome);
            String user = ((String) s.getAttribute("username"));
            data.put("user", user);

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
            
            
            Database.connect();
            search = DataUtil.search(LSearch);
            int count = Database.countRecord("ristampe", "identificatore");
            Database.close();
            System.out.println("COUNTTTT: " + search);
            
            data.put("count", count);
            data.put("search", search);
            data.put("start", start);
            data.put("max", max);
            
            FreeMarker.process("operamultiple.html", data, response, getServletContext());
        } else {
            FreeMarker.process("login.html", data, response, getServletContext());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
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
