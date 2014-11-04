/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Connection;

import Modelo.Beans.Politica;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author geykel
 */
public class Cliente{
    
    private Socket socket = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private boolean isConnected = false;
    private Politica pol;
    public Cliente(){}
    public boolean enviarMensaje(Politica p) throws IOException, InterruptedException, ClassNotFoundException{
        pol = p;
        run();
        return true;
    }
    private void enviarPolitica() throws IOException, InterruptedException, ClassNotFoundException{
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream.writeObject(pol.generarPoliticaEnvio());
        String ok = (String)inputStream.readObject();
        System.out.println(ok);
    }    
    private void run() throws IOException, InterruptedException, ClassNotFoundException{
        while (!isConnected) {
            socket = new Socket(pol.getBaseDatos().getAddress(), 4445);
            System.out.println("Connected");
            isConnected = true;
            enviarPolitica();
        }
    }   

}
