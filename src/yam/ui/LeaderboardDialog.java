package yam.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import javax.swing.JButton;
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
import yam.score.ScoreEntry;

public class LeaderboardDialog extends JDialog {

    private static final String[] COLUMN_NAMES = {"#", "Name", "Time (s)", "Date"};
    private static final int ROW_HEIGHT = 25;
    private static final int MAX_VISIBLE_ROWS = 10;

    private Leaderboard leaderboard;
    private JTextField searchField;
    private JTabbedPane tabs;

    public LeaderboardDialog(Frame owner, Leaderboard leaderboard) {
        super(owner, "Best Times", true);
        this.leaderboard = leaderboard;
        initComponents();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(8, 8));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(8, 8, 8, 8));

        // Khởi tạo ô tìm kiếm và nút bấm
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        
        searchButton.addActionListener(e -> refreshTabs());
        searchField.addActionListener(e -> refreshTabs());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Name:"));
        searchPanel.add(searchField);
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

    private void refreshTabs() {
        int selected = Math.max(0, tabs.getSelectedIndex());
        tabs.removeAll();
        for (Difficulty difficulty : Difficulty.values()) {
            tabs.addTab(difficulty.getDisplayName(), createScoreTable(difficulty));
        }
        tabs.setSelectedIndex(selected);
    }

    private boolean matchesSearch(String name) {
        String keyword = searchField.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            return true;
        }
        return name.toLowerCase().contains(keyword);
    }

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
            rank++; // Rank vẫn giữ nguyên thứ hạng gốc
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

        table.setPreferredScrollableViewportSize(new Dimension(400, ROW_HEIGHT * Math.min(matchedCount > 0 ? matchedCount : 1, MAX_VISIBLE_ROWS)));
        return new JScrollPane(table);
    }

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