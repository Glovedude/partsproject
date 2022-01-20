package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Modify selected parts
 *
 * @author Chase Christensen
 *
 * RUNTIME ERROR: I ran into an issue with the error's this occured in all controllers. I was attempting to use
 * INPUTMISMATCHSELECTION as my error catch. But this didn't work. So I switched to NUMBERFORMATEXCEPTION and that
 * removed the runtime error
 */

public class ModifyPart implements Initializable {
    public RadioButton outsourced;
    public ToggleGroup modifyPart;
    public RadioButton inHouse;
    public Button cancel;
    public Button save;
    public Label radioTitle;
    public TextField name;
    public TextField inv;
    public TextField price;
    public TextField min;
    public TextField max;
    public TextField machineId;
    private static int selectedIndex;
    private static Part partPass = null;

    /**@param pass data pass from mainscreen controller */
    public static void passData(Part pass){
        partPass = pass;
        System.out.print(partPass.getName());
        selectedIndex = Inventory.getAllParts().indexOf(pass);

    }

    /**Initialize and populate tables*/
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int newId = partPass.getId();
        String newName = partPass.getName();
        int newInv = partPass.getStock();
        Double newPrice = partPass.getPrice();
        int newMin = partPass.getMin();
        int newMax = partPass.getMax();

        // check if part is InHouse or Outsourced to cast proper data type to 'machineID' or 'CompanyName' field
        if(partPass instanceof Outsourced){
            String newCompanyName = ((Outsourced) partPass).getCompanyName();
            radioTitle.setText("Company Name");
            machineId.setText(newCompanyName);
        }

        if(partPass instanceof InHouse){
            int newMachineId = ((InHouse) partPass).getMachineID();
            radioTitle.setText("Machine ID");
            machineId.setText(String.valueOf(newMachineId));
        }

        // cast data to textfields
        name.setText(newName);
        inv.setText(String.valueOf(newInv));
        price.setText(String.valueOf(newPrice));
        min.setText(String.valueOf(newMin));
        max.setText(String.valueOf(newMax));

    }

    /**@param actionEvent Check radio button for outsourced*/
    public void Outsourced(ActionEvent actionEvent) {
            radioTitle.setText("Company Name");
    }

    /**@param actionEvent Check radio button for InHouse*/
    public void inHouse(ActionEvent actionEvent) {
            radioTitle.setText("Machine ID");
    }

    /**@param actionEvent return to main screen
     * @throws IOException bypass IOException**/
    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Cancel");
        stage.setScene(scene);
        stage.show();
    }

    /**@param actionEvent Add new part and return to main screen*
     * @throws IOException bypass IOException*
     */
    public void save(ActionEvent actionEvent) throws IOException {

        int newID = partPass.getId();
        String newName;
        int newInv;
        Double newPrice;
        int newMin;
        int newMax;
        int newMachineId = 0;
        String newCompanyName = null;

        // get inputs from text field and perform error checks to display error dialogue boxes
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
                    "Please enter an integer for maximum inventory amount.", ButtonType.OK);
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

        // check radio buttons for final textfield and type to add
        if(inHouse.isSelected()){
            newMachineId = Integer.parseInt(machineId.getText());
            Part i = new InHouse(newID, newName, newInv, newPrice, newMin, newMax, newMachineId);
            Inventory.updatePart((selectedIndex), i);
        }

        else if(outsourced.isSelected()) {
            newCompanyName = machineId.getText();
            Part o = new Outsourced(newID, newName, newInv, newPrice, newMin, newMax, newCompanyName);
            Inventory.updatePart((selectedIndex), o);
        }

        // call main screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Save");
        stage.setScene(scene);
        stage.show();
    }

}
