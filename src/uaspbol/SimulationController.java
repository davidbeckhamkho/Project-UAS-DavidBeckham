/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package uaspbol;

import java.awt.dnd.DnDConstants;
import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class SimulationController implements Initializable {
    
    DBItem dtitem = new DBItem();

    @FXML
    private Button btnBack;
    @FXML
    private DatePicker date;
    @FXML
    private TextField textamount;
    @FXML
    private TextField texttx;
    @FXML
    private Label lblprice;
    @FXML
    private Label lblid;
    @FXML
    private Label lbltotalprice;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Random rnd = new Random();
        int randomAmount = rnd.nextInt(10);
        String randowTRxNum1 = String.valueOf(rnd.nextInt(10));
        String randowTRxNum2 = String.valueOf(rnd.nextInt(10));
        String randowTRxNum3 = String.valueOf(rnd.nextInt(10));
        String randomID = String.valueOf(1+rnd.nextInt(5));
        
        ItemModel im = new ItemModel();
        im.getIdItem();
        im.getNameItem();
        im.getAmountItem();
        
        date.setValue(LocalDate.now());
        textamount.setText(String.valueOf(randomAmount+1));
        texttx.setText(randowTRxNum1+randowTRxNum2+randowTRxNum3);
        lblid.setText("C0"+randomID);
        
        int price = 0;
        switch(lblid.getText()) {
            case "C01": price = 1000; break;
            case "C02": price = 3000; break;
            case "C03": price = 6000; break;
            case "C04": price = 6000; break;
            case "C05": price = 8000; break;
        }
        
        lblprice.setText(String.valueOf(price));
        lbltotalprice.setText(String.valueOf(price*(randomAmount+1)));
    }

    @FXML
    private void backClick(ActionEvent event) {
        btnBack.getScene().getWindow().hide();
    }
    
}
