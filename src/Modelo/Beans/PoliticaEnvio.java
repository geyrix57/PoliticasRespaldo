/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 *
 * @author geykel
 */
public class PoliticaEnvio implements Serializable{
    private Boolean activo;
    private String sid;
    private String user;
    private String password;
    private String nombre;
    private Boolean full;
    private Boolean control;
    private Boolean logs;
    private String modo;
    private Integer puerto;
    private String ip;
    private HashMap<Integer,Integer> dias;
    private ArrayList<String> tablespaces;

    public PoliticaEnvio(String sid, String ip, Integer puerto, String user, String password, String nombre, Boolean full, Boolean control, Boolean logs, String modo, HashMap<Integer, Integer> dias, ArrayList<String> tablespaces) {
        this.sid = sid;
        this.user = user;
        this.nombre = nombre;
        this.full = full;
        this.control = control;
        this.logs = logs;
        this.modo = modo;
        this.dias = dias;
        this.tablespaces = tablespaces;
        this.password = password;
        this.puerto = puerto;
        this.ip = ip;
        activo = true;
    }
    
    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        PoliticaEnvio p = (PoliticaEnvio)o;
        if( p.getNombre().equals(this.nombre) && p.getUser().equals(this.user) && p.getSid().equals(this.sid) )
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.sid);
        hash = 73 * hash + Objects.hashCode(this.user);
        hash = 73 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    public Boolean isActivo(){
        return this.activo;
    }
    
    public void setActivo(Boolean a){
        this.activo = a;
    }
    
    @Override
    public String toString(){
        return this.nombre+" "+tablespaces.toString();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPuerto() {
        return puerto;
    }

    public void setPuerto(Integer puerto) {
        this.puerto = puerto;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUser() {
        return user;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    
    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getFull() {
        return full;
    }

    public void setFull(Boolean full) {
        this.full = full;
    }

    public Boolean getControl() {
        return control;
    }

    public void setControl(Boolean control) {
        this.control = control;
    }

    public Boolean getLogs() {
        return logs;
    }

    public void setLogs(Boolean logs) {
        this.logs = logs;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public HashMap<Integer, Integer> getDias() {
        return dias;
    }

    public void setDias(HashMap<Integer, Integer> dias) {
        this.dias = dias;
    }

    public ArrayList<String> getTablespaces() {
        return tablespaces;
    }

    public void setTablespaces(ArrayList<String> tablespaces) {
        this.tablespaces = tablespaces;
    }
}
