package yam.ui;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import yam.model.Board;
import yam.model.GameState;

/**
 * Grid of {@link CellButton}s. Turns mouse clicks into {@link CellActionListener} calls and
 * repaints every button from the Board after each action.
 */
public class BoardPanel extends JPanel {

    private CellButton[][] buttons;
    private CellActionListener listener;

    public BoardPanel(CellActionListener listener) {
        this.listener = listener;
    }

    /** (Re)creates rows x cols buttons in a GridLayout. Call it whenever a new game starts. */
    public void buildGrid(int rows, int cols) {
        removeAll();
        setLayout(new GridLayout(rows, cols));
        buttons = new CellButton[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                CellButton button = new CellButton(row, col);

                button.addMouseListener(createMouseHandler(row, col));

                buttons[row][col] = button;
                add(button);
            }
        }

        revalidate();
        repaint();
    }

    /** Re-renders every button from the model. */
    public void refresh(Board board, GameState state) {
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                buttons[row][col].render(board.getCell(row, col), state);
            }
        }
    }

    /** Mouse handler for one button: left = reveal, right = flag, middle = chord. */
    private MouseAdapter createMouseHandler(int row, int col) {
        return new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!e.getComponent().contains(e.getPoint())) {
                    return;
                }
                if (SwingUtilities.isLeftMouseButton(e)) {
                    listener.onReveal(row, col);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    listener.onToggleFlag(row, col);
                } else if (SwingUtilities.isMiddleMouseButton(e)) {
                    listener.onChord(row, col);
                }
            }
        };
    }
}
