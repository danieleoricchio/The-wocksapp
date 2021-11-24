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
import java.net.InetAddress;
import java.net.SocketException;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniele
 */
public class HandlePackets extends Thread {

    private DatagramSocket SocketReceive;
    private DatagramSocket SocketSend;

    public HandlePackets() {
        try {
            SocketReceive = new DatagramSocket(12345);
        } catch (SocketException ex) {
            java.util.logging.Logger.getLogger(HandlePackets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        try {
            SocketSend = new DatagramSocket();
        } catch (SocketException ex) {
            java.util.logging.Logger.getLogger(HandlePackets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1500];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        try {
            SocketReceive.receive(packet);
            String data = new String(packet.getData());
            System.out.println(data);

            if (data.charAt(0) == 'a') {
                int result = JOptionPane.showConfirmDialog(null, "Vuoi connetterti con " + data.substring(2, data.length()) + "?", "Warning", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    String nome = JOptionPane.showInputDialog(null, "Inserisci il tuo nome");
                    byte[] responseBuffer = ("y;" + nome).getBytes();
                    DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                    responsePacket.setAddress(packet.getAddress());
                    responsePacket.setPort(12345);
                    SocketSend.send(responsePacket);
                } else {
                    byte[] responseBuffer = ("n;").getBytes();
                    DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                    responsePacket.setAddress(packet.getAddress());
                    responsePacket.setPort(12345);
                    SocketSend.send(responsePacket);
                }
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(HandlePackets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public boolean SendOpeningMessage(String data, InetAddress address) {
        byte[] risposta = data.getBytes();

        DatagramPacket responsePacket = new DatagramPacket(risposta, risposta.length);
        responsePacket.setAddress(address);
        responsePacket.setPort(12345);

        try {
            SocketSend.send(responsePacket);
            return true;
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(HandlePackets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return false;
    }
}
