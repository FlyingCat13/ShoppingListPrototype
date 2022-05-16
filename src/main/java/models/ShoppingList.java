package models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "shopping_lists")
public class ShoppingList {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @ForeignCollectionField
    private ForeignCollection<Item> items;
    @DatabaseField(canBeNull = false, foreign = true)
    private User user;

    public ShoppingList() {

    }

    public ShoppingList(String initName, User initUser) {
        this.setName(initName);
        this.setUser(initUser);
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getName() {
        return this.name;
    }

    public void setUser(User newUser) {
        this.user = newUser;
    }

    public ForeignCollection<Item> getItems() {
        return this.items;
    }
}
