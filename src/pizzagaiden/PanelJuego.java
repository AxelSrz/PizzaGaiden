/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzagaiden;

import java.util.Timer;
import javax.swing.JPanel;

/**
 *
 * @author Evan
 */
public class PanelJuego extends JPanel {
    private boolean bOver;
    protected Timer tTimer;
    public Juego juego;
    
    public PanelJuego() {
        tTimer = new Timer();
    }
    
    public boolean getActivo() {
        return bOver;
    }
    
    public void setJuego(Juego juego) {
        this.juego = juego;
    }
}
