/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Beans.Politica;
import Modelo.Beans.Tablespace;
import Modelo.Connection.CargarDatos;
import Modelo.Connection.Cliente;
import Modelo.Conteiners.RegistroPoliticas;
import Vista.Custom.CheckBoxTableCell;
import Vista.Custom.Exceptions;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author geykel
 */
public class CrearPoliticaController implements Initializable {
    
    @FXML
    Label lb_sid;
    @FXML
    TextField tf_nombre;
    @FXML
    TextField tf_desc;
    @FXML
    ComboBox cb_domingo;
    @FXML
    ComboBox cb_lunes;
    @FXML
    ComboBox cb_martes;
    @FXML
    ComboBox cb_miercoles;
    @FXML
    ComboBox cb_jueves;
    @FXML
    ComboBox cb_viernes;
    @FXML
    ComboBox cb_sabado;
    @FXML
    RadioButton rd_total;
    @FXML
    RadioButton rd_parcial;
    @FXML
    TextField tf_buscarts;
    @FXML
    ComboBox cb_modo;
    @FXML
    CheckBox chb_control;
    @FXML
    CheckBox chb_log;
    @FXML
    TableView<Tablespace> tv_tablespace;
    @FXML
    TableColumn<Tablespace, String> tc_nombre;
    @FXML
    TableColumn<Tablespace, Boolean> tc_agregar;
    @FXML
    VBox vbox_panel;
    
    private ObservableList<Tablespace> masterData;
    private Politica pol;
    //private Politica backup;
    private Stage st;
    private boolean modificar;
    
    private void setTextField(){
        this.tf_nombre.textProperty().bindBidirectional(pol.nombreProperty());
        this.tf_desc.textProperty().bindBidirectional(pol.descProperty());
    }
    private void setComboBoxHora(){
        String hora;
        for(int i=0;i<25;i++){
            if(i<10) hora = "0"+Integer.toString(i)+":00";
            else if(i>23) hora="N.A.";
            else hora = Integer.toString(i)+":00";
            cb_domingo.getItems().add(hora);
            cb_lunes.getItems().add(hora);
            cb_martes.getItems().add(hora);
            cb_miercoles.getItems().add(hora);
            cb_jueves.getItems().add(hora);
            cb_viernes.getItems().add(hora);
            cb_sabado.getItems().add(hora);
        }
        this.cb_domingo.valueProperty().bindBidirectional(pol.getDia(0));
        this.cb_lunes.valueProperty().bindBidirectional(pol.getDia(1));
        this.cb_martes.valueProperty().bindBidirectional(pol.getDia(2));
        this.cb_miercoles.valueProperty().bindBidirectional(pol.getDia(3));
        this.cb_jueves.valueProperty().bindBidirectional(pol.getDia(4));
        this.cb_viernes.valueProperty().bindBidirectional(pol.getDia(5));
        this.cb_sabado.valueProperty().bindBidirectional(pol.getDia(6));
    }
    private void setComboBoxModo(){
        this.cb_modo.getItems().add("Incremental 0");
        this.cb_modo.getItems().add("Incremental 1");
        this.cb_modo.valueProperty().bindBidirectional(pol.modoProperty());
    }
    private void bindRadioButtons(){
         ToggleGroup group = new ToggleGroup();
         this.rd_parcial.setUserData(false);
         this.rd_total.setUserData(true);
         this.rd_parcial.setToggleGroup(group);
         this.rd_total.setToggleGroup(group);
         group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1)->{
              RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); 
              this.pol.fullProperty().set((Boolean)chk.getUserData());
         });
         vbox_panel.disableProperty().bindBidirectional(pol.fullProperty());
         if(pol.fullProperty().get()) this.rd_total.setSelected(true);
         else this.rd_parcial.setSelected(true);
    }
    private void bindCheckBox(){
        this.chb_control.selectedProperty().bindBidirectional(this.pol.controlProperty());
        this.chb_log.selectedProperty().bindBidirectional(this.pol.logsProperty());
    }
    private void cargarTableSpaces() throws SQLException{
        if(!modificar) masterData = FXCollections.observableArrayList(CargarDatos.cargarTablespaces(pol));
        else {
            masterData = FXCollections.observableArrayList();
            pol.getTablespaces().stream().forEach((ts) -> {
                masterData.add(new Tablespace(ts,true,pol));
            });
        }
        FilteredList<Tablespace> filteredData = new FilteredList<>(masterData, p -> true);
        SortedList<Tablespace> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(this.tv_tablespace.comparatorProperty());
        this.tv_tablespace.setItems(sortedData);
        this.tf_buscarts.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(ts -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
		return ts.getNombre().toLowerCase().contains(lowerCaseFilter);
            });
	});
    }
    
    private void deshabilitarCampos(){
        this.tf_desc.setEditable(!modificar);
        this.tf_nombre.setEditable(!modificar);
        this.cb_domingo.setDisable(modificar);
        this.cb_lunes.setDisable(modificar);
        this.cb_martes.setDisable(modificar);
        this.cb_miercoles.setDisable(modificar);
        this.cb_jueves.setDisable(modificar);
        this.cb_viernes.setDisable(modificar);
        this.cb_sabado.setDisable(modificar);
        this.cb_modo.setDisable(modificar);
        //this.tv_tablespace.setDisable(modificar);
        this.rd_parcial.setDisable(modificar);
        this.rd_total.setDisable(modificar);
        this.chb_control.setDisable(modificar);
        this.chb_log.setDisable(modificar);
        //*-----------------------------------------
        this.cb_domingo.setOpacity(1.0);
        this.cb_lunes.setOpacity(1.0);
        this.cb_martes.setOpacity(1.0);
        this.cb_miercoles.setOpacity(1.0);
        this.cb_jueves.setOpacity(1.0);
        this.cb_viernes.setOpacity(1.0);
        this.cb_sabado.setOpacity(1.0);
        this.cb_modo.setOpacity(1.0);
        //this.tv_tablespace.setOpacity(1.0);
        this.rd_parcial.setOpacity(1.0);
        this.rd_total.setOpacity(1.0);
        this.chb_control.setOpacity(1.0);
        this.chb_log.setOpacity(1.0);
    }
    public void initData(Politica pol, Stage stage, boolean modificar) throws SQLException{
        this.modificar = modificar;
        this.st = stage;
        this.pol = pol;
        //backup = new Politica(pol);
        this.lb_sid.setText("Base de Datos: "+pol.getSID());
        this.bindRadioButtons();
        this.bindCheckBox();
        this.cargarTableSpaces();
        this.setComboBoxHora();
        this.setComboBoxModo();
        this.setTextField();
        if(modificar){
            deshabilitarCampos();
            /*this.tf_nombre.setText(pol.getNombre());
            this.tf_desc.setText(pol.getDesc());*/
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tc_nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tc_agregar.setCellValueFactory(new PropertyValueFactory("agregar"));
        tc_agregar.setCellFactory((TableColumn<Tablespace, Boolean> p) ->{ 
            CheckBoxTableCell n = new CheckBoxTableCell<>();
            if(modificar){
                n.setDisable(modificar);
                n.setOpacity(1.0);
            }
            return n;
        });
    }
    
    
    private boolean validarCampos(){
        if(this.tf_nombre.textProperty().get()==null||this.tf_nombre.textProperty().get().isEmpty()){
            Exceptions.ErrorDialog("Campos vacios", "Debe llenar todos los campos.", st);
            return false;
        }
        if(this.tf_desc.textProperty().get()==null||this.tf_desc.textProperty().get().isEmpty())
            this.tf_desc.setText("Sin descripcion.");
        if(this.cb_modo.getValue() == null || this.cb_modo.getValue().toString().isEmpty()){
                Exceptions.ErrorDialog("Campos vacios", "Debe seleccionar el modo Incremental 0/Incremetal 1.", st);
                return false;
        }
        if(this.rd_parcial.isSelected()){
            if(this.pol.getTablespaces().isEmpty()){
                Exceptions.ErrorDialog("Campos vacios", "Debe seleccionar al menos un tablespace.", st);
                return false;
            }
        }
        else{
            boolean t = true;
            for(Object o:pol.getDias().values()){
                if(((StringProperty)o).get() != null && !((StringProperty)o).get().isEmpty() && !((StringProperty)o).get().equals("N.A.")){
                    t = false;
                    break;
                }
            }
            if(t){
                Exceptions.ErrorDialog("Campos vacios", "Debe seleccionar al menos un dia.", st);
                return false;
            }
        }
        return true;
    }
    @FXML
    private void cancelAction(ActionEvent event){
        //if(modificar) pol.copy(backup);
        this.st.close();
    }
    @FXML
    private void aceptarAction(ActionEvent event){
        if(!modificar){
            if(validarCampos()){
                try {
                    RegistroPoliticas.getInstance().agregarPolitica(pol);
                    Exceptions.InformationDialog("La politica ha sido registrada!!", st);
                    Cliente c = new Cliente();
                    c.enviarMensaje(pol);
                    Exceptions.InformationDialog("La politica ha sido Activada correctamennte.", st);
                } catch (IOException | InterruptedException | ClassNotFoundException ex) {
                    pol.setActivo(false);
                    Exceptions.ExceptionDialog(ex, st);
                    Exceptions.ErrorDialog("Activacion de Politica", "No se pudo activar la politica verifique el Ejecutor y Active mas tarde", st);
                    Logger.getLogger(CrearPoliticaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.st.close();
            }
        }  
    }
}
