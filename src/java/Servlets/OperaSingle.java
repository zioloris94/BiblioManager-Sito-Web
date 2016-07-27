package Servlets;

import System.Libro;
import System.Recensione;
import System.Utente;
import Util.DataUtil;
import static Util.DataUtil.crypt;
import Util.Database;
import Util.FreeMarker;
import Util.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.swing.JOptionPane;

/**
 *
 * @author Stefano Cortellessa
 */
public class OperaSingle extends HttpServlet {

    int identificatore = 0;
    String utente = null;
    String username = null;
    int userid = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> data = new HashMap<String, Object>();
        HttpSession s = SecurityLayer.checkSession(request);

        if (s != null) {

            List<Recensione> rece = null;
            List<Recensione> recensionidaapprovare = null;

            int group = DataUtil.getGroup((String) s.getAttribute("username"));
            data.put("group", group);

            int idd = Integer.parseInt(request.getParameter("id"));
            //System.out.println("IDENTIFICATORE: " + idd);
            int useri = (int) s.getAttribute("userid");
            //System.out.println("USERID: " + useri);
            String nome = DataUtil.getEmail((String) s.getAttribute("username"));
            String userna = ((String) s.getAttribute("username"));
            //System.out.println("USERNAME: " + username);

            userid = useri;
            username = userna;
            utente = nome;
            identificatore = idd;
            String Utent = null;
            String Ute = null;
            int button = 0;
            //System.out.println("ID: " + identificatore);
            data.put("user", userna);
            data.put("nome", nome);

            ResultSet buttom = Database.selectRecord("consiglia", "utente = " + userid);
            while (buttom.next()) {
                int utente = buttom.getInt("utente");
                int pub = buttom.getInt("pubblicazione");
                //System.out.println("UTENTE: " + utente);
                //System.out.println("PUB: " + pub);
                if (pub == identificatore) {
                    button = button + 1;
                }
            }
            System.out.println("BUTTON: " + button);
            data.put("button",button);

            Database.connect();
            data.put("id", idd);
            ResultSet rs = Database.selectJoinW("pubblicazioni AS p", "ristampe AS r", "p.id = r.id_pubblicazioni",
                    "identificatore = " + idd);

            while (rs.next()) {
                String titolo = rs.getString("titolo");
                String descrizione = rs.getString("descrizione");
                String editore = rs.getString("editore");
                String autore = rs.getString("autore");
                String date = rs.getString("data");
                String path = rs.getString("path");
                String inserita = rs.getString("inserita");
                String modificata = rs.getString("modificata");
                String uri = rs.getString("uri");
                String isbn = rs.getString("isbn");
                String lingua = rs.getString("lingua");
                String edizione = rs.getString("edizione");
                int npagina = rs.getInt("numeropagina");
                int consigliato = rs.getInt("consigliato");
                //System.out.println(titolo);
                //System.out.println(descrizione);
                //System.out.println(editore);

                data.put("titolo", titolo);
                data.put("uri", uri);
                data.put("isbn", isbn);
                data.put("lingua", lingua);
                data.put("edizione", edizione);
                data.put("npagina", npagina);
                data.put("editore", editore);
                data.put("autore", autore);
                data.put("descrizione", descrizione);
                data.put("data", date);
                data.put("inserita", inserita);
                data.put("modificata", modificata);
                data.put("consigliato", consigliato);
                data.put("path", path);

            }

            /*Show delle recensioni approvate*/
            rece = new ArrayList<Recensione>();
            ResultSet rec = Database.selectRecord("recensioni", "id_lib = " + idd + " AND approvato = 1 ");

            while (rec.next()) {
                //VARIABILI DI RECENSIONE
                int id = rec.getInt("id");
                String titolo = rec.getString("titolo");
                String users = rec.getString("utente");
                String testo = rec.getString("testo");
                String utapp = rec.getString("utapprovante");
                //System.out.println("ID: " + id);
                //System.out.println("USERS: " + titolo);
                //System.out.println("TESTO: " + testo);
                //System.out.println("UTENTE APPROVANTE: " + utapp);
                Recensione tempRec = new Recensione(users, titolo, testo, id, utapp);
                //System.out.println(tempRec);
                rece.add(tempRec);
                //System.out.println(rece);
                Utent = users;
            }

            data.put("recensioni", rece);
            //System.out.println(Recensione.utente);
            //System.out.println(Recensione.testo);

            /*Show delle recensioni da approvare*/
            recensionidaapprovare = new ArrayList<Recensione>();
            ResultSet r = Database.selectRecord("recensioni", "id_lib = " + idd + " AND approvato = 0");
            //System.out.println("R: " + r);

            while (r.next()) {
                //VARIABILI DI RECENSIONE
                int idi = r.getInt("id");
                String tit = r.getString("titolo");
                String ut = r.getString("utente");
                String te = r.getString("testo");
                String utappp = r.getString("utapprovante");
                //System.out.println("ID: " + idi);
                //System.out.println("USERS: " + ut);
                //System.out.println("TESTO: " + te);
                //System.out.println("UTENTE APPROVANTE: " + utappp);

                if (idi != 0) {
                    data.put("controllo", 1);
                } else {
                    data.put("controllo", 0);
                }

                Recensione Rec = new Recensione(ut, tit, te, idi, utappp);
                //System.out.println("REC: " + Rec);
                recensionidaapprovare.add(Rec);
                System.out.println("RECENSIONI DA APPROVARE: " + recensionidaapprovare);
                Ute = ut;
            }
            Database.close();

            data.put("recensionidaapprovare", recensionidaapprovare);
            //System.out.println(Recensione.utente);

            FreeMarker.process("operasingle.html", data, response, getServletContext());
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

        HttpSession s = SecurityLayer.checkSession(request);

        String title = request.getParameter("title");
        String message = request.getParameter("message");
        //System.out.println(title);
        //System.out.println(message);

        int id_libro = identificatore;
        System.out.println(id_libro);

        //NO INTEGER.PARSEINT NON LO PRENDE
        Map<String, Object> map = new HashMap<String, Object>();

        /*Approvare una recensione*/
        if (!isNull(request.getParameter("pubblica"))) {

            try {
                int idd = Integer.parseInt(request.getParameter("id"));
                //System.out.println("IDD = " + idd);
                Database.connect();
                Map<String, Object> temp = new HashMap<String, Object>();
                temp.put("approvato", '1');   //Come da accordo con DB
                temp.put("utapprovante", username);
                Database.updateRecord("recensioni", temp, "id = '" + idd + "'");
                Database.close();
            } catch (Exception ex) {
                Logger.getLogger(OperaSingle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*Eliminare una recensione*/
        if (!isNull(request.getParameter("elimina"))) {

            try {

                int idd = Integer.parseInt(request.getParameter("id"));
                //System.out.println("IDD = " + idd);

                Database.connect();
                Database.deleteRecord("recensioni", "id = '" + idd + "'");
                Database.close();

            } catch (Exception ex) {
                Logger.getLogger(OperaSingle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*Consigliare un opera*/
        if (!isNull(request.getParameter("consiglia"))) {

            try {
                //System.out.println("IDD = " + identificatore);
                Database.connect();
                ResultSet cons = Database.selectRecord("ristampe", "identificatore = " + identificatore);
                ResultSet modif = Database.selectRecord("utenti", "id = " + userid);
                //System.out.println("ID: " + cons);
                int consigliato = 0;
                int cont = 0;
                int modificato = 0;

                while (cons.next()) {

                    int id = cons.getInt("identificatore");
                    int consig = cons.getInt("consigliato");
                    //System.out.println(id);
                    //System.out.println(descrizione);
                    //System.out.println("CONSIGLIATO: " + consig);
                    consigliato = consig;
                }

                while (modif.next()) {
                    int modifiche = modif.getInt("modifiche");
                    //System.out.println("MODIFICHE: " + modifiche);
                    modificato = modifiche;
                }

                ResultSet r = Database.selectRecord("consiglia", "utente = " + userid);
                while (r.next()) {
                    int utente = r.getInt("utente");
                    int pub = r.getInt("pubblicazione");
                    //System.out.println("UTENTE: " + utente);
                    //System.out.println("PUB: " + pub);
                    //System.out.println("ID: " + identificatore);
                    if (pub == identificatore) {
                        cont = cont + 1;
                        //System.out.println("CONT SOPRA " + cont);
                    } else {
                        //System.out.println("ELSE ");
                    }

                }
                System.out.println("CONT: " + cont);

                if (cont == 0) {
                    Map<String, Object> hint = new HashMap<String, Object>();
                    hint.put("utente", userid);
                    hint.put("pubblicazione", identificatore);
                    Database.insertRecord("consiglia", hint);

                    Map<String, Object> temp = new HashMap<String, Object>();
                    temp.put("consigliato", consigliato + 1);
                    //temp.put("consigliato", )
                    Database.updateRecord("ristampe", temp, "identificatore = '" + identificatore + "'");

                    Map<String, Object> mod = new HashMap<String, Object>();
                    mod.put("modifiche", modificato + 1);
                    //temp.put("consigliato", )
                    Database.updateRecord("utenti", mod, "id = '" + userid + "'");
                } else {

                }

                Database.close();
            } catch (Exception ex) {
                Logger.getLogger(OperaSingle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (!isNull(message)) {
            try {

                Database.connect();
                ResultSet modif = Database.selectRecord("utenti", "id = " + userid);
                int modificato = 0;

                while (modif.next()) {
                    int modifiche = modif.getInt("modifiche");
                    //System.out.println("MODIFICHE: " + modifiche);
                    modificato = modifiche;
                }

                map.put("utente", utente);
                map.put("id_lib", id_libro);
                map.put("titolo", title);
                map.put("testo", message);
                map.put("approvato", '0');
                map.put("utapprovante", "");

                Database.insertRecord("recensioni", map);

                Map<String, Object> mod = new HashMap<String, Object>();
                mod.put("modifiche", modificato + 1);
                //temp.put("consigliato", )
                Database.updateRecord("utenti", mod, "id = '" + userid + "'");

                Database.close();

            } catch (NamingException ex) {
                Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //System.out.println("DO POST");
        //System.out.println(identificatore);

        response.sendRedirect("operasingle?id=" + identificatore);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Descrizione OperaSingle";
    }
}
