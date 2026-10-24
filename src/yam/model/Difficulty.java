package yam.model;

/**
 * Board presets of classic Minesweeper.
 */
public enum Difficulty {

    BEGINNER("Beginner", 9, 9, 10),
    INTERMEDIATE("Intermediate", 16, 16, 40),
    EXPERT("Expert", 16, 30, 99);

    private String displayName;
    private int rows;
    private int cols;
    private int mines;

    Difficulty(String displayName, int rows, int cols, int mines) {
        this.displayName = displayName;
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
    }

    /** Name shown in menus and in the leaderboard, e.g. "Beginner". */
    public String getDisplayName() {
        return displayName;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getMines() {
        return mines;
    }
}
