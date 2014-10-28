/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Conteiners;

import Modelo.Beans.BaseDatos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 *
 * @author geykel
 */
public class RegistroBasesDatos{
    
    private RegistroBasesDatos() {
        masterData = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(masterData, b -> true);
        sortedData = new SortedList<>(filteredData);
    }
    
    public void agregarBaseDatos(BaseDatos bd){
        masterData.add(bd);
    }
    
    public void eliminarBaseDatos(BaseDatos bd){
        masterData.remove(bd);
    }

    public FilteredList<BaseDatos> getFilteredList(){
        return filteredData;
    }
    
    public SortedList<BaseDatos> getSortedList(){
        return sortedData;
    }
    
    public static RegistroBasesDatos getInstance() {
        return RegistroBaseDatosHolder.INSTANCE;
    }
    
    private static class RegistroBaseDatosHolder {
        private static final RegistroBasesDatos INSTANCE = new RegistroBasesDatos();
    }
    
    private final ObservableList<BaseDatos> masterData;
    private final FilteredList<BaseDatos> filteredData;
    private final SortedList<BaseDatos> sortedData;
}
