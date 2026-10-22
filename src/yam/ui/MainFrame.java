package yam.ui;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.Timer;
import yam.logic.Game;
import yam.model.Difficulty;
import yam.score.Leaderboard;
import yam.score.ScoreEntry;
import yam.util.Constants;

/**
 * Main window and UI controller. Owns the current {@link Game} and the {@link Leaderboard},
 * forwards clicks from {@link BoardPanel} to the Game, then refreshes the view from the Game's state.
 */
public class MainFrame extends JFrame implements CellActionListener {

    private static final String HOW_TO_PLAY_TEXT
            = "Goal: open every cell that does not contain a mine.\n"
            + "\n"
            + "Left click: open a cell. A number shows how many of its 8 neighbors are mines.\n"
            + "Right click: place or remove a flag on a suspected mine.\n"
            + "Middle click (or left click) on a number: if that many flags are around it,\n"
            + "all its other neighbors are opened.\n"
            + "\n"
            + "The first click is always safe. Opening a mine ends the game.";

    private static final String ABOUT_TEXT
            = "Minesweeper clone\nCSD201 - Data Structures and Algorithms team project.";

    private Game game;
    private Leaderboard leaderboard;
    private HeaderPanel headerPanel;
    private BoardPanel boardPanel;
    private Timer clockTimer;
    /** Makes sure handleGameOver() runs only once per game. */
    private boolean gameOverHandled;

    /** Builds the window, loads the leaderboard and starts a Beginner game. */
    public MainFrame() {
        // TODO: setTitle(Constants.APP_TITLE); setDefaultCloseOperation(EXIT_ON_CLOSE); setResizable(false);
        //       leaderboard = new Leaderboard(Constants.LEADERBOARD_FILE) and load() it;
        //       initComponents(); initClockTimer(); startNewGame(Difficulty.BEGINNER); setLocationRelativeTo(null).
    }

    /** Adds the menu bar, HeaderPanel (NORTH) and BoardPanel (CENTER). */
    private void initComponents() {
        // TODO: setJMenuBar(createMenuBar());
        //       headerPanel = new HeaderPanel(e -> startNewGame(game.getDifficulty()));
        //       boardPanel = new BoardPanel(this); add both using BorderLayout.
    }

    /**
     * Menu "Game": New (F2), Beginner / Intermediate / Expert (radio items in one ButtonGroup),
     * Best Times..., Exit. Menu "Help": How to Play, About.
     */
    private JMenuBar createMenuBar() {
        // TODO: Build the menus above and connect each item to the matching method.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Creates the javax.swing.Timer that refreshes the timer label every CLOCK_REFRESH_MS. */
    private void initClockTimer() {
        // TODO: clockTimer = new Timer(Constants.CLOCK_REFRESH_MS, e -> headerPanel.setTime(game.getElapsedSeconds())).
    }

    /** Starts a new game of the given difficulty and resizes the window to fit the new board. */
    public void startNewGame(Difficulty difficulty) {
        // TODO: Stop clockTimer; game = new Game(difficulty); gameOverHandled = false;
        //       boardPanel.buildGrid(rows, cols); refreshView(); pack().
    }

    @Override
    public void onReveal(int row, int col) {
        // TODO: game.reveal(row, col); afterPlayerAction().
    }

    @Override
    public void onToggleFlag(int row, int col) {
        // TODO: game.toggleFlag(row, col); afterPlayerAction().
    }

    @Override
    public void onChord(int row, int col) {
        // TODO: game.chord(row, col); afterPlayerAction().
    }

    /** Runs after every click: refresh the view, start/stop the clock, detect the end of the game. */
    private void afterPlayerAction() {
        // TODO: refreshView(); start clockTimer if the state is PLAYING and it is not running yet;
        //       if game.isOver() and !gameOverHandled: stop clockTimer, then handleGameOver().
    }

    /** Copies the game state into the header (counter, time, face) and the board panel. */
    private void refreshView() {
        // TODO: headerPanel.setMineCounter(game.getRemainingMines()); headerPanel.setTime(...);
        //       headerPanel.setFace(game.getState()); boardPanel.refresh(game.getBoard(), game.getState()).
    }

    /**
     * After a win with a qualifying time: ask for the player's name, add and save the score,
     * then show the leaderboard. After a loss nothing else is needed (the board already shows the mines).
     */
    private void handleGameOver() {
        // TODO: gameOverHandled = true. If WON and leaderboard.qualifies(difficulty, time):
        //       name = promptPlayerName(); leaderboard.addScore(new ScoreEntry(name, difficulty, time, LocalDate.now()));
        //       leaderboard.save(); showLeaderboard().
    }

    /** Asks for the player's name with JOptionPane; returns Constants.DEFAULT_PLAYER_NAME if cancelled or blank. */
    private String promptPlayerName() {
        // TODO: Use JOptionPane.showInputDialog and handle null / blank input.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private void showLeaderboard() {
        // TODO: new LeaderboardDialog(this, leaderboard).setVisible(true).
    }

    private void showHowToPlay() {
        // TODO: JOptionPane.showMessageDialog(this, HOW_TO_PLAY_TEXT, "How to Play", INFORMATION_MESSAGE).
    }

    private void showAbout() {
        // TODO: JOptionPane.showMessageDialog(this, ABOUT_TEXT, "About", INFORMATION_MESSAGE).
    }
}
