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
public class InserimentoRistampa extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String UPLOAD_DIRECTORY = "upload";
    private static final int THRESHOLD_SIZE = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
    Map<String, Object> data = new HashMap<String, Object>();
    String username = null;
    int idd = 0;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> data = new HashMap<String, Object>();
        HttpSession s = SecurityLayer.checkSession(request);
        List<Libro> opere = null;
        
        if (s != null) {
            
            String user = ((String) s.getAttribute("username"));
            username = user;
            System.out.println("USER: " + username);
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("IDDDDD: " + id);
            idd = id;
            FreeMarker.process("inserimentoristampa.html", data, response, getServletContext());
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

        if (!isNull(request.getParameter("inserisci"))) {
            
            try {
                String editore = request.getParameter("editore");
                String indice = request.getParameter("indice");
                String uri = request.getParameter("uri");
                String isbn = request.getParameter("isbn");
                String lingua = request.getParameter("lingua");
                String edizione = request.getParameter("edizione");
                int npagina = Integer.parseInt(request.getParameter("npagina"));
                
                
                /****************************************************
                System.out.println("inserita: " + username);
                System.out.println("editore: " + editore);
                System.out.println("indice: " + indice);
                System.out.println("tipo: " + tipo);
                System.out.println("uri: " + uri);
                System.out.println("formato: " + formato);
                System.out.println("isbn: " + isbn);
                System.out.println("lingua: " + lingua);
                System.out.println("numeroPagine: " + npagina);
                *****************************************************/
                
                data.put("id_pubblicazioni", idd);
                data.put("inserita", username);
                data.put("modificata", "");
                data.put("editore", editore);
                data.put("indice", indice);
                data.put("uri", uri);
                data.put("isbn", isbn);
                data.put("lingua", lingua);
                data.put("edizione", edizione);
                data.put("numeroPagina", npagina);
                data.put("consigliato", 0);
                data.put("path","");
                
                Database.connect();
                Database.insertRecord("ristampe", data);
                Database.close();
                System.out.println(data);
                
            } catch (Exception ex) {
                Logger.getLogger(InserimentoOpera.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("ListaOpere");
        }

    }
}
