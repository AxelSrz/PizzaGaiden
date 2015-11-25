/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzagaiden.PizzaQuizz;

/**
 *
 * @author axelsuarez
 */
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
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
import pizzagaiden.Enemigo;
import pizzagaiden.Objeto;
import pizzagaiden.PanelJuego;
import pizzagaiden.Pregunta;

public class PizzaQuiz extends PanelJuego implements KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    // Se declaran las variables.
    private Image dbImage; // Imagen a proyectar 
    private Graphics dbg; // Objeto grafico
    private int iXClick;     //Posicion del mouse al dar click
    private int iYClick;
    private int iPoints;  //Puntos del jugador
    private int iVidas;
    private int iRonda;
    private int iDestruidos; //Numero de enemigos destruidos hasta el momento
    private int iVelPreguntas; //la direccion y velocidad de las preguntas
    private Objeto objGranPizza;
    private Objeto objCajaPregunta;
    private Stack<Integer> iDisparosDestruir;
    private Stack<Integer> iPreguntasDestruir;
    private Pregunta preArreglo[];
    private Set<Integer> PreguntasSelec;
    private List<Enemigo> arrPreg;
    private Enemigo auxPregunta;
    private boolean bOver;
    private boolean bPaused;
    private boolean bInitialize;
    private boolean bCambiado;
    private ImageIcon icono;
    private URL pizURL1 = this.getClass().getResource("PizzaGhost_Color_NoPepperoni_1.png");
    private URL pizURL2 = this.getClass().getResource("PizzaGhost_Color_NoPepperoni_2.png");
    private URL pizURL3 = this.getClass().getResource("PizzaGhost_Color_NoPepperoni_3.png");
    private URL pizURL4 = this.getClass().getResource("PizzaGhost_Color_NoPepperoni_4.png");
    private URL pizURL5 = this.getClass().getResource("PizzaGhost_Color_NoPepperoni_5.png");
    private URL cajaURL = this.getClass().getResource("Caja_Color.png");
    private URL pregURL = this.getClass().getResource("peperoni.png");
    private int iRandPregunta;
    private int iPregunta;

    /**
     * Metodo constructor usado para crear el objeto <code>Tarea4</code> En el
     * se llaman los metodos init y start
     */
    public PizzaQuiz() {
        super();
        setFocusable(true);
    }

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {
        setSize(1000, 700);
        iPoints = 0;
        iVidas = 2;
        iRonda = 1;
        PreguntasSelec = new HashSet<Integer>();
        preArreglo = new Pregunta[10];
        arrPreg = new ArrayList<Enemigo>();
        
        int iRndmType = (int)(Math.random() * 2);
        int iArregloSize = juego.getPregBase().get(iRndmType).size();
        
        for(int i=0; i < 10; i++) {
            iRandPregunta = (int) (Math.random() * iArregloSize);
            
            while(PreguntasSelec.contains(iRandPregunta)) {
                iRandPregunta = (int) (Math.random() * iArregloSize);
            }
            
            PreguntasSelec.add(iRandPregunta);
            preArreglo[i] = juego.getPregBase().get(iRndmType).get(iRandPregunta);
        }
        
        iPregunta = iRandPregunta;
        auxPregunta = new Enemigo(iRandPregunta, 480, 40, Toolkit.getDefaultToolkit().getImage(pregURL));
        arrPreg.add(auxPregunta);

        setBackground(Color.red);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        objGranPizza = new Objeto(0, -700, Toolkit.getDefaultToolkit().getImage(pizURL1));
        objCajaPregunta = new Objeto(200, 500, Toolkit.getDefaultToolkit().getImage(cajaURL));

        bOver = false;     //Inicia banderas en falso
        bPaused = false;
        bInitialize = true;
        bCambiado = false;
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
            actualiza();
            repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
        }
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
        switch (iRonda) {
            case 1:
                icono = new ImageIcon(Toolkit.getDefaultToolkit().getImage(pizURL1));
                objGranPizza.setImageIcon(icono);
                objGranPizza.setPosX(0);
                objGranPizza.setPosY(-700);
                break;
            case 2:
                icono = new ImageIcon(Toolkit.getDefaultToolkit().getImage(pizURL2));
                objGranPizza.setImageIcon(icono);
                objGranPizza.setPosX(0);
                objGranPizza.setPosY(-500);
                break;
            case 3:
                icono = new ImageIcon(Toolkit.getDefaultToolkit().getImage(pizURL3));
                objGranPizza.setImageIcon(icono);
                objGranPizza.setPosX(0);
                objGranPizza.setPosY(-400);
                break;
            case 4:
                icono = new ImageIcon(Toolkit.getDefaultToolkit().getImage(pizURL4));
                objGranPizza.setImageIcon(icono);
                objGranPizza.setPosX(0);
                objGranPizza.setPosY(-280);
                break;
            case 5:
                icono = new ImageIcon(Toolkit.getDefaultToolkit().getImage(pizURL5));
                objGranPizza.setImageIcon(icono);
                objGranPizza.setPosX(-20);
                objGranPizza.setPosY(-100);
                break;
            default:
                break;
        }
    }

    /**
     * Metodo usado para checar las colisiones de los objetos Cofre y Monedas
     * con las orillas del <code>Applet</code> y entre ellos.
     */
    public void checaRespuesta() {
        boolean encontrado = false;
        for (int i = 0; i < arrPreg.size() && !encontrado; i++) {
            if (arrPreg.get(i).contiene(iXClick, iYClick)) {
                if (arrPreg.get(i).esCorrecto(iPregunta)) {
                    if (iRonda < 5) {
                        juego.setPunt(juego.getPunt() + I_BIEN);
                        iRonda++;
                        arrPreg.clear();
                        PreguntasSelec.clear();
                        for (int j = 0; j < iRonda; j++) {
                            int iRandPregunta = (int) (Math.random() * 10);

                            while (PreguntasSelec.contains(iRandPregunta)) {
                                iRandPregunta = (int) (Math.random() * 10);
                            }
                            auxPregunta = new Enemigo(iRandPregunta, 0, 0, Toolkit.getDefaultToolkit().getImage(pregURL));
                            arrPreg.add(auxPregunta);
                            PreguntasSelec.add(iRandPregunta);
                        }
                        iPregunta = arrPreg.get(0).getPosicion();
                        Collections.shuffle(arrPreg);
                        switch (iRonda) {
                            case 2:
                                auxPregunta = arrPreg.get(0);
                                auxPregunta.setPosX(400);
                                auxPregunta.setPosY(70);
                                arrPreg.set(0, auxPregunta);

                                auxPregunta = arrPreg.get(1);
                                auxPregunta.setPosX(600);
                                auxPregunta.setPosY(70);
                                arrPreg.set(1, auxPregunta);
                                break;
                            case 3:
                                auxPregunta = arrPreg.get(0);
                                auxPregunta.setPosX(300);
                                auxPregunta.setPosY(50);
                                arrPreg.set(0, auxPregunta);

                                auxPregunta = arrPreg.get(1);
                                auxPregunta.setPosX(500);
                                auxPregunta.setPosY(50);
                                arrPreg.set(1, auxPregunta);

                                auxPregunta = arrPreg.get(2);
                                auxPregunta.setPosX(700);
                                auxPregunta.setPosY(50);
                                arrPreg.set(2, auxPregunta);
                                break;
                            case 4:
                                auxPregunta = arrPreg.get(0);
                                auxPregunta.setPosX(200);
                                auxPregunta.setPosY(60);
                                arrPreg.set(0, auxPregunta);

                                auxPregunta = arrPreg.get(1);
                                auxPregunta.setPosX(400);
                                auxPregunta.setPosY(30);
                                arrPreg.set(1, auxPregunta);

                                auxPregunta = arrPreg.get(2);
                                auxPregunta.setPosX(600);
                                auxPregunta.setPosY(30);
                                arrPreg.set(2, auxPregunta);

                                auxPregunta = arrPreg.get(3);
                                auxPregunta.setPosX(770);
                                auxPregunta.setPosY(60);
                                arrPreg.set(3, auxPregunta);
                                break;
                            case 5:
                                auxPregunta = arrPreg.get(0);
                                auxPregunta.setPosX(100);
                                auxPregunta.setPosY(100);
                                arrPreg.set(0, auxPregunta);

                                auxPregunta = arrPreg.get(1);
                                auxPregunta.setPosX(300);
                                auxPregunta.setPosY(100);
                                arrPreg.set(1, auxPregunta);

                                auxPregunta = arrPreg.get(2);
                                auxPregunta.setPosX(500);
                                auxPregunta.setPosY(100);
                                arrPreg.set(2, auxPregunta);

                                auxPregunta = arrPreg.get(3);
                                auxPregunta.setPosX(650);
                                auxPregunta.setPosY(100);
                                arrPreg.set(3, auxPregunta);

                                auxPregunta = arrPreg.get(4);
                                auxPregunta.setPosX(800);
                                auxPregunta.setPosY(100);
                                arrPreg.set(4, auxPregunta);
                                break;
                            default:
                                break;
                        }
                    } else {
                        if (!bCambiado) {
                            stopGame();
                            bCambiado = true;
                            juego.cambiaJuego();
                        }
                    }
                    encontrado = true;
                } else {
                    juego.setPunt(juego.getPunt() - I_MAL);
                }
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

        g.setFont(new Font("Verdana", Font.BOLD, 30));
        String sDisplay;
        if (bInitialize) {
            //Dibuja la imagen en la posicion actualizada
            g.drawImage(objGranPizza.getImagenI(), objGranPizza.getPosX(), objGranPizza.getPosY(), this);
            g.drawImage(objCajaPregunta.getImagenI(), objCajaPregunta.getPosX(), objCajaPregunta.getPosY(), this);
            sDisplay = preArreglo[iPregunta].getPregunta();
            g.drawString(sDisplay, objCajaPregunta.getPosX() + 100, objCajaPregunta.getPosY() + 60);
            for (int i = 0; i < arrPreg.size(); i++) {
                g.drawImage(arrPreg.get(i).getImagenI(), arrPreg.get(i).getPosX(), arrPreg.get(i).getPosY(), this);
                sDisplay = preArreglo[arrPreg.get(i).getPosicion()].getRespuesta();
                g.drawString(sDisplay, arrPreg.get(i).getPosX() + 40, arrPreg.get(i).getPosY() + 50);
            }
        } else if (!bOver) {
            //Da un mensaje mientras se carga el dibujo 
            g.drawString("No se cargo la imagen..", 20, 20);
        }

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
        int key = e.getKeyCode();
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
                System.out.println("oye ci");
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
        if (!bPaused) {
            //Guardo la posicion del mouse
            iXClick = mseEvent.getX();
            iYClick = mseEvent.getY();

            checaRespuesta();
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
        g.setFont(new Font("Verdana", Font.BOLD, 30));
        String sDisplay;
        if (bInitialize) {
            //Dibuja la imagen en la posicion actualizada
            g.drawImage(objGranPizza.getImagenI(), objGranPizza.getPosX(), objGranPizza.getPosY(), this);
            g.drawImage(objCajaPregunta.getImagenI(), objCajaPregunta.getPosX(), objCajaPregunta.getPosY(), this);
            sDisplay = preArreglo[iPregunta].getPregunta();
            g.drawString(sDisplay, objCajaPregunta.getPosX() + 100, objCajaPregunta.getPosY() + 60);
            for (int i = 0; i < arrPreg.size(); i++) {
                g.drawImage(arrPreg.get(i).getImagenI(), arrPreg.get(i).getPosX(), arrPreg.get(i).getPosY(), this);
                sDisplay = preArreglo[arrPreg.get(i).getPosicion()].getRespuesta();
                g.drawString(sDisplay, arrPreg.get(i).getPosX() - 50, arrPreg.get(i).getPosY() + 50);
            }
        } else if (!bOver) {
            //Da un mensaje mientras se carga el dibujo 
            g.drawString("No se cargo la imagen..", 20, 20);
        }
    }
}
