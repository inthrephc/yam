package yam.ui;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import yam.model.GameState;

/**
 * Top bar: mine counter (left), face / new game button (center), timer (right).
 */
public class HeaderPanel extends JPanel {

    public static final String FACE_PLAYING = ":)";
    public static final String FACE_WON = "B)";
    public static final String FACE_LOST = "X(";

    private JLabel mineCounterLabel;
    private JLabel timerLabel;
    private JButton faceButton;

    /**
     * @param onNewGame called when the face button is clicked
     */
    public HeaderPanel(ActionListener onNewGame) {
        // TODO: Create the two labels (large monospaced font, e.g. red text on black) and the face button,
        //       attach onNewGame to the button, and lay them out with BorderLayout WEST / CENTER / EAST.
    }

    public void setMineCounter(int value) {
        // TODO: Show formatThreeDigits(value) in mineCounterLabel.
    }

    public void setTime(int seconds) {
        // TODO: Show formatThreeDigits(seconds) in timerLabel.
    }

    public void setFace(GameState state) {
        // TODO: FACE_WON for WON, FACE_LOST for LOST, FACE_PLAYING otherwise.
    }

    /** Classic 3-character display: 7 -> "007", -5 -> "-05". The value is clamped to [-99, 999]. */
    private static String formatThreeDigits(int value) {
        // TODO: Clamp the value, then format it (e.g. String.format) as described above.
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
