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
        int rows = board.getRows();
        int cols = board.getCols();
        int mineCount = board.getMineCount();

        int[] candidates = collectCandidates(rows, cols, safeRow, safeCol, Constants.SAFE_FIRST_CLICK_NEIGHBORS);
        if (candidates.length < mineCount) {
            // Board too crowded: only the clicked cell stays safe.
            candidates = collectCandidates(rows, cols, safeRow, safeCol, false);
        }

        int n = candidates.length;
        for (int i = 0; i < mineCount; i++) {
            int j = i + random.nextInt(n - i);
            swap(candidates, i, j);
            board.getCell(candidates[i] / cols, candidates[i] % cols).setMine(true);
        }
    }

    /** Indices (row * cols + col) of every cell outside the safe zone. */
    private static int[] collectCandidates(int rows, int cols, int safeRow, int safeCol, boolean includeNeighbors) {
        int[] buffer = new int[rows * cols];
        int n = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!isInSafeZone(r, c, safeRow, safeCol, includeNeighbors)) {
                    buffer[n++] = r * cols + c;
                }
            }
        }
        int[] result = new int[n];
        System.arraycopy(buffer, 0, result, 0, n);
        return result;
    }

    /** True if (row, col) is the safe cell, or one of its neighbors when includeNeighbors is true. */
    private static boolean isInSafeZone(int row, int col, int safeRow, int safeCol, boolean includeNeighbors) {
        if (!includeNeighbors) {
            return row == safeRow && col == safeCol;
        }
        return Math.abs(row - safeRow) <= 1 && Math.abs(col - safeCol) <= 1;
    }

    private static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
