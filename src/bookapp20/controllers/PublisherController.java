/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookapp20.controllers;

import bookapp20.Datasource;
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
public class PublisherController {

    private String createString = "insert into publisher values(?,?)";
    private String findAllString = "select * from publisher";
    private String findByKeyString = "select * from publisher where id = ?";
    private String deleteByKeyString = "delete from publisher where id = ?";
    private String findByLikeString = "select * from publisher where lower(pname) like ?";

    private PreparedStatement createStmt;
    private PreparedStatement findAllStmt;
    private PreparedStatement findByKeyStmt;
    private PreparedStatement deleteByKeyStmt;
    private PreparedStatement findByLikeStmt;

    private PublisherController() {
        try {
            createStmt = Datasource.getConnection().prepareStatement(createString);
            findAllStmt = Datasource.getConnection().prepareStatement(findAllString);
            findByKeyStmt = Datasource.getConnection().prepareStatement(findByKeyString);
            deleteByKeyStmt = Datasource.getConnection().prepareStatement(deleteByKeyString);
            findByLikeStmt = Datasource.getConnection().prepareStatement(findByLikeString);
        } catch (SQLException ex) {
            Logger.getLogger(PublisherController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void create(Publisher publisher) throws SQLException {
        createStmt.setString(1, publisher.getId());
        createStmt.setString(2, publisher.getPname());
        createStmt.executeUpdate();
    }

    public List<Publisher> findAll() {
        List<Publisher> ls = new ArrayList();
        try {

            ResultSet set = findAllStmt.executeQuery();
            String id;
            String pname;
            while (set.next()) {
                id = set.getString(1);
                pname = set.getString(2);
                ls.add(new Publisher(id, pname));
            }
            set.close();
        } catch (SQLException ex) {
            Logger.getLogger(PublisherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    public Publisher findByKey(String id) {
        try {
            findByKeyStmt.setString(1, id);
            ResultSet set = findByKeyStmt.executeQuery();
            if (set.next()) {
                return new Publisher(id, set.getString(2));
            }
            set.close();
        } catch (SQLException ex) {
            Logger.getLogger(PublisherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void deleteByKey(Publisher publisher) throws SQLException {
        deleteByKeyStmt.setString(1, publisher.getId());
        deleteByKeyStmt.executeUpdate();
    }

    public List<Publisher> findByLike(String subName) {
        List<Publisher> ls = new ArrayList();
        try {
            if (subName == null) {
                findByLikeStmt.setString(1, "%%");
            } else {
                findByLikeStmt.setString(1, "%" + subName + "%");
            }
            ResultSet set = findByLikeStmt.executeQuery();
            while (set.next()) {
                ls.add(new Publisher(set.getString(1), set.getString(2)));
            }
            set.close();
        } catch (SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    public static final PublisherController instance = new PublisherController();

}
