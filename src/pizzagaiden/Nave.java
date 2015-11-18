package pizzagaiden;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import pizzagaiden.Objeto;

public class Nave extends Objeto {

    int iPregunta;
    int dx; // cambio en x

    public Nave(int ipreg, int posX, int posY, Image image) {
        super(posX, posY, image);
        iPregunta = ipreg;
    }

    public int getPregunta() {
        return iPregunta;
    }

    public void setPregunta(int ipreg) {
        iPregunta = ipreg;
    }

    public void move() {
        setPosX(getPosX() + dx);    //sumale a la posicion x el cambio en x
        if (getPosX() <= 2) //que no pase la pared de la izquierda
        {
            setPosX(2);
        }
        if (getPosX() >= 1000-getAncho()) //que no pase la pared de la derecha
        {
            setPosX(1000-getAncho());
        }
    }
    
    public void setDx(int iDx){
        dx= iDx;
    }
}
