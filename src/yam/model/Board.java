package yam.model;

import yam.ds.MyLinkedList;
import yam.util.Constants;

/**
 * The 2D grid of cells plus grid-level queries (bounds, neighbors, counts).
 * Board knows nothing about turns, timer or win/lose: those rules live in {@link yam.logic.Game}.
 */
public class Board {

    private int rows;
    private int cols;
    private int mineCount;
    private Cell[][] cells;
    /** Number of REVEALED cells, kept up to date by {@link #revealCell(int, int)}. */
    private int revealedCount;

    /**
     * Creates a board where every cell is hidden and mine-free.
     * Mines are placed later by {@code MinePlacer}, after the first click.
     *
     * @throws IllegalArgumentException if rows or cols is not positive, or mineCount is not
     *                                  in {@code [1, rows * cols - 1]}
     */
    public Board(int rows, int cols, int mineCount) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("rows and cols must be positive: " + rows + "x" + cols);
        }
        if (mineCount < 1 || mineCount > rows * cols - 1) {
            throw new IllegalArgumentException(
                    "mineCount must be in [1, " + (rows * cols - 1) + "]: " + mineCount);
        }
        this.rows = rows;
        this.cols = cols;
        this.mineCount = mineCount;
        this.revealedCount = 0;
        this.cells = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                cells[r][c] = new Cell(r, c);
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getMineCount() {
        return mineCount;
    }

    public int getRevealedCount() {
        return revealedCount;
    }

    /** Number of cells without a mine: {@code rows * cols - mineCount}. */
    public int getSafeCellCount() {
        return rows * cols - mineCount;
    }

    /** True if (row, col) is inside the grid. */
    public boolean isInBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    /**
     * @throws IndexOutOfBoundsException if (row, col) is outside the grid
     */
    public Cell getCell(int row, int col) {
        if (!isInBounds(row, col)) {
            throw new IndexOutOfBoundsException("Cell (" + row + ", " + col + ") is outside the "
                    + rows + "x" + cols + " board");
        }
        return cells[row][col];
    }

    /**
     * Returns the cells around (row, col) that exist: 8 inside the grid, 5 on an edge, 3 in a corner.
     */
    public MyLinkedList<Cell> getNeighbors(int row, int col) {
        MyLinkedList<Cell> neighbors = new MyLinkedList<>();
        for (int i = 0; i < Constants.NEIGHBOR_ROW_OFFSETS.length; i++) {
            int r = row + Constants.NEIGHBOR_ROW_OFFSETS[i];
            int c = col + Constants.NEIGHBOR_COL_OFFSETS[i];
            if (isInBounds(r, c)) {
                neighbors.addLast(cells[r][c]);
            }
        }
        return neighbors;
    }

    /** Number of mines among the neighbors of (row, col). */
    public int countAdjacentMines(int row, int col) {
        int count = 0;
        for (Cell neighbor : getNeighbors(row, col)) {
            if (neighbor.isMine()) {
                count++;
            }
        }
        return count;
    }

    /** Number of flagged cells among the neighbors of (row, col). Used by chord. */
    public int countAdjacentFlags(int row, int col) {
        int count = 0;
        for (Cell neighbor : getNeighbors(row, col)) {
            if (neighbor.isFlagged()) {
                count++;
            }
        }
        return count;
    }

    /** Stores countAdjacentMines(...) in every non-mine cell. Call once, right after the mines are placed. */
    public void calculateAdjacentCounts() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!cells[r][c].isMine()) {
                    cells[r][c].setAdjacentMines(countAdjacentMines(r, c));
                }
            }
        }
    }

    /**
     * Opens exactly one cell (no flood fill, no mine check) and keeps revealedCount up to date.
     *
     * @return true if the cell was HIDDEN and is now REVEALED
     */
    public boolean revealCell(int row, int col) {
        if (getCell(row, col).reveal()) {
            revealedCount++;
            return true;
        }
        return false;
    }

    /** True when every safe cell is revealed: the win condition. */
    public boolean allSafeCellsRevealed() {
        return revealedCount == getSafeCellCount();
    }

    /**
     * Reveals every mine that is not flagged. Called when the game is lost;
     * revealedCount is not updated (the game is already over).
     */
    public void revealAllMines() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = cells[r][c];
                if (cell.isMine() && !cell.isFlagged()) {
                    cell.reveal();
                }
            }
        }
    }

    /** Flags every mine that is still hidden. Called when the game is won. */
    public void flagAllMines() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = cells[r][c];
                if (cell.isMine() && cell.isHidden()) {
                    cell.toggleFlag();
                }
            }
        }
    }

    /**
     * Text view of the grid for debugging without the GUI: one line per row, one
     * {@link Cell#toDebugChar(boolean)} per cell, separated by spaces.
     */
    public String toDebugString(boolean revealAll) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (c > 0) {
                    sb.append(' ');
                }
                sb.append(cells[r][c].toDebugChar(revealAll));
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
