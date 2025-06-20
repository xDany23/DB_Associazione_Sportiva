package db_ass.view.admin;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import db_ass.view.LimitDocumentFilter;

public class AddPanel extends JPanel{
    
    private List<JTextField> options;
    private JPasswordField pass;
    private JTextField cf;
    private List<JComboBox<?>> selections;

    public AddPanel(List<JTextField> optionAreas) {
        this(optionAreas,List.of());
    }

    public AddPanel(List<JTextField> optionAreas, List<JComboBox<?>> selections) {
        this.pass = new JPasswordField();
        this.cf = new JTextField();
        this.pass.setMinimumSize(new Dimension(150,15));
        this.options = new LinkedList<>(List.copyOf(optionAreas));
        this.setLayout(new GridLayout(optionAreas.size()+selections.size(), 2));
        this.options.forEach(o -> {
                                    o.setMinimumSize(new Dimension(150, 15));
                                    o.setAlignmentX(JLabel.LEFT_ALIGNMENT);
                                });
        List<JTextField> toRemove = new LinkedList<>();
        for (JTextField option : options) {
            var text = option.getText();
            JLabel label = new JLabel(text);
            option.setText("");
            label.setAlignmentX(JLabel.RIGHT);
            this.add(label);
            if (text.equals("Codice Fiscale")) {
                ((AbstractDocument)cf.getDocument()).setDocumentFilter(new LimitDocumentFilter(16));
                this.add(cf);
                toRemove.add(option);
            } else if (text.equals("Password")) {
                this.add(this.pass);
                toRemove.add(option);
            } else {
                this.add(option);
            }
        }
        this.selections = selections;
        if(!this.selections.isEmpty()) {
            for (var  sel : selections) {
                var text = sel.getName();
                sel.setName("");
                JLabel label = new JLabel(text);
                label.setAlignmentX(JLabel.RIGHT);
                sel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
                sel.setMinimumSize(new Dimension(150, 15));
                this.add(label);
                this.add(sel);
            }
        }
        this.options.removeAll(toRemove);
  
    }

    public List<String> getTexts() {
        return this.options.stream().map(o -> o.getText()).toList();
    }

    public String getPassword() {
        return new String(this.pass.getPassword());
    }

    public String getCF() {
        return this.cf.getText().length() < 16 ? "" : cf.getText();
    }

    public List<String> getSelection() {
        return this.selections.stream().map(s -> s.getSelectedItem()).filter(o -> o != null).map(o -> o.toString()).toList();
    }
}
