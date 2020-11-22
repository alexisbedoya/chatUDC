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
  
    

    public Cliente(String name , ServidorIF server,JTextArea jtext1,JTextArea jtext2) throws RemoteException{
        this.name = name;
        this.server = server;
        this.input = jtext1;
        this.output = jtext2;
       
        server.addCliente(this);
    }
    
  
   @Override
    public void retrieveMessage(String message) throws RemoteException {
        output.setText(output.getText() + "\n" + message);
    }
    
    
  
    public void enviarMensaje(List<String> list) {
        try {
            server.enviarMesaje(name + " : " + input.getText(),list);
        } catch (RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
 
    @Override
    public String getNombre() {
        return name;
    }

    
    @Override
    public void cerrarChat(String message) throws RemoteException {
        input.setEditable(false);
        input.setEnabled(false);
        JOptionPane.showMessageDialog(new JFrame(),message,"Alert",JOptionPane.WARNING_MESSAGE); 
    }

   
    @Override
    public void abrirChat() throws RemoteException {
        input.setEditable(true);
        input.setEnabled(true);    
    }

  
}