package pizzagaiden.Pizzarama;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Rectangle;

public class Nave extends Objeto
{
  int iPregunta;
  
  public Nave(int ipreg, int posX, int posY ,Image image)
  {
    super(posX, posY, image);
    iPregunta= ipreg;
  }
  
  public int getPregunta()
  {
    return iPregunta;
  }
  
  public void setPregunta(int ipreg)
  {
    iPregunta= ipreg;
  }
  
}