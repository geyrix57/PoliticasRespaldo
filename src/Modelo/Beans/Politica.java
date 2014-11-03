/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Beans;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author geykel
 */
public class Politica {
    public Politica(String nombre, String desc, Boolean full, Boolean control, Boolean logs, String modo, BaseDatos db){
        this.nombre = new SimpleStringProperty(nombre);
        this.desc = new SimpleStringProperty(desc);
        this.sid = new SimpleStringProperty(db.getSid());
        this.full = new SimpleBooleanProperty(full);
        this.control = new SimpleBooleanProperty(control);
        this.logs = new SimpleBooleanProperty(logs);
        this.tablespaces = new ArrayList();
        this.db = db;
        this.modo = new SimpleStringProperty(modo);
        inicializarDias();
    }
    public Politica(String nombre, String desc, Boolean full, Boolean control, Boolean logs, String modo, BaseDatos db, ArrayList list){
        this.nombre = new SimpleStringProperty(nombre);
        this.desc = new SimpleStringProperty(desc);
        this.sid = new SimpleStringProperty(db.getSid());
        this.full = new SimpleBooleanProperty(full);
        this.control = new SimpleBooleanProperty(control);
        this.logs = new SimpleBooleanProperty(logs);
        this.tablespaces = list;
        this.db = db;
        this.modo = new SimpleStringProperty(modo);
        inicializarDias();
    }
    public Politica(BaseDatos bd){
        this.modo = new SimpleStringProperty();
        this.nombre = new SimpleStringProperty();
        this.desc = new SimpleStringProperty();
        this.sid = new SimpleStringProperty(bd.getSid());
        this.full = new SimpleBooleanProperty(false);
        this.control = new SimpleBooleanProperty(false);
        this.logs = new SimpleBooleanProperty(false);
        this.tablespaces = new ArrayList();
        this.db = bd;
        inicializarDias();
    }
    /*public Politica(Politica p){
        this.db = p.getBaseDatos();
        this.modo = new SimpleStringProperty(p.modoProperty().get());
        this.nombre = new SimpleStringProperty(p.getNombre());
        this.desc = new SimpleStringProperty(p.getDesc());
        this.sid = new SimpleStringProperty(this.db.getSid());
        this.full = new SimpleBooleanProperty(p.fullProperty().get());
        this.control = new SimpleBooleanProperty(p.controlProperty().get());
        this.logs = new SimpleBooleanProperty(p.logsProperty().get());
        this.tablespaces = (ArrayList)p.getTablespaces().clone();
        this.dias = new HashMap();
        for(int i=0;i<7;i++){
            this.dias.put(i,new SimpleStringProperty(p.getDias().get(i).get()));
        }
    }*/
    private void inicializarDias(){
        this.dias = new HashMap();
        for(int i=0;i<7;i++)
            this.dias.put(i, new SimpleStringProperty());
    }
    
    public StringProperty nombreProperty(){
        return this.nombre;
    }
    public StringProperty descProperty(){
        return this.desc;
    }
    public StringProperty sidProperty(){
        return this.sid;
    }
    public BooleanProperty fullProperty(){
        return this.full;
    }
    public BooleanProperty controlProperty(){
        return this.control;
    }
    public BooleanProperty logsProperty(){
        return this.logs;
    }
    public StringProperty modoProperty(){
        return this.modo;
    }
    
    public boolean findTablespace(String ts){
        return this.tablespaces.contains(ts);
    }
    public void agregarTablespace(String tablespace){
        this.tablespaces.add(tablespace);
    }
    public void eliminarTablespace(String tablespace){
        this.tablespaces.remove(tablespace);
    }
    /*public void copy(Politica p){
        this.db = p.getBaseDatos();
        this.modo.set(p.modoProperty().get());
        this.nombre.set(p.getNombre());
        this.desc.set(p.getDesc());
        this.sid.set(this.db.getSid());
        this.full.set(p.fullProperty().get());
        this.control.set(p.controlProperty().get());
        this.logs.set(p.logsProperty().get());
        this.tablespaces = (ArrayList)p.getTablespaces().clone();
        this.dias = (HashMap)p.getDias().clone();
    }*/
    
    public BaseDatos getBaseDatos(){
        return this.db;
    }
    public String getSID(){
        return this.sid.get();
    }
    public String getNombre(){
        return this.nombre.get();
    }
    public String getDesc(){
        return this.desc.get();
    }
    public ArrayList getTablespaces(){
        return this.tablespaces;
    }
    public StringProperty getDia(int ind){
        return this.dias.get(ind);
    }
    public HashMap<Integer,StringProperty> getDias(){
        return this.dias;
    }
    
    private final StringProperty nombre;
    private final StringProperty desc;
    private final StringProperty sid;
    private final BooleanProperty full;
    private final BooleanProperty control;
    private final BooleanProperty logs;
    private final StringProperty modo;
    private HashMap<Integer,StringProperty> dias;
    
    private BaseDatos db;
    private ArrayList<String> tablespaces;
    
}
