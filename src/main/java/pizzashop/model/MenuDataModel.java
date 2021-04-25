package pizzashop.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MenuDataModel {
    private final SimpleStringProperty menuItem;
    private final SimpleIntegerProperty quantity;
    private final SimpleDoubleProperty price;

    public MenuDataModel(String menuItem, Integer quantity, Double price) {
        this.menuItem = new SimpleStringProperty(menuItem);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
    }

    public String getMenuItem() {
        return menuItem.get();
    }

    public SimpleStringProperty menuItemProperty() {
        return menuItem;
    }

    public void setMenuItem(String menuItem) {
        this.menuItem.set(menuItem);
    }

    public Integer getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity.set(quantity);
    }

    public Double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(Double price) {
        this.price.set(price);
    }

    public String toString() {
        return this.menuItem.get() + "," + this.price.get();
    }
}
