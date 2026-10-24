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
        this.row = row;
        this.col = col;
        this.mine = false;
        this.adjacentMines = 0;
        this.state = CellState.HIDDEN;
        this.exploded = false;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    public CellState getState() {
        return state;
    }

    public boolean isHidden() {
        return state == CellState.HIDDEN;
    }

    public boolean isFlagged() {
        return state == CellState.FLAGGED;
    }

    public boolean isRevealed() {
        return state == CellState.REVEALED;
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    /**
     * Opens the cell: {@code HIDDEN -> REVEALED}. A flagged or already revealed cell is not changed.
     * During play, call {@link Board#revealCell(int, int)} instead so the board can count revealed cells.
     *
     * @return true if the state changed
     */
    public boolean reveal() {
        if (state != CellState.HIDDEN) {
            return false;
        }
        state = CellState.REVEALED;
        return true;
    }

    /**
     * Toggles the flag: {@code HIDDEN <-> FLAGGED}. A revealed cell is not changed.
     *
     * @return true if the state changed
     */
    public boolean toggleFlag() {
        if (state == CellState.HIDDEN) {
            state = CellState.FLAGGED;
            return true;
        }
        if (state == CellState.FLAGGED) {
            state = CellState.HIDDEN;
            return true;
        }
        return false;
    }

    /**
     * One character for {@link Board#toDebugString(boolean)}. If the cell is revealed or
     * {@code revealAll} is true: {@code '*'} for a mine, {@code '.'} for 0, {@code '1'..'8'} otherwise.
     * Else: {@code 'F'} if flagged, {@code '#'} if hidden.
     */
    public char toDebugChar(boolean revealAll) {
        if (revealAll || state == CellState.REVEALED) {
            if (mine) {
                return '*';
            }
            if (adjacentMines == 0) {
                return '.';
            }
            return (char) ('0' + adjacentMines);
        }
        return state == CellState.FLAGGED ? 'F' : '#';
    }
}
