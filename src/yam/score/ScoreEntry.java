package yam.score;

import java.time.LocalDate;
import yam.model.Difficulty;
import yam.util.Constants;

/**
 * One leaderboard record. Natural order is best first: shorter time, then earlier date.
 * File format, one entry per line: {@code name;DIFFICULTY;timeSeconds;yyyy-MM-dd},
 * e.g. {@code Alice;BEGINNER;42;2026-10-22}.
 */
public class ScoreEntry implements Comparable<ScoreEntry> {

    private String playerName; //information about the player name
    private Difficulty difficulty; //information about the difficulty of the game
    private int timeSeconds; // information about the time in seconds
    private LocalDate date; // information about the date of the game

    /**
     * Creates a new score entry.
     * @param playerName is name of the player, it will be sanitized by {@link #sanitizeName(String)}.
     * @param difficulty is the difficulty of the game
     * @param timeSeconds is the time in seconds
     * @param date is the date of the game
     */
    public ScoreEntry(String playerName, Difficulty difficulty, int timeSeconds, LocalDate date) {
        if (playerName == null || difficulty == null || date == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        if (timeSeconds < 0) {
            throw new IllegalArgumentException("Time in seconds cannot be negative");
        }
        this.playerName = sanitizeName(playerName);
        this.difficulty = difficulty;
        this.timeSeconds = timeSeconds;
        this.date = date;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getTimeSeconds() {
        return timeSeconds;
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * Compares this entry to another one. Natural order is best first: shorter time, then earlier date.
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(ScoreEntry other) {
        int timeComparison = Integer.compare(this.timeSeconds, other.timeSeconds);
        if (timeComparison != 0) {
            return timeComparison;
        }
        return this.date.compareTo(other.date);
    }

    /**
     * Serializes this entry to a line suitable for storing in a file.
     * @return a line with the format {@code name;DIFFICULTY;timeSeconds;yyyy-MM-dd}
     */
    public String toFileLine() {
        /* Separator character between data columns on the same line */
        String sep = Constants.SCORE_FIELD_SEPARATOR;
        return playerName + sep + difficulty.name() + sep + timeSeconds + sep + date;
    }

    /**
     * Parses a line from a file and returns a new ScoreEntry object.
     * @param line is a line with the format {@code name;DIFFICULTY;timeSeconds;yyyy-MM-dd}
     * @return a new ScoreEntry object
     * @throws IllegalArgumentException if the line is null, empty, malformed, or has the wrong number of fields
     */
    public static ScoreEntry fromFileLine(String line) {
        if (line == null || line.isEmpty()) {
            throw new IllegalArgumentException("Line is null or empty");
        }
        // -1 keeps empty trailing fields. The separator ";" is not a regex special character, so split works as plain text.
        String[] parts = line.split(Constants.SCORE_FIELD_SEPARATOR, -1);
        if (parts.length != 4) {
            throw new IllegalArgumentException("Line has wrong number of fields");
        }
        try {
            String playerName = parts[0].trim();
            Difficulty difficulty = Difficulty.valueOf(parts[1].trim());
            int timeSeconds = Integer.parseInt(parts[2].trim());
            LocalDate date = LocalDate.parse(parts[3].trim());
            return new ScoreEntry(playerName, difficulty, timeSeconds, date);
        } catch (Exception e) {
            throw new IllegalArgumentException("Line is malformed: " + e.getMessage(), e);
        }
    }

    /** For example {@code "Alice - 42 s (Beginner, 2026-10-22)"}. */
    @Override
    public String toString() {
        return playerName + " - " + timeSeconds + " s (" + difficulty.getDisplayName() + ", " + date + ")";
    }

    /**
     * Makes a name safe to store: trimmed, without {@link Constants#SCORE_FIELD_SEPARATOR},
     * at most {@link Constants#MAX_PLAYER_NAME_LENGTH} characters, and
     * {@link Constants#DEFAULT_PLAYER_NAME} if the result is null or empty.
     */
    private static String sanitizeName(String name) {
        if (name == null) {
            return Constants.DEFAULT_PLAYER_NAME;
        }
        String sanitized = name.trim().replace(Constants.SCORE_FIELD_SEPARATOR, "")
                .replace('\r', ' ')
                .replace('\n', ' ')
                .replace('\t', ' ')
                .trim();
        if (sanitized.length() > Constants.MAX_PLAYER_NAME_LENGTH) {
            sanitized = sanitized.substring(0, Constants.MAX_PLAYER_NAME_LENGTH).trim();
        }
        return sanitized.isEmpty() ? Constants.DEFAULT_PLAYER_NAME : sanitized;
    }
}