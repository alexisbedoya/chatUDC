/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rmi.Inteface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexi
 */
public interface ClienteIF extends Remote{
    void retrieveMessage(String message) throws RemoteException;

    void enviarMensaje(List<String> list) throws RemoteException;

    String getNombre()throws RemoteException;

    void cerrarChat(String mensaje) throws RemoteException;

    void abrirChat() throws RemoteException;
    
}
