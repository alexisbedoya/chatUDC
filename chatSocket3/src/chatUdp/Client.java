/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatUdp;

/**
 *
 * @author alexi
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Thread {

    private final DatagramSocket ds;

    public Client(DatagramSocket socket) {
        this.ds = socket;
    }

    public static void main(String[] args) {

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date hora = Calendar.getInstance().getTime();
            String dataFormatada = sdf.format(hora);

            DatagramSocket ds = new DatagramSocket();
            InetAddress destino = InetAddress.getByName("localhost");
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            String envio, textoMensagem;

            System.out.print("Ingrese su nombre: ");
            String nome = teclado.readLine();
            System.out.println("Bienvenido " + nome);

            envio = nome + " Ha entrado a la sala!";

            Thread t = new Client(ds);
            t.start();

            while (!envio.equalsIgnoreCase("Salio de la sala") || !envio.equalsIgnoreCase("")) {
                byte[] buffer = envio.getBytes();
                DatagramPacket msg = new DatagramPacket(buffer, buffer.length, destino, 1314);
                ds.send(msg);
                textoMensagem = teclado.readLine();

                if (!textoMensagem.equals("")) {
                    envio = nome + " [" + dataFormatada + "]: " + textoMensagem;
                } else {
                    envio = textoMensagem;
                }
            }

            envio = nome + " Salio de la sala!";
            
            byte[] buffer = envio.getBytes();
            DatagramPacket msg = new DatagramPacket(buffer, buffer.length, destino, 1314);
            ds.send(msg);
            ds.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {

        try {
            while (true) {
                DatagramPacket resposta = new DatagramPacket(new byte[1024], 1024);
                ds.receive(resposta);

                System.out.println(new String(resposta.getData()));
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}