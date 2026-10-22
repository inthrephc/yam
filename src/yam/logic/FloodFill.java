package yam.logic;

import yam.ds.MyQueue;
import yam.ds.MyStack;
import yam.model.Board;
import yam.model.Cell;

/**
 * Opens a region of the board starting from one safe cell (the "auto-reveal" of Minesweeper).
 * The grid is treated as a graph where every cell is connected to its 8 neighbors.
 * The search only spreads through cells with 0 adjacent mines; numbered cells are opened but
 * stop the spreading. Flagged cells and mines are never opened.
 * <p>
 * Both versions are iterative (no recursion, so no StackOverflowError on big boards) and must
 * open exactly the same cells; only the visiting order differs.
 */
public final class FloodFill {

    private FloodFill() {
    }

    /**
     * Breadth-first flood fill using {@link MyQueue}.
     *
     * @return number of cells newly revealed (0 if the start cell is not HIDDEN or is a mine)
     */
    public static int revealBfs(Board board, int startRow, int startCol) {
        // TODO: 1. If the start cell is not HIDDEN or is a mine, return 0.
        //       2. Reveal it with board.revealCell(...) and enqueue it into a MyQueue<Cell>.
        //       3. While the queue is not empty: dequeue a cell; if its adjacentMines is 0, reveal and
        //          enqueue every HIDDEN, non-mine neighbor (revealing before enqueueing marks it as visited).
        //       4. Return how many cells were revealed.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Depth-first flood fill using {@link MyStack}. Same contract as {@link #revealBfs}.
     */
    public static int revealDfs(Board board, int startRow, int startCol) {
        // TODO: Same steps as revealBfs, but push/pop a MyStack<Cell> instead of using a queue.
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
