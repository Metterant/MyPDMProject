package com.buspass.gui;

import javax.swing.*;
import java.awt.*;

import com.buspass.auth.UserLoginSession;
import com.buspass.gui.app_gui.MainPanel;
import com.buspass.gui.app_gui.MyAccountPanel;
import com.buspass.gui.auth_gui.AuthPanel;


public class AppPanel extends JPanel implements PanelSwitcher {
    public static final String AUTH = "auth";
    public static final String MAIN = "main";
    public static final String MY_ACCOUNT = "my_account";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);

    private final AuthPanel authPanel;
    private final MainPanel mainPanel;
    private final MyAccountPanel myAccountPanel;

    public AppPanel(UserLoginSession userLoginSession) {
        setLayout(new BorderLayout());

        authPanel = new AuthPanel(userLoginSession);
        mainPanel = new MainPanel(userLoginSession);
        myAccountPanel = new MyAccountPanel(userLoginSession);

        // give child panels the callback so they can ask the parent to switch
        authPanel.setPanelSwitcher(this);
        mainPanel.setPanelSwitcher(this);
        myAccountPanel.setPanelSwitcher(authPanel);

        cards.add(authPanel, AUTH);
        cards.add(mainPanel, MAIN);
        cards.add(myAccountPanel, MY_ACCOUNT);

        add(cards, BorderLayout.CENTER);

        // show login by default
        cardLayout.show(cards, AUTH);
        // cardLayout.show(cards, MAIN);
    }

    @Override
    public void showPanel(String name) {
        if (name == MAIN)
            mainPanel.updatePanel();
        if (name == MY_ACCOUNT)
            myAccountPanel.updatePanel();

        // ensure change happens on EDT
        if (SwingUtilities.isEventDispatchThread()) {
            cardLayout.show(cards, name);
        } else {
            SwingUtilities.invokeLater(() -> cardLayout.show(cards, name));
        }
    }
}
