package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * Product setters and getters
 *
 * @author Chase Christensen
 *
 * IMPROVEMENT: delete associated part is not necessary, and as such isn't being used in this program.
 * being able to instead just save a new instance of the product seems to work better.
 */

public class Product {
    private int id;
    private String name;
    private int stock;
    private double price;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public Product(int id, String name, int stock, double price, int min, int max) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.min = min;
        this.max = max;
    }

    /**@return the ID*/
    public int getId() {
        return id;
    }

    /**@param id the id to set*/
    public void setId(int id) { this.id = id; }

    /**@return product name*/
    public String getName() {
        return name;
    }

    /**@param name  product name to set*/
    public void setName(String name) { this.name = name; }

    /**@return stock*/
    public int getStock() { return stock; }

    /**@param stock the stock to set*/
    public void setStock(int stock) { this.stock = stock; }

    /**@return product Stock*/
    public Double getPrice() { return price; }

    /**@param price the price to set*/
    public void setPrice(Double price) { this.price = price; }

    /**@return product price*/
    public int getMin() {return min;}

    /**@param min the min to set*/
    public void setMin(int min) {this.min = min;}

    /**@return product maximum*/
    public int getMax() {return max;}

    /**@param max the max to set*/
    public void setMax(int max) {this.max = max;}

    /**Method to add associated parts to products*
     * @param part the part to add
     */
    public void addAssociatedPart(Part part) { associatedParts.add(part);   }

    /**Method to delete assocated parts from products*
     * @param AssociatedPart the associated part to delete
     */
    public void deleteAssociatedPart(Part AssociatedPart) {
        associatedParts.remove(AssociatedPart);
    }

    /**@return associated parts list for products*/
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }
}
