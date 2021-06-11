/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookapp20.controllers;

import bookapp20.Datasource;
import bookapp20.entities.Book;
import bookapp20.entities.Publisher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hover
 */
public class BookController {

    private String createString = "insert into book values(?,?,?,?)";
    private String findAllString = "Select book.CODE, book.TITLE, book.PUBLISHER_ID, publisher.PNAME, book.bdate from book join publisher on book.PUBLISHER_ID = publisher.ID";
    private String findByKeyString = "Select book.CODE, book.TITLE, book.PUBLISHER_ID, publisher.PNAME, book.bdate from book join publisher on book.PUBLISHER_ID = publisher.ID where book.code = ?";
    private String deleteByKeyString = "delete from book where code = ?";
    private String findByLikeString = "select book.CODE, book.TITLE, book.PUBLISHER_ID, "
            + "publisher.PNAME, book.bdate from book "
            + "join publisher on book.PUBLISHER_ID = publisher.ID "
            + "where lower(code) like ? and lower(title) like ?";

    private PreparedStatement createStmt;
    private PreparedStatement findAllStmt;
    private PreparedStatement findByKeyStmt;
    private PreparedStatement deleteByKeyStmt;
    private PreparedStatement findByLikeStmt;

    private BookController() {
        try {
            createStmt = Datasource.getConnection().prepareStatement(createString);
            findAllStmt = Datasource.getConnection().prepareStatement(findAllString);
            findByKeyStmt = Datasource.getConnection().prepareStatement(findByKeyString);
            deleteByKeyStmt = Datasource.getConnection().prepareStatement(deleteByKeyString);
            findByLikeStmt = Datasource.getConnection().prepareStatement(findByLikeString);
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void create(Book book) throws SQLException {
        createStmt.setString(1, book.getCode());
        createStmt.setString(2, book.getTitle());
        createStmt.setString(3, book.getPublisher().getId());
        createStmt.setDate(4, java.sql.Date.valueOf(book.getDate()));
        createStmt.executeUpdate();
    }

    public List<Book> findAll() {
        List<Book> ls = new ArrayList();
        try {

            ResultSet set = findAllStmt.executeQuery();
            String code;
            String title;
            String id;
            String pname;
            Publisher publisher;
            LocalDate date;
            Book book;
            while (set.next()) {
                code = set.getString(1);
                title = set.getString(2);
                id = set.getString(3);
                pname = set.getString(4);
                date = set.getDate(5).toLocalDate();
                publisher = new Publisher(id, pname);
                book = new Book(code);
                book.setTitle(title);
                book.setPublisher(publisher);
                book.setDate(date);
                ls.add(book);
            }
            set.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    public Book findByKey(String code) {
        try {
            findByKeyStmt.setString(1, code);
            ResultSet set = findByKeyStmt.executeQuery();
            if (set.next()) {
                Book book = new Book(code);
                book.setTitle(set.getString(2));
                Publisher publisher = new Publisher(set.getString(3), set.getString(4));
                book.setPublisher(publisher);
                book.setDate(set.getDate(5).toLocalDate());
                set.close();
                return book;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void deleteByKey(Book book) throws SQLException {
        deleteByKeyStmt.setString(1, book.getCode());
        deleteByKeyStmt.executeUpdate();
    }

    public List<Book> findByLike(String subCode, String subTitle) {
        List<Book> ls = new ArrayList();
        try {
            if (subCode == null) {
                findByLikeStmt.setString(1, "%%");
            } else {
                findByLikeStmt.setString(1, "%" + subCode + "%");
            }
            if (subTitle == null) {
                findByLikeStmt.setString(2, "%%");
            } else {
                findByLikeStmt.setString(2, "%" + subTitle + "%");
            }
            ResultSet set = findByLikeStmt.executeQuery();

            String code;
            String title;
            String id;
            String pname;
            Publisher publisher;
            LocalDate date;
            Book book;
            while (set.next()) {
                code = set.getString(1);
                title = set.getString(2);
                id = set.getString(3);
                pname = set.getString(4);
                date = set.getDate(5).toLocalDate();
                publisher = new Publisher(id, pname);
                book = new Book(code);
                book.setTitle(title);
                book.setPublisher(publisher);
                book.setDate(date);
                ls.add(book);
            }
            set.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ls;
    }

    public static final BookController instance = new BookController();
}
