/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package the.wocksapp;

import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author Daniele
 */
public class ThreadReceive extends Thread {

    public DatagramSocket Receive_socket;
    public boolean running;

    public ThreadReceive() throws SocketException {
        Receive_socket = new DatagramSocket(12345);
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            byte[] buffer = new byte[1500];
            DatagramPacket Packet = new DatagramPacket(buffer, buffer.length);
            try {
                Receive_socket.receive(Packet);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(ThreadReceive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            System.out.println(new String(Packet.getData()));
        }
    }

}
