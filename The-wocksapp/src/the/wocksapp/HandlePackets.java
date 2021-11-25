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
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniele
 */
public class HandlePackets extends Thread {

    private DatagramSocket SocketReceive;
    private DatagramSocket SocketSend;
    private boolean running;
    private boolean inAttesa;
    private boolean connected;
    private boolean messagging;
    private Timer t;

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
        inAttesa = false;
        connected = false;
        t = new Timer(10000);
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1500];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        String nomeDestinatario = "";
        String data = "";
        running = true;
        while (running) {
            try {
                SocketReceive.receive(packet);
                data = new String(packet.getData());
                System.out.println(data);
                nomeDestinatario = data.substring(2, data.length());
                if (!messagging) {
                    if (!inAttesa && !connected && data.charAt(0) == 'a') {
                        int result = JOptionPane.showConfirmDialog(null, "Vuoi connetterti con " + nomeDestinatario + "?", "Warning", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            String nome = JOptionPane.showInputDialog(null, "Inserisci il tuo nome");
                            byte[] responseBuffer = ("y;" + nome).getBytes();
                            inAttesa = true;
                            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                            responsePacket.setAddress(packet.getAddress());
                            responsePacket.setPort(12345);
                            SocketSend.send(responsePacket);
                            JOptionPane.showMessageDialog(null, "Connesso con " + nomeDestinatario);

                        } else {
                            byte[] responseBuffer2 = ("n;").getBytes();
                            DatagramPacket responsePacket2 = new DatagramPacket(responseBuffer2, responseBuffer2.length);
                            responsePacket2.setAddress(packet.getAddress());
                            responsePacket2.setPort(12345);
                            SocketSend.send(responsePacket2);
                            JOptionPane.showMessageDialog(null, nomeDestinatario + "non puó comunicare.");
                        }
                    } else if (connected || inAttesa) {
                        byte[] responseBuffer2 = ("n;").getBytes();
                        DatagramPacket responsePacket2 = new DatagramPacket(responseBuffer2, responseBuffer2.length);
                        responsePacket2.setAddress(packet.getAddress());
                        responsePacket2.setPort(12345);
                        SocketSend.send(responsePacket2);
                    }
                    if (data.charAt(0) == 'y') {
                        byte[] responseBuffer3 = ("y;").getBytes();
                        DatagramPacket responsePacket3 = new DatagramPacket(responseBuffer3, responseBuffer3.length);
                        responsePacket3.setAddress(packet.getAddress());
                        responsePacket3.setPort(12345);
                        SocketSend.send(responsePacket3);
                        connected = true;
                        messagging = true;
                    } else {
                        byte[] responseBuffer4 = ("n;").getBytes();
                        DatagramPacket responsePacket4 = new DatagramPacket(responseBuffer4, responseBuffer4.length);
                        responsePacket4.setAddress(packet.getAddress());
                        responsePacket4.setPort(12345);
                        SocketSend.send(responsePacket4);
                    }
                    
                    if (data.charAt(0) == 'c') {
                        JOptionPane.showMessageDialog(null, "Disconnesso da " + nomeDestinatario);
                        byte[] responseBuffer4 = ("c;").getBytes();
                        DatagramPacket responsePacket4 = new DatagramPacket(responseBuffer4, responseBuffer4.length);
                        responsePacket4.setAddress(packet.getAddress());
                        responsePacket4.setPort(12345);
                        SocketSend.send(responsePacket4);
                        break;
                    }
                }

                if (messagging && data.charAt(0) == 'm') {
                    JOptionPane.showMessageDialog(null, "Puoi iniziare a messaggiare con" + nomeDestinatario);
                    String msg = data.substring(2, data.length());
                    GestisciMessaggi gm = new GestisciMessaggi(msg);
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(HandlePackets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        if (!running && data.charAt(0) == 'a') {
            JOptionPane.showMessageDialog(null, "Disconnesso da " + nomeDestinatario);
            byte[] responseBuffer4 = ("c;").getBytes();
            DatagramPacket responsePacket4 = new DatagramPacket(responseBuffer4, responseBuffer4.length);
            responsePacket4.setAddress(packet.getAddress());
            responsePacket4.setPort(12345);
            try {
                SocketSend.send(responsePacket4);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(HandlePackets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
    }

    public void SendOpeningMessage(String data, InetAddress address) {
        byte[] risposta = data.getBytes();

        DatagramPacket responsePacketOpening = new DatagramPacket(risposta, risposta.length);
        responsePacketOpening.setAddress(address);
        responsePacketOpening.setPort(12345);

        try {
            SocketSend.send(responsePacketOpening);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(HandlePackets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public void CloseConnection(String data, InetAddress address) {
        byte[] risposta = data.getBytes();

        DatagramPacket responsePacketClosing = new DatagramPacket(risposta, risposta.length);
        responsePacketClosing.setAddress(address);
        responsePacketClosing.setPort(12345);

        try {
            SocketSend.send(responsePacketClosing);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(HandlePackets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public void sendMessage(String data, InetAddress address) {
        byte[] risposta = data.getBytes();

        DatagramPacket responsePacketMessage = new DatagramPacket(risposta, risposta.length);
        responsePacketMessage.setAddress(address);
        responsePacketMessage.setPort(12345);

        try {
            SocketSend.send(responsePacketMessage);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(HandlePackets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
