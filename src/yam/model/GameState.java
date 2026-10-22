package yam.model;

/**
 * Life cycle of one game: {@code READY -> PLAYING -> WON | LOST}.
 */
public enum GameState {

    /** New board, nothing opened yet: mines are not placed and the timer is not running. */
    READY,

    /** The first cell has been opened and the timer is running. */
    PLAYING,

    /** Every safe cell has been opened. */
    WON,

    /** A mine has been opened. */
    LOST;

    /** Returns true for WON and LOST. */
    public boolean isOver() {
        // TODO: Return true if this state is WON or LOST.
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
