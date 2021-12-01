/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniele
 */
public class GestioneChat extends Thread {

    private ArrayList<String> messaggi;
    private Main m;
    private static GestioneChat instanceGC = null;
    private String msg;

    private GestioneChat() {
        messaggi = new ArrayList<String>();
        msg = "";
    }

    public GestioneChat(Main m) {
        messaggi = new ArrayList<String>();
        this.m = m;
    }

    public void AddMessaggio(String mess, String messarrivato) {
        messaggi.add(mess);
        if (messarrivato.equals("sx")) {
            // il messaggio é arrivato quindi lo stampo a sinistra
            msg = "sx;" + mess;
        } else {
            // altrimenti lo stampo a destra
            msg = "dx;" + mess;
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static GestioneChat getInstance() {
        if (instanceGC == null) {
            return instanceGC = new GestioneChat();
        }
        return instanceGC;
    }

}
