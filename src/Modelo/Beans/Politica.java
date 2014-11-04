/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author geykel
 */
public class Politica{
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
    public ArrayList<String> getTablespaces(){
        return this.tablespaces;
    }
    public StringProperty getDia(int ind){
        return this.dias.get(ind);
    }
    public BooleanProperty activoProperty(){
        return this.activo;
    }
    public HashMap<Integer,StringProperty> getDias(){
        return this.dias;
    }
    public Boolean getActivo(){
        return this.activo.get();
    }
    public void setActivo(Boolean a){
        this.activo.set(a);
    }
    
    public PoliticaEnvio generarPoliticaEnvio(){
        HashMap<Integer,Integer> nd = new HashMap();
        for(int i=0;i<7;i++){
            if(dias.get(i).get() != null){
                StringTokenizer tokens=new StringTokenizer(dias.get(i).get(), ":");
                nd.put(i, Integer.parseInt(tokens.nextToken()));
            }
        }
        PoliticaEnvio p= new PoliticaEnvio(this.getSID(),this.db.getUser(),this.getBaseDatos().getPassword(),this.getNombre(),this.full.get(),this.control.get(),this.logs.get(),this.modo.get(),nd,this.tablespaces);
        p.setActivo(this.activo.get());
        return p;
    }
    
    @Override
    public String toString(){
        String str = "Nombre: "+this.getNombre()+" Descripcion: "+this.getDesc()+"\n";
        str +=this.tablespaces.toString();
        return str;
    }
    
    private BooleanProperty activo = new SimpleBooleanProperty(true);
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