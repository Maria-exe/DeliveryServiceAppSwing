package bll;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author Tirlea Maria Cristina
 */
public interface IDeliveryServiceProcessing {
    /**
     * method that uses streams and lambda expressions to import data from a .csv file
     * @invariant isWellFormed()
     */
    void importProducts();

    /**
     * method that adds a new product to the set of products named 'menuItems'
     * @param o object that has to be an instance of class MenuItem
     * @invariant isWellFormed()
     */
    void addProduct(Object o);

    /**
     * method that deletes a product from the set of products named 'menuItems'
     * @param o o object that has to be an instance of class MenuItem
     * @invariant isWellFormed()
     */
    void deleteProduct(Object o);

    /**
     * method that creates a new composite product
     * @param name the name given to the created CompositeProduct
     * @param selectedMenuItems the products that are added to the CompositeProduct
     * @return the newly created CompositeProduct
     */
    CompositeProduct createCompositeProduct(String name,HashSet<MenuItem> selectedMenuItems);

    /**
     * method for modifying an existing product
     * @param index index of product to be modified
     * @param l list of new attributes of the product
     */
    void modifyProduct(int index,List l);

    /**
     * method used to serialize the data from deliveryService before closing a window
     * @invariant isWellFormed()
     */
    void storeData();

    /**
     * the method used to add a new order
     * @param clientUID ID of client that placed the order
     * @param orderedItems set with the items that have been ordered by the client
     * @return the ordered that was just created
     * @invariant isWellFormed()
     */
    Order placeOrder(int clientUID, HashSet<MenuItem> orderedItems);

    /**
     * method used to generate a bill for an order, as a .txt file
     * @param o the order for which a bill is generated
     * @invariant isWellFormed()
     */
    void createBill (Order o);

    /**
     * method for generating a report consisting of all the orders performed
     * between a given start hour and a given end hour regardless the date.
     * @param startHour the start hour used to filter the data
     * @param endHour  the end hour used to filter the data
     * @invariant isWellFormed()
     */
    void generateReportOne(int startHour, int endHour);

    /**
     * method for generating a report consisting of all the products ordered more than a specified number of times so far.
     * @param minNumberOfPurchases  the criteria used to filter tha data for the report
     * @invariant isWellFormed()
     */
    void generateReportTwo(int minNumberOfPurchases);

    /**
     * method for generating a report consisting of all the clients that have ordered more than a specified number of times and the value
     * of the order was higher than a specified amount.
     * @param minNumberOfPurchases the specified number of times a client has ordered
     * @param minValue the minimum amount of the orders
     * @invariant isWellFormed()
     */
    void generateReportThree(int minNumberOfPurchases, int minValue);

    /**
     * method for generating a report consisting of all the products ordered within a specified day with the number of times they have
     * been ordered
     * @param date date used to filter the data showed in the report
     * @invariant isWellFormed()
     */
    void generateReportFour(Date date);
}
