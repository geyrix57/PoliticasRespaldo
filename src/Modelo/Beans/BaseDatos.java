/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Beans;

import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author geykel
 */
public class BaseDatos{
    public BaseDatos(String address, Integer port, String sid, String user, String password){
        this.sid = new SimpleStringProperty(sid);
        this.address = new SimpleStringProperty(address);
        this.user = new SimpleStringProperty(user);
        this.password = password;
        this.port = new SimpleIntegerProperty(port);
    }    
    
    public StringProperty sidProperty(){
        return this.sid;
    }
    
    public StringProperty addressProperty(){
        return this.address;
    }
    
    public StringProperty userProperty(){
        return this.user;
    }
    
    public IntegerProperty portProperty(){
        return this.port;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getSid(){
        return this.sid.get();
    }
    
    public void setSid(String sid){
        this.sid.set(sid);
    }
    
    public String getAddress(){
        return this.address.get();
    }
    
    public void setAddress(String address){
        this.address.set(address);
    }
    
    public String getUser(){
        return this.user.get();
    }
    
    public void setUser(String user){
        this.user.set(user);
    }
    
    public Integer getPort(){
        return this.port.get();
    }
    
    public void setPort(Integer port){
        this.port.set(port);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.sid.get());
        hash = 67 * hash + Objects.hashCode(this.address.get());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseDatos other = (BaseDatos) obj;
        if (!Objects.equals(this.sid.get(), other.sid.get())) {
            return false;
        }
        return Objects.equals(this.address.get(), other.address.get());
    }
    
    public String getUrl(){
        StringBuilder url = new StringBuilder("jdbc:oracle:thin:");
        url.append("@//").append(this.getAddress()).append(":").append(this.getPort()).append("/").append(this.getSid());
        return url.toString();
    }
    
    private final StringProperty sid;
    private final StringProperty address;
    private final StringProperty user;
    private String password;
    private final IntegerProperty port;
}
