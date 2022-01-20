package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Provides methods that each controller uses, from searching, to pressing buttons
 *
 */

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static final AtomicInteger idSequence = new AtomicInteger(0);
    public static final AtomicInteger productIdSequence = new AtomicInteger(1000);

    /**@param part Add part */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**@param product Add Product*/
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**@param partId  search by part id
     * @return the part*/

    public static Part lookupPart(int partId) {
        ObservableList<Part> parts = getAllParts();

        for(int i = 0; i < parts.size(); i++){
            Part p = parts.get(i);

            if (p.getId() == partId ) {
                return p;
            }
        }
        return null;
    }

    /**@param Part Search by Part Name
     * @return the part*/

    public static ObservableList<Part> lookupPart(String Part){

        ObservableList<Part> parts = FXCollections.observableArrayList();

        for (Part part : getAllParts()) {
            if (part.getName().contains(Part)) {
                parts.add(part);
            }
        }
        return parts;
    }

    /**@param productId search by product id *
     *                  @return the product
     */

    public static Product lookupProduct(int productId) {
        for (Product product : getAllProducts()) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**@param Product  Search by name for product *
     *                 @return the product
     */

    public static ObservableList<Product> lookupProduct(String Product){
        ObservableList<Product> products = FXCollections.observableArrayList();

        for (Product product : getAllProducts()) {
            if (product.getName().contains(Product)) {
                products.add(product);
            }
        }

        return products;
    }

    /**@param selectedPart update parts*
     *                     @param index select by index
     */

    public static void updatePart(int index, Part selectedPart){
        getAllParts().set(index, selectedPart);
    }

    /**@param selectedProduct Update products
     * @param index select by index*/

    public static void updateProduct(int index, Product selectedProduct){
        getAllProducts().set(index, selectedProduct);
    }

    /**@param selectedPart  delete parts *
     *               @return selectedPart
     */


    public static boolean deletePart(Part selectedPart) {
        for (Part part : getAllParts()) {
            if (part.getId() == selectedPart.getId())
                return getAllParts().remove(part);
        }
        return false;
    }

    /**@param selectedProduct Delete products*
     * @return selectedProduct
     */


    public static boolean deleteProduct(Product selectedProduct) {
        for (Product product : getAllProducts()) {
            if (product.getId() == selectedProduct.getId())
                return getAllProducts().remove(product);
        }
        return false;
    }

    /** @return allParts */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** @return all products*/
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

// test data found here.
/*
    static {
        addTestData();
    }

    public static void addTestData(){

        InHouse o = new InHouse(1, "What?", 1, 100.00, 0, 1,50);
        Inventory.addPart(o);
        Outsourced z = new Outsourced(2, "cool", 10, 10.00, 1, 5, "place");
        Inventory.addPart(z);
        InHouse g = new InHouse(3, "Vampire Juice", 1, 10.00, 0, 1, 52);
        Inventory.addPart(g);



        Product p = new Product(1001, "this is weird", 1, 100.00, 0, 1);
        Inventory.addProduct((p));
        Product f = new Product(1002, "this is super", 1, 100.00, 0, 1);
        Inventory.addProduct((f));



    }

 */
}
