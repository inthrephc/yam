package yam.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import yam.model.Cell;
import yam.model.GameState;
import yam.util.Constants;

/**
 * Button that draws one cell. It stores no game data: {@link #render} repaints it from the model.
 */
public class CellButton extends JButton {

    /** Text color of each number; index = number of adjacent mines (index 0 is unused). */
    public static final Color[] NUMBER_COLORS = {
        null,
        new Color(0, 0, 255), // 1 blue
        new Color(0, 128, 0), // 2 green
        new Color(255, 0, 0), // 3 red
        new Color(0, 0, 128), // 4 navy
        new Color(128, 0, 0), // 5 maroon
        new Color(0, 128, 128), // 6 teal
        new Color(0, 0, 0), // 7 black
        new Color(128, 128, 128), // 8 gray
    };

    public static final Color HIDDEN_BACKGROUND = new Color(192, 192, 192);
    public static final Color REVEALED_BACKGROUND = new Color(224, 224, 224);
    public static final Color EXPLODED_BACKGROUND = new Color(255, 0, 0);

    // Plain text works with every font; it can be replaced with icons later.
    public static final String FLAG_TEXT = "F";
    public static final String MINE_TEXT = "*";
    public static final String WRONG_FLAG_TEXT = "X";

    private int row;
    private int col;

    /** Creates a square button of {@link Constants#CELL_SIZE_PX} for the cell at (row, col). */
    public CellButton(int row, int col) {
        this.row = row;
        this.col = col;
        setPreferredSize(
            new Dimension(Constants.CELL_SIZE_PX, Constants.CELL_SIZE_PX)
        );
        setFocusPainted(false);
        setMargin(new Insets(0, 0, 0, 0));
        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /**
     * Updates text, colors and border so the button matches the cell.
     * <ul>
     * <li>HIDDEN: raised border, HIDDEN_BACKGROUND, no text.</li>
     * <li>FLAGGED: FLAG_TEXT; after a loss, WRONG_FLAG_TEXT if there is no mine under the flag.</li>
     * <li>REVEALED: flat border, REVEALED_BACKGROUND; MINE_TEXT for a mine (EXPLODED_BACKGROUND if
     * it is the exploded one), no text for 0, otherwise the number in NUMBER_COLORS[number].</li>
     * </ul>
     * Keep the button enabled: a disabled JButton ignores mouse clicks (breaking chord) and greys out its text.
     */
    public void render(Cell cell, GameState state) {
        setText("");
        setForeground(Color.BLACK);
        setBackground(HIDDEN_BACKGROUND);
        setBorder(BorderFactory.createRaisedBevelBorder());

        if (cell.isFlagged()) {
            if (state == GameState.LOST && !cell.isMine()) {
                setText(WRONG_FLAG_TEXT);
            } else {
                setText(FLAG_TEXT);
            }
            return;
        }

        if (cell.isHidden()) {
            return;
        }

        setBackground(REVEALED_BACKGROUND);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        if (cell.isMine()) {
            setText(MINE_TEXT);

            if (cell.isExploded()) {
                setBackground(EXPLODED_BACKGROUND);
            }
        } else {
            int count = cell.getAdjacentMines();

            if (count > 0) {
                setText(String.valueOf(count));
                setForeground(NUMBER_COLORS[count]);
            }
        }
    }
}
