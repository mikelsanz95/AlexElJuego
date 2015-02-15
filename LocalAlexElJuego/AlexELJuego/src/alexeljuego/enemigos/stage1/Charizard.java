package alexeljuego.enemigos.stage1;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import alexeljuego.Animation;
import alexeljuego.Canvas;
import alexeljuego.Framework;

public class Charizard {
	
	private int cordX;
	private int cordY;
	
	private int velMovX;
	private int velMovY;
	
	private long tiempoMinCambio;
	private long tiempoUltimoCambio;
	
	private int vida;
	
	private int daño;
	private int dañoLlama;
	private int dañoZarpa;
	private Random random;
	
	private boolean mirandoFrente;
	
	public BufferedImage charizardFlyFrontImg;
	public BufferedImage charizardAttFrontImg;
	public BufferedImage charizardFlameSpitFrontImg;
	public BufferedImage charizardFlyBackImg;
	public BufferedImage charizardAttBackImg;
	public BufferedImage charizardFlameSpitBackImg;
	
	public Animation charizardFlyFrontAnim;
	public Animation charizardAttFrontAnim;
	public Animation charizardFlameSpitFrontAnim;
	public Animation charizardFlyBackAnim;
	public Animation charizardAttBackAnim;
	public Animation charizardFlameSpitBackAnim;
	public enum IA{ ATACANDO, MOVIENDOSEALEATORIO, ACERCANDOSE}
	public IA Ia;
	
	@SuppressWarnings("static-access")
	public void Inicializar(int cordX , int cordY)
	{
		
		this.cordX = cordX;
		this.cordY = cordY;
		velMovX = 6;
		velMovY = 6;
		this.daño=0;
		this.dañoLlama = 20;
		this.dañoZarpa = 30;
		random= new Random();
		Ia = Ia.MOVIENDOSEALEATORIO;
		
	}
	
	public void LoadContent()
	{
			
		try {
			URL charizardMovFrontUrl  = this.getClass().getResource("resources/images/barraVidaImg.png");	
			charizardFlyFrontImg= ImageIO.read(charizardMovFrontUrl);
			URL charizardMovBackUrl  = this.getClass().getResource("resources/images/barraVidaImg.png");		
			charizardFlyBackImg= ImageIO.read(charizardMovBackUrl);
			
			URL charizardAttFrontUrl  = this.getClass().getResource("resources/images/barraVidaImg.png");		
			charizardAttFrontImg= ImageIO.read(charizardAttFrontUrl);
			
			URL charizardAttBackImgUrl  = this.getClass().getResource("resources/images/barraVidaImg.png");		
			charizardAttBackImg= ImageIO.read(charizardAttBackImgUrl);
			
			URL charizardFlameSpitFrontImgUrl  = this.getClass().getResource("resources/images/barraVidaImg.png");		
			charizardFlameSpitFrontImg= ImageIO.read(charizardFlameSpitFrontImgUrl);
			
			URL charizardFlameSpitBackImgUrl  = this.getClass().getResource("resources/images/barraVidaImg.png");		
			charizardFlameSpitBackImg= ImageIO.read(charizardFlameSpitBackImgUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Charizard CargaMal");
		}
		charizardAttBackAnim = new Animation(charizardAttBackImg, 1445/5 , 207, 5, 100, false, cordX,cordY, 0);
		charizardAttFrontAnim= new Animation(charizardAttFrontImg,1445/5 , 207, 5, 100, false, cordX, cordY, 0);
		charizardFlameSpitBackAnim= new Animation(charizardFlameSpitBackImg, 1268/4, 209, 4, 100, false, cordX,cordY, 0);
		charizardFlameSpitFrontAnim= new Animation(charizardFlameSpitFrontImg,1268/4 , 209, 4, 100, false, cordX, cordY, 0);
		charizardFlyFrontAnim = new Animation(charizardFlyFrontImg, 1248/4, 193, 4, 100, false, cordX, cordY, 0);
		charizardFlyBackAnim= new Animation(charizardFlyBackImg, 1248/4 , 193, 4, 100, false, cordX, cordY, 0);
		
		
		
		
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
		    	int j = random.nextInt(200);
		    	if( j== 0)
		    	{
		    		velMovY=6;
		    		tiempoMinCambio=gameTime;
		    	}
		    	else if (j == 1)
		    	{
		    		velMovY=-6;
		    		tiempoMinCambio=gameTime;
		    	}
		    	if (j == 4)
		    	{
		    		velMovX=+3;
		    		tiempoMinCambio=gameTime;
		    	}
		    	else if(j==5)
		    	{
		    		velMovY=-3;
		    		tiempoMinCambio=gameTime;
		    	}
			} 	
				if(cordY>Framework.altoFrame-charizardFlyFrontImg.getHeight())
		    	{
			    	cordY=Framework.altoFrame-charizardFlyFrontImg.getHeight();
			    	velMovY=0;
		    	}
		        else if(cordY<Framework.altoFrame/3)
		        {
		        	cordY=Framework.altoFrame/3;
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
		        cordX=+velMovX;
		        cordY=+velMovY;
		     
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
}
