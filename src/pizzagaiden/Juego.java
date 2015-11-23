/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzagaiden;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.Timer;
import pizzagaiden.PizzaInvaders.PizzaInvaders;
import pizzagaiden.PizzaQuizz.PizzaQuiz;
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
    private URL urlClip;
//    private Player acMusic;
    
    /**
     * Creates new form Juego
     */
    public Juego() {
        urlClip = this.getClass().getResource("undertale.mp3");
//        acMusic = Manager.getClip();
        initComponents();
        iJuegoActual = -1;
        getContentPane().setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cardPrincipal = (CardLayout) panelPrincipal.getLayout();
        cardMinis = (CardLayout) panelMinis.getLayout();
        menu1.addMouseListenerToLabels(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {
                System.out.println(me.getID() + " " + me.getComponent());
//                switch(me) {
//                    
//                }
                startJuego();
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
            }
        });  
        
        gameOver1.addMouseListenerToLabels(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {
                System.out.println(me.getID() + " " + me.getComponent());
                cardPrincipal.show(panelPrincipal, "menu");
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
            }
        }); 
    }
    
    public int getPunt() {
        return iPunt;
    }
    
    public void setPunt(int iPunt) {
        this.iPunt = iPunt;
        barraJuego1.setjLabel2Value(iPunt);
    }
    
    public void stopJuego() {
        cardPrincipal.show(panelPrincipal, "gameOver");
    }
    
    public void startJuego() {
        // Checamos cuál es el label que se clickea para poder definir qué
        // pantalla sigue
        bOver = false;
        iPunt = 0;
        setPunt(iPunt);
        cardPrincipal.show(panelPrincipal, "panelJuego");
        ActionListener listener = new ActionListener() {
            int counter = 60;

            @Override
            public void actionPerformed(ActionEvent e) {
                counter--;
                barraJuego1.getjProgressBar1().setValue(counter);  
                if (counter < 1) {
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
                break;
            case 1:
                pizzarama1 = new Pizzarama();
                panelMinis.add(pizzarama1, "pizzarama");
                juegoActivo = pizzarama1;
                syncGame(juegoActivo);
                pizzarama1.init();
                cardMinis.show(panelMinis, "pizzarama");
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
//            default:
//                cambiaJuego();
//                break;
        }
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
        gameOver1 = new pizzagaiden.GameOver();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panelPrincipal.setMinimumSize(new java.awt.Dimension(1000, 700));
        panelPrincipal.setLayout(new java.awt.CardLayout());
        panelPrincipal.add(menu1, "menu");

        panelJuego.setLayout(new java.awt.BorderLayout());
        panelJuego.add(barraJuego1, java.awt.BorderLayout.CENTER);

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

        panelJuego.add(panelMinis, java.awt.BorderLayout.PAGE_START);

        panelPrincipal.add(panelJuego, "panelJuego");
        panelJuego.add(barraJuego1, BorderLayout.NORTH);
        panelJuego.add(panelMinis, BorderLayout.SOUTH);
        panelPrincipal.add(gameOver1, "gameOver");

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
    private pizzagaiden.BarraJuego barraJuego1;
    private pizzagaiden.GameOver gameOver1;
    private pizzagaiden.Menu menu1;
    private javax.swing.JPanel panelJuego;
    private javax.swing.JPanel panelMinis;
    private javax.swing.JPanel panelPrincipal;
    private pizzagaiden.PizzaInvaders.PizzaInvaders pizzaInvaders1;
    private pizzagaiden.PizzaQuizz.PizzaQuiz pizzaQuiz1;
    private pizzagaiden.Pizzarama.Pizzarama pizzarama1;
    // End of variables declaration//GEN-END:variables

}
