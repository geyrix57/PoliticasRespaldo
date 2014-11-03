/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Beans;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author geykel
 */
public class Tablespace {
    public Tablespace(String nombre, Boolean agregado, Politica pol){
        this.pol = pol;
        this.nombre = new SimpleStringProperty(nombre);
        this.agregar = new SimpleBooleanProperty(agregado);
        this.agregar.addListener((ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) -> {
            if(t1) this.pol.agregarTablespace(this.nombre.get());
            else this.pol.eliminarTablespace(this.nombre.get());
        });
    }
    public StringProperty nombreProperty(){
        return this.nombre;
    }
    public BooleanProperty agregarProperty(){
        return this.agregar;
    }
    public String getNombre(){
        return this.nombre.get();
    }
    private final StringProperty nombre;
    private final BooleanProperty agregar;
    private final Politica pol;
}
