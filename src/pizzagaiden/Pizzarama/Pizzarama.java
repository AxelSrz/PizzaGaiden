package pizzagaiden.Pizzarama;

/**
 * @(#)Tarea4.java
 *
 * Aplicacion JFrame de la Tarea 4
 *
 * @author Alejandro Villaseñor
 * @version 1.00 2015/9/9
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
import pizzagaiden.PanelJuego;
import pizzagaiden.Pregunta;

public class Pizzarama extends PanelJuego implements KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    // Se declaran las variables.
    private int cont = 0;
    private Image dbImage; // Imagen a proyectar 
    private Graphics dbg; // Objeto grafico
    private int iXClick;     //Posicion del mouse al dar click
    private int iYClick;
    private int iPoints;  //Puntos del jugador
    private int iVidas;
    private int iParesEncontrados;
    private Pregunta preArreglo[];
    private boolean bPreguntaSelec[];
    private boolean bCajasSelec[];
    private boolean respuestaEquivocada;
    private PMemorama memoCajas[];
    private int iCajaSelected;
    private boolean bPaused;
    private boolean bInitialize;

    /**
     * Metodo constructor usado para crear el objeto <code>Tarea4</code> En el
     * se llaman los metodos init y start
     */
    public Pizzarama() {
        //Constructor de JFrame
        super();
        //start();
    }

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {
        setSize(1000, 700);
        URL memoURL = this.getClass().getResource("PizzaMemorama_Color.png");
        iPoints = 0;
        iVidas = 2;
        iParesEncontrados = 0;

        memoCajas = new PMemorama[6];
        bCajasSelec = new boolean[6];
        bPreguntaSelec = new boolean[10];
        preArreglo = new Pregunta[10];

        for (int i = 0; i < 10; i++) {
            bPreguntaSelec[i] = false;
            char cPreg = (char) (i + 65), cResp = (char) (i + 97);
            String sPreg = "" + cPreg, sResp = "" + cResp;
            preArreglo[i] = new Pregunta(sPreg, sResp);
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

            memoCajas[iRand] = new PMemorama(true, iRandPregunta, x, y, Toolkit.getDefaultToolkit().getImage(memoURL));

            while (bCajasSelec[iRand]) {
                iRand = (int) (Math.random() * 6);
            }

            bCajasSelec[iRand] = true;
            x = 0;
            y = 0;

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

            memoCajas[iRand] = new PMemorama(false, iRandPregunta, x, y, Toolkit.getDefaultToolkit().getImage(memoURL));
        }

        //objOver= new Objeto(posX,posY,Toolkit.getDefaultToolkit().getImage(goURL));
        setBackground(Color.red);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        bOver = false;     //Inicia banderas en falso
        bPaused = false;
        respuestaEquivocada = false;
        bInitialize = true;

        iCajaSelected = -1;
        tTimer = new Timer();
        tTimer.scheduleAtFixedRate(new ScheduleTask(), 1000, 10);
    }

    /**
     * Metodo <I>start</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo para la animacion este metodo
     * es llamado despues del init o cuando el usuario visita otra pagina y
     * luego regresa a la pagina en donde esta este <code>Applet</code>
     *
     *
     * public void start () { // Declaras un hilo Thread th = new Thread (this);
     * // Empieza el hilo th.start (); }
     */
    class ScheduleTask extends TimerTask {

        /**
         * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
         * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
         * incrementa la posicion en x o y dependiendo de la direccion,
         * finalmente se repinta el <code>Applet</code> y luego manda a dormir
         * el hilo.
         *
         */
        public void run() {

            while (true) {
                actualiza();
                checaVidas();
                repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
                if (respuestaEquivocada) {
                    deselect();
                }

                /*cont+= 20;
                 if(cont>=1000&&iCajaSelected!=-1)
                 {
                 System.out.println("DESELECT");
                 deselectCajas();
                 cont= 0;
                 }*/
            }
        }
    }

    public void initTimer() {
        tTimer = new Timer();
        tTimer.scheduleAtFixedRate(new ScheduleTask(), 1000, 10);
    }

    public void stopGame() {
        tTimer.cancel();
        bOver = true;
    }

    public void continueGame() {
        juego.continueGame();
        tTimer = new Timer();
        tTimer.scheduleAtFixedRate(new ScheduleTask(), 1000, 10);
        bPaused = false;
    }

    public void pauseGame() {
        juego.pauseGame();
        tTimer.cancel();
        bPaused = true;
    }

    /**
     * Metodo usado para actualizar la posicion de los objetos.
     *
     */
    public void actualiza() {

    }

    public void deselect() {
        try {
            // El thread se duerme.
            Thread.sleep(1500);
            for (int i = 0; i < 6; i++) {
                if (memoCajas[i].isSelected()) {
                    memoCajas[i].select();
                }
            }
            respuestaEquivocada = false;
        } catch (InterruptedException ex) {
            System.out.println("Error en " + ex.toString());
        }
    }

    /**
     * Metodo usado para revisar cada caja por si es la seleccionada.
     *
     */
    public void checaCajas() {

        for (int i = 0; i < 6; i++) {
            if (i != iCajaSelected && !memoCajas[i].isLocked()) {
                boolean estaCaja = memoCajas[i].contiene(iXClick, iYClick);

                if (estaCaja && iCajaSelected == -1) // Si es la primera caja del par en ser seleccionada
                {
                    iCajaSelected = i;
                    memoCajas[i].select();
                } else if (estaCaja) // Si es la segunda caja en ser seleccionada
                {
                    memoCajas[i].select();
                    if (memoCajas[iCajaSelected].esCorrecto(memoCajas[i].getPosicion())) {
                        juego.setPunt(juego.getPunt() + I_BIEN);
                        memoCajas[iCajaSelected].lockAnswer();
                        memoCajas[i].lockAnswer();
                        iParesEncontrados++;
                    } else {
                        juego.setPunt(juego.getPunt() - I_MAL);
                        respuestaEquivocada = true;
                    }
                    iCajaSelected = -1;
                    cont = 0;
                }
            }
        }//Termina for

        if (iParesEncontrados == 3) {
            juego.cambiaJuego();
            tTimer.cancel();
        }
    }

    public void deselectCajas() {
        for (int i = 0; i < 6; i++) {
            if (memoCajas[i].isSelected() && !memoCajas[i].isLocked()) {
                memoCajas[i].select();
            }
        }
    }

    /**
     * Metodo <I>checaVidas</I>
     * En este metodo lo que hace es revisar si el objeto tiene 0 vidas para
     * acabar el juego
     */
    public void checaVidas() {
        if (iVidas == 0) {
            bOver = true;
        }
    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
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
        if (!bPaused) {
            //Guardo la posicion del mouse
            iXClick = mseEvent.getX();
            iYClick = mseEvent.getY();

            checaCajas();
        }
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
        g.setFont(new Font("Verdana", Font.BOLD, 30));
        if (bInitialize) {
            //Dibuja la imagen en la posicion actualizada
            for (int i = 0; i < 6; i++) {
                g.drawImage(memoCajas[i].getImagenI(), memoCajas[i].getPosX(), memoCajas[i].getPosY(), this);
                if (memoCajas[i].isSelected() || memoCajas[i].isLocked()) {
                    String sDisplay;

                    if (memoCajas[i].isPregunta()) {
                        sDisplay = preArreglo[memoCajas[i].getPosicion()].getPregunta();
                    } else {
                        sDisplay = preArreglo[memoCajas[i].getPosicion()].getRespuesta();
                    }

                    g.drawString(sDisplay, memoCajas[i].getPosX() + 120, memoCajas[i].getPosY() + 130);
                }
            }

            g.drawString("Score: " + iPoints, getWidth() - 100, 15);
        } else if (!bOver) {
            //Da un mensaje mientras se carga el dibujo 
            g.drawString("No se cargo la imagen..", 20, 20);
        }

        /*else{
         g.drawImage(objOver.getImagenI(), getWidth()/2-objOver.getAncho()/2, 
         getHeight()/2-objOver.getAlto()/2, this);
         }*/
    }
}
