package pizzagaiden.Pizzarama;

/**
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.*;
import javax.swing.JLabel;
import pizzagaiden.PanelJuego;
import pizzagaiden.Pregunta;
import pizzagaiden.SoundClip;

public class Pizzarama extends PanelJuego implements KeyListener, MouseListener, MouseMotionListener {

    // Se declaran las variables.
    private static final long serialVersionUID = 1L;
    private Image dbImage; // Imagen a proyectar 
    private Graphics dbg; // Objeto grafico
    private int iXClick;     //Posicion del mouse al dar click
    private int iYClick;
    private int iParesEncontrados;
    private Pregunta preArreglo[];
    private boolean bPreguntaSelec[];
    private boolean bCajasSelec[];
    private boolean respuestaEquivocada;
    private PMemorama memoCajas[];
    private int iCajaSelected;
    private boolean bPaused;
    private boolean bInitialize;
    private SoundClip audClick;
    public SoundClip audClickCorrecto;
    public SoundClip audClickError;
    private int iRndmType;
    private int iArregloSize;
    private final URL memoURL = this.getClass().getResource("PizzaMemorama_Color.png");
    private final URL cajaCerradaURL = this.getClass().getResource("PizzaMemorama_CajaCerrada.png");


    /**
     * Metodo constructor usado para crear el objeto <code>Pizzarama</code>
     */
    public Pizzarama() {
        super();
    }

    /**
     * Metodo <I>init</I>.
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el programa y se definen funcionalidades.
     */
    public void init() {
        juego.getBarraJuego1().getInstruccionLabel().setText("Encuentra los pares");
        setSize(1000, 700);
        setBackground(new java.awt.Color(255, 51, 51));
        iParesEncontrados = 0;

        memoCajas = new PMemorama[6];
        bCajasSelec = new boolean[6];
        preArreglo = new Pregunta[10];
        bPreguntaSelec = new boolean[10];
        iArregloSize = juego.getPregBase().get(iRndmType).size();
        

        iRndmType = (int) (Math.random()*2);

        for (int i = 0; i < 10; i++) {
            int iRandPregunta = (int) (Math.random() * iArregloSize);

            while (juego.esPreguntaUsada(iRandPregunta, iRndmType)) {
                iRandPregunta = (int) (Math.random() * iArregloSize);
            }

            juego.agregaPreguntaUsada(iRandPregunta, iRndmType);
            preArreglo[i] = juego.getPregBase().get(iRndmType).get(iRandPregunta);
            bPreguntaSelec[i]=false;
        }

        for (int i = 0; i < 6; i++) {
            bCajasSelec[i] = false;
        }

        for (int i = 0; i < 3; i++) {
            int iRand = (int) (Math.random() * 6);
            int iRandPregunta = (int) (Math.random() * 10);

            while (bPreguntaSelec[iRandPregunta]) {
                iRandPregunta = (int) (Math.random() * 10);
            }

            while (bCajasSelec[iRand]) {
                iRand = (int) (Math.random() * 6);
            }

            bPreguntaSelec[iRandPregunta] = true;
            bCajasSelec[iRand] = true;
            int x = 0, y = 0;

            switch (iRand) {
                case 0:
                    x = 40;
                    y = 100;
                    break;
                case 1:
                    x = 383;
                    y = 100;
                    break;
                case 2:
                    x = 706;
                    y = 100;
                    break;
                case 3:
                    x = 40;
                    y = 400;
                    break;
                case 4:
                    x = 383;
                    y = 400;
                    break;
                case 5:
                    x = 706;
                    y = 400;
                    break;
                default:
                    break;
            }

            memoCajas[iRand] = new PMemorama(true, iRandPregunta, x, y, Toolkit.getDefaultToolkit().getImage(cajaCerradaURL));

            while (bCajasSelec[iRand]) {
                iRand = (int) (Math.random() * 6);
            }

            bCajasSelec[iRand] = true;
            x = 0;
            y = 0;

            switch (iRand) { //Pocisiones de las cajas de preguntas
                case 0:
                    x = 40;
                    y = 100;
                    break;
                case 1:
                    x = 383;
                    y = 100;
                    break;
                case 2:
                    x = 706;
                    y = 100;
                    break;
                case 3:
                    x = 40;
                    y = 400;
                    break;
                case 4:
                    x = 383;
                    y = 400;
                    break;
                case 5:
                    x = 706;
                    y = 400;
                    break;
                default:
                    break;
            }

            memoCajas[iRand] = new PMemorama(false, iRandPregunta, x, y, Toolkit.getDefaultToolkit().getImage(cajaCerradaURL));
        }

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        bOver = false;     //Inicia banderas en falso
        bPaused = false;
        respuestaEquivocada = false;
        bInitialize = true;

        iCajaSelected = -1;
        audClick= new SoundClip("click.wav"); //Carga los sonidos
        audClickCorrecto = new SoundClip("correct.wav");
        audClickError = new SoundClip("error.wav");
        tTimer = new Timer(); //Inicializa el timer
        tTimer.scheduleAtFixedRate(new ScheduleTask(), 1000, 10);
    }

    /**
     * Metodo <I>ScheduleTask</I>.<P>
     * En este metodo se crea e inicializa el hilo para el proceso que se va 
     * repetir segun el ritmo del timer
     */
    class ScheduleTask extends TimerTask {

        /**
         * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
         * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
         * incrementa la posicion en x o y dependiendo de la direccion,
         * finalmente.
         *
         */
        public void run() {
                repaint();    // Se actualiza el Panel repintando el contenido.
                if (respuestaEquivocada) {
                    audClick.play();
                    deselect();
                }
        }
    }

    /**
     * Metodo <I>stopGame</I> no recive nada y es void.
     * Detiene la ejecucion del minijuego.
     */
    public void stopGame() {
        tTimer.cancel();
        bOver = true;
    }

    /**
     * Metodo <I>continueGame</I> no recibe nada y es void.
     * Se encarga de reanudar el juego
     *
     */
    public void continueGame() {
        tTimer = new Timer();
        tTimer.scheduleAtFixedRate(new ScheduleTask(), 1000, 10);
        bPaused = false;
    }

    /**
     * Metodo <I>pauseGame</I> no recive nada y es void.
     * Pausa la ejecucion del minijuego.
     */
    public void pauseGame() {
        juego.pauseGame();
        tTimer.cancel();
        bPaused = true;
    }

    public void deselect() {
        try {
            // El thread se duerme.
            Thread.sleep(1500);
            for (int i = 0; i < 6; i++) {
                if (memoCajas[i].isSelected() && !memoCajas[i].isLocked()) {
                    memoCajas[i].select();
                    memoCajas[i].setImage(Toolkit.getDefaultToolkit().getImage(cajaCerradaURL));
                }
            }
            respuestaEquivocada = false;
        } catch (InterruptedException ex) {
        }
    }

    /**
     * Metodo usado para revisar cada caja por si es la seleccionada.
     *
     */
    public void checaCajas() {
        boolean todasCajasAbiertas= true;
        for (int i = 0; i < 6; i++) {
            if (i != iCajaSelected && !memoCajas[i].isLocked()) {
                boolean estaCaja = memoCajas[i].contiene(iXClick, iYClick);

                if (estaCaja && iCajaSelected == -1) // Si es la primera caja del par en ser seleccionada
                {
                    audClick.play();
                    iCajaSelected = i;
                    memoCajas[i].select();
                    memoCajas[i].setImage(Toolkit.getDefaultToolkit().getImage(memoURL));
                } else if (estaCaja) // Si es la segunda caja en ser seleccionada
                {
                    audClick.play();
                    memoCajas[i].select();
                    memoCajas[i].setImage(Toolkit.getDefaultToolkit().getImage(memoURL));
                    if (memoCajas[iCajaSelected].esCorrecto(memoCajas[i].getPosicion())) {
                        juego.setPunt(juego.getPunt() + I_BIEN);
                        memoCajas[iCajaSelected].lockAnswer();
                        memoCajas[i].lockAnswer();
                        iParesEncontrados++;
                        audClickCorrecto.play();
                    } else {
                        juego.setPunt(juego.getPunt() - I_MAL);
                        respuestaEquivocada = true;
                    }
                    iCajaSelected = -1;
                }
                else
                    todasCajasAbiertas= false;
            }
        }//Termina for

        if (iParesEncontrados == 3 || todasCajasAbiertas) {
            juego.cambiaJuego();
        }
    }

    /**
     * Metodo <I>paint</I> .<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paintComponent(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Metodo <I>keyPressed</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la
     * tecla.
     *
     * @param e es el <code>evento</code> generado al presionar las teclas.
     */
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que
     * no es de accion.
     *
     * @param e es el <code>evento</code> que se genera en al presionar las
     * teclas.
     */
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_P) {
            if (!bPaused) {
                pauseGame();
            } else {
                continueGame();
            }
        }
    }

    /**
     * Metodo <I>mouseClicked</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo se maneja el evento que se genera al dar un click en el
     * mouse.
     *
     * @param me es el <code>evento</code> que se genera en al soltar las
     * teclas.
     */
    public void mouseClicked(MouseEvent mseEvent) {
        if (!bPaused && !respuestaEquivocada) { //si el juego no esta pausa
            //Guardo la posicion del mouse
            iXClick = mseEvent.getX();
            iYClick = mseEvent.getY();

            checaCajas();
        }
    }

    /**
     * Metodo <I>mouseEntered</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo se maneja el evento que se genera al entrar el mouse al
     * applet.
     *
     * @param me es el <code>evento</code> que se genera en al soltar las
     * teclas.
     */
    public void mouseEntered(MouseEvent mseEvent) {
    }

    /**
     * Metodo <I>mouseExited</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo se maneja el evento que se genera al salir el mouse del
     * applet.
     *
     * @param me es el <code>evento</code> que se genera en al soltar las
     * teclas.
     */
    public void mouseExited(MouseEvent mseEvent) {
    }

    /**
     * Metodo <I>mousePressed</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo se maneja el evento que se genera al presionar el mouse.
     *
     * @param me es el <code>evento</code> que se genera en al soltar las
     * teclas.
     */
    public void mousePressed(MouseEvent mseEvent) {
    }

    /**
     * Metodo <I>mouseReleased</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo se maneja el evento que se genera al soltar el boton del
     * mouse.
     *
     * @param me es el <code>evento</code> que se genera en al soltar el boton.
     */
    public void mouseReleased(MouseEvent mseEvent) {
    }

    /**
     * Metodo <I>mouseDragged</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo se maneja el evento que se genera al arrastrar el mouse.
     *
     * @param me es el <code>evento</code> que se genera al arrastrar.
     */
    public void mouseDragged(MouseEvent mseEvent) {
    }

    /**
     * Metodo <I>mouseMoved</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo se maneja el evento que se genera al mover el mouse.
     *
     * @param me es el <code>evento</code> que se genera en al mover el mouse.
     */
    public void mouseMoved(MouseEvent mseEvent) {
    }

    /**
     * Metodo <I>pintar</I>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paintComponent(Graphics g) {
        int iDiffH = 0;
        int iDiffW = 0;
        double iRatio = 0;
        g.setFont(new Font("Verdana", Font.BOLD, 20));
        int iOffsetX = (iRndmType == 2 ? 120 : 55);
        if (bInitialize) {
            //Dibuja la imagen en la posicion actualizada
            for (int i = 0; i < 6; i++) {
                g.drawImage(memoCajas[i].getImagenI(), memoCajas[i].getPosX(), memoCajas[i].getPosY(), this);
                if (memoCajas[i].isSelected() || memoCajas[i].isLocked()) { //Si la pregunta está seleccionada o ya se respondio correctamente
                    String sDisplay;
                    if (memoCajas[i].isPregunta()) {
                        sDisplay = preArreglo[memoCajas[i].getPosicion()].getPregunta();
                    } else {
                        sDisplay = preArreglo[memoCajas[i].getPosicion()].getRespuesta();
                    }
                    resizeLabelFont(sDisplay, memoCajas[i].getImageIcon(), g);
                    iRatio = memoCajas[i].getImageIcon().getIconWidth() / g.getFontMetrics().stringWidth(sDisplay);
                    iDiffW = (memoCajas[i].getAncho() - g.getFontMetrics().stringWidth(sDisplay)) / 2;
                    iDiffH = (memoCajas[i].getAlto() - g.getFontMetrics(g.getFont()).getHeight()) / 2;
                    g.drawImage(memoCajas[i].getImagenI(), memoCajas[i].getPosX(), memoCajas[i].getPosY(), this);
                    g.drawString(sDisplay, memoCajas[i].getPosX() + iDiffW, memoCajas[i].getPosY() + memoCajas[i].getImageIcon().getIconHeight() / 2);
                }
            }
        } else if (!bOver) {
            //Da un mensaje mientras se carga el dibujo 
            g.drawString("No se cargo la imagen..", 20, 20);
        }
    }
}
