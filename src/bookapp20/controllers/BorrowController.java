/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookapp20.controllers;

import bookapp20.Datasource;
import bookapp20.entities.Book;
import bookapp20.entities.Borrow;
import bookapp20.entities.Copy;
import bookapp20.entities.Publisher;
import bookapp20.entities.Student;
import java.sql.Date;
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
public class BorrowController {

    private String createString = "insert into borrow(student_id, copy_code, loan_date) values(?,?,?) ";

    private String findAllString = "SELECT BORROW.ID, STUDENT.ID, STUDENT.FIRST_NAME,"
            + " STUDENT.LAST_NAME, COPY.CODE, COPY.AVAILABLE, COPY.STATUS,"
            + " BOOK.CODE, BOOK.TITLE, BOOK.BDATE, PUBLISHER.ID, PUBLISHER.PNAME,"
            + " BORROW.LOAN_DATE, BORROW.RETURN_DATE"
            + " from BORROW"
            + " join STUDENT"
            + " on BORROW.STUDENT_ID = STUDENT.ID"
            + " join COPY"
            + " on BORROW.COPY_CODE = COPY.CODE"
            + " join BOOK"
            + " on COPY.BOOK_CODE = BOOK.CODE"
            + " join PUBLISHER"
            + " on BOOK.PUBLISHER_ID = PUBLISHER.ID";

    private String findByStudentString = "SELECT BORROW.ID, STUDENT.ID, STUDENT.FIRST_NAME,"
            + " STUDENT.LAST_NAME, COPY.CODE, COPY.AVAILABLE, COPY.STATUS,"
            + " BOOK.CODE, BOOK.TITLE, BOOK.BDATE, PUBLISHER.ID, PUBLISHER.PNAME,"
            + " BORROW.LOAN_DATE, BORROW.RETURN_DATE"
            + " from BORROW"
            + " join STUDENT"
            + " on BORROW.STUDENT_ID = STUDENT.ID"
            + " join COPY"
            + " on BORROW.COPY_CODE = COPY.CODE"
            + " join BOOK"
            + " on COPY.BOOK_CODE = BOOK.CODE"
            + " join PUBLISHER"
            + " on BOOK.PUBLISHER_ID = PUBLISHER.ID"
            + " where BORROW.STUDENT_ID = ?"
            + " and COPY.AVAILABLE = false";

    private String findByCopyString = "SELECT BORROW.ID, STUDENT.ID, STUDENT.FIRST_NAME,"
            + " STUDENT.LAST_NAME, COPY.AVAILABLE, COPY.STATUS,"
            + " BOOK.CODE, BOOK.TITLE, BOOK.BDATE, PUBLISHER.ID, PUBLISHER.PNAME,"
            + " BORROW.LOAN_DATE, BORROW.RETURN_DATE"
            + " from BORROW"
            + " join STUDENT"
            + " on BORROW.STUDENT_ID = STUDENT.ID"
            + " join COPY"
            + " on BORROW.COPY_CODE = COPY.CODE"
            + " join BOOK"
            + " on COPY.BOOK_CODE = BOOK.CODE"
            + " join PUBLISHER"
            + " on BOOK.PUBLISHER_ID = PUBLISHER.ID"
            + " where BORROW.LOAN_DATE = "
            + " (SELECT MAX(BORROW.LOAN_DATE) FROM BORROW WHERE COPY.CODE = ?)";

    private PreparedStatement createStmt;
    private PreparedStatement findAllStmt;
    private PreparedStatement findByStudentStmt;
    private PreparedStatement findByCopyStmt;

    private BorrowController() {
        try {
            createStmt = Datasource.getConnection().prepareStatement(createString);
            findAllStmt = Datasource.getConnection().prepareStatement(findAllString);
            findByStudentStmt = Datasource.getConnection().prepareStatement(findByStudentString);
            findByCopyStmt = Datasource.getConnection().prepareStatement(findByCopyString);
        } catch (SQLException ex) {
            Logger.getLogger(BorrowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void create(Borrow borrow) throws SQLException {
        createStmt.setString(1, borrow.getStudent().getId());
        createStmt.setString(2, borrow.getCopy().getCode());
        createStmt.setDate(3, Date.valueOf(borrow.getLoanDate()));
        createStmt.executeUpdate();

        ResultSet rs = createStmt.getGeneratedKeys();
        if (rs.next()) {
            borrow.setId(rs.getInt(1));
        }
        rs.close();
    }

    public List<Borrow> findByCopy(String copyCode) {
        List<Borrow> ls = new ArrayList();
        int borrowId;
        String studentId;
        String studentFirstName;
        String studentLastName;
        boolean copyAvailable;
        String copyStatus;
        String bookCode;
        String bookTitle;
        LocalDate bookDate;
        String publisherId;
        String publisherName;
        LocalDate loanDate;
        LocalDate returnDate = null;

        Book book;
        Publisher publisher;
        Copy copy;
        Student student;
        Borrow borrow;

        try {
            findByCopyStmt.setString(1, copyCode);
            ResultSet set = findByCopyStmt.executeQuery();
            while (set.next()) {
                borrowId = set.getInt(1);
                studentId = set.getString(2);
                studentFirstName = set.getString(3);
                studentLastName = set.getString(4);
                copyAvailable = set.getBoolean(5);
                copyStatus = set.getString(6);
                bookCode = set.getString(7);
                bookTitle = set.getString(8);
                bookDate = set.getDate(9).toLocalDate();
                publisherId = set.getString(10);
                publisherName = set.getString(11);
                loanDate = set.getDate(12).toLocalDate();
                Date date = set.getDate(13);
                if (date != null) {
                    returnDate = date.toLocalDate();
                }

                student = new Student(studentId, studentFirstName, studentLastName);
                publisher = new Publisher(publisherId, publisherName);
                book = new Book(bookCode);
                book.setPublisher(publisher);
                book.setTitle(bookTitle);
                book.setDate(bookDate);
                copy = new Copy(copyCode);
                copy.setStatus(copyStatus);
                copy.setAvailable(copyAvailable);
                copy.setBook(book);
                borrow = new Borrow(student, copy);
                borrow.setId(borrowId);
                borrow.setLoanDate(loanDate);
                borrow.setReturnDate(returnDate);
                ls.add(borrow);
            }
            set.close();

        } catch (SQLException ex) {
            Logger.getLogger(BorrowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    public static final BorrowController instance = new BorrowController();
}
