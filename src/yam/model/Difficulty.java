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
        // TODO: Assign all fields.
    }

    /** Name shown in menus and in the leaderboard, e.g. "Beginner". */
    public String getDisplayName() {
        // TODO: Return displayName.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getRows() {
        // TODO: Return rows.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getCols() {
        // TODO: Return cols.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getMines() {
        // TODO: Return mines.
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
