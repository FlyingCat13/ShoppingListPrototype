package models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "items")
public class Item {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField
    private int quantity;
    @DatabaseField
    private String unit;
    @DatabaseField
    private boolean isChecked;
    @DatabaseField(canBeNull = false, foreign = true)
    private ShoppingList shoppingList;

    public void setName(String newName) {
        this.name = newName;
    }

    public String getName() {
        return this.name;
    }

    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setUnit(String newUnit) {
        this.unit = newUnit;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setIsChecked(boolean newIsChecked) {
        this.isChecked = newIsChecked;
    }

    public boolean getIsChecked() {
        return this.isChecked;
    }

    public ShoppingList getShoppingList() {
        return this.shoppingList;
    }

    public String toString() {
        if (this.quantity == 0) {
            return this.getName();
        } else {
            if (this.unit != null && !this.unit.equals("")) {
                return String.format("%d%s of %s", this.getQuantity(), this.getUnit(), this.getName());
            } else {
                return String.format("%d %s", this.getQuantity(), this.getName());
            }
        }
    }
}
