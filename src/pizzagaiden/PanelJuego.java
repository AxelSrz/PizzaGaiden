/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzagaiden;

import java.awt.Font;
import java.awt.Graphics;
import java.util.Timer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

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
    
    /**
     *
     * @param str
     * @param image
     * @param g
     */
    public void resizeLabelFont(String str, ImageIcon image, Graphics g) {
        Font labelFont = g.getFont();

        int stringWidth = g.getFontMetrics(labelFont).stringWidth(str);
        int componentWidth = image.getIconWidth();

        // Find out how much the font can grow in width.
        double widthRatio = (double) componentWidth / (double) stringWidth;

        int newFontSize = (int) (labelFont.getSize() * widthRatio);
        int componentHeight = image.getIconHeight();

        // Pick a new font size so it will not be larger than the height of label.
        int fontSizeToUse = Math.min(newFontSize, componentHeight);
        fontSizeToUse = fontSizeToUse <= 34 ? fontSizeToUse : 34;

        // Set the label's font size to the newly determined size.
        g.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse - 3));
    }
}
