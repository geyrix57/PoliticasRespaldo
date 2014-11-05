/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Beans.BaseDatos;
import Modelo.Beans.Politica;
import Modelo.Connection.Cliente;
import Modelo.Connection.Coneccion;
import Modelo.Conteiners.RegistroBasesDatos;
import Modelo.Conteiners.RegistroPoliticas;
import Vista.Custom.CheckBoxTableCell;
import Vista.Custom.Exceptions;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author geykel
 */
public class BaseDatosController implements Initializable {
    
    @FXML
    TextField tf_bd; 
    @FXML
    TableView<BaseDatos> tv_bd;
    @FXML
    TableColumn<BaseDatos,String> tc_host;
    @FXML
    TableColumn<BaseDatos,Integer> tc_puerto;
    @FXML
    TableColumn<BaseDatos,String> tc_sid;
    @FXML
    TableColumn<BaseDatos,String> tc_usuario;
    @FXML
    AnchorPane root;
    
    /*-----------------------Tab Politicas------------------------*/
    @FXML
    TextField tf_sidPolitica;
    @FXML
    TextField tf_nombrePolitica;
    @FXML
    TableView<Politica> tv_politicas;
    @FXML
    TableColumn<Politica, String> tc_sidPolitica;
    @FXML
    TableColumn<Politica, String> tc_nombrePolitica;
    @FXML
    TableColumn<Politica, String> tc_descPolitica;
    @FXML
    TableColumn<Politica, Boolean> tc_activo; 
    
    private Stage st;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    private void initializeTabBaseDatos(){
        this.tc_host.setCellValueFactory(new PropertyValueFactory("address"));
        this.tc_puerto.setCellValueFactory(new PropertyValueFactory("port"));
        this.tc_sid.setCellValueFactory(new PropertyValueFactory("sid"));
        this.tc_usuario.setCellValueFactory(new PropertyValueFactory("user"));
        RegistroBasesDatos.getInstance().getSortedList().comparatorProperty().bind(this.tv_bd.comparatorProperty());
        this.tv_bd.setItems(RegistroBasesDatos.getInstance().getSortedList());
        this.tf_bd.textProperty().addListener((observable, oldValue, newValue) -> {
            RegistroBasesDatos.getInstance().getFilteredList().setPredicate(bd -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
		if(bd.getSid().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if(bd.getAddress().toLowerCase().contains(lowerCaseFilter))
                    return true;
                return false;
            });
	});
        this.tv_bd.setRowFactory((TableView<BaseDatos> param) -> {
            final TableRow<BaseDatos> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            rowMenu.getItems().addAll(this.crearPoliticaMenuItem(row), this.modificarMenuItem(row), this.eliminarMenuItem(row));
            row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty()))
               .then(rowMenu)
               .otherwise((ContextMenu)null));
            return row;
        });
    }
    private void initializeTabPoliticas(){
        this.tc_sidPolitica.setCellValueFactory(new PropertyValueFactory("sid"));
        this.tc_nombrePolitica.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tc_descPolitica.setCellValueFactory(new PropertyValueFactory("desc"));
        this.tc_activo.setCellValueFactory(new PropertyValueFactory("activo"));
        this.tc_activo.setCellFactory((TableColumn<Politica, Boolean> p) ->{ 
            CheckBoxTableCell n = new CheckBoxTableCell<>();
                n.setDisable(true);
                n.setOpacity(1.0);
            return n;
        });
        
        RegistroPoliticas.getInstance().getSortedList().comparatorProperty().bind(this.tv_politicas.comparatorProperty());
        this.tv_politicas.setItems(RegistroPoliticas.getInstance().getSortedList());
        this.tf_sidPolitica.textProperty().addListener((observable, oldValue, newValue) -> {
            RegistroPoliticas.getInstance().getFilteredList().setPredicate(politica -> {
                return filtroPoliticas(true, politica);
            });
	});
        this.tf_nombrePolitica.textProperty().addListener((observable, oldValue, newValue) -> {
            RegistroPoliticas.getInstance().getFilteredList().setPredicate(politica -> {
                return filtroPoliticas(false,politica);
            });
	});
        this.tv_politicas.setRowFactory((TableView<Politica> param) -> {
            final TableRow<Politica> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            rowMenu.getItems().addAll(this.modificarPoliticaMenuItem(row),activarPoliticaMenuItem(row));
            row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty()))
               .then(rowMenu)
               .otherwise((ContextMenu)null));
            return row;
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTabBaseDatos();
        initializeTabPoliticas();
        ArrayList<String> list = new ArrayList();list.add("SYSAUX");list.add("SYSTEM");
        RegistroBasesDatos.getInstance().agregarBaseDatos(new BaseDatos("localhost",1521,"XE","rmanuser","rmanuser"));
    }    
    
    /*-----------------------Tab Base de Datos----------------------------*/
    private MenuItem eliminarMenuItem(TableRow<BaseDatos> row){
        MenuItem removeItem = new MenuItem("Eliminar");
        removeItem.setOnAction((ActionEvent event) -> {
            RegistroBasesDatos.getInstance().eliminarBaseDatos(row.getItem());
        });
        return removeItem;
    }
    private MenuItem modificarMenuItem(TableRow<BaseDatos> row){
        MenuItem editItem = new MenuItem("Editar");
        editItem.setOnAction((ActionEvent event)->{
            crearBDStage(row.getItem());
        });
        return editItem;
    }
    private MenuItem crearPoliticaMenuItem(TableRow<BaseDatos> row){
        MenuItem editItem = new MenuItem("Agregar Politica");
        editItem.setOnAction((ActionEvent event)->{
             crearPoliticaStage(new Politica(row.getItem()),false);
        });
        return editItem;
    }
    private void crearBDStage(BaseDatos bd){
        FXMLLoader loader = new FXMLLoader(BaseDatosController.class.getResource("/Vista/FXML/CrearBD.fxml"));
            final Stage secondaryStage = new Stage(StageStyle.UTILITY);
            try {
                secondaryStage.setScene(new Scene((Pane) loader.load()));
            } catch (IOException ex) {
                Exceptions.ExceptionDialog(ex,st);
                Logger.getLogger(BaseDatosController.class.getName()).log(Level.SEVERE, null, ex);
            }
            CrearBDController controller = loader.<CrearBDController>getController();
            controller.initData(bd,secondaryStage);
            secondaryStage.initOwner(st);
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.show();
    }
    
    /*-----------------------Tab Politicas-------------------------------*/
    private boolean filtroPoliticas(Boolean flag, Politica pol){
        if(this.tf_nombrePolitica.getText().isEmpty() && this.tf_sidPolitica.getText().isEmpty()) return true;
        if(pol.getNombre().toLowerCase().contains(this.tf_nombrePolitica.getText().toLowerCase()) &&
           pol.getSID().toLowerCase().contains(this.tf_sidPolitica.getText().toLowerCase()))
            return true;
        return false;
    }
    private void crearPoliticaStage(Politica pol, boolean modificar){
        FXMLLoader loader = new FXMLLoader(BaseDatosController.class.getResource("/Vista/FXML/CrearPolitica.fxml"));
            final Stage secondaryStage = new Stage(StageStyle.UTILITY);
        try {
            if(!modificar) Coneccion.getInstance().conectar(pol.getBaseDatos());/*Trata de conectarse*/
            secondaryStage.setScene(new Scene((Pane) loader.load()));
            CrearPoliticaController controller = loader.<CrearPoliticaController>getController();
            controller.initData(pol,secondaryStage, modificar);
            secondaryStage.initOwner(st);
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.show();
        } catch (IOException | SQLException ex) {
            Exceptions.ExceptionDialog(ex,st);
            Logger.getLogger(BaseDatosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private MenuItem modificarPoliticaMenuItem(TableRow<Politica> row){
        MenuItem editItem = new MenuItem("Detalles");
        editItem.setOnAction((ActionEvent event)->{
            crearPoliticaStage(row.getItem(),true);
        });
        return editItem;
    }    
    private MenuItem activarPoliticaMenuItem(TableRow<Politica> row){
        MenuItem editItem = new MenuItem("Activar/Desactivar");
        editItem.setOnAction((ActionEvent event)->{
            Cliente c = new Cliente();
            try {
                System.out.println(row.getItem().toString());
                c.enviarMensaje(row.getItem());
                row.getItem().setActivo(!row.getItem().getActivo());
                if(row.getItem().getActivo())Exceptions.InformationDialog("La politica se ha Activado correctamente", st);
                else Exceptions.InformationDialog("La politica se ha Desactivado correctamente", st);
            } catch (IOException | InterruptedException | ClassNotFoundException ex) {
                Exceptions.ExceptionDialog(ex, st);
                Exceptions.ErrorDialog("Activacion de Politica", "No se pudo activar la politica verifique el Ejecutor y Active mas tarde", st);
                Logger.getLogger(CrearPoliticaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return editItem;
    }
    
    @FXML
    private void crearBaseDatosBarMenuAction(ActionEvent event){
        this.crearBDStage(null);
    }
}
