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
        Cell start = board.getCell(startRow, startCol);
        if (!start.isHidden() || start.isMine()) {
            return 0;
        }
        MyQueue<Cell> queue = new MyQueue<>();
        board.revealCell(startRow, startCol);
        queue.enqueue(start);
        int revealed = 1;
        while (!queue.isEmpty()) {
            Cell cell = queue.dequeue();
            if (cell.getAdjacentMines() == 0) {
                for (Cell neighbor : board.getNeighbors(cell.getRow(), cell.getCol())) {
                    if (!neighbor.isMine() && board.revealCell(neighbor.getRow(), neighbor.getCol())) {
                        // Revealing before enqueueing marks this cell as visited.
                        queue.enqueue(neighbor);
                        revealed++;
                    }
                }
            }
        }
        return revealed;
    }

    /**
     * Depth-first flood fill using {@link MyStack}. Same contract as {@link #revealBfs}.
     */
    public static int revealDfs(Board board, int startRow, int startCol) {
        Cell start = board.getCell(startRow, startCol);
        if (!start.isHidden() || start.isMine()) {
            return 0;
        }
        MyStack<Cell> stack = new MyStack<>();
        board.revealCell(startRow, startCol);
        stack.push(start);
        int revealed = 1;
        while (!stack.isEmpty()) {
            Cell cell = stack.pop();
            if (cell.getAdjacentMines() == 0) {
                for (Cell neighbor : board.getNeighbors(cell.getRow(), cell.getCol())) {
                    if (!neighbor.isMine() && board.revealCell(neighbor.getRow(), neighbor.getCol())) {
                        // Revealing before pushing marks this cell as visited.
                        stack.push(neighbor);
                        revealed++;
                    }
                }
            }
        }
        return revealed;
    }
}
