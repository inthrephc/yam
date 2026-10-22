package yam.ui;

/**
 * Callbacks from {@link BoardPanel} to the owner of the game ({@link MainFrame}).
 * BoardPanel only reports which cell was clicked and how; it contains no game rules.
 */
public interface CellActionListener {

    /** Left click on (row, col). */
    void onReveal(int row, int col);

    /** Right click on (row, col). */
    void onToggleFlag(int row, int col);

    /** Middle click on (row, col). */
    void onChord(int row, int col);
}
