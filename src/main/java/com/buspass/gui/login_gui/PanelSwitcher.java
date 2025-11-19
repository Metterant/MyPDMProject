package com.buspass.gui.login_gui;

/** Simple callback interface used by child panels to request a panel switch from their parent. */
public interface PanelSwitcher {
    void showPanel(String name);
}
