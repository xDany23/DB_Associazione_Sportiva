package db_ass.view;

import javax.swing.table.AbstractTableModel;

public class CustomTableModel extends AbstractTableModel{

    private final Object[][] rowData;
    private final Object[] columnNames;

    public CustomTableModel( Object[][] rowData, Object[] columnNames) {
        this.rowData = rowData;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return rowData.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowData[rowIndex][columnIndex];
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public String getColumnName(int column) {
        return this.columnNames[column].toString();
    }
}
