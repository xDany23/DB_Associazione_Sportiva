package db_ass;

import java.util.Optional;
import java.util.function.Consumer;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public final class View {

    private Optional<Controller> controller;
    private final JFrame mainFrame;

    public View(/* Runnable onClose */) {
        this.controller = Optional.empty();
        this.mainFrame = this.setUpMainFrame(/* onClose */);
    }

    private JFrame setUpMainFrame(/* Runnable onClose */) {
        var frame = new JFrame("PROGETTO");
        var padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        ((JComponent) frame.getContentPane()).setBorder(padding);
        frame.setMinimumSize(new Dimension(300, 100));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
        frame.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    //onClose.run();
                    System.exit(0);
                }
            }
        );
        return frame;
    }

    public void loadingProduct() {
        freshPane(cp -> cp.add(new JLabel("Loading product...", SwingConstants.CENTER)));
    }

    private void freshPane(Consumer<Container> consumer) {
        var cp = this.mainFrame.getContentPane();
        cp.removeAll();
        consumer.accept(cp);
        cp.validate();
        cp.repaint();
        this.mainFrame.pack();
    }

}
