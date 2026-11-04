package yam.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import yam.model.GameState;
import yam.util.Constants;

/**
 * Top bar: mine counter (left), face / new game button (center), timer (right).
 */
public class HeaderPanel extends JPanel {

    public static final String FACE_PLAYING = "☺";
    public static final String FACE_WON = "😎";
    public static final String FACE_LOST = "☹";

    private static final int MIN_DISPLAY_VALUE = -99;
    private static final int MAX_DISPLAY_VALUE = Constants.MAX_TIME_SECONDS; // 999

    private static final Color LED_BACKGROUND = Color.BLACK;
    private static final Color LED_FOREGROUND = new Color(255, 40, 40);
    private static final Color PANEL_BACKGROUND = new Color(192, 192, 192);
    private static final Font LED_FONT = new Font(Font.MONOSPACED, Font.BOLD, 26);
    private static final Font FACE_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

    private JLabel mineCounterLabel;
    private JLabel timerLabel;
    private JButton faceButton;

    /**
     * @param onNewGame called when the face button is clicked
     */
    public HeaderPanel(ActionListener onNewGame) {
        super(new BorderLayout());
        setBackground(PANEL_BACKGROUND);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(5, 6, 5, 6)));

        mineCounterLabel = createLedLabel();
        timerLabel = createLedLabel();

        faceButton = new JButton(FACE_PLAYING);
        faceButton.setFont(FACE_FONT);
        faceButton.setPreferredSize(new Dimension(40, 36));
        faceButton.setMargin(new Insets(0, 0, 0, 0));
        faceButton.setBackground(PANEL_BACKGROUND);
        faceButton.setBorder(BorderFactory.createRaisedBevelBorder());
        faceButton.setFocusable(false);        // keep keyboard focus off the button
        if (onNewGame != null) {
            faceButton.addActionListener(onNewGame);
        }

        
        JPanel facePanel = new JPanel();
        facePanel.setOpaque(false);
        facePanel.add(faceButton);

        add(mineCounterLabel, BorderLayout.WEST);
        add(facePanel, BorderLayout.CENTER);
        add(timerLabel, BorderLayout.EAST);

        setMineCounter(0);
        setTime(0);
    }

    private static JLabel createLedLabel() {
        JLabel label = new JLabel("000", SwingConstants.CENTER);
        label.setOpaque(true);                 // needed, otherwise the black background is not painted
        label.setBackground(LED_BACKGROUND);
        label.setForeground(LED_FOREGROUND);
        label.setFont(LED_FONT);
        label.setPreferredSize(new Dimension(68, 38));
        label.setBorder(BorderFactory.createLoweredBevelBorder());
        return label;
    }

    public void setMineCounter(int value) {
        mineCounterLabel.setText(formatThreeDigits(value));
    }

    public void setTime(int seconds) {
        timerLabel.setText(formatThreeDigits(seconds));
    }

    public void setFace(GameState state) {
        if (state == GameState.WON) {
            faceButton.setText(FACE_WON);
        } else if (state == GameState.LOST) {
            faceButton.setText(FACE_LOST);
        } else {
            faceButton.setText(FACE_PLAYING);  // READY, PLAYING and null
        }
    }

    /** Classic 3-character display: 7 -> "007", -5 -> "-05". The value is clamped to [-99, 999]. */
    private static String formatThreeDigits(int value) {
        int v = Math.max(MIN_DISPLAY_VALUE, Math.min(MAX_DISPLAY_VALUE, value));
        if (v < 0) {
            int abs = -v;                      // 1..99
            return abs < 10 ? "-0" + abs : "-" + abs;
        }
        if (v < 10) {
            return "00" + v;
        }
        if (v < 100) {
            return "0" + v;
        }
        return String.valueOf(v);
    }
}