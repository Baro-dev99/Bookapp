/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookapp20.utilities;

import bookapp20.entities.Book;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author hover
 */
public class BookTableModel extends AbstractGuiTableModels<Book> {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final String[] columnNames = {"Code", "Title", "Publisher", "Date"};
    private final Class[] columnClasses = {String.class, String.class, String.class, String.class};

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Class getColumnClass(int column) {
        return columnClasses[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Book book = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return book.getCode();
            case 1:
                return book.getTitle();
            case 2:
                return book.getPublisher().getPname();
            case 3:
                return book.getDate().format(formatter);
            default:
                return null;
        }
    }

}
