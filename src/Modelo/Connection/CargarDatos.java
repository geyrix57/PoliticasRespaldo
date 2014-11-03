/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Connection;

import Modelo.Beans.Politica;
import Modelo.Beans.Tablespace;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author geykel
 */
public class CargarDatos {
    public static ArrayList<Tablespace> cargarTablespaces(Politica pol) throws SQLException{
        ArrayList<Tablespace> list = new ArrayList();
        ResultSet result = Coneccion.getInstance().ExecuteQuery("SELECT TABLESPACE_NAME FROM DBA_DATA_FILES");
        while (result.next()) {
            String tsname = result.getString("TABLESPACE_NAME");
            list.add(new Tablespace(tsname,pol.findTablespace(tsname),pol));
        }
        result.getStatement().close();
        return list;
    }
}
