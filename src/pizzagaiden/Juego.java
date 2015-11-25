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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.Timer;
import pizzagaiden.PizzaInvaders.PizzaInvaders;
import pizzagaiden.Pizzarama.Pizzarama;
//import javax.media.Player;

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
    private boolean bOver;
    private boolean bPaused;
    private URL urlClip;
    public int iCounter;
    private File file;
//    private Player acMusic;
    private ArrayList<ArrayList<Pregunta>> pregBase;
    
    /**
     * Creates new form Juego
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
        
        initComponents();
        iJuegoActual = -1;
        
        // Pasa el objeto a todas la clases
        initPanels();
        
        // Inicializa la música del juego
        urlClip = this.getClass().getResource("undertale.mp3");
//        acMusic = Manager.getClip();

        getContentPane().setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardPrincipal = (CardLayout) panelPrincipal.getLayout();
        cardMinis = (CardLayout) panelMinis.getLayout();  
    }
    
    // Metodos de clase
    
    public ArrayList<ArrayList<Pregunta>> getPregBase() {
        return pregBase;
    }

    public final void initPanels() {
        agregar1.setJuego(this);
        menu1.setJuego(this);
        configuracion1.setJuego(this);
        gameOver1.setJuego(this);
        editar1.setJuego(this);
    }
    
    public static void changeFont(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                changeFont(child, font);
            }
        }
    }
    
    public void cambiaPanelPrincipal(String strPanel) {
        cardPrincipal.show(panelPrincipal, strPanel);
    }
    
    public void stopJuego() {
        cardPrincipal.show(panelPrincipal, "gameOver");
    }
    
    public boolean getPaused(){
        return bPaused;
    }
    
    public void startJuego() {
        bOver = false;
        iPunt = 0;
        setPunt(iPunt);
        cardPrincipal.show(panelPrincipal, "panelJuego");
        iCounter = 60;
        ActionListener listener = new ActionListener() {
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
        cambiaJuego();
    }
    
    public void syncGame(PanelJuego juego) {
        juego.setJuego(this);
    }
          
    public void cambiaJuego() {
        iJuegoActual++;

        switch(iJuegoActual % 3) {
            case 0:
                pizzaQuiz1 = new pizzagaiden.PizzaQuizz.PizzaQuiz();
                panelMinis.add(pizzaQuiz1, "quiz");
                juegoActivo = pizzaQuiz1;
                syncGame(juegoActivo);
                pizzaQuiz1.init();
                cardMinis.show(panelMinis, "quiz");
                pizzaQuiz1.requestFocus();
                break;
            case 1:
                pizzarama1 = new Pizzarama();
                panelMinis.add(pizzarama1, "pizzarama");
                juegoActivo = pizzarama1;
                syncGame(juegoActivo);
                pizzarama1.init();
                cardMinis.show(panelMinis, "pizzarama");
                pizzarama1.requestFocus();
                break;
            case 2:
                pizzaInvaders1 = new PizzaInvaders();
                panelMinis.add(pizzaInvaders1, "invaders");
                juegoActivo = pizzaInvaders1;
                syncGame(juegoActivo);
                pizzaInvaders1.init();  
                cardMinis.show(panelMinis, "invaders");
                pizzaInvaders1.requestFocus();
                break;
        }
    }
    
    // Getters y setters
    
    public File getDatabaseFile() {
        return file;
    }

    public int getPunt() {
        return iPunt;
    }

    public void setPunt(int iPunt) {
        Juego.iPunt = iPunt;
        barraJuego1.setjLabel2Value(iPunt);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
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
        editar1 = new pizzagaiden.Editar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panelPrincipal.setMinimumSize(new java.awt.Dimension(1000, 700));
        panelPrincipal.setLayout(new java.awt.CardLayout());

        menu1.setBackground(new java.awt.Color(255, 51, 51));
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
        panelMinis.add(panelPausa1, "card8");

        panelJuego.add(panelMinis, java.awt.BorderLayout.PAGE_START);

        panelPrincipal.add(panelJuego, "panelJuego");
        panelJuego.add(barraJuego1, BorderLayout.NORTH);
        panelJuego.add(panelMinis, BorderLayout.SOUTH);
        panelPrincipal.add(gameOver1, "gameOver");
        panelPrincipal.add(configuracion1, "config");
        panelPrincipal.add(agregar1, "agregar");
        panelPrincipal.add(editar1, "editar");

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
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private pizzagaiden.Editar editar1;
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
    
    public void pauseGame(){
        timCountdown.stop();
        bPaused=true;
    }
    
    public void continueGame(){
        ActionListener listener = new ActionListener() {

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
        bPaused = false;
    }

    public Editar getEditar1() {
        return editar1;
    }
}
