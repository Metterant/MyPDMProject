package com.buspass.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;
import com.buspass.db.QueryExecutionModule;
import com.buspass.queries.UserService;

public class MainGUI extends JFrame {

    private JTextField queryField;
    private JButton runButton;
    private JTable resultTable;
    private JTextArea consoleArea;

    public MainGUI() {
        setTitle("BusPass Database Tool");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ---------------- TOP PANEL ----------------
        JPanel topPanel = new JPanel(new BorderLayout());
        queryField = new JTextField();
        runButton = new JButton("Execute");

        topPanel.add(new JLabel(" SQL Query: "), BorderLayout.WEST);
        topPanel.add(queryField, BorderLayout.CENTER);
        topPanel.add(runButton, BorderLayout.EAST);

        // ---------------- TABLE (FOR SELECT QUERIES) ----------------
        resultTable = new JTable();
        JScrollPane tableScroll = new JScrollPane(resultTable);

        // ---------------- CONSOLE AREA ----------------
        consoleArea = new JTextArea();
        consoleArea.setEditable(false);
        JScrollPane consoleScroll = new JScrollPane(consoleArea);
        consoleScroll.setPreferredSize(new Dimension(800, 120));

        add(topPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        add(consoleScroll, BorderLayout.SOUTH);


        runButton.addActionListener(e -> executeSQL());
    }

    private void executeSQL() {
        String sql = queryField.getText().trim();
        if (sql.isEmpty()) {
            consoleArea.setText("Please enter a SQL query.");
            return;
        }

        try {
            if (sql.toLowerCase().startsWith("select")) {
                runSelectQuery(sql);
            } else {
                runUpdateQuery(sql);
            }
        } catch (Exception ex) {
            consoleArea.setText("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void runSelectQuery(String sql) {
        UserService userService = new UserService();

        List<Map<String, Object>> results = userService.getAllUsers();

        if (results.isEmpty()) {
            resultTable.setModel(new DefaultTableModel());
            consoleArea.setText("Query executed successfully. No rows found.");
            return;
        }

        // Extract column names
        Map<String, Object> firstRow = results.get(0);
        String[] columns = firstRow.keySet().toArray(new String[0]);

        // Table model
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // Fill rows
        for (Map<String, Object> row : results) {
            Object[] rowData = row.values().toArray();
            model.addRow(rowData);
        }

        resultTable.setModel(model);
        consoleArea.setText("SELECT executed successfully. Rows returned: " + results.size());
    }

    private void runUpdateQuery(String sql) {
        int affected = QueryExecutionModule.executeUpdate(sql);
        consoleArea.setText("UPDATE executed. Rows affected: " + affected);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}
