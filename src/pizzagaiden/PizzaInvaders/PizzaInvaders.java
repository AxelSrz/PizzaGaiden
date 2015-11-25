/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzagaiden.PizzaInvaders;

/**
 *
 * @author axelsuarez
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.*;
import pizzagaiden.Disparo;
import pizzagaiden.Enemigo;
import pizzagaiden.Nave;
import pizzagaiden.PanelJuego;
import pizzagaiden.Pregunta;

public class PizzaInvaders extends PanelJuego implements KeyListener {

    private static final long serialVersionUID = 1L;
    // Se declaran las variables.
    private Image dbImage; // Imagen a proyectar 
    private Graphics dbg; // Objeto grafico
    private int iDestruidos; //Numero de enemigos destruidos hasta el momento
    private int iVelPreguntas; //la direccion y velocidad de las preguntas
    private Stack<Integer> iDisparosDestruir;
    private Stack<Integer> iPreguntasDestruir;
    private Pregunta preArreglo[];
    private Set<Integer> PreguntasSelec;
    private List<Integer> arrPregSelct;
    private Vector<Disparo> diDisparos;
    private Disparo disAux;
    private Vector<Enemigo> eneEnemigos;
    private Enemigo eneAux;
    private Nave navPizza;
    private boolean bOver;
    private boolean bPaused;
    private boolean bInitialize;

    /**
     * Metodo constructor usado para crear el objeto <code>Tarea4</code> En el
     * se llaman los metodos init y start
     */
    public PizzaInvaders() {
        super();
        setFocusable(true);
    }

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inicializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {
        setSize(1000, 700);
        URL eneURL = this.getClass().getResource("Pizzitas_PizzaInvader_Color.png");
        URL shiURL = this.getClass().getResource("PizzaShip_PizzaInvaders_Color.png");
        URL disURL = this.getClass().getResource("Fuego_PizzaInvaders_Color.png");

        eneEnemigos = new Vector();
        PreguntasSelec = new HashSet<>();
        preArreglo = new Pregunta[10];

        int iRndmType = (int) (Math.random() * 2);
        
        for (int i = 0; i < 10; i++) {
            int iRandPregunta = (int) (Math.random() * 10);

            while (PreguntasSelec.contains(iRandPregunta)) {
                iRandPregunta = (int) (Math.random() * 10);
            }

            PreguntasSelec.add(iRandPregunta);
            preArreglo[i] = juego.getPregBase().get(iRndmType).get(iRandPregunta);
        }
        
        PreguntasSelec.clear();

        for (int i = 0; i < 7; i++) {

            int iRandPregunta = (int) (Math.random() * 10);

            while (PreguntasSelec.contains(iRandPregunta)) {
                iRandPregunta = (int) (Math.random() * 10);
            }

            PreguntasSelec.add(iRandPregunta);
            int x = 0, y = 0;

            switch (i) {
                case 0:
                    x = 100;
                    y = 40;
                    break;
                case 1:
                    x = 300;
                    y = 40;
                    break;
                case 2:
                    x = 500;
                    y = 40;
                    break;
                case 3:
                    x = 700;
                    y = 40;
                    break;
                case 4:
                    x = 200;
                    y = 140;
                    
                    break;
                case 5:
                    x = 400;
                    y = 140;
                    break;
                case 6:
                    x = 600;
                    y = 140;
                    break;
                default:
                    break;
            }

            eneAux = new Enemigo(iRandPregunta, x, y, Toolkit.getDefaultToolkit().getImage(eneURL));
            eneEnemigos.add(eneAux);
        }

        //objOver= new Objeto(posX,posY,Toolkit.getDefaultToolkit().getImage(goURL));
        arrPregSelct = new ArrayList<>();
        arrPregSelct.addAll(PreguntasSelec);
        Collections.shuffle(arrPregSelct);
        iDestruidos = 0;
        navPizza = new Nave(arrPregSelct.get(iDestruidos), 435, 550, Toolkit.getDefaultToolkit().getImage(shiURL));
        diDisparos = new Vector();
        iDisparosDestruir = new Stack();
        iPreguntasDestruir = new Stack();
        iVelPreguntas = 1;

        setBackground(Color.red);
        addKeyListener(this);

        bOver = false;     //Inicia banderas en falso
        bPaused = false;
        bInitialize = true;
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
        @Override
        public void run() {
            actualiza();
            checaDisparos();
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
        navPizza.move();
        for (int i = 0; i < diDisparos.size(); i++) {
            disAux= diDisparos.elementAt(i);
            disAux.move();
            diDisparos.set(i, disAux);
        }
        for(int i=0; i<eneEnemigos.size();i++){
            if((eneEnemigos.elementAt(i).getPosX()+eneEnemigos.elementAt(i).getAncho()>=1000) && iVelPreguntas>0)
                iVelPreguntas= -1;
            else if(eneEnemigos.elementAt(i).getPosX()<=0 && iVelPreguntas < 0)
                iVelPreguntas= 1;
        }
        for(int i=0; i<eneEnemigos.size();i++){
            eneAux= eneEnemigos.elementAt(i);
            eneAux.setPosX(eneAux.getPosX()+iVelPreguntas);
            eneEnemigos.set(i, eneAux);
        }
    }

    /**
     * Metodo usado para revisar cada caja por si es la seleccionada.
     *
     */
    public void checaDisparos() {
        iDisparosDestruir.clear();
        iPreguntasDestruir.clear();
        for(int i=0; i< diDisparos.size(); i++) {
            if(diDisparos.elementAt(i).getPosY()<=0){
                iDisparosDestruir.push(i);
                juego.setPunt(juego.getPunt() - I_MAL);
            }
            else{
                for(int j=0; j< eneEnemigos.size();j++) {
                    if(diDisparos.elementAt(i).intersecta(eneEnemigos.elementAt(j))) {
                        if(eneEnemigos.elementAt(j).esCorrecto(navPizza.getPregunta())) { // Refactorizar nombre iPosicion
                            juego.setPunt(juego.getPunt() + I_BIEN);
                            iDisparosDestruir.push(i);
                            iPreguntasDestruir.push(j);
                            System.out.println(navPizza.getPregunta());
                            System.out.println(iPreguntasDestruir.size());
                        }
                        else {
                            juego.setPunt(juego.getPunt() - I_MAL);
                            iDisparosDestruir.push(i);
                        }
                    }
                }
            }
        }
        while(!iDisparosDestruir.empty()) {
            diDisparos.removeElementAt(iDisparosDestruir.pop());
        }
        while(!iPreguntasDestruir.empty()) {
            eneEnemigos.removeElementAt(iPreguntasDestruir.pop());
            iDestruidos++;
        }
        if(iDestruidos<arrPregSelct.size()) {
            navPizza.setPregunta(arrPregSelct.get(iDestruidos));
        }
        else {
            stopGame();
            juego.cambiaJuego();
        }
    }

    /**
     * Metodo usado para checar las colisiones de los objetos Cofre y Monedas
     * con las orillas del <code>Applet</code> y entre ellos.
     */
    public void checaColision() {

    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    @Override
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
            for (int i = 0; i < eneEnemigos.size(); i++) {
                g.drawImage(eneEnemigos.elementAt(i).getImagenI(), eneEnemigos.elementAt(i).getPosX(), eneEnemigos.elementAt(i).getPosY(), this);
                sDisplay = preArreglo[eneEnemigos.elementAt(i).getPosicion()].getRespuesta();
                g.drawString(sDisplay, eneEnemigos.elementAt(i).getPosX() + 40, eneEnemigos.elementAt(i).getPosY() + 50);
            }
            g.drawImage(navPizza.getImagenI(), navPizza.getPosX(), navPizza.getPosY(), this);
            sDisplay = preArreglo[navPizza.getPregunta()].getPregunta();
            g.drawString(sDisplay, navPizza.getPosX() + 50, navPizza.getPosY() + 100);
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
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {  //presiona flecha izq para moverlo a la izq
            navPizza.setDx(-5);
        }

        if (key == KeyEvent.VK_RIGHT) { //presiona flecha der para moverlo a la der
            navPizza.setDx(5 );
        }
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
    @Override
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
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_P) {
            if (!bPaused) {
                pauseGame();
            } else {
                continueGame();
            }
        }

        if (key == KeyEvent.VK_LEFT) {  //presiona flecha izq para moverlo a la izq
            navPizza.setDx(0);

        }

        if (key == KeyEvent.VK_RIGHT) { //presiona flecha der para moverlo a la der
            navPizza.setDx(0);
        }

        if (key == KeyEvent.VK_SPACE) { //presiona la space bar para disparar
            URL disURL = this.getClass().getResource("Fuego_PizzaInvaders_Color.png");
            disAux = new Disparo(navPizza.getPosX() + 52, navPizza.getPosY() - 54, Toolkit.getDefaultToolkit().getImage(disURL), 4);
            diDisparos.add(disAux);
            
        }
    }

    /**
     * Metodo <I>pintar</I>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    @Override
    public void paintComponent(Graphics g) {
        g.setFont(new Font("Verdana", Font.BOLD, 30));
        String sDisplay;
        if (bInitialize) {
            //Dibuja la imagen en la posicion actualizada
            for (int i = 0; i < eneEnemigos.size(); i++) {
                g.drawImage(eneEnemigos.elementAt(i).getImagenI(), eneEnemigos.elementAt(i).getPosX(), eneEnemigos.elementAt(i).getPosY(), this);
                sDisplay = preArreglo[eneEnemigos.elementAt(i).getPosicion()].getRespuesta();
                g.drawString(sDisplay, eneEnemigos.elementAt(i).getPosX() + 40, eneEnemigos.elementAt(i).getPosY() + 50);
            }
            g.drawImage(navPizza.getImagenI(), navPizza.getPosX(), navPizza.getPosY(), this);
            sDisplay = preArreglo[navPizza.getPregunta()].getPregunta();
            g.drawString(sDisplay, navPizza.getPosX() + 50, navPizza.getPosY() + 100);
            for (int i = 0; i < diDisparos.size(); i++) {
                g.drawImage(diDisparos.elementAt(i).getImagenI(), diDisparos.elementAt(i).getPosX(), diDisparos.elementAt(i).getPosY(), this);
            }
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
