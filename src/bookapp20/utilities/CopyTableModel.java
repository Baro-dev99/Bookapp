/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookapp20.utilities;

import bookapp20.entities.Copy;

/**
 *
 * @author hover
 */
public class CopyTableModel extends AbstractGuiTableModels<Copy> {

    private final String[] columnNames = {"Code", "Status"};
    private final Class[] columnClasses = {String.class, String.class};

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
        Copy copy = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return copy.getCode();
            case 1:
                return copy.getStatus();
            default:
                return null;
        }
    }

}
