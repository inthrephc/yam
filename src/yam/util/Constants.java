package yam.util;

/**
 * Settings shared by several packages.
 * Constants used by a single class (colors, labels, ...) are declared in that class.
 */
public final class Constants {

    // ---- Board ----

    /** Row offsets of the 8 neighbors of a cell, paired by index with {@link #NEIGHBOR_COL_OFFSETS}. */
    public static final int[] NEIGHBOR_ROW_OFFSETS = {-1, -1, -1, 0, 0, 1, 1, 1};

    /** Column offsets of the 8 neighbors of a cell, paired by index with {@link #NEIGHBOR_ROW_OFFSETS}. */
    public static final int[] NEIGHBOR_COL_OFFSETS = {-1, 0, 1, -1, 1, -1, 0, 1};

    // ---- Game rules ----

    /** If true, the neighbors of the first opened cell are mine-free too, so the first click opens an area. */
    public static final boolean SAFE_FIRST_CLICK_NEIGHBORS = true;

    /** Flood fill used by Game: true = BFS with MyQueue, false = DFS with MyStack. */
    public static final boolean USE_BFS_FLOOD_FILL = true;

    /** The timer stops counting at this value (classic 3-digit display). */
    public static final int MAX_TIME_SECONDS = 999;

    // ---- Leaderboard ----

    /** Created in the working directory (the project folder when run from NetBeans). */
    public static final String LEADERBOARD_FILE = "leaderboard.txt";

    /** Number of best times kept for each difficulty. */
    public static final int LEADERBOARD_SIZE = 10;

    /** Field separator in the leaderboard file; it is removed from player names. */
    public static final String SCORE_FIELD_SEPARATOR = ";";

    public static final String DEFAULT_PLAYER_NAME = "Anonymous";

    public static final int MAX_PLAYER_NAME_LENGTH = 20;

    // ---- UI ----

    public static final String APP_TITLE = "Minesweeper";

    /** Width and height of one cell button, in pixels. */
    public static final int CELL_SIZE_PX = 30;

    /** How often the timer label is refreshed, in milliseconds. */
    public static final int CLOCK_REFRESH_MS = 200;

    private Constants() {
    }
}
