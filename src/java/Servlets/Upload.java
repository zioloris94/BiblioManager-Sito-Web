/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import System.Libro;
import Util.DataUtil;
import Util.Database;
import Util.FreeMarker;
import Util.SecurityLayer;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Stefano Cortellessa
 */
public class Upload extends HttpServlet {

    Map<String, Object> data = new HashMap<String, Object>();
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIRECTORY = "upload";
    private static final int THRESHOLD_SIZE = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

    int titurl = 0;

    public static String toString(int a, int b, int c) {
        return a + "-" + b + "-" + c;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // checks if the request actually contains upload file
        Map<String, Object> map = new HashMap<String, Object>();
        if (!ServletFileUpload.isMultipartContent(request)) {

            PrintWriter writer = response.getWriter();
            writer.println("Request does not contain upload data");
            writer.flush();
            return;
        }

        // Configurazione impostazioni di upload
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(THRESHOLD_SIZE);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);
        System.out.println("UPLOAD: " + upload);

        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);

        //Construisce il path della cartella dove fare l'upload del file
        String uploadPath = "C:\\Users\\Stefano Cortellessa\\Documents\\NetBeansProjects\\BiblioManager\\web\\template\\" + UPLOAD_DIRECTORY;
        System.out.println("UPLOAD PATH: " + uploadPath);

        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        System.out.println("UPLOAD DIR: " + uploadDir);

        if (!uploadDir.exists()) {
            System.out.println("DENTRO IF-EXIST");

            uploadDir.mkdir();
        }

        try {
            // parses the request's content to extract file data
            List formItems = upload.parseRequest(request);
            Iterator iter = formItems.iterator();
            System.out.println("FORM ITEMS: " + formItems);
            System.out.println("ITER: " + iter);

            // iterates over form's fields
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                System.out.println("ITEM: " + item);

                // processes only fields that are not form fields
                if (!item.isFormField()) {
                    String fileName2 = new File(item.getName()).getName();
                    System.out.println("FILENAME2: " + fileName2);

                    String fileName = item.getName();
                    System.out.println("FILENAME: " + fileName);

                    String filePath = uploadPath + File.separator + fileName;
                    System.out.println("FILEPATH: " + filePath);

                    File storeFile = new File(filePath);
                    System.out.println("STOREFILE" + storeFile);

                    map.put("path", "template/upload/" + fileName);
                    // saves the file on disk
                    item.write(storeFile);

                    System.out.println("TITOLO POST: " + titurl);

                    Database.connect();
                    Database.updateRecord("ristampe", map, "identificatore = '" + titurl + "'");
                    Database.close();

                    map.clear();
                }
            }
            request.setAttribute("message", "Upload has been done successfully!");
        } catch (Exception ex) {
            request.setAttribute("message", "There was an error: " + ex.getMessage());
        }
        response.sendRedirect("operasingle?id=" + titurl);
        //FreeMarker.process("inserimentoopera.html", data, response, getServletContext());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        HttpSession s = SecurityLayer.checkSession(request);

        if (s != null) {

            int tit = Integer.parseInt(request.getParameter("id"));
            titurl = tit;
            System.out.println("TITOLO: " + tit);

            FreeMarker.process("uploadform.html", data, response, getServletContext());
        } else {
            FreeMarker.process("login.html", data, response, getServletContext());
        }
    }
}
