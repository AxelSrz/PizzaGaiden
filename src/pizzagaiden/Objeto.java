package pizzagaiden;

/**
 * Clase Objeto
 *
 * @author Alejandro Villase�or
 * @version 1.00 2015/9/2
 */
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.JLabel;

public class Objeto {

    protected int posX;    //posicion en x.       
    protected int posY; //posicion en y.
    protected ImageIcon icono;    //icono.
    protected JLabel label;

    /**
     * Metodo constructor usado para crear el objeto
     *
     * @param posX es la <code>posicion en x</code> del objeto.
     * @param posY es la <code>posicion en y</code> del objeto.
     * @param image es la <code>imagen</code> del objeto.
     */
    public Objeto(int posX, int posY, Image image) {
        this.posX = posX;
        this.posY = posY;
        icono = new ImageIcon(image);
        label = new JLabel(icono);
    }

    public JLabel getLabel() {
        return label;
    }

    /**
     * Metodo modificador usado para cambiar la posicion en x del objeto
     *
     * @param posX es la <code>posicion en x</code> del objeto.
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Metodo de acceso que regresa la posicion en x del objeto
     *
     * @return posX es la <code>posicion en x</code> del objeto.
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Metodo modificador usado para cambiar la posicion en y del objeto
     *
     * @param posY es la <code>posicion en y</code> del objeto.
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Metodo de acceso que regresa la posicion en y del objeto
     *
     * @return posY es la <code>posicion en y</code> del objeto.
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Metodo modificador usado para cambiar el icono del objeto
     *
     * @param icono es el <code>icono</code> del objeto.
     */
    public void setImageIcon(ImageIcon icono) {
        this.icono = icono;
    }

    /**
     * Metodo de acceso que regresa el icono del objeto
     *
     * @return icono es el <code>icono</code> del objeto.
     */
    public ImageIcon getImageIcon() {
        return icono;
    }

    /**
     * Metodo de acceso que regresa el ancho del icono
     *
     * @return un objeto de la clase <code>ImageIcon</code> que es el ancho del
     * icono.
     */
    public int getAncho() {
        return icono.getIconWidth();
    }

    /**
     * Metodo de acceso que regresa el alto del icono
     *
     * @return un objeto de la clase <code>ImageIcon</code> que es el alto del
     * icono.
     */
    public int getAlto() {
        return icono.getIconHeight();
    }

    /**
     * Metodo de acceso que regresa la imagen del icono
     *
     * @return un objeto de la clase <code>Image</code> que es la imagen del
     * icono.
     */
    public Image getImagenI() {
        return icono.getImage();
    }

    /**
     * Metodo de acceso que regresa un nuevo rectangulo peque�o
     *
     * @return un objeto de la clase <code>Rectangle</code> que es el perimetro
     * del rectangulo
     */
    public Rectangle getPerimetro() {
        return new Rectangle(getPosX(), getPosY(), getAncho(), getAlto());
    }

    /**
     * Metodo de acceso que regresa un nuevo rectangulo peque�o
     *
     * @return un objeto de la clase <code>Rectangle</code> que es el perimetro
     * del rectangulo
     */
    public Rectangle getPerimetroAbajo() {
        return new Rectangle(getPosX(), getPosY() + 3 * getAlto() / 4, getAncho(), getAlto() / 4);
    }

    /**
     * Metodo de acceso que regresa un nuevo rectangulo peque�o
     *
     * @return un objeto de la clase <code>Rectangle</code> que es el perimetro
     * del rectangulo
     */
    public Rectangle getPerimetroArriba() {
        return new Rectangle(getPosX() + getAncho() / 3, getPosY() + getAlto() / 2, 2 * getAncho() / 3, getAlto() / 2);
    }

    /**
     * Checa si el objeto <code>Objeto</code> intersecta a otro
     * <code>Objeto</code>
     *
     * @return un valor boleano <code>true</code> si lo intersecta
     * <code>false</code> en caso contrario
     */
    public boolean intersecta(Objeto obj) {
        return getPerimetro().intersects(obj.getPerimetro());
    }

    /**
     * Checa si el objeto <code>Objeto</code> intersecta a otro
     * <code>Objeto</code> por el area definida
     *
     * @return un valor boleano <code>true</code> si lo intersecta
     * <code>false</code> en caso contrario
     */
    public boolean intersectaReal(Objeto obj) {
        return getPerimetroArriba().intersects(obj.getPerimetroAbajo());
    }

    public boolean contiene(int iX, int iY) {
        return getPerimetro().contains(iX, iY);
    }

    public void resizeLabelFont() {
        Font labelFont = label.getFont();
        String labelText = label.getText();

        int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
        int componentWidth = label.getWidth();

        // Find out how much the font can grow in width.
        double widthRatio = (double) componentWidth / (double) stringWidth;

        int newFontSize = (int) (labelFont.getSize() * widthRatio);
        int componentHeight = label.getHeight();

        // Pick a new font size so it will not be larger than the height of label.
        int fontSizeToUse = Math.min(newFontSize, componentHeight);

        // Set the label's font size to the newly determined size.
        label.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
    }
}
