/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package uaspbol;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class TransactionController implements Initializable {

    DBItem dtitem = new DBItem();
    DBTransaction dttrx = new DBTransaction();
    public int pilihBarisItem;
    int price = 0;
    
    @FXML
    private Button btnBack;
    @FXML
    private TableView<ItemModel> tbvitem;
    @FXML
    private Button btnbuy;
    @FXML
    private TextField texttx;
    @FXML
    private TextField textamount;
    @FXML
    private TableColumn<ItemModel, String> coliditem;
    @FXML
    private TableColumn<ItemModel, String> colnameitem;
    @FXML
    private TableColumn<ItemModel, String> colamountitem;
    @FXML
    private Label lblid;
    @FXML
    private Label lblprice;
    @FXML
    private DatePicker date;
    
    public int getPilihBarisItem() {
        return pilihBarisItem;
    }

    public void setPilihBarisItem(int pilihBarisItem) {
        this.pilihBarisItem = pilihBarisItem;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showData();
        date.setValue(LocalDate.now());
    }    

    private void showData() {
        ObservableList<ItemModel> dataItem = dtitem.Load();
        
        if(dataItem!=null){
            coliditem.setCellValueFactory(itemData -> new SimpleStringProperty(itemData.getValue().getIdItem()));
            colnameitem.setCellValueFactory(itemData -> new SimpleStringProperty(itemData.getValue().getNameItem()));
            colamountitem.setCellValueFactory(itemData -> new SimpleStringProperty(itemData.getValue().getAmountItem()));
            tbvitem.setItems(dataItem);
        } else {
            Alert a=new Alert(Alert.AlertType.ERROR,"Data kosong",ButtonType.OK);
            a.showAndWait();
            tbvitem.getScene().getWindow().hide();
        }
    }
    
    @FXML
    private void backClick(ActionEvent event) {
        btnBack.getScene().getWindow().hide();
    }

    @FXML
    private void buyClick(ActionEvent event) {
        TransactionModel trx = new TransactionModel();
        ItemModel im = new ItemModel();
        dttrx.setTransactionModel(trx);
        dtitem.setItemModel(im);
        
        int amountitem = Integer.parseInt(tbvitem.getSelectionModel().getSelectedItem().getAmountItem());
        int totalprice = Integer.parseInt(textamount.getText())*price;
        
        trx.setTxtime(Date.valueOf(date.getValue()));
        trx.setNumtx(texttx.getText());
        trx.setBuyamount(textamount.getText());
        trx.setPrice(String.valueOf(totalprice));
        trx.setIditem(lblid.getText());
        
        if(amountitem < Integer.parseInt(textamount.getText())) {
            Alert a=new Alert(Alert.AlertType.ERROR,"Item amount is not enough!",ButtonType.OK);
            a.showAndWait();
            textamount.setText("");
        } else {
            im.setIdItem(lblid.getText());
            im.setNameItem(tbvitem.getSelectionModel().getSelectedItem().getNameItem());
            im.setAmountItem(String.valueOf(amountitem-1));
            dttrx.insert();
            dtitem.update();
            btnbuy.getScene().getWindow().hide();
        }
    }

    @FXML
    private void pilihItem(MouseEvent event) {
        lblid.setText(tbvitem.getSelectionModel().getSelectedItem().getIdItem());
        
        int itemCode = tbvitem.getSelectionModel().getSelectedIndex();
        switch(itemCode) {
            case 0: price = 1000; break;
            case 1: price = 3000; break;
            case 2: price = 6000; break;
            case 3: price = 6000; break;
            case 4: price = 8000; break;
        }
        lblprice.setText(String.valueOf(price + " coin/item"));
    }
}
