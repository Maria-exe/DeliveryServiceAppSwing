package bll;

import java.io.Serializable;
import java.util.List;

/**
 * @author Tirlea Maria Cristina
 * class that models the BaseProducts in the MenuItems
 */
public class BaseProduct extends MenuItem implements Serializable {
    private String title;
    private double rating;
    private int calories;
    private int proteins;
    private int fats;
    private int sodium;
    private int price;

    /**
     * Constructor
     * @param title
     * @param rating
     * @param calories
     * @param proteins
     * @param fats
     * @param sodium
     * @param price
     */
    public BaseProduct(String title, double rating, int calories, int proteins, int fats, int sodium, int price) {
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.sodium = sodium;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int computePrice() {
        return this.price;
    }

    /**
     * method for creating a new BaseProduct based on a list of attributes
     * @param list list of attributes for the new base product
     * @return the base product created
     */
    public static BaseProduct createBaseProduct (List list) {
        String title = list.get(0).toString();
        double rating = Double.parseDouble(list.get(1).toString());
        int calories = Integer.parseInt(list.get(2).toString());
        int proteins = Integer.parseInt(list.get(3).toString());
        int fats =Integer.parseInt(list.get(4).toString());
        int sodium = Integer.parseInt(list.get(5).toString());
        int price = Integer.parseInt(list.get(6).toString());
        return new BaseProduct(title,rating,calories,proteins,fats,sodium,price);
    }

    /**
     * @return a string for representing the base product
     */
    public String stringToPrint(){
        return "[base product : title= "+this.title + ", rating= "+this.rating+", calories="+this.calories
                +", proteins=" + this.proteins+", fats="+this.fats
                +", sodium="+this.sodium+", price=" + this.price+"]";
    }
}
