/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Conteiners;

import Modelo.Beans.Politica;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 *
 * @author geykel
 */
public class RegistroPoliticas {
    private RegistroPoliticas() {
        masterData = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(masterData, b -> true);
        sortedData = new SortedList<>(filteredData);
    }
    
    public void agregarPolitica(Politica pol){
        masterData.add(pol);
    }
    
    public void eliminarPolitica(Politica pol){
        masterData.remove(pol);
    }

    public FilteredList<Politica> getFilteredList(){
        return filteredData;
    }
    
    public SortedList<Politica> getSortedList(){
        return sortedData;
    }
    
    public static RegistroPoliticas getInstance() {
        return RegistroPoliticasHolder.INSTANCE;
    }
    
    private static class RegistroPoliticasHolder {
        private static final RegistroPoliticas INSTANCE = new RegistroPoliticas();
    }
    
    private final ObservableList<Politica> masterData;
    private final FilteredList<Politica> filteredData;
    private final SortedList<Politica> sortedData;
}
