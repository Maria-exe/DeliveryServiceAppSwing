package bll;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author Tirlea Maria Cristina
 * class that models the orders
 */
public class Order implements Serializable, Comparable {
    private int orderID;
    private int clientID;
    private Date orderDate;
    private int totalPrice;
    private HashSet<MenuItem> orderedProducts;

    public Order(int orderID, int clientID) {
        this.orderID = orderID;
        this.clientID = clientID;
    }

    @Override
    public String toString (){
        return "Order [orderID = "+ this.getOrderID() + ", clientUID = " + this.clientID
                + ", orderDate = "+ this.orderDate + "] \n";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderID == order.orderID && clientID == order.clientID && Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, clientID, orderDate);
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate() {
        this.orderDate = new Date();
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void computeTotalPrice() {
        int totalPrice = 0;
        for (MenuItem mi: orderedProducts) {
            totalPrice += mi.computePrice();
        }
        this.totalPrice = totalPrice;
    }

    public HashSet<MenuItem> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(HashSet<MenuItem> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    /**
     * @return a string used to represent the order object in a text file
     */
    public String display(){
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: " + this.orderID + "\n");
        sb.append("Order date: " + this.getOrderDate().toString() +"\n");
        sb.append("Ordered products: " + "\n");
        for(MenuItem mi: this.orderedProducts) {
            if(mi instanceof BaseProduct) sb.append(((BaseProduct) mi).getTitle() + "\t" + ((BaseProduct) mi).getPrice() +"\n");
            else if (mi instanceof CompositeProduct) sb.append(((CompositeProduct) mi).getName() + "\t" + ((CompositeProduct) mi).getTotalPrice() +"\n");
        }
        sb.append("\n" + "Total price: ").append(this.getTotalPrice()+"\n\n");
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
       return(this.getOrderID() - ((Order)o).getOrderID());
    }
}
