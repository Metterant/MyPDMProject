package com.buspass.gui.app_gui.admin;

import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

import javax.swing.JTable;
import javax.swing.JTextArea;

public class MiddlePanel {
    public void setTableContents(JTable table, List<Map<String, Object>> results) {
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

        table.setModel(model);
    }
}
