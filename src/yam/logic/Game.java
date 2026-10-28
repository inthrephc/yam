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
        this(difficulty, new Random());
    }

    /** Creates a READY game. Pass {@code new Random(seed)} to get the same mine layout every time (for testing). */
    public Game(Difficulty difficulty, Random random) {
        if (difficulty == null || random == null) {
            throw new IllegalArgumentException("difficulty and random must not be null");
        }
        this.difficulty = difficulty;
        this.random = random;
        board = new Board(difficulty.getRows(), difficulty.getCols(), difficulty.getMines());
        state = GameState.READY;
        flagsPlaced = 0;
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
        if (isOver() || !board.isInBounds(row, col) || board.getCell(row, col).isFlagged()) {
            return;
        }
        if (board.getCell(row, col).isRevealed()) {
            chord(row, col);
            return;
        }
        if (state == GameState.READY) {
            startGame(row, col);
        }
        openCell(row, col);
        checkWin();
    }

    /**
     * Right-click action: {@code HIDDEN <-> FLAGGED}, keeping flagsPlaced up to date.
     * Allowed while READY or PLAYING; it does not place mines or start the timer.
     */
    public void toggleFlag(int row, int col) {
        if (isOver() || !board.isInBounds(row, col)) {
            return;
        }
        Cell cell = board.getCell(row, col);
        if (cell.toggleFlag()) {
            flagsPlaced += cell.isFlagged() ? 1 : -1;
        }
    }

    /**
     * Chord (middle click, or left click on a number): if the number on a revealed cell equals
     * the number of flags around it, open all its other hidden neighbors at once.
     * A wrong flag can make this open a mine and lose the game.
     */
    public void chord(int row, int col) {
        if (state != GameState.PLAYING || !board.isInBounds(row, col)) {
            return;
        }
        Cell cell = board.getCell(row, col);
        if (!cell.isRevealed() || cell.getAdjacentMines() == 0
                || board.countAdjacentFlags(row, col) != cell.getAdjacentMines()) {
            return;
        }
        for (Cell neighbor : board.getNeighbors(row, col)) {
            if (neighbor.isHidden()) {
                openCell(neighbor.getRow(), neighbor.getCol());
                if (state == GameState.LOST) {
                    return;
                }
            }
        }
        checkWin();
    }

    public Board getBoard() {
        return board;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public GameState getState() {
        return state;
    }

    /** True if the game is WON or LOST. */
    public boolean isOver() {
        return state.isOver();
    }

    public int getFlagsPlaced() {
        return flagsPlaced;
    }

    /** Value for the mine counter: total mines minus flags placed (can be negative). */
    public int getRemainingMines() {
        return board.getMineCount() - flagsPlaced;
    }

    /**
     * Seconds since the first reveal: 0 while READY, counting while PLAYING, frozen once the game is over.
     * Never larger than {@link Constants#MAX_TIME_SECONDS}.
     */
    public int getElapsedSeconds() {
        if (state == GameState.READY) {
            return 0;
        }
        long now = isOver() ? endTimeMillis : System.currentTimeMillis();
        long seconds = Math.max(0L, (now - startTimeMillis) / 1000L);
        return (int) Math.min(seconds, Constants.MAX_TIME_SECONDS);
    }

    /** First reveal: place the mines around the safe cell, compute the numbers, start the timer. */
    private void startGame(int safeRow, int safeCol) {
        MinePlacer.placeMines(board, safeRow, safeCol, random);
        board.calculateAdjacentCounts();
        startTimeMillis = System.currentTimeMillis();
        state = GameState.PLAYING;
    }

    /** Opens one hidden cell: a mine ends the game, a safe cell is opened with the flood fill. */
    private void openCell(int row, int col) {
        Cell cell = board.getCell(row, col);
        if (!cell.isHidden()) {
            return;
        }
        if (cell.isMine()) {
            handleLoss(cell);
        } else if (Constants.USE_BFS_FLOOD_FILL) {
            FloodFill.revealBfs(board, row, col);
        } else {
            FloodFill.revealDfs(board, row, col);
        }
    }

    /** Switches to WON when every safe cell is revealed. */
    private void checkWin() {
        if (state == GameState.PLAYING && board.allSafeCellsRevealed()) {
            handleWin();
        }
    }

    private void handleWin() {
        state = GameState.WON;
        endTimeMillis = System.currentTimeMillis();
        board.flagAllMines();
        flagsPlaced = board.getMineCount();
    }

    private void handleLoss(Cell explodedCell) {
        state = GameState.LOST;
        endTimeMillis = System.currentTimeMillis();
        explodedCell.setExploded(true);
        board.revealAllMines();
    }
}
