/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creation01;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;        
import java.awt.Color;
import java.awt.Shape;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Cursor;    
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.swing.Action;

public class Creation01 extends JFrame implements ActionListener{
    // Botones
    JMenu archivo, dibujar, ayuda;
    JMenuItem acerca, salir, nuevo, guardar, abrir, color;
    JRadioButtonMenuItem linea, rectangulo, elipse;
    JCheckBoxMenuItem relleno;
    JColorChooser colorChooser = new JColorChooser();
    ButtonGroup btn;
    MiPanel miPanel;
    public Creation01(){
        crearmenu();
        addListeners();
        miPanel = new MiPanel();
        this.add(miPanel);
        this.setSize(800, 600);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setTitle(" Creation");
    }    
    private void addListeners(){
        acerca.addActionListener(this);
        salir.addActionListener(this);
        nuevo.addActionListener(this);
        guardar.addActionListener(this);
        abrir.addActionListener(this);
        color.addActionListener(this);
        linea.addActionListener(this);
        
    }
    // MENU
    public void crearmenu(){
                JMenuBar menu = new JMenuBar();
                archivo = new JMenu (" Archivo");
                nuevo = new JMenuItem("Nuevo");
                abrir = new JMenuItem("Abrir");
                guardar = new JMenuItem("Guardar");
                salir = new JMenuItem("Salir");
                archivo.add(nuevo);
                archivo.add(abrir);
                archivo.add(guardar);
                archivo.add(salir); menu.add(archivo);
                
                dibujar = new JMenu(" Trazos"); btn = new ButtonGroup();
                linea = new JRadioButtonMenuItem(" Linea");
                rectangulo = new JRadioButtonMenuItem("Rectangulo");
                elipse = new JRadioButtonMenuItem("Elipse");
                btn.add(elipse);
                btn.add(rectangulo);
                btn.add(linea);
                btn.setSelected(linea.getModel(),true);
                relleno = new JCheckBoxMenuItem("Relleno");
                color = new JMenuItem("Color");
                dibujar.add(linea);
                dibujar.add(rectangulo);
                dibujar.add(elipse);
                dibujar.add(relleno);
                dibujar.add(color);
                menu.add(dibujar);
                
                ayuda = new JMenu("Mas");
                acerca = new JMenuItem(" By");
                ayuda.add(acerca);
                menu.add(ayuda);
                this.setJMenuBar(menu);
       
    }
    //Eventos de los botones
    public void actionPerformed (ActionEvent e) {
                if(e.getSource()== nuevo){
                    miPanel.resetAll();
                }
                if(e.getSource()== abrir){
                    miPanel.resetAll();
                }
                if(e.getSource()== guardar){
                    miPanel.resetAll();
                }
                if(e.getSource()== salir){
                    System.exit(0);
                }
                if(e.getSource()== linea){
                    miPanel.linea = true;
                    miPanel.rectangulo=false;
                }
                if(e.getSource()== rectangulo){
                    miPanel.linea = false;
                    miPanel.rectangulo=true;
                }
                if(e.getSource()== elipse){
                    miPanel.linea = false;
                    miPanel.rectangulo=false;
                }
                //Figuras rellenas
                if(e.getSource()== relleno){
                    if(miPanel.relleno){
                        miPanel.relleno = false;  
                    }else{
                            miPanel.relleno = true;
                    }        
                }
                if(e.getSource()== color){
                        Color color = JColorChooser.showDialog(this, "Elija un color", this.miPanel.getColorActual());
                    this.miPanel.setColorActual(color);
                if(e.getSource()== acerca){
                        JOptionPane.showMessageDialog(null, " Angie");
                }
                   
                }
    }
    
} 

// Clase para el panel
class MiPanel extends JPanel {
        Point p1;
        Point p2;
        Shape figura;
        Random R = new Random();
        
    public Color coloractual = Color.MAGENTA;
        BufferedImage myImage;
        Graphics2D g2D;
        boolean rectangulo = false;
        boolean linea = true;
        boolean relleno= false;
        
        public MiPanel(){
            OyenteDeRaton miOyente = new OyenteDeRaton();
            OyenteDeMovimiento miOyente2 = new OyenteDeMovimiento();
            addMouseListener(miOyente);
            addMouseMotionListener(miOyente2);
      
        }
        public Color getColorActual(){
            return coloractual;
        }
        public void setColorActual(Color color){
            coloractual = color;
        }
        public Graphics2D crearGraphics2D(){
            Graphics2D g2 = null;
            if(myImage == null || myImage.getWidth()!= getSize().width
                               || myImage.getHeight() != getSize().height){
                    myImage = (BufferedImage) createImage(getSize().width, getSize().height);
            }
            if(myImage != null){
                    g2 = myImage.createGraphics();
                    g2.setColor(coloractual);
                    g2.setBackground(getBackground());
            }
            g2.clearRect(0, 0, getSize().width,getSize().height);
            return g2;
        }
        public void paintComponent (Graphics g){
                super.paintComponent(g);
                if( myImage == null){
                    g2D = crearGraphics2D();
                }
                if(figura !=null){
                    if(relleno){
                            g2D.setColor(coloractual);
                            g2D.draw(figura);
                            g2D.draw(figura);
                    }else{
                            g2D.setColor(coloractual);
                            g2D.draw(figura);
                    }
                    if(myImage != null && isShowing()) {
                            g.drawImage(myImage, 0, 0, this);
                    }
                    figura=null;
                }
        }
        public void resetAll(){
            myImage = null;
            repaint();
        }
        // Creacion de Figuras
        public Shape crearFigura (Point p1, Point p2){
                double xInicio = Math.min(p1.getX(), p2.getX());
                double yInicio = Math.min(p1.getY(), p2.getY());
                double ancho = Math.abs( p2.getX()- p1.getX());
                double altura = Math.abs(p2.getY()-p1.getY());
                Shape nuevaFigura = new Rectangle2D.Double(xInicio, yInicio, ancho, altura);
                return nuevaFigura;   
        }
        public Shape crearLinea(Point p1, Point p2){
                Shape nuevaFigura = new Line2D.Double(p1.getX(),p1.getY(), p2.getX(),p2.getY());
                return nuevaFigura;
        }
        public Shape crearElipse(Point p1, Point p2){
                double xInicio = Math.min(p1.getX(), p2.getX());
                double yInicio = Math.min(p1.getY(), p2.getY());
                double ancho = Math.abs( p2.getX()- p1.getX());
                double altura = Math.abs(p2.getY()-p1.getY());
                Shape nuevaFigura = new Ellipse2D.Double(xInicio, yInicio, ancho, altura);
                return nuevaFigura;  
        }
        class OyenteDeRaton extends MouseAdapter{
            public void mousePressed(MouseEvent evento){
                MiPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                p1 = evento.getPoint();
            }
            public void mouseReleased (MouseEvent evento){
                    MiPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                    if(rectangulo){
                            p2 = evento.getPoint();
                            figura = crearFigura(p1, p2);
                            repaint();            
                    }else{
                            if(linea){
                                p2 = evento.getPoint();
                                figura = crearLinea(p1, p2);
                                repaint();
                            }else{
                                p2 = evento.getPoint();
                                figura = crearElipse(p1, p2);
                                repaint();
                            }
                repaint();
            }
        }
        }
        class OyenteDeMovimiento extends MouseMotionAdapter{
                public void mouseDragged(MouseEvent evento){
                    Graphics2D g2D;
                    if(rectangulo){
                        if(figura != null){
                                g2D = (Graphics2D)MiPanel.this.getGraphics();
                                g2D.setXORMode(MiPanel.this.getBackground());
                                g2D.setColor(coloractual);
                                g2D.draw(figura);
                        }
                        p2 = evento.getPoint();
                        figura = crearFigura(p1, p2);
                        g2D = (Graphics2D) MiPanel.this.getGraphics();
                        g2D.setXORMode( MiPanel.this.getBackground());
                        g2D.setColor(coloractual);
                        g2D.draw(figura);
                    }else if(linea){
                        if(figura != null){
                                g2D = (Graphics2D)MiPanel.this.getGraphics();
                                g2D.setXORMode(MiPanel.this.getBackground());
                                g2D.setColor(coloractual);
                                g2D.draw(figura);
                        }
                        p2 = evento.getPoint();
                        figura = crearLinea(p1, p2);
                        g2D = (Graphics2D)MiPanel.this.getGraphics();
                        g2D.setXORMode( MiPanel.this.getBackground());
                        g2D.setColor(coloractual);
                        g2D.draw(figura); 
                    } else {
                        if(figura != null){
                                g2D = (Graphics2D)MiPanel.this.getGraphics();
                                g2D.setXORMode(MiPanel.this.getBackground());
                                g2D.setColor(coloractual);
                                g2D.draw(figura);
                        }
                        p2 = evento.getPoint();
                        figura = crearElipse(p1, p2);
                        g2D = (Graphics2D)MiPanel.this.getGraphics();
                        g2D.setXORMode( MiPanel.this.getBackground());
                        g2D.setColor(coloractual);
                        g2D.draw(figura); 
                        
                }
        }
        
        
        }  

}
