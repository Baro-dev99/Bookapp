/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookapp20.controllers;

import bookapp20.Datasource;
import bookapp20.entities.Author;
import bookapp20.entities.Book;
import bookapp20.entities.BookAuthor;
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
public class BookAuthorController {

    private String createString = "insert into book_has_authors values(?,?)";
    private String findBookByAuthorString = "Select BOOK_HAS_AUTHORS.BOOK_CODE, BOOK.TITLE, PUBLISHER.ID, PUBLISHER.PNAME, BOOK.bdate"
            + " from BOOK_HAS_AUTHORS"
            + " join BOOK"
            + " on BOOK_CODE = book.CODE"
            + " join publisher"
            + " on book.PUBLISHER_ID = publisher.ID"
            + " where BOOK_HAS_AUTHORS.AUTHOR_ID = ?";

    private String findAuthorByBookString = "Select BOOK_HAS_AUTHORS.AUTHOR_ID, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME"
            + " from BOOK_HAS_AUTHORS"
            + " join AUTHOR"
            + " on BOOK_HAS_AUTHORS.AUTHOR_ID = AUTHOR.ID"
            + " where BOOK_HAS_AUTHORS.BOOK_CODE = ?";

    private String deleteByBookString = "delete from book_has_authors where book_code = ?";
    private String deleteByAuthorString = "delete from book_has_authors where author_id = ?";

    private PreparedStatement createStmt;
    private PreparedStatement findBookByAuthorStmt;
    private PreparedStatement findAuthorByBookStmt;
    private PreparedStatement deleteByBookStmt;
    private PreparedStatement deleteByAuthorStmt;

    private BookAuthorController() {
        try {
            createStmt = Datasource.getConnection().prepareStatement(createString);
            findBookByAuthorStmt = Datasource.getConnection().prepareStatement(findBookByAuthorString);
            findAuthorByBookStmt = Datasource.getConnection().prepareStatement(findAuthorByBookString);
            deleteByBookStmt = Datasource.getConnection().prepareStatement(deleteByBookString);
            deleteByAuthorStmt = Datasource.getConnection().prepareStatement(deleteByAuthorString);
        } catch (SQLException ex) {
            Logger.getLogger(PublisherController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void create(BookAuthor ba) throws SQLException {
        createStmt.setString(1, ba.getBook().getCode());
        createStmt.setString(2, ba.getAuthor().getId());
        createStmt.executeUpdate();
    }

    public List<Author> findAuthorByBook(Book book) {
        List<Author> ls = new ArrayList();
        try {
            findAuthorByBookStmt.setString(1, book.getCode());
            ResultSet set = findAuthorByBookStmt.executeQuery();
            while (set.next()) {
                Author author = new Author(set.getString(1), set.getString(2), set.getString(3));
                ls.add(author);
            }
            set.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookAuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    public List<Book> findBookByAuthor(Author author) {
        List<Book> ls = new ArrayList();
        try {
            findBookByAuthorStmt.setString(1, author.getId());
            ResultSet set = findBookByAuthorStmt.executeQuery();
            String code;
            String title;
            String id;
            String pname;
            while (set.next()) {
                code = set.getString(1);
                title = set.getString(2);
                id = set.getString(3);
                pname = set.getString(4);
                Book book = new Book(code);
                book.setTitle(title);
                book.setPublisher(new Publisher(id, pname));
                book.setDate(set.getDate(5).toLocalDate());
                ls.add(book);
            }
            set.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookAuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    public void deleteByBook(Book book) throws SQLException {
        deleteByBookStmt.setString(1, book.getCode());
        deleteByBookStmt.executeUpdate();
    }

    public void deleteByAuthor(Author author) throws SQLException {
        deleteByAuthorStmt.setString(1, author.getId());
        deleteByAuthorStmt.executeUpdate();
    }

    public static final BookAuthorController instance = new BookAuthorController();
}
