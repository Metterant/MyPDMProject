/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buspass.gui.app_gui.etc;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import com.buspass.auth.UserLoginSession;
import com.buspass.queries.TripQuery;

/**
 *
 * @author USER
 */
public class TicketTableCellEditor extends DefaultCellEditor {

    private UserLoginSession userLoginSession;
    private TripQuery tripQuery;

    public TicketTableCellEditor(UserLoginSession userLoginSession, TripQuery tripQuery) {
        super(new JCheckBox());
        this.userLoginSession = userLoginSession;
        this.tripQuery = tripQuery;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // extract TripID from the row (prefer column named "TripID", fallback to column 0)
        Integer tripId = null;
        try {
            int cols = table.getColumnCount();
            int tripCol = -1;
            for (int c = 0; c < cols; c++) {
                String name = table.getColumnName(c);
                if (name != null && name.equalsIgnoreCase("TripID")) { tripCol = c; break; }
            }
            if (tripCol == -1 && cols > 0) tripCol = 0;
            Object v = table.getValueAt(row, tripCol);
            if (v != null) tripId = Integer.parseInt(v.toString());
        } catch (Exception ex) {
            // ignore
        }

        TicketAction ticketAction = new TicketAction(new TripInject(userLoginSession, tripQuery, tripId));
        return ticketAction;
    }
}
