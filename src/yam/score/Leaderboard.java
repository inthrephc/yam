package yam.score;

import yam.ds.MyLinkedList;
import yam.model.Difficulty;
import yam.util.Constants;

/**
 * Best times for every difficulty, saved to a text file between runs.
 * All entries are kept in ONE linked list sorted best first (via {@code MyLinkedList.addSorted});
 * at most {@link Constants#LEADERBOARD_SIZE} entries are kept per difficulty.
 */
public class Leaderboard {

    private MyLinkedList<ScoreEntry> entries;
    private String filePath;

    /** Creates an empty leaderboard backed by filePath. Call {@link #load()} to read the file. */
    public Leaderboard(String filePath) {
        // TODO: Store filePath and create an empty MyLinkedList.
    }

    /** True if a win in timeSeconds would enter the top list of this difficulty. */
    public boolean qualifies(Difficulty difficulty, int timeSeconds) {
        // TODO: True if this difficulty has fewer than Constants.LEADERBOARD_SIZE entries,
        //       or timeSeconds is strictly smaller than its worst (last) time.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Inserts the entry at its sorted position, then drops the worst entries of its difficulty beyond the limit. */
    public void addScore(ScoreEntry entry) {
        // TODO: entries.addSorted(entry, Comparator.naturalOrder()); then trim(entry.getDifficulty()).
    }

    /** Entries of one difficulty, best first, in a new list (the internal list is not exposed). */
    public MyLinkedList<ScoreEntry> getTopScores(Difficulty difficulty) {
        // TODO: Iterate entries and addLast the ones with this difficulty to a new list.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Removes every entry (the file is not changed until {@link #save()}). */
    public void clear() {
        // TODO: entries.clear().
    }

    /**
     * Replaces the current entries with the file content. A missing file means an empty
     * leaderboard; malformed lines are skipped.
     *
     * @return false if the file exists but could not be read
     */
    public boolean load() {
        // TODO: clear(); return true if the file does not exist. Otherwise read it line by line
        //       (BufferedReader in try-with-resources), addScore(ScoreEntry.fromFileLine(line)) for each
        //       line and skip lines that throw IllegalArgumentException. Return false on IOException.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Writes every entry to the file, one {@link ScoreEntry#toFileLine()} per line.
     *
     * @return false on an I/O error
     */
    public boolean save() {
        // TODO: Write with a BufferedWriter/PrintWriter in try-with-resources; return false on IOException.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Number of stored entries for one difficulty. */
    private int countEntries(Difficulty difficulty) {
        // TODO: Count the entries whose difficulty matches.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Removes the worst entries of a difficulty until at most LEADERBOARD_SIZE remain. */
    private void trim(Difficulty difficulty) {
        // TODO: Walk the list with an index and a counter for this difficulty; when the counter
        //       goes over LEADERBOARD_SIZE, entries.remove(index) (and do not advance the index).
    }
}
