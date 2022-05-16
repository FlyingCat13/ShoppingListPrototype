package controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import models.Item;
import models.ShoppingList;
import models.User;
import views.CreateUserView;
import views.ShoppingListSelectionView;
import views.ShoppingListView;
import views.UserLoginView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

public class ApplicationController {
    private final String USER_LOGIN = "User login";
    private final String CREATE_USER = "Create user";
    private final String SHOPPING_LIST_SELECTION = "Shopping list selection";
    private final String SHOPPING_LIST = "Shopping list";

    private User loggedInUser;
    private ShoppingList selectedShoppingList;

    private ConnectionSource databaseConnectionSource;
    private Dao<User, String> userDao;
    private Dao<ShoppingList, Integer> shoppingListDao;
    private Dao<Item, Integer> itemDao;
    private JFrame window;
    private JPanel mainPane;

    private ShoppingListSelectionView shoppingListSelectionView;
    private ShoppingListView shoppingListView;

    public ApplicationController() throws SQLException {
        this.databaseConnectionSource = new JdbcConnectionSource("jdbc:sqlite:app.db");

        TableUtils.createTableIfNotExists(this.databaseConnectionSource, User.class);
        TableUtils.createTableIfNotExists(this.databaseConnectionSource, ShoppingList.class);
        TableUtils.createTableIfNotExists(this.databaseConnectionSource, Item.class);

        userDao = DaoManager.createDao(this.databaseConnectionSource, User.class);
        shoppingListDao = DaoManager.createDao(this.databaseConnectionSource, ShoppingList.class);
        itemDao = DaoManager.createDao(this.databaseConnectionSource, Item.class);

        this.window = new JFrame("Christmas shopping list");
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setSize(600, 800);

        this.mainPane = new JPanel(new CardLayout());
        this.mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.window.setContentPane(this.mainPane);

        this.mainPane.add(new UserLoginView(this), USER_LOGIN);
        this.mainPane.add(new CreateUserView(this), CREATE_USER);

        this.window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    databaseConnectionSource.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                super.windowClosing(e);
            }
        });
    }

    public void start() {
        this.switchToLoginView();
        this.window.pack();
        this.window.setVisible(true);
    }

    public void logIn(String username, String password) throws SQLException {
        User userLoggingIn = userDao.queryForId(username);
        if (userLoggingIn != null && userLoggingIn.getPassword().equals(password)) {
            this.loggedInUser = userLoggingIn;
            this.switchToShoppingListSelectionView();
        }
    }

    public void createNewUser(String username, String password) throws SQLException {
        User newUser = new User(username, password);
        userDao.create(newUser);
    }

    public void selectShoppingList(ShoppingList shoppingList) {
        this.selectedShoppingList = shoppingList;
        this.switchToShoppingListView();
    }

    public void deleteShoppingList(ShoppingList shoppingList) throws SQLException {
        shoppingListDao.delete(shoppingList);
        userDao.update(loggedInUser);
        this.switchToShoppingListSelectionView();
    }

    public void deleteItem(Item item) throws SQLException {
        itemDao.delete(item);
        shoppingListDao.update(selectedShoppingList);
        this.switchToShoppingListView();
    }

    public void switchToLoginView() {
        ((CardLayout) this.mainPane.getLayout()).show(this.mainPane, USER_LOGIN);
        this.window.pack();
    }

    public void switchToCreateUserView() {
        ((CardLayout) this.mainPane.getLayout()).show(this.mainPane, CREATE_USER);
        this.window.pack();
    }

    /**
     * Serves as a switching-to and refreshing function for shopping list selection view.
     */
    public void switchToShoppingListSelectionView() {
        if (this.shoppingListSelectionView != null) {
            this.mainPane.remove(this.shoppingListSelectionView);
        }
        this.shoppingListSelectionView = new ShoppingListSelectionView(loggedInUser, this);
        this.mainPane.add(this.shoppingListSelectionView, SHOPPING_LIST_SELECTION);
        ((CardLayout) this.mainPane.getLayout()).show(this.mainPane, SHOPPING_LIST_SELECTION);
        this.window.pack();
    }

    /**
     * Serves as a switching-to and refreshing function for shopping list view.
     */
    public void switchToShoppingListView() {
        if (this.shoppingListView != null) {
            this.mainPane.remove(this.shoppingListView);
        }
        this.shoppingListView = new ShoppingListView(selectedShoppingList, this);
        this.mainPane.add(this.shoppingListView, SHOPPING_LIST);
        ((CardLayout) this.mainPane.getLayout()).show(this.mainPane, SHOPPING_LIST);
        this.window.pack();
    }
}
