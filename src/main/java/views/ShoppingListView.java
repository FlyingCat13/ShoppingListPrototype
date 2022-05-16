package views;

import controllers.ApplicationController;
import models.Item;
import models.ShoppingList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Iterator;

import static java.lang.Integer.parseInt;

public class ShoppingListView extends JPanel {
    ShoppingList shoppingList;
    ApplicationController controller;

    public ShoppingListView(ShoppingList initShoppingList, ApplicationController initController) {
        this.shoppingList = initShoppingList;
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
                controller.switchToShoppingListSelectionView();
            }
        });
        this.add(backPanel);

        for (Item item : this.shoppingList.getItems()) {
            GridLayout itemPanelLayout = new GridLayout(0, 3);
            itemPanelLayout.setHgap(3);
            JPanel itemPanel = new JPanel(itemPanelLayout);

            JCheckBox itemCheckBox = new JCheckBox(item.toString(), item.getIsChecked());
            itemPanel.add(itemCheckBox);
            itemCheckBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    item.setIsChecked(e.getStateChange() == ItemEvent.SELECTED);
                    try {
                        shoppingList.getItems().update(item);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });

            JButton itemEditButton = new JButton("Edit");
            itemPanel.add(itemEditButton);
            itemEditButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    enterItemDetails(item);
                    try {
                        shoppingList.getItems().update(item);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    itemCheckBox.setText(item.toString());
                }
            });

            JButton itemDeleteButton = new JButton("Delete");
            itemPanel.add(itemDeleteButton);
            itemDeleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        controller.deleteItem(item);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });

            this.add(itemPanel);
        }

        JButton createNewItemButton = new JButton("Create new item");
        this.add(createNewItemButton);
        createNewItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item newItem = new Item();
                enterItemDetails(newItem);
                if (!newItem.getName().equals("")) {
                    shoppingList.getItems().add(newItem);
                    controller.switchToShoppingListView();
                }
            }
        });
    }

    public static void enterItemDetails(Item item) {
        int quantity = parseInt(JOptionPane.showInputDialog(null,
                "Enter quantity of the item (leave as 0 if not applicable)", item.getQuantity()));
        String unit = JOptionPane.showInputDialog(null,
                "Enter unit of the quantity (e.g. kg, m) (leave blank if not applicable)", item.getUnit());
        String name = JOptionPane.showInputDialog(null, "Enter the name of the item",
                item.getName());

        item.setQuantity(quantity);
        item.setUnit(unit);
        item.setName(name);
    }
}
