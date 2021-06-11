/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookapp20.utilities;

import bookapp20.entities.Student;

/**
 *
 * @author hover
 */
public class StudentTableModel extends AbstractGuiTableModels<Student> {

    private final String[] columnNames = {"ID", "First Name", "Last Name"};
    private final Class[] columnClasses = {String.class, String.class, String.class};

    @Override
    public Class getColumnClass(int column) {
        return columnClasses[column];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student std = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return std.getId();
            case 1:
                return std.getFirstName();
            case 2:
                return std.getLastName();
            default:
                return null;
        }
    }

}
