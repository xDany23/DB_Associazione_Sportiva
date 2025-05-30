package db_ass.view;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

//classe che serve per settare il numero massimo di caratteri in ad esempio JTextField
public class LimitDocumentFilter extends DocumentFilter{

    private final int max;

    public LimitDocumentFilter(int max) {
        this.max = max;
    }
    
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (fb.getDocument().getLength() + string.length() <= max) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int lenght, String text, AttributeSet attrs) throws BadLocationException {
        if (fb.getDocument().getLength() - lenght + text.length() <= max) {
            super.replace(fb, offset, lenght, text, attrs);
        }
    }
}
