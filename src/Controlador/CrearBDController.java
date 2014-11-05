/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Beans.BaseDatos;
import Modelo.Connection.Coneccion;
import Modelo.Conteiners.RegistroBasesDatos;
import Vista.Custom.Exceptions;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author geykel
 */
public class CrearBDController implements Initializable {

    @FXML
    TextField tf_ip;
    @FXML
    TextField tf_puerto;
    @FXML
    TextField tf_sid;
    @FXML
    TextField tf_usuario;
    @FXML
    PasswordField tf_pass;
    @FXML 
    Button btn_aceptar;
    @FXML
    Button btn_test;
    @FXML
    AnchorPane root;
    
    private BaseDatos bd;
    private Stage st;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void initData(BaseDatos bd, Stage st){
        if(bd != null){
            this.bd = bd;
            this.tf_ip.setText(bd.getAddress());
            this.tf_pass.setText(bd.getPassword());
            this.tf_puerto.setText(Integer.toString(bd.getPort()));
            this.tf_sid.setText(bd.getSid());
            this.tf_usuario.setText(bd.getUser());
        }
        this.st = st;
    }
    
    @FXML
    private void guardarAction(ActionEvent event) {
        if(this.tf_ip.getText().equals("") || this.tf_pass.getText().equals("") || this.tf_puerto.getText().equals("") || this.tf_sid.getText().equals("") || this.tf_usuario.getText().equals("")){
            Exceptions.ErrorDialog("Campos", "Debe llenar todos los campos", st);
        }
        else if(this.bd != null){
            bd.setAddress(this.tf_ip.getText());
            bd.setPassword(this.tf_pass.getText());
            bd.setPort(Integer.parseInt(this.tf_puerto.getText()));
            bd.setSid(this.tf_sid.getText());
            bd.setUser(this.tf_usuario.getText());
            this.st.close();
        }
        else{
            this.bd = new BaseDatos(this.tf_ip.getText(),Integer.parseInt(this.tf_puerto.getText()),this.tf_sid.getText(),this.tf_usuario.getText(),this.tf_pass.getText());
            RegistroBasesDatos.getInstance().agregarBaseDatos(bd);
            this.st.close();
        }
        
    }
    
    @FXML
    private void testAction(ActionEvent event) {
        try {
            Coneccion.getInstance().testConnection(new BaseDatos(this.tf_ip.getText(),Integer.parseInt(this.tf_puerto.getText()),this.tf_sid.getText(),this.tf_usuario.getText(),this.tf_pass.getText()));
            Exceptions.InformationDialog("La prueba fue exitosa.", st);
        } catch (SQLException ex) {
            Exceptions.ExceptionDialog(ex, st);
            Logger.getLogger(CrearBDController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
