package alexeljuego;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public abstract class Canvas extends JPanel implements KeyListener, MouseListener {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 65416L;

	// estados del teclado si estan activados o no
    private static boolean[] estadoTeclado = new boolean[525];
    
    // estados del raton activado o no activado
    private static boolean[] estadoRaton = new boolean[3];
        

    public Canvas()
    {
        // Double buffer para dibujar en la pantalla
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(Color.black);
        // true y desaparece tu raton
        if(true)
        {
        	BufferedImage blankCursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        	Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImg, new Point(0, 0), null);
        	this.setCursor(blankCursor);  
        }
        
        // keylistener para que reciva los eventos de teclado
        this.addKeyListener(this);
        //  keylistener para que reciva los eventos de raton
        this.addMouseListener(this);
    }
    
    
    // metodo sobreescrito en Framework.java
    public abstract void Draw(Graphics2D g2d);
    
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;        
        super.paintComponent(g2d);        
        Draw(g2d);
    }
       
    
    // Keyboard
    /**
     * devuelve el estado del  teclado 
     */
    public static boolean keyboardKeyState(int key)
    {
        return estadoTeclado[key];
    }
    
    //Escuchadores...
    @Override
    public void keyPressed(KeyEvent e) 
    {
        estadoTeclado[e.getKeyCode()] = true;
    }
    
    @Override
    public void keyReleased(KeyEvent e)
    {
        estadoTeclado[e.getKeyCode()] = false;
        keyReleasedFramework(e);
    }
    
    @Override
    public void keyTyped(KeyEvent e) { }
    
    public abstract void keyReleasedFramework(KeyEvent e);
    
    
    // Raton
   
    public static boolean mouseButtonState(int button)
    {
        return estadoRaton[button - 1];
    }
    
    // Sets mouse key status.
    private void mouseKeyStatus(MouseEvent e, boolean status)
    {
        if(e.getButton() == MouseEvent.BUTTON1)
            estadoRaton[0] = status;
        else if(e.getButton() == MouseEvent.BUTTON2)
            estadoRaton[1] = status;
        else if(e.getButton() == MouseEvent.BUTTON3)
            estadoRaton[2] = status;
    }
    
    // Methods of the mouse listener.
    @Override
    public void mousePressed(MouseEvent e)
    {
        mouseKeyStatus(e, true);
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
        mouseKeyStatus(e, false);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) { }
    
    @Override
    public void mouseEntered(MouseEvent e) { }
    
    @Override
    public void mouseExited(MouseEvent e) { }
    
}



