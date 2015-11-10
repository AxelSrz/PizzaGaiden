package pizzagaiden.Pizzarama;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Rectangle;

public class PMemorama extends Enemigo
{
  public boolean bSelected;
  public boolean bPregunta;
  public boolean bAnswerLock;
  
  public PMemorama(boolean bpreg, int ires, int posX, int posY ,Image image)
  {
    super(ires, posX, posY, image);
    bSelected= false;
    bAnswerLock= false;
    bPregunta= bpreg;
  }
  
  public void select()
  {
    bSelected= !bSelected;
  }
  
  public boolean isSelected()
  {
    return bSelected;
  }
  
  public boolean isPregunta()
  {
    return bPregunta;
  }
  
  public boolean isLocked()
  {
    return bAnswerLock;
  }
  
  public void lockAnswer()
  {
    bAnswerLock= true;
  }
}