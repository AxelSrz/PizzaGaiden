package pizzagaiden.Pizzarama;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Rectangle;

public class Enemigo extends Objeto
{
  int iPosicion;
  
  public Enemigo(int ires, int posX, int posY ,Image image)
  {
    super(posX, posY, image);
    iPosicion= ires;
  }
  
  public boolean esCorrecto(int iPreg)
  {
    return iPosicion==iPreg;
  }
  
  public int getPosicion()
  {
    return iPosicion;
  }  
}