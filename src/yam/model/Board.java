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
        // TODO: Validate the arguments, store them, and fill cells[r][c] with new Cell(r, c).
    }

    public int getRows() {
        // TODO: Return rows.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getCols() {
        // TODO: Return cols.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getMineCount() {
        // TODO: Return mineCount.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getRevealedCount() {
        // TODO: Return revealedCount.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Number of cells without a mine: {@code rows * cols - mineCount}. */
    public int getSafeCellCount() {
        // TODO: Return the value described above.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** True if (row, col) is inside the grid. */
    public boolean isInBounds(int row, int col) {
        // TODO: Check 0 <= row < rows and 0 <= col < cols.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * @throws IndexOutOfBoundsException if (row, col) is outside the grid
     */
    public Cell getCell(int row, int col) {
        // TODO: Check isInBounds, then return cells[row][col].
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Returns the cells around (row, col) that exist: 8 inside the grid, 5 on an edge, 3 in a corner.
     */
    public MyLinkedList<Cell> getNeighbors(int row, int col) {
        // TODO: For i in 0..7, check (row + Constants.NEIGHBOR_ROW_OFFSETS[i], col + Constants.NEIGHBOR_COL_OFFSETS[i])
        //       with isInBounds and addLast the cell to a new MyLinkedList.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Number of mines among the neighbors of (row, col). */
    public int countAdjacentMines(int row, int col) {
        // TODO: Count the neighbors where isMine() is true.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Number of flagged cells among the neighbors of (row, col). Used by chord. */
    public int countAdjacentFlags(int row, int col) {
        // TODO: Count the neighbors where isFlagged() is true.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Stores countAdjacentMines(...) in every non-mine cell. Call once, right after the mines are placed. */
    public void calculateAdjacentCounts() {
        // TODO: Loop over all cells; for each non-mine cell call setAdjacentMines(countAdjacentMines(r, c)).
    }

    /**
     * Opens exactly one cell (no flood fill, no mine check) and keeps revealedCount up to date.
     *
     * @return true if the cell was HIDDEN and is now REVEALED
     */
    public boolean revealCell(int row, int col) {
        // TODO: Call getCell(row, col).reveal(); if it returns true, increment revealedCount.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** True when every safe cell is revealed: the win condition. */
    public boolean allSafeCellsRevealed() {
        // TODO: Compare revealedCount with getSafeCellCount().
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Reveals every mine that is not flagged. Called when the game is lost;
     * revealedCount is not updated (the game is already over).
     */
    public void revealAllMines() {
        // TODO: For every cell that is a mine and not flagged, call cell.reveal().
    }

    /** Flags every mine that is still hidden. Called when the game is won. */
    public void flagAllMines() {
        // TODO: For every hidden mine, call cell.toggleFlag().
    }

    /**
     * Text view of the grid for debugging without the GUI: one line per row, one
     * {@link Cell#toDebugChar(boolean)} per cell, separated by spaces.
     */
    public String toDebugString(boolean revealAll) {
        // TODO: Build the text with a StringBuilder (add '\n' after each row).
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
