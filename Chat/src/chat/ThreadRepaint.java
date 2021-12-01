/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Daniele
 */
public class ThreadRepaint extends Thread{
    JFrame f;
    int FPS;

    public ThreadRepaint(JFrame f, int i) {
        this.f = f;
        FPS = 1000 / i;
    }

    @Override
    public void run() {
        while (true) {
            f.repaint();
            try {
                Thread.sleep(FPS);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadRepaint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
