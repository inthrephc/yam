package yam.score;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
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

    /**
     * Creates a new leaderboard that will be saved to the given file path.
     * @param filePath is the path to the file where the leaderboard will be saved and loaded from
     */
    public Leaderboard(String filePath) {
        this.filePath = filePath;
        this.entries = new MyLinkedList<>();
    }

    /**
     * Checks if a score qualifies for the leaderboard: either there are less than {@link Constants#LEADERBOARD_SIZE}
     * @param difficulty is the difficulty of the game
     * @param timeSeconds is the time in seconds
     * @return true if the score qualifies for the leaderboard, false otherwise 
     */
    public boolean qualifies(Difficulty difficulty, int timeSeconds) {
        if (difficulty == null || timeSeconds < 0) {
            throw new IllegalArgumentException("Arguments cannot be null or negative");
        }
        int count = 0; // Count the number of entries for the specified difficulty
        int worstTime = 0; // Track the worst time for the specified difficulty
        /* Count entries of the specified difficulty and track the worst time */
        for (ScoreEntry entry : entries) {
            if (entry.getDifficulty() == difficulty) {
                count++;
                worstTime = entry.getTimeSeconds();
            }
        }
        /* Return true if the entry qualifies for the leaderboard */
        return count < Constants.LEADERBOARD_SIZE || timeSeconds < worstTime;
    }

    /**
     * Adds a new score entry to the leaderboard if it qualifies. If the leaderboard already has
     * @param entry is the score entry to be added
     */
    public void addScore(ScoreEntry entry) {
        if (entry == null) {
            throw new IllegalArgumentException("Entry cannot be null");
        }
        /* Add the entry in sorted order and then trim the leaderboard for its difficulty */
        entries.addSorted(entry, Comparator.<ScoreEntry>naturalOrder());
        trim(entry.getDifficulty());
    }

    /** Entries of one difficulty, best first, in a new list (the internal list is not exposed). */
    public MyLinkedList<ScoreEntry> getTopScores(Difficulty difficulty) {
        MyLinkedList<ScoreEntry> topScores = new MyLinkedList<>();
        for (ScoreEntry entry : entries) {
            if (entry.getDifficulty() == difficulty) {
                topScores.addLast(entry);
            }
        }
        return topScores;
    }

    /** Removes every entry (the file is not changed until {@link #save()}). */
    public void clear() {
        entries.clear();
    }

    /**
     * Replaces the current entries with the file content. A missing file means an empty
     * leaderboard; malformed lines are skipped.
     *
     * @return false if the file exists but could not be read
     */
    public boolean load() {
        clear();
        if (filePath == null) {
            throw new IllegalArgumentException("File path cannot be null");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return true; // Missing file means empty leaderboard
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        continue; // Skip empty lines
                    }
                    try {
                        addScore(ScoreEntry.fromFileLine(line));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Skipping malformed line: " + line);
                    }
                }
            }
            return true; // Successfully loaded
        } catch (IOException e) {
            System.err.println("Error reading leaderboard file: " + e.getMessage());
            return false; // I/O error
        }
        return true;
    }

    /**
     * Writes every entry to the file, one {@link ScoreEntry#toFileLine()} per line.
     *
     * @return false on an I/O error
     */
    public boolean save() {
        if (filePath == null) {
            throw new IllegalArgumentException("File path cannot be null");
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            for (ScoreEntry entry : entries) {
                writer.write(entry.toFileLine());
                writer.newLine();
            }
            return true; // Successfully saved
        } catch (IOException e) {
            System.err.println("Error writing leaderboard file: " + e.getMessage());
            return false; // I/O error
        }
    }

    /** Number of stored entries for one difficulty. */
    private int countEntries(Difficulty difficulty) {
        int count = 0;
        for (ScoreEntry entry : entries) {
            if (entry.getDifficulty() == difficulty) {
                count++;
            }
        }
        return count;
    }

    /**
     * Trims the leaderboard to at most {@link Constants#LEADERBOARD_SIZE} entries for the given difficulty.
     * The worst entries are removed first.
     * @param difficulty
     */
    private void trim(Difficulty difficulty) {
        if (countEntries(difficulty) <= Constants.LEADERBOARD_SIZE) {
            return; // No trimming needed
        }
        int index = 0; // Start from the beginning of the list
        int seen = 0; // Count how many entries of the specified difficulty have been seen
        while (index < entries.size()) {
            ScoreEntry entry = entries.get(index);
            if (entry.getDifficulty() == difficulty) {
                seen++;
                /* If we have seen more entries of this difficulty than the allowed size, remove the current one     */
                if (seen > Constants.LEADERBOARD_SIZE) {
                    entries.remove(index); // Remove the worst entry
                    continue; // Do not increment index, as the list has shifted
                }
            }
            index++;
        }
    }
}
