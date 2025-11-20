package com.buspass.gui.auth_gui;

import javax.swing.*;

import com.buspass.auth.UserLoginSession;
import com.buspass.gui.PanelSwitcher;

import java.awt.*;

/**
 * Parent panel that contains login/register cards and manages switching between them.
 */
public class AuthPanel extends JPanel implements PanelSwitcher {
    public static final String LOGIN = "login";
    public static final String REGISTER = "register";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);

    private final LoginPanel loginPanel;
    private final RegisterPanel registerPanel;

    public AuthPanel(UserLoginSession userLoginSession) {
        setLayout(new BorderLayout());

        loginPanel = new LoginPanel(userLoginSession);
        registerPanel = new RegisterPanel();

        // give child panels the callback so they can ask the parent to switch
        loginPanel.setPanelSwitcher(this);
        registerPanel.setPanelSwitcher(this);

        cards.add(loginPanel, LOGIN);
        cards.add(registerPanel, REGISTER);

        add(cards, BorderLayout.CENTER);

        // show login by default
        cardLayout.show(cards, LOGIN);
    }

    @Override
    public void showPanel(String name) {
        // ensure change happens on EDT
        if (SwingUtilities.isEventDispatchThread()) {
            cardLayout.show(cards, name);
        } else {
            SwingUtilities.invokeLater(() -> cardLayout.show(cards, name));
        }
    }
}
