package db_ass.view.admin;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

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

    public AddPanel(List<JTextField> optionAreas) {
        this.pass = new JPasswordField();
        this.cf = new JTextField();
        this.pass.setMinimumSize(new Dimension(150,15));
        this.options = new LinkedList<>(List.copyOf(optionAreas));
        this.setLayout(new GridLayout(options.size(), 2));
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

        this.options.removeAll(toRemove);
    }

    public List<String> getTexts() {
        return this.options.stream().map(o -> o.getText()).toList();
    }

    public String getPassword() {
        return new String(this.pass.getPassword());
    }

    public String getCF() {
        return cf.getText().length() < 16 ? "" : cf.getText();
    }
}
