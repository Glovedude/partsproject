package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Mainscreen provides is the controller for MainScreen.fxml. It provides functionality to jump to other screens
 * and controls various delete, search, and addition launch points
 *
 * @author Chase Christensen
 * @since 2021-10-01
 *
 *
 */

public class MainScreen implements Initializable {
    public Label MainLabel;
    public Button mainExit;
    public TableView partTable;
    public TableColumn partID;
    public TableColumn partName;
    public TableColumn partInventoryLevel;
    public TableColumn partPrice;
    public TableView productTable;
    public TableColumn productID;
    public TableColumn productName;
    public TableColumn productInventoryLevel;
    public TableColumn productCost;
    public Button deletePart;
    public Button modifyPart;
    public Button addPart;
    public Button deleteMainB;
    public Button modifyProduct;
    public Button addProduct;
    public Label partsLabel;
    public Label productsLabel;
    public TextField partSearch;
    public TextField productSearch;

    private ObservableList<Part> partsTable = FXCollections.observableArrayList();
    private ObservableList<Product> productsTable = FXCollections.observableArrayList();

    String confirmDeletePart;
    String confirmDeleteProduct;
    String partDeletedSuccess;
    String productDeletedSuccess;
    String partDeleteCancelled;
    String productDeleteCancelled;
    String errorNoPartSelected = "Please select a part.";
    String errorNoProductSelected = "Please select a product.";

    /**Initialize parts and products lists for search and selection*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        partTable.setItems(Inventory.getAllParts());

        partID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        productTable.setItems(Inventory.getAllProducts());

        productID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productCost.setCellValueFactory(new PropertyValueFactory<>("Price"));

    }

    /**@param actionEvent Close program*/
    public void ExitMain(ActionEvent actionEvent) {
        Stage stage = (Stage) mainExit.getScene().getWindow();
        stage.close();
    }

    /**@param actionEvent Delete selected parts*/
    public void onDeletePart(ActionEvent actionEvent) {

        // check that part is selected
        if(partTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert noPartSelectedAlert = new Alert(Alert.AlertType.ERROR, errorNoPartSelected, ButtonType.OK);
            noPartSelectedAlert.show();
            return;
        }

        // select part
        Part p = (Part)partTable.getSelectionModel().getSelectedItem();
        String selectedPartName = p.getName();

        // provide deletion confirmation
        confirmDeletePart = "Confirm part deletion?";
        Alert alertDeletePart = new Alert(Alert.AlertType.CONFIRMATION, confirmDeletePart,
                ButtonType.YES, ButtonType.CANCEL);
        alertDeletePart.showAndWait();

        // Confirm deletion success
        if(alertDeletePart.getResult() == ButtonType.YES)
        {
            Inventory.deletePart(p);
            partDeletedSuccess = selectedPartName + " was deleted.";
            Alert partDeletedAlert = new Alert(Alert.AlertType.INFORMATION, partDeletedSuccess, ButtonType.OK);
            partDeletedAlert.show();

        }
        // confirm deletion cancelled
        else if(alertDeletePart.getResult() == ButtonType.CANCEL)
        {
            partDeleteCancelled = "Deletion of " + selectedPartName + " cancelled.";
            Alert partDeleteCancelledAlert = new Alert(Alert.AlertType.INFORMATION,
                    partDeleteCancelled, ButtonType.CANCEL);
            partDeleteCancelledAlert.show();
        }
    }

    /**@param actionEvent Delete selected product*/
    public void onDeleteProduct(ActionEvent actionEvent) {

        // check that product is selected for deletion
        if(productTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert noProductSelectedAlert = new Alert(Alert.AlertType.ERROR, errorNoProductSelected, ButtonType.OK);
                    noProductSelectedAlert.show();
            return;
        }

        //select product
        Product r = (Product)productTable.getSelectionModel().getSelectedItem();
        String selectedProductName = r.getName();

        if(!r.getAllAssociatedParts().isEmpty())
        {
            Alert associatedPartAlert = new Alert(Alert.AlertType.ERROR, selectedProductName +
                    " has associated parts. Please remove before deleting product.", ButtonType.OK);
            associatedPartAlert.show();
            return;
        }

        //deletion confirmation
        confirmDeleteProduct = "Are you sure you want to delete " + selectedProductName + "?";
        Alert alertDeleteProduct = new Alert(Alert.AlertType.CONFIRMATION, confirmDeleteProduct,
                ButtonType.YES, ButtonType.CANCEL);
        alertDeleteProduct.showAndWait();

        // deletion successful dialogue box
        if(alertDeleteProduct.getResult() == ButtonType.YES)
        {
            Inventory.deleteProduct(r);
            productDeletedSuccess = selectedProductName + " was deleted.";
            Alert productDeletedAlert = new Alert(Alert.AlertType.INFORMATION, productDeletedSuccess, ButtonType.OK);
            productDeletedAlert.show();
        }

        // deletion cancelled dialogue box
        else if (alertDeleteProduct.getResult() == ButtonType.CANCEL)
        {
            productDeleteCancelled = "Deletion of " + selectedProductName + " cancelled.";
            Alert productDeletedCancelledAlert = new Alert(Alert.AlertType.INFORMATION,
                    productDeleteCancelled, ButtonType.CANCEL);
            productDeletedCancelledAlert.show();
        }
    }

    /**@param actionEvent Take selected part to the modify part screen*
     * @throws IOException bypass IOException*
     */
    public void onModifyPart(ActionEvent actionEvent) throws IOException {

        // check that part is selected
        if(partTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert noPartSelectedAlert = new Alert(Alert.AlertType.ERROR, errorNoPartSelected, ButtonType.OK);
            noPartSelectedAlert.show();
            return;
        }

        Part p = (Part)partTable.getSelectionModel().getSelectedItem();
        ModifyPart.passData(p);

        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 600);
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();
    }

    /**@param actionEvent Open add part screen*
     * @throws IOException bypass IOException*
     */
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }


    /**@param actionEvent Take selected product to modify product screen*
     *  @throws IOException bypass IOException*
     */
    public void onModifyProduct(ActionEvent actionEvent) throws IOException {

        // check that product is selected
        if(productTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert noProductSelectedAlert = new Alert(Alert.AlertType.ERROR, errorNoProductSelected, ButtonType.OK);
            noProductSelectedAlert.show();
            return;
        }

        Product p = (Product)productTable.getSelectionModel().getSelectedItem();
        ModifyProduct.passData(p);

        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**@param actionEvent Open add product screen*
     *  @throws IOException bypass IOException*
     */
    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**@param actionEvent Search by part name or ID*/
    public void searchPart(ActionEvent actionEvent) {
        String q = partSearch.getText();

        ObservableList<Part> parts = Inventory.lookupPart(q);

        if(parts.size() == 0) {

            int partId = Integer.parseInt(q);
            Part p = Inventory.lookupPart(partId);
            if (p != null)
                parts.add(p);
        }

        partTable.setItems(parts);
        partSearch.setText("");
    }

    /**@param actionEvent Search by product name or ID*/
    public void searchProduct(ActionEvent actionEvent) {
        String q = productSearch.getText();

        ObservableList<Product> products = Inventory.lookupProduct(q);

        if(products.size() == 0){
            int productId = Integer.parseInt(q);
            Product p = Inventory.lookupProduct(productId);
            if (p != null)
                products.add(p);
        }

        productTable.setItems(products);
        productSearch.setText("");
    }
}


