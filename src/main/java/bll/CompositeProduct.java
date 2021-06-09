package bll;

import java.io.Serializable;
import java.util.HashSet;


public class CompositeProduct extends MenuItem implements Serializable {
    private String name;
    private HashSet<MenuItem> menuItems;
    private int totalPrice;

    public CompositeProduct(String name, HashSet<MenuItem> menuItems) {
        this.name = name;
        this.menuItems = menuItems;
        this.totalPrice =0;
    }

    public CompositeProduct(String name) {
        this.name = name;
        this.menuItems = new HashSet<>();
        this.totalPrice =0;
    }

    @Override
    public int computePrice() {
        int totalPrice =0;
        for (MenuItem i : this.menuItems) {
            totalPrice += i.computePrice();
        }
        this.totalPrice = totalPrice;
        return this.totalPrice;
    }

    public void addMenuItem(MenuItem newMenuItem) {
        this.menuItems.add(newMenuItem);
    }

    public void removeMenuItem(MenuItem itemToRemove) {
        this.menuItems.remove(itemToRemove);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<MenuItem> getMenuItems() {
        return this.menuItems;
    }

    public void setMenuItems(HashSet<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
