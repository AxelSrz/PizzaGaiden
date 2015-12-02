/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzagaiden;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.Timer;
import pizzagaiden.PizzaInvaders.PizzaInvaders;
import pizzagaiden.Pizzarama.Pizzarama;
/**
 *
 * @author axelsuarez
 */
public class Juego extends javax.swing.JFrame {

    // Variables de clase
    private static int iJuegoActual;
    private static int iPunt;
    private CardLayout cardPrincipal;
    private CardLayout cardMinis;
    private PanelJuego juegoActivo;
    private Timer timCountdown;
    private boolean bPaused;
    private boolean bOver;
    public int iCounter;
    private File file;
    public SoundClip audTemaJuego;
    public SoundClip audTemaMenu;
//    private Player acMusic;
    private ArrayList<ArrayList<Pregunta>> pregBase;
    public Set<Integer> setPregEsp;
    public Set<Integer> setPregMat;
     /**
     * Metodo <I>Juego</I> constructor de la clase
     * que se encarga de leer el archivo de preguntas
     * y mandar a los respectivos metodos de inicialización
     * para poder correr el juego
     */
    public Juego() {
        // Inicializa dependencias
        file = new File("db.txt");
        // Checa el archivo si exista, si no lo carga
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        
        try {
            pregBase = Pregunta.separaTipos(Database.getContents(file));
        } catch (IOException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Manda llamar a los metodos de inicialización
        initComponents();
        setPregEsp = new HashSet<Integer>();
        setPregMat = new HashSet<Integer>();
        iJuegoActual = -1;
        
        // Pasa el objeto a todas la clases
        initPanels();
//        acMusic = Manager.getClip();

        //pone los valores por default
        getContentPane().setSize(1000, 700); //tamaño del frame
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // acabar la ejecución al cerrar
        cardPrincipal = (CardLayout) panelPrincipal.getLayout();
        cardMinis = (CardLayout) panelMinis.getLayout();
        audTemaMenu = new SoundClip("menuMusic.wav");
        audTemaMenu.setLooping(true);
        audTemaMenu.play();
    }
    
    // Metodos de clase
    
    public void agregaPreguntaUsada(int preg, int tipo){
        if(tipo==1)
            setPregEsp.add(preg);
        else if(tipo==2)
            setPregMat.add(preg);
    }
    
    public boolean esPreguntaUsada(int preg, int tipo){
        if(tipo==1)
            return setPregEsp.contains(preg);
        else
            return setPregMat.contains(preg);
    }
    
    public void limpiaSetPreguntas(int tipo){
        if(tipo==1)
            setPregEsp.clear();
        else
            setPregMat.clear();
    }
    
    public int numPreguntasUsadas(int tipo){
        if(tipo==1)
            return setPregEsp.size();
        else
            return setPregMat.size();
    }
    
    /**
     * Metodo <I>getPregBase</I> no recibe parametros y regresa un ArrayList con
     * un ArrayList anidado. Sirve para hacer get de la variable que contiene 
     * los datos de la base de preguntas.
     */
    public ArrayList<ArrayList<Pregunta>> getPregBase() {
        return pregBase;
    }

     /**
     * Metodo <I>initPanels</I> no recibe parametros y es void.
     * se encarga de que los paneles principales de la aplicación esten
     * ligados al JFrame general
     *
     */
    
    public final void initPanels() {
        agregar1.setJuego(this);
        menu1.setJuego(this);
        configuracion1.setJuego(this);
        gameOver1.setJuego(this);
        panelPausa1.setJuego(this);
    }
    
    /**
     * Metodo <I>changeFont</I> recibe un componente y una font, es void.
     * Este metodo permite cambiar la tipografía de un componente despué de su
     * inicialización.
     *
     */
    public static void changeFont(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                changeFont(child, font);
            }
        }
    }
    
    /**
     * Metodo <I>cambiaPanelPrincipal</I> recibe un string que representa
     * un panel dentro de la estructura del CardPane, es void.
     * Muestra en el CardPane el panel que se especifica en el string.
     *
     */
    public void cambiaPanelPrincipal(String strPanel) {
        audTemaMenu.play();
        cardPrincipal.show(panelPrincipal, strPanel);
    }
    
    /**
     * Metodo <I>stopJuego</I> no recive nada y es void.
     * Detiene la ejecucion del ciclo de minijuegos.
     *
     */
    public void stopJuego() {
        audTemaJuego.stop();
        cardPrincipal.show(panelPrincipal, "gameOver");
    }
    
    /**
     * Metodo <I>getPaused</I> no recive nada y regresa un boolean.
     * Regresa si el juego esta en modo de pausa
     *
     */
    public boolean getPaused(){
        return bPaused;
    }
    
    /**
     * Metodo <I>startJuego</I> no recibe nada, es void.
     * Empieza la ejecución del ciclo de juegos
     *
     */
    public void startJuego() {
        audTemaMenu.stop(); //Para la musica del menu
        audTemaJuego = new SoundClip("gameMusic.wav");
        audTemaJuego.setLooping(true);
        audTemaJuego.play(); //Empieza la música del juego
        bOver = false; //inicialización de variables de control
        iPunt = 0;
        setPunt(iPunt);
        cardPrincipal.show(panelPrincipal, "panelJuego"); //pone el template de panel de juego en el panel principal
        iCounter = 60; // empieza la cuenta regresiva en cierto numero de segundos
        ActionListener listener = new ActionListener() {
            @Override // Una accion que se repite segun el contador donde se instancie
            public void actionPerformed(ActionEvent e) {
                iCounter--; //Le decrementa en una unidad al contador del tiempo
                barraJuego1.getjProgressBar1().setValue(iCounter);  
                if (iCounter < 1) { //Si el tiempo se acaba destruye el timer y para el juego
                    bOver = true;
                    timCountdown.stop();
                    stopJuego();
                }
            }
        };
        timCountdown = new Timer(1000, listener); //Instancia el timer junto con la task
        timCountdown.start();
        cambiaJuego(); //empieza el primer cambio de juego
    }
    
    
    /**
     * Metodo <I>syncGame</I> recibe el PanelJuego a sincronizar y es void.
     * Se encarga de conectar la instancia del Frame con la de cada Panel de los
     * minijuegos.
     *
     */
    public void syncGame(PanelJuego juego) {
        juego.setJuego(this);
    }
    
    /**
     * Metodo <I>cambiaJuego</I> no recibe nada y es void.
     * Se encarga de inicializar y presentar el nuevo minijuego segun el orden 
     * predefinido y a partir del estado actual del juego.
     *
     */
    public void cambiaJuego() {
        iJuegoActual++; // Variable de control para saber el próximo juego a cargar
        setPregEsp.clear();
        setPregMat.clear();

        switch(iJuegoActual % 3) {
            case 0: //Cuando se carga PizzaQuiz
                pizzaQuiz1 = new pizzagaiden.PizzaQuizz.PizzaQuiz(); //Crea una nueva instancia de la clase
                panelMinis.add(pizzaQuiz1, "quiz"); // la agrega al CardPane
                juegoActivo = pizzaQuiz1; // cambia la variable de control de juego activo para indicarl el actual
                syncGame(juegoActivo); //Lo conecta con el frame principal
                pizzaQuiz1.init(); //inicializa los valores necesarios para la ejecucion del juego
                cardMinis.show(panelMinis, "quiz"); //Muestra el juego ya en el panel
                pizzaQuiz1.requestFocus(); //Se encarga de observar los listeners del juego
                break;
            case 1: //Cuando se carga Pizzarama
                pizzarama1 = new Pizzarama(); //misma descripcion que en el caso de pizza quiz
                panelMinis.add(pizzarama1, "pizzarama");
                juegoActivo = pizzarama1;
                syncGame(juegoActivo);
                pizzarama1.init();
                cardMinis.show(panelMinis, "pizzarama");
                pizzarama1.requestFocus();
                break;
            case 2: //Cuando se carga PizzaInvaders
                pizzaInvaders1 = new PizzaInvaders(); //misma descripcion que en el caso de pizza quiz
                panelMinis.add(pizzaInvaders1, "invaders");
                juegoActivo = pizzaInvaders1;
                syncGame(juegoActivo);
                pizzaInvaders1.init();  
                cardMinis.show(panelMinis, "invaders");
                pizzaInvaders1.requestFocus();
                break;
        }
    }
    
    /**
     * Metodo <I>getDatabaseFile</I> Handler para el archivo de preguntas
     *
     */
    public File getDatabaseFile() {
        return file;
    }
    
    /**
     * Metodo <I>getPunt</I> Handler get para el numero de puntos actuales
     *
     */
    public int getPunt() {
        return iPunt;
    }
    
    /**
     * Metodo <I>getPunt</I> Handler set para el numero de puntos actuales
     *
     */
    public void setPunt(int iPunt) {
        Juego.iPunt = iPunt;
        barraJuego1.setjLabel2Value(iPunt);
    }
    /**
     * Método con código autogenerado
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        menu1 = new pizzagaiden.Menu();
        panelJuego = new javax.swing.JPanel();
        barraJuego1 = new pizzagaiden.BarraJuego();
        panelMinis = new javax.swing.JPanel();
        pizzarama1 = new pizzagaiden.Pizzarama.Pizzarama();
        pizzaInvaders1 = new pizzagaiden.PizzaInvaders.PizzaInvaders();
        pizzaQuiz1 = new pizzagaiden.PizzaQuizz.PizzaQuiz();
        panelPausa1 = new pizzagaiden.panelPausa();
        gameOver1 = new pizzagaiden.GameOver();
        configuracion1 = new pizzagaiden.Configuracion();
        agregar1 = new pizzagaiden.Agregar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panelPrincipal.setMinimumSize(new java.awt.Dimension(1000, 700));
        panelPrincipal.setLayout(new java.awt.CardLayout());
        panelPrincipal.add(menu1, "menu");

        panelJuego.setLayout(new java.awt.BorderLayout());
        panelJuego.add(barraJuego1, java.awt.BorderLayout.CENTER);

        panelMinis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                panelMinisKeyReleased(evt);
            }
        });
        panelMinis.setLayout(new java.awt.CardLayout());

        pizzarama1.setPreferredSize(new java.awt.Dimension(1000, 700));

        javax.swing.GroupLayout pizzarama1Layout = new javax.swing.GroupLayout(pizzarama1);
        pizzarama1.setLayout(pizzarama1Layout);
        pizzarama1Layout.setHorizontalGroup(
            pizzarama1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        pizzarama1Layout.setVerticalGroup(
            pizzarama1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 770, Short.MAX_VALUE)
        );

        panelMinis.add(pizzarama1, "pizzarama");

        javax.swing.GroupLayout pizzaInvaders1Layout = new javax.swing.GroupLayout(pizzaInvaders1);
        pizzaInvaders1.setLayout(pizzaInvaders1Layout);
        pizzaInvaders1Layout.setHorizontalGroup(
            pizzaInvaders1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        pizzaInvaders1Layout.setVerticalGroup(
            pizzaInvaders1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 770, Short.MAX_VALUE)
        );

        panelMinis.add(pizzaInvaders1, "invaders");

        javax.swing.GroupLayout pizzaQuiz1Layout = new javax.swing.GroupLayout(pizzaQuiz1);
        pizzaQuiz1.setLayout(pizzaQuiz1Layout);
        pizzaQuiz1Layout.setHorizontalGroup(
            pizzaQuiz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        pizzaQuiz1Layout.setVerticalGroup(
            pizzaQuiz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 770, Short.MAX_VALUE)
        );

        panelMinis.add(pizzaQuiz1, "quiz");
        panelMinis.add(panelPausa1, "pause");

        panelJuego.add(panelMinis, java.awt.BorderLayout.PAGE_START);

        panelPrincipal.add(panelJuego, "panelJuego");
        panelJuego.add(barraJuego1, BorderLayout.NORTH);
        panelJuego.add(panelMinis, BorderLayout.SOUTH);
        panelPrincipal.add(gameOver1, "gameOver");
        panelPrincipal.add(configuracion1, "config");
        panelPrincipal.add(agregar1, "agregar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panelMinisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_panelMinisKeyReleased
        
    }//GEN-LAST:event_panelMinisKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Establece el look and feel Nimbus */
        // < DefaultState editor = " colapsado " desc = " look and feel establecimiento de códigos (opcional) ">
        /* Si Nimbus (introducido en Java SE 6 ) no está disponible, se queda con el aspecto por defecto y se siente.
         * Para más detalles ver http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Juego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private pizzagaiden.Agregar agregar1;
    private pizzagaiden.BarraJuego barraJuego1;
    private pizzagaiden.Configuracion configuracion1;
    private pizzagaiden.GameOver gameOver1;
    private pizzagaiden.Menu menu1;
    private javax.swing.JPanel panelJuego;
    private javax.swing.JPanel panelMinis;
    private pizzagaiden.panelPausa panelPausa1;
    private javax.swing.JPanel panelPrincipal;
    private pizzagaiden.PizzaInvaders.PizzaInvaders pizzaInvaders1;
    private pizzagaiden.PizzaQuizz.PizzaQuiz pizzaQuiz1;
    private pizzagaiden.Pizzarama.Pizzarama pizzarama1;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Metodo <I>pauseGame</I> no recibe nada y es void.
     * Se encarga de pausar el juego
     *
     */
    public void pauseGame(){
        timCountdown.stop();
        bPaused=true;
        cardMinis.show(panelMinis, "pause");
        panelPausa1.requestFocus();
    }
    
    /**
     * Metodo <I>continueGame</I> no recibe nada y es void.
     * Se encarga de reanudar el juego
     *
     */
    public void continueGame(){
        ActionListener listener = new ActionListener() {
            // Una accion que se repite segun el contador donde se instancie
            @Override
            public void actionPerformed(ActionEvent e) {
                iCounter--;
                barraJuego1.getjProgressBar1().setValue(iCounter);  
                if (iCounter < 1) {
                    bOver = true;
                    timCountdown.stop();
                    stopJuego();
                }
            }
        };
        timCountdown = new Timer(1000, listener);
        timCountdown.start();
        String str = null;
        if(juegoActivo.equals(pizzaQuiz1)) {
            str = "quiz";
        }
        else if(juegoActivo.equals(pizzaInvaders1)) {
            str = "invaders";
        }
        else if(juegoActivo.equals(pizzarama1)) {
            str = "pizzarama";
        }
        juegoActivo.continueGame();
        cardMinis.show(panelMinis, str);
        juegoActivo.requestFocus();
        bPaused = false;
    }
}
