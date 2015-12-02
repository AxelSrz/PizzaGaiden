/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzagaiden;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
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
    
    // Overridable method
    public void continueGame() {
    }
    
    public BufferedImage componentToImage(Component component) {
        BufferedImage img = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics g = img.getGraphics();
        g.setColor(component.getForeground());
        g.setFont(component.getFont());
        component.paintAll(g);
        Rectangle region = new Rectangle(0, 0, img.getWidth(), img.getHeight());
        return img.getSubimage(region.x, region.y, region.width, region.height);
    }
}
