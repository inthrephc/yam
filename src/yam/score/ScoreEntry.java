package yam.score;

import java.time.LocalDate;
import yam.model.Difficulty;
import yam.util.Constants;

/**
 * One leaderboard record. Natural order is best first: shorter time, then earlier date.
 * <p>
 * File format, one entry per line: {@code name;DIFFICULTY;timeSeconds;yyyy-MM-dd},
 * e.g. {@code Alice;BEGINNER;42;2026-10-22}.
 */
public class ScoreEntry implements Comparable<ScoreEntry> {

    private String playerName;
    private Difficulty difficulty;
    private int timeSeconds;
    private LocalDate date;

    /** The name is cleaned with {@link #sanitizeName(String)} before it is stored. */
    public ScoreEntry(String playerName, Difficulty difficulty, int timeSeconds, LocalDate date) {
        // TODO: Store sanitizeName(playerName) and the other fields.
    }

    public String getPlayerName() {
        // TODO: Return playerName.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Difficulty getDifficulty() {
        // TODO: Return difficulty.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getTimeSeconds() {
        // TODO: Return timeSeconds.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public LocalDate getDate() {
        // TODO: Return date.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Negative if this entry is better (shorter time; on a tie, earlier date). */
    @Override
    public int compareTo(ScoreEntry other) {
        // TODO: Compare timeSeconds with Integer.compare; on a tie compare the dates.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Converts the entry to one line of the leaderboard file (see the class comment). */
    public String toFileLine() {
        // TODO: Join name, difficulty.name(), timeSeconds and date with Constants.SCORE_FIELD_SEPARATOR.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Parses a line produced by {@link #toFileLine()}.
     *
     * @throws IllegalArgumentException if the line is malformed
     */
    public static ScoreEntry fromFileLine(String line) {
        // TODO: Split by Constants.SCORE_FIELD_SEPARATOR and expect 4 parts; parse them with
        //       Difficulty.valueOf, Integer.parseInt and LocalDate.parse. Wrap any parsing error
        //       (e.g. DateTimeParseException) in an IllegalArgumentException.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** For example {@code "Alice - 42 s (Beginner, 2026-10-22)"}. */
    @Override
    public String toString() {
        // TODO: Build the text shown above.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Makes a name safe to store: trimmed, without {@link Constants#SCORE_FIELD_SEPARATOR},
     * at most {@link Constants#MAX_PLAYER_NAME_LENGTH} characters, and
     * {@link Constants#DEFAULT_PLAYER_NAME} if the result is null or empty.
     */
    private static String sanitizeName(String name) {
        // TODO: Apply the rules above.
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
