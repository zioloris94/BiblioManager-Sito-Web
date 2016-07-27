/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Stefano Cortellessa
 */
public class Database {

    protected static String DRIVER = "com.mysql.jdbc.Driver";
    protected static String url = "jdbc:mysql://localhost/biblio";
    protected static String user = "root";
    protected static String psw = "";

    private static Connection db;

    /**
     * Connessione al database
     *
     * @throws Exception
     */
    public static void connect() throws Exception {

        /*InitialContext ctx = new InitialContext();
         DataSource ds = (DataSource) ctx.lookup("java:comp/env/BDigitale");
         Database.db  = ds.getConnection();  */
        try {
            Class.forName(DRIVER);
            db = DriverManager.getConnection(url, user, psw);
            System.out.println("CONNESSO");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Chiusura connessione al database
     *
     * @throws java.sql.SQLException
     */
    public static void close() throws SQLException {
        Database.db.close();
        System.out.println("CHIUSA");
    }

    /**
     * Select record con condizione
     *
     * @param table tabella da cui prelevare i dati
     * @param condition condizione per il filtro dei dati
     * @return dati prelevati
     * @throws java.sql.SQLException
     */
    public static ResultSet selectRecord(String table, String condition) throws SQLException {
        // Generazione query
        String query = "SELECT * FROM " + table + " WHERE " + condition;
        // Esecuzione query
        return Database.executeQuery(query);
    }

    /**
     * Select record con condizione
     *
     * @param table tabella da cui prelevare i dati
     * @param condition condizione per il filtro dei dati
     * @return dati prelevati
     * @throws java.sql.SQLException
     */
    public static ResultSet selectConsigliato(String table, String condition) throws SQLException {
        // Generazione query
        String query = "SELECT Consigliato FROM " + table + " WHERE " + condition;
        // Esecuzione query
        return Database.executeQuery(query);
    }

    public static ResultSet selectNome(String table, String condition) throws SQLException {
        // Generazione query
        String query = "SELECT 'nome' FROM " + table + " WHERE " + condition;
        // Esecuzione query
        return Database.executeQuery(query);
    }

    /**
     * Select record con condizione e ordinamento
     *
     * @param table tabella da cui prelevare i dati
     * @param condition condizione per il filtro dei dati
     * @param order ordinamento dei dati
     * @return dati prelevati
     * @throws java.sql.SQLException
     */
    public static ResultSet selectRecordOr(String table, String order) throws SQLException {
        // Generazione query

        System.out.println("SELECT ORDERED");
        String query = "SELECT * FROM " + table + " ORDER BY " + order;
        // Esecuzione query
        return Database.executeQuery(query);
    }

    /**
     * Select record con join tra due tabelle
     *
     * @param table_1 nome della prima tabella
     * @param table_2 nome della seconda tabella
     * @param join_condition condizione del join tra la tabelle
     * @param where_condition condizione per il filtro dei dati
     * @return dati prelevati
     * @throws java.sql.SQLException
     */
    public static ResultSet selectJoin(String table_1, String table_2, String join_condition, String Order) throws SQLException {
        // Generazione query
        String query = "SELECT * FROM " + table_1 + " JOIN " + table_2 + " ON " + join_condition + " ORDER BY " + Order;
        // Esecuzione query
        return Database.executeQuery(query);
    }
    
    /**
     * Select record con join tra due tabelle
     *
     * @param table_1 nome della prima tabella
     * @param table_2 nome della seconda tabella
     * @param join_condition condizione del join tra la tabelle
     * @param where_condition condizione per il filtro dei dati
     * @return dati prelevati
     * @throws java.sql.SQLException
     */
    public static ResultSet selectJoinL(String table_1, String table_2, String join_condition, String Order, int Start, int Max) throws SQLException {
        // Generazione query
        String query = "SELECT * FROM " + table_1 + " JOIN " + table_2 + " ON " + join_condition + " ORDER BY " + Order + " LIMIT " + Start + "," + Max;
        // Esecuzione query
        return Database.executeQuery(query);
    }

    /**
     * Select record con join tra due tabelle
     *
     * @param table_1 nome della prima tabella
     * @param table_2 nome della seconda tabella
     * @param join_condition condizione del join tra la tabelle
     * @param where_condition condizione per il filtro dei dati
     * @return dati prelevati
     * @throws java.sql.SQLException
     */
    public static ResultSet selectJoinW(String table_1, String table_2, String join_condition, String condition) throws SQLException {
        // Generazione query
        String query = "SELECT * FROM " + table_1 + " JOIN " + table_2 + " ON " + join_condition + " WHERE " + condition;
        // Esecuzione query
        return Database.executeQuery(query);
    }
    

    /**
     * Select record con join tra due tabelle e ordinamento
     *
     * @param table_1 nome della prima tabella
     * @param table_2 nome della seconda tabella
     * @param join_condition condizione del join tra la tabelle
     * @param where_condition condizione per il filtro dei dati
     * @param order ordinamento dei dati
     * @return dati prelevati
     * @throws java.sql.SQLException
     */
    public static ResultSet selectJoinOr(String table_1, String table_2, String join_condition, String where_condition, String order) throws SQLException {
        // Generazione query
        String query = "SELECT * FROM " + table_1 + " JOIN " + table_2 + " ON " + join_condition + " WHERE " + where_condition + "ORDER BY" + order;
        // Esecuzione query
        return Database.executeQuery(query);
    }

    /**
     * Insert record
     *
     * @param table tabella in cui inserire i dati
     * @param data dati da inserire
     * @return dati prelevati
     * @throws java.sql.SQLException
     */
    public static boolean insertRecord(String table, Map<String, Object> data) throws SQLException {
        // Generazione query
        String query = "INSERT INTO " + table + " SET ";
        Object value;
        String attr;

        for (Map.Entry<String, Object> e : data.entrySet()) {
            attr = e.getKey();
            value = e.getValue();
            if (value instanceof Integer) {
                query = query + attr + " = " + value + ", ";
            } else {
                value = value.toString().replace("\'", "\\'");
                query = query + attr + " = '" + value + "', ";
            }
        }
        query = query.substring(0, query.length() - 2);
        // Esecuzione query

        return Database.updateQuery(query);
    }

    /**
     * Update record
     *
     * @param table tabella in cui aggiornare i dati
     * @param data dati da inserire
     * @param condition condizione per il filtro dei dati
     * @return true se l'inserimento Ã¨ andato a buon fine, false altrimenti
     * @throws java.sql.SQLException
     */
    public static boolean updateRecord(String table, Map<String, Object> data, String condition) throws SQLException {
        // Generazione query
        String query = "UPDATE " + table + " SET ";

        Object value;
        String attr;

        for (Map.Entry<String, Object> e : data.entrySet()) {
            attr = e.getKey();
            value = e.getValue();
            if (value instanceof String) {
                value = value.toString().replace("\'", "\\'");
                query = query + attr + " = '" + value + "', ";
            } else {
                query = query + attr + " = " + value + ", ";
            }

        }
        query = query.substring(0, query.length() - 2) + " WHERE " + condition;
        System.out.println(query);
        // Esecuzione query
        return Database.updateQuery(query);
    }

    /**
     * Delete record
     *
     * @param table tabella in cui eliminare i dati
     * @param condition condizione per il filtro dei dati
     * @return true se l'eliminazione Ã¨ andata a buon fine, false altrimenti
     * @throws java.sql.SQLException
     */
    public static boolean deleteRecord(String table, String condition) throws SQLException {
        // Generazione query
        String query = "DELETE FROM " + table + " WHERE " + condition;
        // Esecuzione query
        return Database.updateQuery(query);
    }

    /**
     * Count record
     *
     * @param table tabella in cui contare i dati
     * @param condition condizione per il filtro dei dati
     * @return numero dei record se la query Ã¨ stata eseguita on successo, -1
     * altrimenti
     * @throws java.sql.SQLException
     */
    public static int countRecord(String table, String condition) throws SQLException {

        // Generazione query
        String query = "SELECT COUNT(*) FROM " + table + " WHERE " + condition;
        // Esecuzione query
        ResultSet record = Database.executeQuery(query);
        record.next();
        // Restituzione del risultato
        return record.getInt(1);

    }

    /**
     * Imposta a NULL un attributo di una tabella
     *
     * @param table tabella in cui Ã¨ presente l'attributo
     * @param attribute attributo da impostare a NULL
     * @param condition condizione
     * @return
     * @throws java.sql.SQLException
     */
    public static boolean resetAttribute(String table, String attribute, String condition) throws SQLException {
        String query = "UPDATE " + table + " SET " + attribute + " = NULL WHERE " + condition;
        return Database.updateQuery(query);
    }

    // <editor-fold defaultstate="collapsed" desc="Metodi ausiliari.">
    /**
     * executeQuery personalizzata
     *
     * @param query query da eseguire
     */
    private static ResultSet executeQuery(String query) throws SQLException {

        Statement s1 = Database.db.createStatement();

        System.out.println(query);
        ResultSet records = s1.executeQuery(query);

        System.out.println("executeQuery");
        return records;
    }

    /**
     * updateQuery personalizzata
     *
     * @param query query da eseguire
     */
    private static boolean updateQuery(String query) throws SQLException {

        Statement s1;
        s1 = Database.db.createStatement();
        s1.executeUpdate(query);
        s1.close();

        return true;

    }
    // </editor-fold>

}
