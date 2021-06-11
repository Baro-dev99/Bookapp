/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookapp20.controllers;

import bookapp20.Datasource;
import bookapp20.entities.Book;
import bookapp20.entities.Copy;
import bookapp20.entities.Publisher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hover
 */
public class CopyController {

    private String createString = "insert into copy(code, book_code, status) values(?,?,?)";
    private String findByBookString = "select copy.CODE, copy.AVAILABLE, copy.STATUS from copy where copy.BOOK_CODE = ?";
    private String findByKeyString = "select COPY.AVAILABLE, COPY.STATUS,"
            + " BOOK.CODE, BOOK.TITLE, PUBLISHER.ID, PUBLISHER.PNAME, BOOK.bdate"
            + " from COPY"
            + " join BOOK"
            + " on COPY.BOOK_CODE = BOOK.CODE"
            + " join PUBLISHER"
            + " on BOOK.PUBLISHER_ID = PUBLISHER.ID"
            + " where COPY.CODE = ?";
    private String deleteByKeyString = "delete from copy where copy.code = ? ";
    
    private String updateString = "update copy set status = ?, available = ? where code = ?";

    private PreparedStatement createStmt;
    private PreparedStatement findByBookStmt;
    private PreparedStatement findByKeyStmt;
    private PreparedStatement deleteByKeyStmt;
    private PreparedStatement updateStmt;

    private CopyController() {
        try {
            createStmt = Datasource.getConnection().prepareStatement(createString);
            findByBookStmt = Datasource.getConnection().prepareStatement(findByBookString);
            findByKeyStmt = Datasource.getConnection().prepareStatement(findByKeyString);
            deleteByKeyStmt = Datasource.getConnection().prepareStatement(deleteByKeyString);
            updateStmt = Datasource.getConnection().prepareStatement(updateString);
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void create(Copy copy) throws SQLException {
        createStmt.setString(1, copy.getCode());
        createStmt.setString(2, copy.getBook().getCode());
        createStmt.setString(3, copy.getStatus());
        createStmt.executeUpdate();
    }

    public List<Copy> findByBook(Book book) {
        List<Copy> ls = new ArrayList<>();
        Copy copy;
        try {
            findByBookStmt.setString(1, book.getCode());
            ResultSet set = findByBookStmt.executeQuery();
            while (set.next()) {
                copy = new Copy(set.getString(1));
                copy.setAvailable(set.getBoolean(2));
                copy.setStatus(set.getString(3));
                copy.setBook(book);
                ls.add(copy);
            }
            set.close();
        } catch (SQLException ex) {
            Logger.getLogger(CopyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    public Copy findByKey(String code) {
        try {
            findByKeyStmt.setString(1, code);
            ResultSet set = findByKeyStmt.executeQuery();
            Copy copy;
            Book book;
            if (set.next()) {
                copy = new Copy(code);
                copy.setAvailable(set.getBoolean(1));
                copy.setStatus(set.getString(2));
                book = new Book(set.getString(3));
                book.setTitle(set.getString(4));
                book.setPublisher(new Publisher(set.getString(5), set.getString(6)));
                book.setDate(set.getDate(7).toLocalDate());
                copy.setBook(book);
                set.close();
                return copy;
            }

        } catch (SQLException ex) {
            Logger.getLogger(CopyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void update(Copy copy) throws SQLException{
        updateStmt.setString(1, copy.getStatus());
        updateStmt.setBoolean(2, copy.isAvailable());
        updateStmt.setString(3, copy.getCode());
        updateStmt.executeUpdate();
    }

    public void deleteByKey(String code) throws SQLException {
        deleteByKeyStmt.setString(1, code);
        deleteByKeyStmt.executeUpdate();
    }

    public final static CopyController instance = new CopyController();
}
