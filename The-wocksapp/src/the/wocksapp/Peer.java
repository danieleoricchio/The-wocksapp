/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package the.wocksapp;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Daniele
 */
public class Peer {
    private String Name;
    private InetAddress IpAddress;
    public int Port;

    public Peer(String Name, String IpAddress, int Port) throws UnknownHostException {
        this.Name = Name;
        this.IpAddress = InetAddress.getByName(IpAddress);
        this.Port = Port;
    }

    @Override
    public String toString() {
        return Name + ";" + IpAddress.getHostAddress() + ";" + Port;
    }

    public String getName() {
        return Name;
    }

    public InetAddress getIpAddress() {
        return IpAddress;
    }

    public int getPort() {
        return Port;
    }
}
