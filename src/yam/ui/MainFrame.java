package yam.ui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.JMenuBar;
import javax.swing.Timer;
import yam.logic.Game;
import yam.model.Difficulty;
import yam.model.GameState;
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
        setTitle(Constants.APP_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        leaderboard = new Leaderboard(Constants.LEADERBOARD_FILE);
        if (!leaderboard.load()) {
            JOptionPane.showMessageDialog(this, "Could not load best times.",
                    "Best Times", JOptionPane.WARNING_MESSAGE);
        }
        initComponents();
        initClockTimer();
        startNewGame(Difficulty.BEGINNER);
        setLocationRelativeTo(null);
    }

    /** Adds the menu bar, HeaderPanel (NORTH) and BoardPanel (CENTER). */
    private void initComponents() {
        setLayout(new BorderLayout());
        setJMenuBar(createMenuBar());
        headerPanel = new HeaderPanel(e -> startNewGame(game.getDifficulty()));
        boardPanel = new BoardPanel(this);
        add(headerPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
    }

    /**
     * Menu "Game": New (F2), Beginner / Intermediate / Expert (radio items in one ButtonGroup),
     * Best Times..., Exit. Menu "Help": How to Play, About.
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newItem = new JMenuItem("New");
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
        newItem.addActionListener(e -> startNewGame(game.getDifficulty()));
        gameMenu.add(newItem);
        gameMenu.addSeparator();

        ButtonGroup difficulties = new ButtonGroup();
        for (Difficulty difficulty : Difficulty.values()) {
            JRadioButtonMenuItem item = new JRadioButtonMenuItem(
                    difficulty.getDisplayName(), difficulty == Difficulty.BEGINNER);
            item.addActionListener(e -> startNewGame(difficulty));
            difficulties.add(item);
            gameMenu.add(item);
        }

        gameMenu.addSeparator();
        JMenuItem bestTimes = new JMenuItem("Best Times...");
        bestTimes.addActionListener(e -> showLeaderboard());
        gameMenu.add(bestTimes);
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> {
            clockTimer.stop();
            dispose();
        });
        gameMenu.add(exit);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem howToPlay = new JMenuItem("How to Play");
        howToPlay.addActionListener(e -> showHowToPlay());
        helpMenu.add(howToPlay);
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> showAbout());
        helpMenu.add(about);
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        return menuBar;
    }

    /** Creates the javax.swing.Timer that refreshes the timer label every CLOCK_REFRESH_MS. */
    private void initClockTimer() {
        clockTimer = new Timer(Constants.CLOCK_REFRESH_MS,
                e -> headerPanel.setTime(game.getElapsedSeconds()));
    }

    /** Starts a new game of the given difficulty and resizes the window to fit the new board. */
    public void startNewGame(Difficulty difficulty) {
        clockTimer.stop();
        game = new Game(difficulty);
        gameOverHandled = false;
        boardPanel.buildGrid(difficulty.getRows(), difficulty.getCols());
        refreshView();
        pack();
    }

    @Override
    public void onReveal(int row, int col) {
        game.reveal(row, col);
        afterPlayerAction();
    }

    @Override
    public void onToggleFlag(int row, int col) {
        game.toggleFlag(row, col);
        afterPlayerAction();
    }

    @Override
    public void onChord(int row, int col) {
        game.chord(row, col);
        afterPlayerAction();
    }

    /** Runs after every click: refresh the view, start/stop the clock, detect the end of the game. */
    private void afterPlayerAction() {
        refreshView();
        if (game.getState() == GameState.PLAYING && !clockTimer.isRunning()) {
            clockTimer.start();
        }
        if (game.isOver() && !gameOverHandled) {
            clockTimer.stop();
            handleGameOver();
        }
    }

    /** Copies the game state into the header (counter, time, face) and the board panel. */
    private void refreshView() {
        headerPanel.setMineCounter(game.getRemainingMines());
        headerPanel.setTime(game.getElapsedSeconds());
        headerPanel.setFace(game.getState());
        boardPanel.refresh(game.getBoard(), game.getState());
    }

    /**
     * After a win with a qualifying time: ask for the player's name, add and save the score,
     * then show the leaderboard. After a loss nothing else is needed (the board already shows the mines).
     */
    private void handleGameOver() {
        gameOverHandled = true;
        if (game.getState() == GameState.WON
                && leaderboard.qualifies(game.getDifficulty(), game.getElapsedSeconds())) {
            String name = promptPlayerName();
            leaderboard.addScore(new ScoreEntry(name, game.getDifficulty(),
                    game.getElapsedSeconds(), LocalDate.now()));
            if (!leaderboard.save()) {
                JOptionPane.showMessageDialog(this, "Could not save best times.",
                        "Best Times", JOptionPane.ERROR_MESSAGE);
            }
            showLeaderboard();
        }
    }

    /** Asks for the player's name with JOptionPane; returns Constants.DEFAULT_PLAYER_NAME if cancelled or blank. */
    private String promptPlayerName() {
        String name = JOptionPane.showInputDialog(this, "Your name:",
                "New Best Time", JOptionPane.QUESTION_MESSAGE);
        return name == null || name.trim().isEmpty()
                ? Constants.DEFAULT_PLAYER_NAME : name.trim();
    }

    private void showLeaderboard() {
        new LeaderboardDialog(this, leaderboard).setVisible(true);
    }

    private void showHowToPlay() {
        JOptionPane.showMessageDialog(this, HOW_TO_PLAY_TEXT,
                "How to Play", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(this, ABOUT_TEXT,
                "About", JOptionPane.INFORMATION_MESSAGE);
    }
}
