package db_ass;

import java.util.Optional;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;

public final class View {

    private Optional<Controller> controller;
    private final JFrame mainFrame;

    public View(Runnable onClose) {
        this.controller = Optional.empty();
        this.mainFrame = this.setUpMainFrame(onClose);
    }

    private JFrame setUpMainFrame(Runnable onClose) {
        var frame = new JFrame("PROGETTO");
        var padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        ((JComponent) frame.getContentPane()).setBorder(padding);
        frame.setMinimumSize(new Dimension(300, 100));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    onClose.run();
                    System.exit(0);
                }
            }
        );
        return frame;
    }

}
