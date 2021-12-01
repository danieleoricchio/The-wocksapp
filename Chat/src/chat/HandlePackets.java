/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat;

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
    private boolean connesso;
    private GestioneChat gc;

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
        connesso = false;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1500];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        String nomeDestinatario = "";
        String data = "";
        running = true;
        boolean s = false;
        while (running) {
            try {
                SocketReceive.receive(packet);
                data = new String(packet.getData());
                String[] vs = data.split(";");
                System.out.println(data);
                nomeDestinatario = vs[1];
                if (!connesso && data.charAt(0) == 'a') {
                    int result = JOptionPane.showConfirmDialog(null, "Vuoi connetterti con " + nomeDestinatario + "?", "Richiesta di connessione", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        String nome = JOptionPane.showInputDialog(null, "Inserisci il tuo nome");
                        Send("y;" + nome, packet.getAddress());
                        connesso = true;
                    } else {
                        Send("n;", packet.getAddress());
                    }
                }
                if (!connesso && data.charAt(0) == 'y' && data.length() > 2) {
                    Send("y;", packet.getAddress());
                    System.out.println(data);
                    connesso = true;
                    JOptionPane.showMessageDialog(null, "Sei connesso con: " + nomeDestinatario);
                }
                if (!connesso && data.charAt(0) == 'y') {
                    connesso = true;
                }
                if (connesso) {
                    JOptionPane.showMessageDialog(null, "Puoi iniziare a messaggiare con: " + nomeDestinatario);
                    if (data.charAt(0) == 'm') {
                        String msg = vs[1];
                        GestioneChat.getInstance().AddMessaggio(msg, "sx");
                        msg = "";
                    }
                    if (data.charAt(0) == 'c') {
                        JOptionPane.showMessageDialog(null, "Disconnesso da " + nomeDestinatario);
                        Send("c;", packet.getAddress());
                        break;
                    }
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(HandlePackets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            buffer = new byte[1500];
        }
    }

    public void Send(String data, InetAddress indirizzoIp) {
        byte[] responseBuffer = data.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, indirizzoIp, 12345);
        try {
            SocketSend.send(responsePacket);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(HandlePackets.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
