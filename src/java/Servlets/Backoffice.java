package Servlets;

import System.Libro;
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
public class Backoffice extends HttpServlet {

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
        Map<String, Object> data = new HashMap<String, Object>();
        if (s != null) {

            /*contare il numero di recensioni nel DB che non sono state approvate*/
            Database.connect();
            int recensioni = 0;
            ResultSet rs = Database.selectRecord("recensioni", "approvato = 0");

            while (rs.next()) {
                recensioni++;
            }
            System.out.println("NUMERO RECENSIONI: " + recensioni);
            Database.close();

            data.put("recensioni", recensioni);

            
            /*contare il numero di opere totali nel DB*/
            Database.connect();
            int opere= 0;
            ResultSet op = Database.selectRecord("pubblicazioni", "id");

            while (op.next()) {
                opere++;
            }
            System.out.println("NUMERO OPERE: " + opere);
            Database.close();

            data.put("opere", opere);
            
            
            /*contare il numero di utenti attivi nel sistema*/
            Database.connect();
            int attivi= 0;
            ResultSet at = Database.selectRecord("utenti", "gruppo = 1");

            while (at.next()) {
                attivi++;
            }
            System.out.println("UTENTI ATTIVI: " + attivi);
            Database.close();

            data.put("attivi", attivi);
            
            /*contare il numero di utenti passivi nel sistema*/
            Database.connect();
            int passivi= 0;
            ResultSet ps = Database.selectRecord("utenti", "gruppo = 2");

            while (ps.next()) {
                passivi++;
            }
            System.out.println("UTENTI PASSIVI: " + passivi);
            Database.close();

            data.put("passivi", passivi);
            
            
            FreeMarker.process("homebackoffice.html", data, response, getServletContext());
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
}
