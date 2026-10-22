package yam.logic;

import java.util.Random;
import yam.model.Board;
import yam.util.Constants;

/**
 * Places the mines randomly after the first click, so the first click is always safe.
 * Algorithm: partial Fisher-Yates shuffle of the candidate cell indices, O(rows * cols).
 */
public final class MinePlacer {

    private MinePlacer() {
    }

    /**
     * Puts exactly {@code board.getMineCount()} mines on the board, never on (safeRow, safeCol).
     * When {@link Constants#SAFE_FIRST_CLICK_NEIGHBORS} is true and the board has enough room,
     * the 8 neighbors of the safe cell stay mine-free too.
     * Does not compute the numbers: call {@code board.calculateAdjacentCounts()} afterwards.
     */
    public static void placeMines(Board board, int safeRow, int safeCol, Random random) {
        // TODO: 1. Put the index (row * cols + col) of every cell outside the safe zone into an int[].
        //          If there are fewer candidates than mines, use only the clicked cell as the safe zone.
        //       2. Partial Fisher-Yates: for i in [0, mineCount) pick j = i + random.nextInt(n - i),
        //          swap(candidates, i, j), then set a mine on the cell candidates[i]
        //          (row = index / cols, col = index % cols).
    }

    /** True if (row, col) is the safe cell, or one of its neighbors when includeNeighbors is true. */
    private static boolean isInSafeZone(int row, int col, int safeRow, int safeCol, boolean includeNeighbors) {
        // TODO: Without neighbors: same position. With neighbors: |row - safeRow| <= 1 && |col - safeCol| <= 1.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private static void swap(int[] array, int i, int j) {
        // TODO: Swap array[i] and array[j].
    }
}
