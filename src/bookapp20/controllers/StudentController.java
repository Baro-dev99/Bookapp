/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookapp20.controllers;

import bookapp20.Datasource;
import bookapp20.entities.Student;
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
public class StudentController {

    private String createString = "insert into student values(?,?,?)";
    private String findAllString = "select * from student";
    private String findByKeyString = "select * from student where id = ?";
    private String updateString = "update student set first_name = ?, last_name = ? where id = ?";
    private String deleteByKeyString = "delete from student where id = ?";
    private String findByLikeString = "select * from student where "
            + "lower(id) like ? and "
            + "lower(first_name) like ? and "
            + "lower(last_name) like ?";

    private PreparedStatement createStmt;
    private PreparedStatement findAllStmt;
    private PreparedStatement findByKeyStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteByKeyStmt;
    private PreparedStatement findByLikeStmt;

    private StudentController() {
        try {
            createStmt = Datasource.getConnection().prepareStatement(createString);
            findAllStmt = Datasource.getConnection().prepareStatement(findAllString);
            findByKeyStmt = Datasource.getConnection().prepareStatement(findByKeyString);
            updateStmt = Datasource.getConnection().prepareStatement(updateString);
            deleteByKeyStmt = Datasource.getConnection().prepareStatement(deleteByKeyString);
            findByLikeStmt = Datasource.getConnection().prepareStatement(findByLikeString);
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void create(Student student) throws SQLException {
        createStmt.setString(1, student.getId());
        createStmt.setString(2, student.getFirstName());
        createStmt.setString(3, student.getLastName());
        createStmt.executeUpdate();
    }

    public List<Student> findAll() {
        List<Student> ls = new ArrayList();
        try {

            ResultSet set = findAllStmt.executeQuery();
            String id;
            String firstName;
            String lastName;
            while (set.next()) {
                id = set.getString(1);
                firstName = set.getString(2);
                lastName = set.getString(3);
                ls.add(new Student(id, firstName, lastName));
            }
            set.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    public Student findByKey(String id) {
        try {
            findByKeyStmt.setString(1, id);
            ResultSet set = findByKeyStmt.executeQuery();
            if (set.next()) {
                return new Student(id, set.getString(2), set.getString(3));
            }
            set.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void update(Student student) {
        try {
            updateStmt.setString(1, student.getFirstName());
            updateStmt.setString(2, student.getLastName());
            updateStmt.setString(3, student.getId());
            updateStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteByKey(Student student) throws SQLException {
        deleteByKeyStmt.setString(1, student.getId());
        deleteByKeyStmt.executeUpdate();
    }

    public List<Student> findByLike(String subId, String subFirst, String subLast) {
        List<Student> ls = new ArrayList();
        try {
            if (subId == null) {
                findByLikeStmt.setString(1, "%%");
            } else {
                findByLikeStmt.setString(1, "%" + subId + "%");
            }

            if (subFirst == null) {
                findByLikeStmt.setString(2, "%%");
            } else {
                findByLikeStmt.setString(2, "%" + subFirst + "%");
            }

            if (subLast == null) {
                findByLikeStmt.setString(3, "%%");
            } else {
                findByLikeStmt.setString(3, "%" + subLast + "%");
            }

            ResultSet set = findByLikeStmt.executeQuery();
            while (set.next()) {
                ls.add(new Student(set.getString(1), set.getString(2), set.getString(3)));
            }

            set.close();

        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    public static final StudentController instance = new StudentController();
}
