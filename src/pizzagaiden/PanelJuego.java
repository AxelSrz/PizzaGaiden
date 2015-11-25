/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzagaiden;

import java.util.Timer;
import javax.swing.JPanel;
import pizzagaiden.Pizzarama.Pizzarama;

/**
 *
 * @author Evan
 */
public class PanelJuego extends JPanel{
    protected boolean bOver;
    protected Timer tTimer;
    protected Juego juego;
    protected final int I_BIEN = 100;
    protected final int I_MAL = 50;
   
    public PanelJuego() {
    }
    
    public boolean getActivo() {
        return bOver;
    }
    
    public void setJuego(Juego juego) {
        this.juego = juego;
    }
    
    public void pause() {
        
    }
    
    public void unpause(PanelJuego pj) {
        pj.iniciaTimers();
    }
    
    // Overridable method
    public void iniciaTimers() {
    }
}
