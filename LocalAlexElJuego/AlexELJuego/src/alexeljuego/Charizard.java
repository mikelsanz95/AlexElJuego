package alexeljuego;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

public class Charizard {
	
	private int cordX;
	private int cordY;
	
	private int velMovX;
	private int velMovY;
	
	private long tiempoMinCambio = Framework.secEnNanosec*5;
	private long tiempoUltimoCambio;
	
	private boolean cambio;
	
	private int vida;
	
	private int daño;
	private int dañoLlama;
	private int dañoZarpa;
	private Random random;
	
	private boolean mirandoFrente;
	               
	public static BufferedImage charizardFlyFrontImg;
	public static BufferedImage charizardAttFrontImg;
	public static BufferedImage charizardFlameSpitFrontImg;
	public static BufferedImage charizardFlyBackImg;
	public static BufferedImage charizardAttBackImg;
	public static BufferedImage charizardFlameSpitBackImg;
	               
	private Animation charizardFlyFrontAnim;
	private Animation charizardAttFrontAnim;
	private Animation charizardFlameSpitFrontAnim;
	private Animation charizardFlyBackAnim;
	private Animation charizardAttBackAnim;
	private Animation charizardFlameSpitBackAnim;
	public enum IA{ ATACANDO, MOVIENDOSEALEATORIO, ACERCANDOSE}
	public IA Ia;
	
	@SuppressWarnings("static-access")
	public void Inicializar(int cordX , int cordY)
	{
		
		this.cordX = cordX;
		this.cordY = cordY;
		velMovX = 0;
		velMovY = 0;
		this.daño=0;
		this.dañoLlama = 20;
		this.dañoZarpa = 30;
		random= new Random();
		Ia = Ia.MOVIENDOSEALEATORIO;
		this.charizardAttBackAnim = new Animation(charizardAttBackImg, 1445/5 , 207, 5, 100, false, cordX,cordY, 0);
		this.charizardAttFrontAnim= new Animation(charizardAttFrontImg,1445/5 , 207, 5, 100, false, cordX, cordY, 0);
		this.charizardFlameSpitBackAnim= new Animation(charizardFlameSpitBackImg, 1268/4, 209, 4, 100, false, cordX,cordY, 0);
		this.charizardFlameSpitFrontAnim= new Animation(charizardFlameSpitFrontImg,1268/4 , 209, 4, 100, false, cordX, cordY, 0);
		this.charizardFlyFrontAnim = new Animation(charizardFlyFrontImg, 1248/4, 193, 4, 100, false, cordX, cordY, 0);
		this.charizardFlyBackAnim= new Animation(charizardFlyBackImg, 1248/4 , 193, 4, 100, false, cordX, cordY, 0);
		cambio=true;
		double rng = random.nextDouble()*random.nextInt(2)+0.5;
		tiempoMinCambio= (long) (Framework.secEnNanosec*rng);
		tiempoUltimoCambio=0;
	}
	
	
	public void Update(long gameTime)
	{
		
		switch (Ia) 
		{
		case ACERCANDOSE:
			
			
			
			break;

		case ATACANDO :
			
			
			break;
			
		case MOVIENDOSEALEATORIO :
		{   
			if(gameTime-tiempoUltimoCambio>= tiempoMinCambio )
			{
		    	
		    	if( cambio)
		    	{
		    		velMovY=2;
		    		tiempoUltimoCambio=gameTime;
		    		velMovX=2;
		    		cambio= false;
		    	}
		    	else
		    	{
		    		if(random.nextInt(2)+1==2 )
		    		{
		    		velMovY=-2;
		    		}
		    		velMovX=-2;
		    		cambio= true;
		    		tiempoUltimoCambio=gameTime;
		    	}
		    	
			} 	
				if(cordY>Framework.altoFrame-charizardFlyFrontImg.getHeight()-2)
		    	{
			    	cordY=Framework.altoFrame-charizardFlyFrontImg.getHeight()-2;
			    	velMovY=0;
		    	}
		        else if(cordY<Framework.altoFrame/3+1)
		        {
		        	cordY=Framework.altoFrame/3+1;
		        	velMovY=0;
		        }
		        
		        if(cordX<0)
		        {
		        	cordX=0;
		        	velMovX=0;
		        }
		        else if(cordX>Framework.anchoFrame-charizardFlyFrontImg.getWidth()/4)
		        {
		        	cordX=Framework.anchoFrame-charizardFlyFrontImg.getWidth()/4;
		        	velMovX=0;
		        }	
		        cordX+=velMovX;
		        cordY+=velMovY;
		       
		        charizardFlyFrontAnim.changeCoordinates(cordX, cordY);
		        charizardFlyBackAnim.changeCoordinates(cordX, cordY);
			break;
		}
		}
		
	}
	public void Draw(Graphics2D g2d)
	{
		switch(Ia)
		{
		case ATACANDO:
		break;
		case ACERCANDOSE:
			if(mirandoFrente)
			{
				charizardFlyFrontAnim.Draw(g2d);
			}
			else
			{
				charizardFlyBackAnim.Draw(g2d);
			}
		break;
		
		case MOVIENDOSEALEATORIO:
		{	if(mirandoFrente)
			{
				charizardFlyFrontAnim.Draw(g2d);
			}
			else
			{
				charizardFlyBackAnim.Draw(g2d);
			}
		break;
		
		
		}
		}
	}
	public int getCordX()
	{
		return cordX;
	}
	public int getCordY()
	{
		return cordY;
	}
}
