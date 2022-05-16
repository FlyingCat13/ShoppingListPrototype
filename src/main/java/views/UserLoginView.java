package views;

import controllers.ApplicationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UserLoginView extends JPanel {
    ApplicationController controller;

    public UserLoginView(ApplicationController initController) {
        super();

        this.controller = initController;

        GridLayout panelLayout = new GridLayout(0, 1);
        panelLayout.setHgap(3);
        panelLayout.setVgap(3);
        this.setLayout(panelLayout);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameTextField = new JTextField(20);
        usernameLabel.setLabelFor(usernameTextField);

        JPanel usernamePanel = new JPanel(new GridLayout(0, 2));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);

        JLabel passwordLabel = new JLabel("Password:");
        JTextField passwordField = new JPasswordField(20);
        passwordLabel.setLabelFor(passwordField);

        JPanel passwordPanel = new JPanel((new GridLayout(0, 2)));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        this.add(usernamePanel);
        this.add(passwordPanel);

        JButton submitButton = new JButton("Log in");
        this.add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.logIn(usernameTextField.getText(), passwordField.getText());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        JButton createNewUserButton = new JButton("Create new user");
        this.add(createNewUserButton);
        createNewUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.switchToCreateUserView();
            }
        });
    }
}
