/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookapp20.controllers;

import bookapp20.Datasource;
import bookapp20.entities.Author;
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
public class AuthorController {

    private String createString = "insert into author values(?,?,?)";
    private String findAllString = "select * from author";
    private String findByKeyString = "select * from author where id = ?";
    private String deleteByKeyString = "delete from author where id = ?";
    private String findByLikeString = "select * from author where lower(first_name) like ? or lower(last_name) like ?";

    private PreparedStatement createStmt;
    private PreparedStatement findAllStmt;
    private PreparedStatement findByKeyStmt;
    private PreparedStatement deleteByKeyStmt;
    private PreparedStatement findByLikeStmt;

    private AuthorController() {
        try {
            createStmt = Datasource.getConnection().prepareStatement(createString);
            findAllStmt = Datasource.getConnection().prepareStatement(findAllString);
            findByKeyStmt = Datasource.getConnection().prepareStatement(findByKeyString);
            deleteByKeyStmt = Datasource.getConnection().prepareStatement(deleteByKeyString);
            findByLikeStmt = Datasource.getConnection().prepareStatement(findByLikeString);
        } catch (SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void create(Author author) throws SQLException {
        createStmt.setString(1, author.getId());
        createStmt.setString(2, author.getFirstName());
        createStmt.setString(3, author.getLastName());
        createStmt.executeUpdate();
    }

    public List<Author> findAll() {
        List<Author> ls = new ArrayList();
        try {

            ResultSet set = findAllStmt.executeQuery();
            String id;
            String firstName;
            String lastName;
            while (set.next()) {
                id = set.getString(1);
                firstName = set.getString(2);
                lastName = set.getString(3);
                ls.add(new Author(id, firstName, lastName));
            }
            set.close();
        } catch (SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    public Author findByKey(String id) {
        try {
            findByKeyStmt.setString(1, id);
            ResultSet set = findByKeyStmt.executeQuery();
            if (set.next()) {
                return new Author(id, set.getString(2), set.getString(3));
            }
            set.close();
        } catch (SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void deleteByKey(Author author) throws SQLException {
        deleteByKeyStmt.setString(1, author.getId());
        deleteByKeyStmt.executeUpdate();
    }

    public List<Author> findByLike(String subName) {
        List<Author> ls = new ArrayList();
        try {
            if (subName == null) {
                findByLikeStmt.setString(1, "%%");
                findByLikeStmt.setString(2, "%%");
            } else {
                findByLikeStmt.setString(1, "%" + subName + "%");
                findByLikeStmt.setString(2, "%" + subName + "%");
            }
            ResultSet set = findByLikeStmt.executeQuery();
            while (set.next()) {
                ls.add(new Author(set.getString(1), set.getString(2), set.getString(3)));
            }
            set.close();
        } catch (SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    public static final AuthorController instance = new AuthorController();

}
