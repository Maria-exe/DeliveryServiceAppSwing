package bll;

import data.FileWriter;
import data.ReportWriter;
import data.Serializator;
import model.User;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing, Serializable {
    private Map<Order, HashSet<MenuItem>> orders = new HashMap<>();
    private HashSet<MenuItem> menuItems = new HashSet<>();
    private List<User> users = new ArrayList<>();
    private int ordersCount = 0;
    private Order lastOrder;

    /**
     * invariant for validating the operations implemented in this class
     * @return value returned by the conditions
     */
    public boolean isWellFormed() {
        if(orders == null)
            return false;
        if(menuItems == null)
            return false;
        if(users == null)
            return false;
        return true;
    }

    @Override
    public void importProducts() {
        assert isWellFormed();
        try {
            Path path = Paths.get("products.csv");
            BufferedReader buffer = Files.newBufferedReader(path);
            buffer.lines().skip(1).distinct()
                    .map(line -> Arrays.asList(line.split(",")))
                    .forEach(list -> menuItems.add(BaseProduct.createBaseProduct(list)));
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        menuItems = (HashSet<MenuItem>) menuItems.stream().filter(distinctByName(mi -> ((BaseProduct)mi).getTitle())).collect(Collectors.toSet());

    }

    @Override
    public void addProduct(Object o){
        assert isWellFormed();
        if (o instanceof BaseProduct){
            menuItems.add((BaseProduct)o);
        }
        else if (o instanceof CompositeProduct) {
            menuItems.add((CompositeProduct)o);
        }
    }

    @Override
    public void deleteProduct(Object o) {
        assert isWellFormed();
        if (o instanceof BaseProduct){
            menuItems.remove((BaseProduct)o);
        }
        else if (o instanceof CompositeProduct) {
            menuItems.remove((CompositeProduct)o);
        }
    }

    @Override
    public CompositeProduct createCompositeProduct(String name, HashSet<MenuItem> selectedMenuItems) {
        assert !name.isBlank() || menuItems.isEmpty();
        CompositeProduct cp = new CompositeProduct(name,menuItems);
        return cp;
    }

    @Override
    public void modifyProduct(int index, List list) {
        assert isWellFormed();
        assert menuItems.size() <= index;
        BaseProduct bp = BaseProduct.createBaseProduct(list);
        int i = 0;
        for (MenuItem menuItem : menuItems) {
            if (i  == index) menuItem = bp; i++;
        }
    }

    public Map<Order, HashSet<MenuItem>> getOrders() {
        return orders;
    }

    public void setOrders(Map<Order, HashSet<MenuItem>> orders) {
        this.orders = orders;
    }

    public HashSet<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(HashSet<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void serializeMenu(){}

    public void deserializeMenu(){}

    public HashSet<MenuItem> filterProducts (int minLimit, int maxLimit, String criteria ){
        List<String> crit = Arrays.asList("Rating","Calories","Proteins","Fats", "Sodium", "Price");
        assert crit.contains(criteria);
        Set<MenuItem> result;
        System.out.println(criteria);
        switch (criteria){
            case "Rating" -> result = menuItems.stream()
                    .filter(i-> ((BaseProduct) i).getRating() >=minLimit && ((BaseProduct) i).getRating() <= maxLimit).collect(Collectors.toSet());
            case "Calories" -> result = menuItems.stream()
                    .filter(i-> ((BaseProduct) i).getCalories() >=minLimit &&  ((BaseProduct) i).getCalories() <=maxLimit).collect(Collectors.toSet());
            case "Proteins" -> result = menuItems.stream()
                    .filter(i-> ((BaseProduct) i).getProteins() >=minLimit &&   ((BaseProduct) i).getProteins() <=maxLimit).collect(Collectors.toSet());
            case "Fats" -> result = menuItems.stream()
                    .filter(i-> ((BaseProduct) i).getFats() >=minLimit &&   ((BaseProduct) i).getFats() <=maxLimit).collect(Collectors.toSet());
            case "Sodium" -> result = menuItems.stream()
                    .filter(i-> ((BaseProduct) i).getSodium() >=minLimit && ((BaseProduct) i).getSodium() <=maxLimit).collect(Collectors.toSet());
            case "Price" ->  result = menuItems.stream()
                    .filter(i-> ((BaseProduct) i).getPrice() <=maxLimit).collect(Collectors.toSet());
            default -> throw new IllegalStateException("Unexpected value: " + criteria);
        }
        return (HashSet<MenuItem>) result;
    }

    public static <T> Predicate<T> distinctByName(Function<? super T, ?> keyExtractor) {
        Set<Object> obj = ConcurrentHashMap.newKeySet();
        return t -> obj.add(keyExtractor.apply(t));
    }

    public HashSet<MenuItem> filterByName (String charSequence){
        Set<MenuItem> result;
        result = menuItems.stream().filter(mi-> mi instanceof BaseProduct).map(mi-> (BaseProduct) mi)
                .filter(mi-> mi.getTitle().contains(charSequence)).collect(Collectors.toSet());
        return (HashSet<MenuItem>) result;
    }

    @Override
    public void storeData(){
        assert isWellFormed();
        Serializator.serialize(this,"delivery.ser");
    }

    @Override
    public Order placeOrder(int clientUID, HashSet<MenuItem> orderedItems) {
        assert isWellFormed();
        assert orderedItems.isEmpty() || clientUID < 0;
        Order order = new Order(ordersCount, clientUID);
        order.setOrderedProducts(orderedItems); order.setOrderDate();order.computeTotalPrice();
        orders.put(order,orderedItems);
        this.lastOrder = order;
        setChanged();
        notifyObservers();
        ordersCount++;
        return order;
    }

    public void createBill (Order o){
        FileWriter.getInst().writeToFile(o.display());
        FileWriter.getInst().getMyWriter().close();
    }

    @Override
    public void generateReportOne(int startHour, int endHour) {
        assert isWellFormed();
        List<Order> result = orders.keySet().stream().filter(items -> items.getOrderDate().getHours() >= startHour && items.getOrderDate().getHours() <= endHour).collect(Collectors.toList());
        ReportWriter reportWriter = new ReportWriter("Report1.txt");
        reportWriter.writeToFile("Report of orders performed \n" +
                "between given start hour " + startHour + " and given end hour "+ endHour+ " regardless the date \n");
        StringBuilder sb = new StringBuilder();
        for (Order o: result) {
            sb.append(o.toString());
        }
        reportWriter.writeToFile(sb);
        reportWriter.getMyWriter().close();
    }

    @Override
    public void generateReportTwo(int minNumberOfPurchases) {
        assert isWellFormed();
        ReportWriter reportWriter = new ReportWriter("Report2.txt");
        reportWriter.writeToFile("Report of the products ordered more than "+ minNumberOfPurchases +" times so far\n");
        List<MenuItem> allOrdered = new ArrayList<MenuItem>();
        orders.values().stream().forEach(allOrdered::addAll);
        StringBuilder sb = new StringBuilder();
        Set<MenuItem> distinct = allOrdered.stream().filter(distinctByName(mi -> ((BaseProduct)mi).getTitle())).collect(Collectors.toSet());
        for (MenuItem mi: distinct) {
            long freq = allOrdered.stream().filter(m-> ((BaseProduct)m).getTitle().equals(((BaseProduct)mi).getTitle())).count();
            if(freq >= minNumberOfPurchases) {
                if(mi instanceof BaseProduct) sb.append(((BaseProduct) mi).getTitle()+"\n");
                else sb.append(((CompositeProduct)mi).getName() + "\n");
            }
        }
        reportWriter.writeToFile(sb);
        reportWriter.getMyWriter().close();
    }

    @Override
    public void generateReportThree(int minNumberOfPurchases, int minValue) {
        assert isWellFormed();
        List<Order> result = orders.keySet().stream().filter(items -> items.getTotalPrice() >= minValue).collect(Collectors.toList());
        ReportWriter reportWriter = new ReportWriter("Report3.txt");
        reportWriter.writeToFile("Report of the clients that have ordered more than " + minNumberOfPurchases+ " times and the value " + "of the order was higher than "+minValue+"\n");
        StringBuilder sb = new StringBuilder();
        for (Order o: result) {
            long clientFreq = result.stream().filter(f-> f.getClientID() == o.getClientID()).count();
            if(clientFreq >= minNumberOfPurchases)
            sb.append(o.toString());
        }
        reportWriter.writeToFile(sb);
        reportWriter.getMyWriter().close();
    }

    @Override
    public void generateReportFour(Date date) {
        assert isWellFormed();
        ReportWriter reportWriter = new ReportWriter("Report4.txt");
        reportWriter.writeToFile("Report of the products ordered within a specified day with the number of times they have been ordered\n");
        Map<Order, HashSet<MenuItem>> intermediate = orders.entrySet().stream().filter(k-> k.getKey().getOrderDate().getDay() == date.getDay() && k.getKey().getOrderDate().getYear() == date.getYear() && k.getKey().getOrderDate().getMonth() == date.getMonth()).collect(Collectors.toMap(map-> map.getKey(), map->map.getValue()));
        List<MenuItem> allOrdered = new ArrayList<MenuItem>();
        intermediate.values().stream().forEach(allOrdered::addAll);
        StringBuilder sb = new StringBuilder();
        Set<MenuItem> distinct = allOrdered.stream().filter(distinctByName(mi -> ((BaseProduct)mi).getTitle())).collect(Collectors.toSet());
        for (MenuItem mi: distinct) {
            long freq = allOrdered.stream().filter(m-> ((BaseProduct)m).getTitle().equals(((BaseProduct)mi).getTitle())).count();
            if(freq >= 0) {
                if(mi instanceof BaseProduct) sb.append(((BaseProduct) mi).getTitle()+"  ordered "+freq+" times\n");
                else sb.append(((CompositeProduct)mi).getName() +"  ordered "+freq+" times\n");
            }
        }
        reportWriter.writeToFile(sb);
        reportWriter.getMyWriter().close();
    }

    public void displayUnresolvedOrders(JTextArea textArea){
        this.orders.forEach(((order, orderItems) -> textArea.append(order.display())));
    }

    public int getOrdersCount() {
        return ordersCount;
    }

    public Order getLastOrder() {
        return lastOrder;
    }
}
