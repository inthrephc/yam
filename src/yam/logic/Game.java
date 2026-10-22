package yam.logic;

import java.util.Random;
import yam.model.Board;
import yam.model.Cell;
import yam.model.Difficulty;
import yam.model.GameState;
import yam.util.Constants;

/**
 * Rules of one Minesweeper game: reveal, flag, chord, safe first click, timer, win and loss.
 * The UI calls an action method and then reads the new state back through the getters.
 * Actions are ignored once the game is over; a new game is a new Game object.
 */
public class Game {

    private Board board;
    private Difficulty difficulty;
    private GameState state;
    private int flagsPlaced;
    private long startTimeMillis;
    private long endTimeMillis;
    private Random random;

    /** Creates a READY game with a random mine layout. */
    public Game(Difficulty difficulty) {
        // TODO: Delegate to this(difficulty, new Random()).
    }

    /** Creates a READY game. Pass {@code new Random(seed)} to get the same mine layout every time (for testing). */
    public Game(Difficulty difficulty, Random random) {
        // TODO: Store difficulty and random, create a Board with the difficulty's rows, cols and mines,
        //       set state to READY and flagsPlaced to 0.
    }

    /**
     * Left-click action.
     * <ul>
     * <li>Ignored if the game is over, (row, col) is out of bounds, or the cell is flagged.</li>
     * <li>On an already revealed cell it performs a {@link #chord}.</li>
     * <li>The first reveal places the mines (never under this cell) and starts the timer.</li>
     * <li>Opening a mine loses the game; otherwise the cell (and its empty region) is opened
     * and the win condition is checked.</li>
     * </ul>
     */
    public void reveal(int row, int col) {
        // TODO: Apply the guards above, call startGame(row, col) while READY,
        //       then openCell(row, col) and checkWin().
    }

    /**
     * Right-click action: {@code HIDDEN <-> FLAGGED}, keeping flagsPlaced up to date.
     * Allowed while READY or PLAYING; it does not place mines or start the timer.
     */
    public void toggleFlag(int row, int col) {
        // TODO: Ignore if the game is over or out of bounds. If cell.toggleFlag() returns true,
        //       add 1 to flagsPlaced when the cell is now flagged, otherwise subtract 1.
    }

    /**
     * Chord (middle click, or left click on a number): if the number on a revealed cell equals
     * the number of flags around it, open all its other hidden neighbors at once.
     * A wrong flag can make this open a mine and lose the game.
     */
    public void chord(int row, int col) {
        // TODO: Only while PLAYING, on a REVEALED cell with adjacentMines > 0 and
        //       board.countAdjacentFlags(row, col) == adjacentMines: openCell(...) every HIDDEN neighbor
        //       (stop as soon as the game is lost), then checkWin().
    }

    public Board getBoard() {
        // TODO: Return board.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Difficulty getDifficulty() {
        // TODO: Return difficulty.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public GameState getState() {
        // TODO: Return state.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** True if the game is WON or LOST. */
    public boolean isOver() {
        // TODO: Return state.isOver().
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getFlagsPlaced() {
        // TODO: Return flagsPlaced.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Value for the mine counter: total mines minus flags placed (can be negative). */
    public int getRemainingMines() {
        // TODO: Return board.getMineCount() - flagsPlaced.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Seconds since the first reveal: 0 while READY, counting while PLAYING, frozen once the game is over.
     * Never larger than {@link Constants#MAX_TIME_SECONDS}.
     */
    public int getElapsedSeconds() {
        // TODO: Use startTimeMillis with System.currentTimeMillis() (PLAYING) or endTimeMillis (game over),
        //       convert to whole seconds and cap at Constants.MAX_TIME_SECONDS.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** First reveal: place the mines around the safe cell, compute the numbers, start the timer. */
    private void startGame(int safeRow, int safeCol) {
        // TODO: MinePlacer.placeMines(board, safeRow, safeCol, random); board.calculateAdjacentCounts();
        //       startTimeMillis = System.currentTimeMillis(); state = PLAYING.
    }

    /** Opens one hidden cell: a mine ends the game, a safe cell is opened with the flood fill. */
    private void openCell(int row, int col) {
        // TODO: If the cell is a mine, handleLoss(cell). Otherwise call FloodFill.revealBfs or
        //       FloodFill.revealDfs depending on Constants.USE_BFS_FLOOD_FILL.
    }

    /** Switches to WON when every safe cell is revealed. */
    private void checkWin() {
        // TODO: If state is still PLAYING and board.allSafeCellsRevealed(), call handleWin().
    }

    private void handleWin() {
        // TODO: state = WON; endTimeMillis = now; board.flagAllMines();
        //       flagsPlaced = mine count (so the counter shows 0).
    }

    private void handleLoss(Cell explodedCell) {
        // TODO: state = LOST; endTimeMillis = now; explodedCell.setExploded(true); board.revealAllMines().
    }
}
