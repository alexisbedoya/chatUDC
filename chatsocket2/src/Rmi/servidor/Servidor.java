package Rmi.servidor;

import Rmi.Inteface.ServidorIF;
import Rmi.Inteface.ClienteIF;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alexi
 */
public class Servidor extends UnicastRemoteObject implements ServidorIF {
    private final ArrayList<ClienteIF> clients; 
    private VentanaS ventana;
    
    
    public Servidor(VentanaS ventana) throws RemoteException{
        super();
        this.clients = new ArrayList<>();
        this.ventana = ventana;
    }
    

    @Override
    public synchronized void enviarMesaje(String message,List<String> list) throws RemoteException {
        if(list.isEmpty()){
            int i= 0;
            while (i < clients.size()){
                clients.get(i++).retrieveMessage(message);
            }
        }else{
            for (ClienteIF client : clients) {
                for(int i=0;i<list.size();i++){
                    if(client.getNombre().equals(list.get(i))){
                        client.retrieveMessage(message);
                    }
                }
            }
        }
    }
    
        
    //cette fonction pour ajouter un client connectes a la liste des clients sur le serveur
    @Override
    public synchronized void addCliente(ClienteIF client) throws RemoteException {
        this.clients.add(client);
    }
    
    //cette fonction pour recupere le nom des clients connectes
    @Override
    public synchronized Vector<String> getListClienteName(String name) throws RemoteException {
        Vector<String> list = new Vector<>();
        for (ClienteIF client : clients) {
            if(!client.getNombre().equals(name)){
                list.add(client.getNombre());
            }
        }
        return list;
    }
    
    //cette fonction pour supprimer totalement une liste des clients de chat (kick-out)
    @Override
    public synchronized void removeClient(List<String> clients){
        for(int j=0;j<this.clients.size();j++){
            for(int i=0;i<clients.size();i++){
                try {
                    if(this.clients.get(j).getNombre().equals(clients.get(i))){
                        this.clients.get(j).cerrarChat(clients.get(i) + " you are removed from the chat");
                        this.clients.remove(j);
                    }
                } catch (RemoteException ex) {
                    ventana.agregarLog("Error: " + ex.getMessage());
                }
            }
        }
    }
    
    //cette fonction pour supprimer totalement un seul client de chat (kick-out)
    @Override
    public synchronized void removeClient(String clients){
        for(int j=0;j<this.clients.size();j++){
            try {
                if(this.clients.get(j).getNombre().equals(clients)){
                    this.clients.remove(j);
                }
            } catch (RemoteException ex) {
                ventana.agregarLog("Error: " + ex.getMessage());
            }
        }
    }

   
    //cette fonction pour verfifier est que un username existe deja dans le serveur ou non, car username est l'identificateur sur chat
    @Override
    public boolean checkNombreusuario(String username) throws RemoteException {
        boolean exist = false;
        for(int i=0;i<clients.size();i++){
            if(clients.get(i).getNombre().equals(username)){
                exist = true;
            }
        }
        return exist;
    }
    
}
