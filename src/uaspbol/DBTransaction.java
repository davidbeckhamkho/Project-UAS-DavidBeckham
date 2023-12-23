/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uaspbol;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ASUS
 */
public class DBTransaction {
    private TransactionModel dt=new TransactionModel();    
    public TransactionModel getTransactionModel(){return(dt);}
    public void setTransactionModel(TransactionModel s){dt=s;}
    
    public ObservableList<TransactionModel> Load() {
        try {
            ObservableList<TransactionModel> tableData=FXCollections.observableArrayList();
            Koneksi con = new Koneksi();            
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT numtx, txtime, buyamount, price, iditem FROM transaction");
            
            int i = 1;
            while (rs.next()) {
                TransactionModel d=new TransactionModel();
                d.setNumtx(rs.getString("numtx"));                
                d.setTxtime(rs.getDate("txtime"));
                d.setBuyamount(rs.getString("buyamount"));
                d.setPrice(rs.getNString("price"));
                d.setIditem(rs.getString("iditem"));

                tableData.add(d);                
                i++;            
            }
            
            con.tutupKoneksi();
            return tableData;
        } catch (Exception e) {
            e.printStackTrace();            
            return null;        
        }
    }
    
    public int validasi(String nomor) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();            
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT count(*) AS jml FROM transaction WHERE numtx = '" + nomor + "'");
            while (rs.next()) {                
                val = rs.getInt("jml");            
            }            
            con.tutupKoneksi();
        } catch (SQLException e) {            
            e.printStackTrace();        
        }
        return val;
    }
    
    public boolean insert() {
        boolean berhasil = false;        
        Koneksi con = new Koneksi();
        try {       
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("INSERT INTO transaction (numtx, txtime, buyamount, price, iditem) VALUES (?,?,?,?,?)");
            con.preparedStatement.setString(1, getTransactionModel().getNumtx());           
            con.preparedStatement.setDate(2, getTransactionModel().getTxtime());
            con.preparedStatement.setString(3, getTransactionModel().getBuyamount());
            con.preparedStatement.setString(4, getTransactionModel().getPrice());
            con.preparedStatement.setString(5, getTransactionModel().getIditem());
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();            
            berhasil = false;
        } finally {
            con.tutupKoneksi();            
            return berhasil;        
        }    
     }
    
    public boolean delete(String nomor) {
        boolean berhasil = false;        
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("DELETE FROM transaction WHERE numtx  = ? ");
            con.preparedStatement.setString(1, nomor);
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {            
            e.printStackTrace();
        } finally {            
            con.tutupKoneksi();            
            return berhasil;
        }
    }
    
    public boolean update() {
        boolean berhasil = false;        
        Koneksi con = new Koneksi();
        try {            
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("UPDATE transaction SET txtime = ?, buyamount = ?, price = ?, iditem = ? WHERE numtx = ? ");
            con.preparedStatement.setDate(1, getTransactionModel().getTxtime());
            con.preparedStatement.setString(2, getTransactionModel().getBuyamount());
            con.preparedStatement.setString(3, getTransactionModel().getIditem());
            con.preparedStatement.setString(4, getTransactionModel().getPrice());
            con.preparedStatement.setString(5, getTransactionModel().getNumtx());
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();            
            berhasil = false;
        } finally {            
            con.tutupKoneksi();            
            return berhasil;        
        }    
    }
    
    public void cetakReportTransaction(){
        Koneksi con = new Koneksi();        
        String is = "./src/uaspbol/ReportTransaction.jasper";   
        Map map = new HashMap(); 
        map.put("judul", "Transaction Report");
        con.bukaKoneksi();
        try{
           JasperPrint jasperPrint = JasperFillManager.fillReport(is, map,  con.dbKoneksi);
           JasperViewer.viewReport(jasperPrint, false);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        con.tutupKoneksi();
    }
}
