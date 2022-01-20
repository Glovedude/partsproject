package controller;

import javafx.event.ActionEvent;
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
 * Adds parts to the main screen and the stored inventory list
 *
 * @author Chase Christensen
 *
 * IMPROVEMENT: I would like to have the try section include all the data sections and then have the catches do a list
 * of if elses, rather than having each try be seperate.
 */

public class AddPart implements Initializable {
    public RadioButton inHouse;
    public ToggleGroup addPart;
    public RadioButton outsourced;
    public Button save;
    public Button cancel;
    public Label radioTitle;
    public TextField name;
    public TextField inv;
    public TextField price;
    public TextField max;
    public TextField min;
    public TextField machineId;
    public TextField iD;


    /**Initialize add parts (this particular initialize has no parameters)*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**@param actionEvent Method to check if radio button for inhouse is selected*/
    public void inHouse(ActionEvent actionEvent) {
        radioTitle.setText("Machine ID");
    }

    /**@param actionEvent Method to check if radio button for outsourced is selected*/
    public void Outsourced(ActionEvent actionEvent) {
        radioTitle.setText("Company Name");
    }


    /**@param actionEvent  for save button to save new part, also performs error checks and input validation
     * @throws IOException bypass IOException**/
    public void save(ActionEvent actionEvent) throws IOException {

        int newID = Inventory.idSequence.incrementAndGet();
        String newName;
        int newInv;
        Double newPrice;
        int newMin;
        int newMax;
        int newMachineId = 0;
        String newCompanyName = null;



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

        if (inHouse.isSelected()) {
            newMachineId = Integer.parseInt(machineId.getText());Part i = new InHouse(newID, newName, newInv, newPrice,
                    newMin, newMax, newMachineId);
            Inventory.addPart(i);

        }

        else if (outsourced.isSelected()) {
            newCompanyName = machineId.getText();
            Part o = new Outsourced(newID, newName, newInv, newPrice, newMin, newMax, newCompanyName);
            Inventory.addPart(o);
        }


        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Save");
        stage.setScene(scene);
        stage.show();
    }

    /**@param actionEvent cancel and return to main screen
     * @throws IOException bypass IOException**/
    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Cancel");
        stage.setScene(scene);
        stage.show();
    }


}
