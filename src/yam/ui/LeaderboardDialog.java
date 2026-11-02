package yam.ui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import yam.ds.MyLinkedList;
import yam.model.Difficulty;
import yam.score.Leaderboard;
import yam.score.NameSearch;
import yam.score.ScoreEntry;
/**
 * Modal "Best Times" window: one tab per difficulty, each with a table of the top scores.
 */
public class LeaderboardDialog extends JDialog {

    private static final String[] COLUMN_NAMES = {"#", "Name", "Time (s)", "Date"};

    private Leaderboard leaderboard;

    public LeaderboardDialog(Frame owner, Leaderboard leaderboard) {
        super(owner, "Best Times", true);
        this.leaderboard = leaderboard;
        initComponents();
        pack();
        setLocationRelativeTo(owner);
    }


    /**
     * Initializes the components of the dialog, including the search field, algorithm selection, tabs for each difficulty, and the close button.
     */
    private void initComponents() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(8, 8));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(8, 8, 8, 8));

        searchField = new JTextField(20);
        algorithmComboBox = new JComboBox<>(ALGORITHMS);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> refreshTabs());
        searchField.addActionListener(e -> refreshTabs());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Name:"));
        searchPanel.add(searchField);
        searchPanel.add(algorithmComboBox);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        tabs = new JTabbedPane();
        refreshTabs();
        add(tabs, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }


    /**
     * Refreshes the tabs with the latest score data.
     */
    private void refreshTabs() {
        int selected = Math.max(0, tabs.getSelectedIndex());
        tabs.removeAll();
        for (Difficulty difficulty : Difficulty.values()) {
            tabs.addTab(difficulty.getDisplayName(), createScoreTable(difficulty));
        }
        tabs.setSelectedIndex(selected);
    }


    /**
     * Checks if the given name matches the search keyword based on the selected search algorithm.
     * @param name The name to check against the search keyword.
     * @return True if the name matches the search keyword; false otherwise.
     */
    private boolean matchesSearch(String name) {
        String keyword = searchField.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            return true;
        }
        String text = name.toLowerCase();
        if (algorithmBox.getSelectedIndex() == 0) {
            return NameSearch.bruteForce(text, keyword) >= 0;
        }
        return NameSearch.kmp(text, keyword) >= 0;
    }

    /**
     * Creates a JScrollPane containing a JTable of the top scores for the specified difficulty, filtered by the search keyword.
     * @param difficulty The difficulty level for which to display the top scores.
     * @return A JScrollPane containing the JTable of top scores.
     */
    private JScrollPane createScoreTable(Difficulty difficulty) {
        MyLinkedList<ScoreEntry> topScores = leaderboard.getTopScores(difficulty);
        int matchedCount = 0;
        for (ScoreEntry entry : topScores) {
            if (matchesSearch(entry.getPlayerName())) {
                matchedCount++;
            }
        }
        Object[][] data = new Object[matchedCount][COLUMN_NAMES.length];
        int rank = 0;
        int row = 0;
        for (ScoreEntry entry : topScores) {
            rank++;
            if (matchesSearch(entry.getPlayerName())) {
                data[row][0] = rank;
                data[row][1] = entry.getPlayerName();
                data[row][2] = entry.getTimeSeconds();
                data[row][3] = entry.getDate();
                row++;
            }
        }
        JTable table = new JTable(new ScoreTableModel(data));
        table.setRowHeight(ROW_HEIGHT);
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(center);
        table.getColumnModel().getColumn(2).setCellRenderer(center);
        table.getColumnModel().getColumn(3).setCellRenderer(center);
        table.getColumnModel().getColumn(0).setMaxWidth(40);

        table.setPreferredScrollableViewportSize(new Dimension(400, ROW_HEIGHT * Math.min(matchedCount, MAX_VISIBLE_ROWS)));
        return new JScrollPane(table);
    }

    /**
     * A table model for displaying score data in a JTable.
     */
    private static class ScoreTableModel extends AbstractTableModel {
        private Object[][] data;
        ScoreTableModel(Object[][] data) {
            this.data = data;
        }
        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return COLUMN_NAMES.length;
        }

        @Override
        public String getColumnName(int column) {
            return COLUMN_NAMES[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }
}
