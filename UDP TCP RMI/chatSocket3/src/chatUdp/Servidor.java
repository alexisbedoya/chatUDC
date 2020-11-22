/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatUdp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Servidor extends Thread {

    private DatagramSocket ds;
    private List<Integer> portList;
    private List<InetAddress> addressList;

    public static void main(String args[]) throws Exception {
        Servidor s = new Servidor();
        s.start();
    }

    public Servidor() {
        try {
            ds = new DatagramSocket(1313);
            addressList = new ArrayList<>();
            portList = new ArrayList<>();
        } catch (SocketException ex) {
            System.out.println(ex.getMessage() + " Local: Servidor iniciado");
        }
    }

    @Override
    public void run() {
        try {

            DatagramSocket ds = new DatagramSocket(1314);
            System.out.println("Esperando por conexion......");

            DatagramPacket received = new DatagramPacket(new byte[1024], 1024);

            while (true) {
                ds.receive(received);

                if (!portList.contains(received.getPort())) {
                    portList.add(received.getPort());
                    addressList.add(received.getAddress());
                }

                for (int i = 0; i < portList.size(); i++) {
                    if (portList.get(i) != received.getPort()) {
                        DatagramPacket resp = new DatagramPacket(received.getData(), received.getLength(), addressList.get(i), portList.get(i));
                        ds.send(resp);
                    }
                }
                System.out.println();
            }

        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }
    }
}