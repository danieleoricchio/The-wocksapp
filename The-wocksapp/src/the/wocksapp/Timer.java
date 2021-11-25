/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package the.wocksapp;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniele
 */
public class Timer extends Thread {

    private boolean finito;
    private boolean running;
    public int waitingTime;

    public Timer(int waitingTime) {
        this.waitingTime = waitingTime;
        finito = false;
        running = false;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                sleep(waitingTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
            }
            finito = true;
            running = false;
        }
    }

    public boolean isDone() {
        return finito;
    }
}
