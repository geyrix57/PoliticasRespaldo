/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package politicasrespaldo;

import Modelo.Beans.BaseDatos;
import Modelo.Beans.Politica;
import Modelo.Beans.PoliticaEnvio;
import Modelo.Connection.Cliente;
import Modelo.Conteiners.RegistroBasesDatos;
import Modelo.Conteiners.RegistroPoliticas;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author geykel
 */
public class PoliticasRespaldo extends Application {
    
    @Override
    public void start(Stage stage) throws FileNotFoundException, IOException {
        Parent root = FXMLLoader.load(PoliticasRespaldo.class.getResource("/Vista/FXML/BaseDatos.fxml"));
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
       launch(args);
    }
    
}
