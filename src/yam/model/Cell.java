package yam.model;

/**
 * One square of the board. A cell only knows its own data; anything that involves
 * neighbors is handled by {@link Board} and {@link yam.logic.Game}.
 */
public class Cell {

    private int row;
    private int col;
    private boolean mine;
    /** Mines among the 8 neighbors (0..8); only meaningful when this cell is not a mine. */
    private int adjacentMines;
    private CellState state;
    /** True only for the mine the player opened (drawn with a red background). */
    private boolean exploded;

    /** Creates a hidden, mine-free cell at (row, col). */
    public Cell(int row, int col) {
        // TODO: Store row and col; start HIDDEN, without a mine, 0 adjacent mines, not exploded.
    }

    public int getRow() {
        // TODO: Return row.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getCol() {
        // TODO: Return col.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isMine() {
        // TODO: Return mine.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setMine(boolean mine) {
        // TODO: Store the value.
    }

    public int getAdjacentMines() {
        // TODO: Return adjacentMines.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setAdjacentMines(int adjacentMines) {
        // TODO: Store the value.
    }

    public CellState getState() {
        // TODO: Return state.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isHidden() {
        // TODO: Return true if state is HIDDEN.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isFlagged() {
        // TODO: Return true if state is FLAGGED.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isRevealed() {
        // TODO: Return true if state is REVEALED.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isExploded() {
        // TODO: Return exploded.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setExploded(boolean exploded) {
        // TODO: Store the value.
    }

    /**
     * Opens the cell: {@code HIDDEN -> REVEALED}. A flagged or already revealed cell is not changed.
     * During play, call {@link Board#revealCell(int, int)} instead so the board can count revealed cells.
     *
     * @return true if the state changed
     */
    public boolean reveal() {
        // TODO: Change HIDDEN to REVEALED and return true; otherwise return false.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Toggles the flag: {@code HIDDEN <-> FLAGGED}. A revealed cell is not changed.
     *
     * @return true if the state changed
     */
    public boolean toggleFlag() {
        // TODO: Switch HIDDEN and FLAGGED and return true; return false for a REVEALED cell.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * One character for {@link Board#toDebugString(boolean)}. If the cell is revealed or
     * {@code revealAll} is true: {@code '*'} for a mine, {@code '.'} for 0, {@code '1'..'8'} otherwise.
     * Else: {@code 'F'} if flagged, {@code '#'} if hidden.
     */
    public char toDebugChar(boolean revealAll) {
        // TODO: Return the character described above.
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
