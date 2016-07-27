package Servlets;

import Util.FreeMarker;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Stefano Cortellessa
 */
public class Error extends HttpServlet{
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        
        Map<String, Object> data = new HashMap<>();
        
        
        HttpSession session = request.getSession(false);  
        
        // Se non è stata generata la sessione
        if(session == null){
            
            FreeMarker.process("404.html", data, response, getServletContext());
        }else{
            
            // Altrimenti vai alla pagina dell'utente loggato
            FreeMarker.process("404.html", data, response, getServletContext());
        }        
    }

    
}
