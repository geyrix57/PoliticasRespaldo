/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Connection;

import Modelo.Beans.BaseDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author geykel
 */
public class Coneccion {
    
    private Coneccion(){}
    
    public static Coneccion getInstance() {
        if(INSTANCE == null){
            INSTANCE = new Coneccion();
        }
        return INSTANCE;
    }
    
    public boolean conectar(BaseDatos bd) throws SQLException{
        con = DriverManager.getConnection(bd.getUrl(),bd.getUser(),bd.getPassword());
        return true;
    }
    
    public boolean isConnected() throws SQLException{
        return !con.isClosed();
    }
    
    public void close() throws SQLException{
        con.close();  
    }
    
    public ResultSet ExecuteQuery(String sql) throws SQLException{
        ResultSet resp = null;
            if(this.isConnected()){
                Statement stm = con.createStatement();
                resp = stm.executeQuery(sql);
            }
        return resp;
    }
    
    public boolean testConnection(BaseDatos bd) throws SQLException {
        this.conectar(bd);
        this.close();
        return true;
    }
    
    private static Coneccion INSTANCE = null;
    private Connection con;
}
