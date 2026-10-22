package yam.model;

/**
 * What the player currently sees on a cell.
 */
public enum CellState {

    /** Not opened yet. */
    HIDDEN,

    /** Marked as a suspected mine; cannot be opened until the flag is removed. */
    FLAGGED,

    /** Opened: shows a number, an empty square, or a mine (only after losing). */
    REVEALED
}
