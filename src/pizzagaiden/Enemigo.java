package pizzagaiden;

import java.awt.Image;

public class Enemigo extends Objeto
{
  protected int iPosicion;
  
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