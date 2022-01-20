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
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Modify selected product
 *
 * @author Chase Christensen
 *
 * RUNTIME ERROR: on the add part action section, I had to create a template for the parts to be added to. Even though
 * it would know what it is adding to the removetable, it needed a template to know where to place the individual data
 * points.
 */

public class ModifyProduct implements Initializable {
    public TextField id;
    public TextField name;
    public TextField price;
    public TextField inv;
    public TextField max;
    public TableView addPart;
    public TextField min;
    public TableView removePart;
    public TextField search;
    public Button removeAssociatedPart;
    public Button add;
    public Button save;
    public Button cancel;
    public TableColumn partId;
    public TableColumn partName;
    public TableColumn partInv;
    public TableColumn partPrice;
    public TableColumn productPartId;
    public TableColumn productPartName;
    public TableColumn productPartInv;
    public TableColumn productPartCost;

    private static Product productPass = null;
    private static int selectedIndex;
    String confirmRemovePart;
    String partRemovalSuccessful;
    String partRemovalCancel;


    /** @param pass calls data pass from MainSCreen */
    public static void passData(Product pass) {
        productPass = pass;
        System.out.print(productPass.getName());
        selectedIndex = Inventory.getAllProducts().indexOf(pass);
    }

    private ObservableList<Part> productParts = FXCollections.observableArrayList();

    /**Populate tables at launch of screen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int newId = productPass.getId();
        String newName = productPass.getName();
        int newInv = productPass.getStock();
        Double newPrice = productPass.getPrice();
        int newMin = productPass.getMin();
        int newMax = productPass.getMax();

        name.setText(newName);
        inv.setText(String.valueOf(newInv));
        price.setText(String.valueOf(newPrice));
        min.setText(String.valueOf(newMin));
        max.setText(String.valueOf(newMax));

        addPart.setItems(Inventory.getAllParts());
        partId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        productParts = FXCollections.observableArrayList((productPass.getAllAssociatedParts()));
        removePart.setItems(productParts);
        productPartId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productPartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productPartInv.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productPartCost.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

    /**@param actionEvent Remove associated part from product*/
    public void removeAssociatedPart(ActionEvent actionEvent) {

        // check that part is selected.
        if(removePart.getSelectionModel().getSelectedItem() == null)
        {
            Alert noPartSelectedAlert = new Alert(Alert.AlertType.ERROR, "Please select a part to remove",
                    ButtonType.OK);
            noPartSelectedAlert.show();
            return;
        }

        Part p = (Part)removePart.getSelectionModel().getSelectedItem();
        String selectedPartToRemove = p.getName();

        //begin error checks
        confirmRemovePart = "Confirm part removal?";
        Alert alertRemovePart = new Alert(Alert.AlertType.CONFIRMATION, confirmRemovePart,
                ButtonType.YES, ButtonType.CANCEL);
        alertRemovePart.showAndWait();

        // part removed alert
        if(alertRemovePart.getResult() == ButtonType.YES)
        {
            removePart.getItems().removeAll(p);
            partRemovalSuccessful = selectedPartToRemove + " was removed.";
            Alert partRemovedAlert = new Alert(Alert.AlertType.INFORMATION,
                    partRemovalSuccessful, ButtonType.OK);
            partRemovedAlert.show();
        }

        // cancel removal alert
        else if(alertRemovePart.getResult() == ButtonType.CANCEL)
        {
            partRemovalCancel = "Removal of " + selectedPartToRemove + " cancelled.";
            Alert partRemovalCancelledAlert = new Alert(Alert.AlertType.INFORMATION,
                    partRemovalCancel, ButtonType.CANCEL);
            partRemovalCancelledAlert.show();
        }
    }

    /**@param actionEvent Add part to associated part list*/
    public void add(ActionEvent actionEvent) {

        // error check that part is selected
        if(addPart.getSelectionModel().getSelectedItem() == null)
        {
            Alert noPartSelectedAlert = new Alert(Alert.AlertType.ERROR, "Please select a part to add",
                    ButtonType.OK);
            noPartSelectedAlert.show();
            return;
        }

        Part p = (Part) addPart.getSelectionModel().getSelectedItem();

        // add part based on inHouse or Outsourced
        if (p instanceof InHouse) {

            Part q = new InHouse(p.getId(), p.getName(), p.getStock(), p.getPrice(), p.getMin(), p.getMax(),
                    ((InHouse) p).getMachineID());
            productParts.add(q);
        }

        if (p instanceof Outsourced) {

            Part t = new Outsourced(p.getId(), p.getName(), p.getStock(), p.getPrice(), p.getMin(), p.getMax(),
                    ((Outsourced) p).getCompanyName());
            productParts.add(t);
        }


    }

    /**@param actionEvent return to main screen*
     *  @throws IOException bypass IOException*
     */
    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Cancel");
        stage.setScene(scene);
        stage.show();
    }

    /**@param actionEvent Save product and return to main screen*
     *  @throws IOException bypass IOException*
     */
    public void save(ActionEvent actionEvent) throws IOException {
        int newID = productPass.getId();
        String newName;
        int newInv;
        Double newPrice;
        int newMin;
        int newMax;

        // get inputs from text field and perform error checks on inputs.
        newName = name.getText();
        try {
            newInv = Integer.parseInt(inv.getText());
        }
        catch(NumberFormatException e)
        {
            Alert invalidInventoryAlert = new Alert(Alert.AlertType.ERROR,
                    "Please enter valid integer for inventory amount", ButtonType.OK);
            invalidInventoryAlert.showAndWait();
            return;
        }

        try {
            newPrice = Double.parseDouble(price.getText());
        }
        catch(NumberFormatException e)
        {
            Alert invalidPriceAlert = new Alert(Alert.AlertType.ERROR,
                    "Please enter a decimal number for price. Example: 9.56", ButtonType.OK);
            invalidPriceAlert.showAndWait();
            return;
        }

        try {
            newMin = Integer.parseInt(min.getText());
        }
        catch (NumberFormatException e)
        {
            Alert invalidMinAlert = new Alert(Alert.AlertType.ERROR,
                    "Please enter an integer for minimum inventory amount.", ButtonType.OK);
            invalidMinAlert.showAndWait();
            return;
        }

        try {
            newMax = Integer.parseInt(max.getText());
        }
        catch(NumberFormatException e)
        {
            Alert invalidMaxAlert = new Alert(Alert.AlertType.ERROR,
                    "Please enter an  integer for maximum inventory amount.", ButtonType.OK);
            invalidMaxAlert.showAndWait();
            return;
        }

        if (newMin > newMax)
        {
            Alert minMaxAlert = new Alert(Alert.AlertType.ERROR,
                    "Please enter a minimum inventory value less than the maximum.", ButtonType.OK);
            minMaxAlert.showAndWait();
            return;
        }

        if(newInv < newMin || newInv > newMax)
        {
            Alert invMinimumAlert = new Alert(Alert.AlertType.ERROR,
                    "Please enter Inventory amount greater than minimum and less than maximum inventory parameters.",
                    ButtonType.OK);
            invMinimumAlert.showAndWait();
            return;
        }

        if (newName.isEmpty())
        {
            Alert nameInputAlert = new Alert(Alert.AlertType.ERROR, "Please enter a part Name", ButtonType.OK);
            nameInputAlert.showAndWait();
            return;
        }

        if(newMax == 0)
        {
            Alert standardInputAlert = new Alert(Alert.AlertType.ERROR,
                    "Please enter a maximum inventory amount greater than 0", ButtonType.OK);
            standardInputAlert.showAndWait();
            return;
        }

        Product i = new Product(newID, newName, newInv, newPrice, newMin, newMax);
        for (Part p : productParts) {
            i.addAssociatedPart(p); // add part to associated list
        }

        Inventory.updateProduct((selectedIndex), i);

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Save");
        stage.setScene(scene);
        stage.show();
    }

    /**@param actionEvent Search parts by name or id*/
    public void search(ActionEvent actionEvent) {
        String q = search.getText();

        ObservableList<Part> parts = Inventory.lookupPart(q);

        if(parts.size() == 0) {

            int partId = Integer.parseInt(q);
            Part p = Inventory.lookupPart(partId);
            if (p != null)
                parts.add(p);
        }

        addPart.setItems(parts);
        search.setText("");
    }

}
