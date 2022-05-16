package views;

import controllers.ApplicationController;
import models.ShoppingList;
import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ShoppingListSelectionView extends JPanel {
    User user;
    ApplicationController controller;

    public ShoppingListSelectionView(User initUser, ApplicationController initController) {
        super();

        this.user = initUser;
        this.controller = initController;

        GridLayout panelLayout = new GridLayout(0, 1);
        panelLayout.setHgap(3);
        panelLayout.setVgap(3);
        this.setLayout(panelLayout);

        JPanel backPanel = new JPanel(new BorderLayout(3, 3));
        JButton backButton = new JButton("Back");
        backPanel.add(backButton, BorderLayout.WEST);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.switchToLoginView();
            }
        });
        this.add(backPanel);

        for (ShoppingList shoppingList : this.user.getShoppingLists()) {
            GridLayout shoppingListPanelLayout = new GridLayout(0, 3);
            shoppingListPanelLayout.setHgap(3);
            JPanel shoppingListPanel = new JPanel(shoppingListPanelLayout);

            JLabel shoppingListLabel = new JLabel(shoppingList.getName());
            shoppingListPanel.add(shoppingListLabel);

            JButton shoppingListViewButton = new JButton("View");
            shoppingListPanel.add(shoppingListViewButton);
            shoppingListViewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.selectShoppingList(shoppingList);
                }
            });

            JButton shoppingListDeleteButton = new JButton("Delete");
            shoppingListPanel.add(shoppingListDeleteButton);
            shoppingListDeleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        controller.deleteShoppingList(shoppingList);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });

            this.add(shoppingListPanel);
        }

        JButton createNewListButton = new JButton("Create a new shopping list");
        this.add(createNewListButton);
        createNewListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newShoppingListName = JOptionPane.showInputDialog(null,
                        "Enter new shopping list name", "New shopping list",
                        JOptionPane.INFORMATION_MESSAGE);
                if (!newShoppingListName.equals("")) {
                    ShoppingList newShoppingList = new ShoppingList(newShoppingListName, user);
                    user.getShoppingLists().add(newShoppingList);
                    controller.switchToShoppingListSelectionView();
                }
            }
        });
    }
}
