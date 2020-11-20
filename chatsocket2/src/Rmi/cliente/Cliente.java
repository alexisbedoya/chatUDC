package Rmi.cliente;

import Rmi.Inteface.ClienteIF;
import Rmi.Inteface.ServidorIF;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Cliente extends UnicastRemoteObject implements ClienteIF{
    private final ServidorIF server;
    private final String name;
    private final JTextArea input;
    private final JTextArea output;
  
    
    //constructeur
    public Cliente(String name , ServidorIF server,JTextArea jtext1,JTextArea jtext2) throws RemoteException{
        this.name = name;
        this.server = server;
        this.input = jtext1;
        this.output = jtext2;
       
        server.addCliente(this);
    }
    
    //cette fonction pour recupere les messages de la discuttions a partir de server
   @Override
    public void retrieveMessage(String message) throws RemoteException {
        output.setText(output.getText() + "\n" + message);
    }
    
    
    //cette fonction pour envoyer un message vers le serveur
    public void enviarMensaje(List<String> list) {
        try {
            server.enviarMesaje(name + " : " + input.getText(),list);
        } catch (RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    //cette fonction pour recupere le nom d'un client connectées
    @Override
    public String getNombre() {
        return name;
    }

    //cette fonction pour desactiver a un client la fonctionnalité d'envoyer un message
    @Override
    public void cerrarChat(String message) throws RemoteException {
        input.setEditable(false);
        input.setEnabled(false);
        JOptionPane.showMessageDialog(new JFrame(),message,"Alert",JOptionPane.WARNING_MESSAGE); 
    }

    //cette fonction pour activer a un client la fonctionnalité d'envoyer un message
    @Override
    public void abrirChat() throws RemoteException {
        input.setEditable(true);
        input.setEnabled(true);    
    }

  
}