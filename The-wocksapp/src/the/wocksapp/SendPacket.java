/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package the.wocksapp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author Daniele
 */
public class SendPacket {

    DatagramSocket Send_socket;

    public SendPacket(Peer p) throws SocketException, IOException {
        Send_socket = new DatagramSocket();
        byte[] buffer = "ciao".getBytes();
        DatagramPacket Packet = new DatagramPacket(buffer, buffer.length, p.getIpAddress(), p.getPort());
        Send_socket.send(Packet);
    }
}
