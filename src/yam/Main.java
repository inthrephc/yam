package yam;

import javax.swing.SwingUtilities;
import yam.ui.MainFrame;

/**
 * Application entry point.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
            () -> new MainFrame().setVisible(true)
        );
    }

}
