package pizzagaiden.Pizzarama;

import pizzagaiden.Enemigo;
import java.awt.Image;
import javax.swing.ImageIcon;

public class PMemorama extends Enemigo
{
  protected boolean bSelected;
  protected boolean bPregunta;
  protected boolean bAnswerLock;
  
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
  
  public void setImage(Image image) {
      super.setImageIcon(new ImageIcon(image));
  }
}