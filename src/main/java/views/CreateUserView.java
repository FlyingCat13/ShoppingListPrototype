package views;

import controllers.ApplicationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CreateUserView extends JPanel {
    ApplicationController controller;

    public CreateUserView(ApplicationController initController) {
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

        JLabel passwordConfirmLabel = new JLabel("Confirm password:");
        JTextField passwordConfirmField = new JPasswordField(20);
        passwordConfirmLabel.setLabelFor(passwordConfirmField);

        JPanel passwordConfirmPanel = new JPanel((new GridLayout(0, 2)));
        passwordConfirmPanel.add(passwordConfirmLabel);
        passwordConfirmPanel.add(passwordConfirmField);

        this.add(usernamePanel);
        this.add(passwordPanel);
        this.add(passwordConfirmPanel);

        JButton createNewUserButton = new JButton("Create");
        this.add(createNewUserButton);
        createNewUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (passwordField.getText().equals(passwordConfirmField.getText())) {
                    try {
                        controller.createNewUser(usernameTextField.getText(), passwordField.getText());
                        controller.switchToLoginView();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
    }
}
