package models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class User {
    @DatabaseField(id = true)
    private String username;
    @DatabaseField(canBeNull = false)
    private String password;
    @ForeignCollectionField
    private ForeignCollection<ShoppingList> shoppingLists;

    public User() {

    }

    public User(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public String getPassword() {
        return this.password;
    }

    public ForeignCollection<ShoppingList> getShoppingLists() {
        return this.shoppingLists;
    }
}
