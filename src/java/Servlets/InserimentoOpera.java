package Servlets;

import System.Libro;
import Util.Database;
import Util.DataUtil;
import static Util.DataUtil.crypt;
import Util.SecurityLayer;
import Util.FreeMarker;
import System.Utente;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Stefano Cortellessa
 */
public class InserimentoOpera extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String UPLOAD_DIRECTORY = "upload";
    private static final int THRESHOLD_SIZE = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
    Map<String, Object> data = new HashMap<String, Object>();
    String username = null;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> data = new HashMap<String, Object>();
        HttpSession s = SecurityLayer.checkSession(request);
        List<Libro> opere = null;
        
        if (s != null) {
            
            String user = ((String) s.getAttribute("username"));
            username = user;
            System.out.println("USER: " + username);
            
            FreeMarker.process("inserimentoopera.html", data, response, getServletContext());
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

        String title = null;
        if (!isNull(request.getParameter("inserisci"))) {

            try {
                String titolo = request.getParameter("titolo");
                String autore = request.getParameter("autore");
                String descrizione = request.getParameter("descrizione");
                
                
                /****************************************************
                System.out.println("titolo: " + titolo);
                System.out.println("autore: " + autore);
                System.out.println("descrizione: " + descrizione);
                *****************************************************/
                
                data.put("titolo", titolo);
                data.put("autore", autore);
                data.put("descrizione", descrizione);
                
                title = titolo;
                
                Database.connect();
                Database.insertRecord("pubblicazioni", data);
                Database.close();
                System.out.println(data);
                
            } catch (Exception ex) {
                Logger.getLogger(InserimentoOpera.class.getName()).log(Level.SEVERE, null, ex);
            }

            response.sendRedirect("ListaOpere");
        }

    }
}
