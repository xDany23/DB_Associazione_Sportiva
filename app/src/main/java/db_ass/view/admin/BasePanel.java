package db_ass.view.admin;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import db_ass.view.CustomTableModel;
import db_ass.view.OptionArea;

public abstract class BasePanel extends JPanel{
    
    private List<OptionArea> fields;
    private List<JButton> buttons;
    private JLabel title;
    private JScrollPane tablePanel;
    private JPanel optionPanel;
    private JTextField searchField;
    private JTable table;

    public BasePanel() {
        this.fields = new LinkedList<>();
        this.buttons = new LinkedList<>();
        optionPanel = new JPanel(new GridLayout());
        this.searchField = new JTextField();
        this.tablePanel = new JScrollPane(); 
        this.table = new JTable();
    }

    public JLabel getTitle() {
        return title;
    }

    public String getSearchField() {
        return this.searchField.getText();
    }

    public List<OptionArea> getOptionAreas() {
        return this.fields;
    }

    public JScrollPane getTablePanel() {
        return tablePanel;
    }

    public JPanel getOptionPanel() {
        return optionPanel;
    }

    public void fillOptionPanel() {
        this.fields.forEach(f -> this.optionPanel.add(f));
        this.optionPanel.add(this.searchField);
        this.buttons.forEach(b -> this.optionPanel.add(b));
    }

    public void setFields(List<OptionArea> options) {
        this.fields = List.copyOf(options);
    }

    public void setButtons(List<JButton> buttons) {
        this.buttons = List.copyOf(buttons);
    }

    public void setTitle(String text, int horizontalAllignment) {
        this.title = new JLabel(text, horizontalAllignment);
    }

    public void createTablePanel(List<String> columnNames, List<?>... elements) {
        Object[][] rowsData = new Object[elements[0].size()][elements.length];
        int j = 0;
        for(var element: elements) {
            int i = 0;
            for (var elem : element) {
                rowsData[i][j] = elem;
                i++;
            }
            j++;
        }
        this.tablePanel.remove(table);
        table = new JTable(rowsData, columnNames.toArray());
        table.setModel(new CustomTableModel(rowsData, columnNames.toArray()));
        tablePanel.setViewportView(table);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable t = (JTable)e.getSource();
                int row = t.rowAtPoint(e.getPoint());
                fillAreas(table, row, fields);
            }
        });
    }

    private void fillAreas(JTable t, int row, List<OptionArea> options) {
        List<Object> objs = new LinkedList<>();
        for(int i = 0; i < t.getColumnCount(); i++) {
            objs.add(t.getValueAt(row, i));
        }
        for (int i = 0; i < objs.size(); i++) {
            for (OptionArea opt : options) {
                if (opt.getName().equals(t.getColumnName(i))) {
                    opt.setText(objs.get(i).toString());
                }
            }
        }
    }

    public String getSearchedOptionOutput(String optionName) {
        String result = "";
        for (var option : getOptionAreas()) {
            if (option.getName().equals(optionName)) {
                result = option.getText();
            }
        }
        return result;
    }
}
