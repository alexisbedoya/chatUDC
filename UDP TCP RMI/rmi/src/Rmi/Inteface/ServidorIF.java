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
import java.util.Vector;

/**
 *
 * @author alexi
 */
public interface ServidorIF extends Remote{
    
    void enviarMesaje(String mensaje,List<String> list) throws RemoteException;
    Vector<String> getListClienteName(String name) throws RemoteException;
    
    void addCliente(ClienteIF client) throws RemoteException;
   
    void removeClient(List<String> clients) throws RemoteException;
    
    void removeClient(String clients) throws RemoteException;
  
    boolean checkNombreusuario(String username) throws RemoteException;
    
}
