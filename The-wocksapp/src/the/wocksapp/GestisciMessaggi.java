/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package the.wocksapp;

import java.util.ArrayList;

/**
 *
 * @author Daniele
 */
public class GestisciMessaggi {

    private ArrayList<String> messaggi;

    public GestisciMessaggi() {
        messaggi = new ArrayList<String>();
    }

    public GestisciMessaggi(String m) {
        messaggi.add(m);
    }

    public String getMessaggi() {
        String str = "";
        for (int i = 0; i < messaggi.size(); i++) {
            str += messaggi.get(i).toString();
        }
        return str;
    }

}
