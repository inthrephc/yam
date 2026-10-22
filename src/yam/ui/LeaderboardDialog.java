package yam.ui;

import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import yam.model.Difficulty;
import yam.score.Leaderboard;

/**
 * Modal "Best Times" window: one tab per difficulty, each with a table of the top scores.
 */
public class LeaderboardDialog extends JDialog {

    private static final String[] COLUMN_NAMES = {"#", "Name", "Time (s)", "Date"};

    private Leaderboard leaderboard;

    public LeaderboardDialog(Frame owner, Leaderboard leaderboard) {
        // TODO: First statement: super(owner, "Best Times", true). Then store leaderboard,
        //       initComponents(), pack() and setLocationRelativeTo(owner).
    }

    private void initComponents() {
        // TODO: Add a JTabbedPane with one tab per Difficulty.values() (title = getDisplayName(),
        //       content = createScoreTable(difficulty)) and a "Close" button that calls dispose().
    }

    /** Table of {@code leaderboard.getTopScores(difficulty)}; an empty table if there are no scores yet. */
    private JScrollPane createScoreTable(Difficulty difficulty) {
        // TODO: Fill an Object[][] with rank, name, time and date while iterating the list, put it in a
        //       non-editable JTable with COLUMN_NAMES and return the table inside a JScrollPane.
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
