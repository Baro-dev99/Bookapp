/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookapp20;

import bookapp20.controllers.BorrowController;
import bookapp20.entities.Borrow;
import java.util.List;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author hover
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        try {
//            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
//            Connection con = DriverManager.getConnection("jdbc:derby:database/bookdb20");
//            Statement st = con.createStatement();
//            ResultSet set = st.executeQuery("select book.code,book.title, publisher.pname from book"
//                    + " join publisher on book.publisher_id = publisher.id");
//
//            while (set.next()) {
//                String code = set.getString(1);
//                String title = set.getString(2);
//                String pname = set.getString(3);
//
//                System.out.printf("%s, %s, %s%n", code, title, pname);
//            }
//
//            con.close();
//
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }

        LogManager.getLogger(Main.class).warn("Warn !");
        Datasource.getConnection();
//        List<Author> ls = BookAuthorController.instance.findAuthorByBook(new Book("intalg"));
//        for (Author b : ls) {
//            System.out.println(b);
//        }

//        Copy cp = CopyController.instance.findByKey("javhow02");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        System.out.println(formatter.format(cp.getBook().getDate()));
        List<Borrow> ls = BorrowController.instance.findByCopy("dperoo01");
        for (Borrow b : ls) {
            System.out.println(b);
        }
        Datasource.closeConnection();
    }

}
