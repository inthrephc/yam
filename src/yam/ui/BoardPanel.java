package yam.ui;

import java.awt.event.MouseAdapter;
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
        // TODO: Store the listener.
    }

    /** (Re)creates rows x cols buttons in a GridLayout. Call it whenever a new game starts. */
    public void buildGrid(int rows, int cols) {
        // TODO: removeAll(); setLayout(new GridLayout(rows, cols)); create every CellButton,
        //       addMouseListener(createMouseHandler(r, c)) and add it; finally revalidate() and repaint().
    }

    /** Re-renders every button from the model. */
    public void refresh(Board board, GameState state) {
        // TODO: For every cell: buttons[r][c].render(board.getCell(r, c), state).
    }

    /** Mouse handler for one button: left = reveal, right = flag, middle = chord. */
    private MouseAdapter createMouseHandler(int row, int col) {
        // TODO: Return a MouseAdapter whose mouseReleased(...) uses SwingUtilities.isLeftMouseButton /
        //       isRightMouseButton / isMiddleMouseButton to call the matching listener method.
        //       (An ActionListener is not enough: it only reports left clicks.)
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
