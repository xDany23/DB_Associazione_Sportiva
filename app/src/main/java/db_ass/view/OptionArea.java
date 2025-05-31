package db_ass.view;

import javax.swing.JLabel;

public class OptionArea extends JLabel{

    private final String name;
    
    public OptionArea(String name, String Text) {
        super(Text);
        this.setEnabled(false);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
