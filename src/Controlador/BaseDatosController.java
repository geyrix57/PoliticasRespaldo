/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Beans.BaseDatos;
import Modelo.Conteiners.RegistroBasesDatos;
import Vista.Custom.Exceptions;
import java.io.IOException;
import java.net.URL;
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
    private Stage st;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                else if(bd.getAddress().toLowerCase().contains(lowerCaseFilter))
                    return true;
                return false;
            });
	});
        this.tv_bd.setRowFactory((TableView<BaseDatos> param) -> {
            final TableRow<BaseDatos> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            rowMenu.getItems().addAll(this.modificarMenuItem(row), this.eliminarMenuItem(row));
            row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty()))
               .then(rowMenu)
               .otherwise((ContextMenu)null));
            return row;
        });
        RegistroBasesDatos.getInstance().agregarBaseDatos(new BaseDatos("localhost",1521,"XE","sys","123"));
    }    
    
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
    
    @FXML
    private void crearBaseDatosBarMenuAction(ActionEvent event){
        this.crearBDStage(null);
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
}
