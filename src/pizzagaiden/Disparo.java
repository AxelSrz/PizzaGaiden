/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzagaiden;


import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Rectangle;
import pizzagaiden.Objeto;

/**
 *
 * @author axelsuarez
 */
public class Disparo extends Objeto{
    int velocidad;

    public Disparo(int posX, int posY, Image image, int vel) {
        super(posX, posY, image);
        velocidad= vel;
    }
    
    public void move(){
        setPosY(getPosY()-velocidad);
    }
}
