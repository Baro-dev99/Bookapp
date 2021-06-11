/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookapp20;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hover
 */
public class Datasource {

    private static Connection con = null;

    public static Connection getConnection() {
        if (con == null) {
            try {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                con = DriverManager.getConnection("jdbc:derby:database/bookdb20");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Datasource.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Datasource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return con;
    }

    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
                con = null;
                DriverManager.getConnection("jdbc:derby:database/bookdb20;shutdown=true");
            } catch (SQLException ex) {
                //Logger.getLogger(Datasource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
