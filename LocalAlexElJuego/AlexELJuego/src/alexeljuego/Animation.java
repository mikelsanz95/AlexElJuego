package alexeljuego;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Animation {
	  //Imgaen de la animacion.
    private BufferedImage animImage;

    // Ancho del frame.
    private int anchoFrame;

    // alto de la imagen.
    private int altoFrame;

    // numero de frames en la imagen.
    private int numeroDeFrames;

    // Atiempo entre frames en milisegundos
    private long tiempoPorFrame;

    // Tiempo en el que frame se empieza a visualizar
    private long comienzoTiempoPorFrame;

    // tiempo restante para que se musetre el proximo frame(milis). 
    private long tiempoParaProximoFrame;

    // numero del frame actual
    private int numeroActualFrame;

    // Se repite en el bucle?
    private boolean bucle;

    /** cordenadas x y y en pantalla */
    private int cordX;
    private int cordY;

    // x inicial del frame actual.
    private int xInicialDelFrame;

    // x final del frame actual
    private int xFinalDelFrame ;

    /** Mientras este activa una animacion no puede ser borrada. */
    public boolean active;
    
    // tiempo en el que se quiere empezar a visualizar al animacion
    private long desfase;
    
    // tiempo en el que la animacion fue creada
    private long tiempoDeCreacionAnimacion;


    /**
     * Creates animation. 
     */
    public Animation(BufferedImage animImage, int anchoFrame, int altoFrame, int numeroDeFrames, long tiempoPorFrame, boolean bucle, int x, int y, long desfase)
    {
        this.animImage = animImage;
        this.anchoFrame = anchoFrame;
        this.altoFrame = altoFrame;
        this.numeroDeFrames = numeroDeFrames;
        this.tiempoPorFrame = tiempoPorFrame;
        this.bucle = bucle;

        this.cordX = x;
        this.cordY = y;
        
        this.desfase = desfase;
        
        tiempoDeCreacionAnimacion = System.currentTimeMillis();

        xInicialDelFrame = 0;
        xFinalDelFrame  = anchoFrame;

        comienzoTiempoPorFrame = System.currentTimeMillis() + desfase;
        tiempoParaProximoFrame = comienzoTiempoPorFrame + this.tiempoPorFrame;
        numeroActualFrame = 0;
        active = true;
    }


    /**
     * Changes the coordinates of the animation.    
     */
    public void changeCoordinates(int x, int y)
    {
        this.cordX = x;
        this.cordY= y;
    }


    /**
     * Comprueba si tiene que saltar al porximo frame
     * comprueba si al animacion ha terminado.
     */
    private void Update()
    {
        if(tiempoParaProximoFrame <= System.currentTimeMillis())
        {
            // salta al proximo frame.
            numeroActualFrame++;

            // si el bucle no esta activo se quita el frame.
            if(numeroActualFrame >= numeroDeFrames)
            {
                numeroActualFrame = 0;

               
                if(!bucle)
                    active = false;
            }

            // Fraciona miento de la imagen
            xInicialDelFrame = numeroActualFrame * anchoFrame;
            xFinalDelFrame  = xInicialDelFrame + anchoFrame;

            // pone tiempo para el proximo frame
            comienzoTiempoPorFrame = System.currentTimeMillis();
            tiempoParaProximoFrame = comienzoTiempoPorFrame + tiempoPorFrame;
        }
    }

    /**
     * dibuja el frame.    
     */
    public void Draw(Graphics2D g2d)
    {
        this.Update();
        
        // Checks if show delay is over.
        if(this.tiempoDeCreacionAnimacion + this.desfase <= System.currentTimeMillis())
            g2d.drawImage(animImage, cordX,cordY, cordX + anchoFrame, cordY + altoFrame, xInicialDelFrame, 0, xFinalDelFrame , altoFrame, null);
    }
    
    public void resetAnim()
    {
    	numeroActualFrame=0;
    }

	public int getAnchoFrame() {
		return anchoFrame;
	}


	public void setAnchoFrame(int anchoFrame) {
		this.anchoFrame = anchoFrame;
	}


	public int getAltoFrame() {
		return altoFrame;
	}


	public void setAltoFrame(int altoFrame) {
		this.altoFrame = altoFrame;
	}

}
