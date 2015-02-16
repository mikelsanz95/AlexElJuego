package alexeljuego;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

public class Charizard extends Enemigos {
	
	private int cordX;
	private int cordY;
	
	private int velMovX;
	private int velMovY;
	
	private long tiempoMinCambio = Framework.secEnNanosec*5;
	private long tiempoUltimoCambio;
	
	private boolean cambio;
	
	private int vida;
	
	private Rectangle2D r2D;
	
	private int daño;
	private int dañoLlama;
	private int dañoZarpa;
	private Random random;
	private int rangoDeTaunt;
	
	
	private boolean xDesc;
	private boolean yDesc;
	
	private long tiempoEntreAtaques = Framework.secEnNanosec /2;
	private long tiempoUltimoataque;
	private int numAtaque ;
	private boolean atacando;
	private boolean timeToStrike;
	
	
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
		rangoDeTaunt= 10;
		random= new Random();
		Ia = Ia.MOVIENDOSEALEATORIO;
		this.charizardAttBackAnim = new Animation(charizardAttBackImg, 1445/5 , 207, 5, 100, false, cordX,cordY, 0);
		this.charizardAttFrontAnim= new Animation(charizardAttFrontImg,1445/5 , 207, 5, 100, false, cordX, cordY, 0);
		this.charizardFlameSpitBackAnim= new Animation(charizardFlameSpitBackImg, 1268/4, 209, 4, 100, false, cordX,cordY, 0);
		this.charizardFlameSpitFrontAnim= new Animation(charizardFlameSpitFrontImg,1268/4 , 209, 4, 100, false, cordX, cordY, 0);
		this.charizardFlyFrontAnim = new Animation(charizardFlyFrontImg, 1248/4, 193, 4, 100, false, cordX, cordY, 0);
		this.charizardFlyBackAnim= new Animation(charizardFlyBackImg, 1248/4 , 193, 4, 100, false, cordX, cordY, 0);
		cambio=true;
		xDesc=true;
		numAtaque=1;
		double rng = random.nextDouble()*random.nextInt(2)+0.5;
		tiempoMinCambio= (long) (Framework.secEnNanosec*rng);
		tiempoUltimoCambio=0;
		r2D= new Rectangle(cordX, cordY, charizardFlyFrontImg.getWidth()/4, charizardFlyFrontImg.getHeight());
	}
	
	
	public void Update(long gameTime,PlayerAlex player)
	{	
		
		
		switchDireccion(player);		
		
		switch (Ia) 
		{
		case ACERCANDOSE:
		{	
			int distY;
			int distX;
			
			if(mirandoFrente)
			{
				if(xDesc)
				{
					distX=cordX-player.cordX+charizardFlyFrontImg.getWidth()/4;
//					if(distX>0)
//					{
//						velMovX=-6;
//						if(distX<6 && distX>0)
//							velMovX=-1;
//					}
					if (distX<0)
					{
						velMovX=+6;
						if(distX>-6 && distX<0 )
							velMovX=+1;
						
					}
					else if(distX==0)
					{
						velMovX=0;
						xDesc=false;
					}
				}
			
			}			
			else
			{
			
				if(xDesc)
				{
					distX=cordX-player.cordX+100;
					if(distX>0)
					{
						velMovX=-6;
						if(distX<6 && distX>0)
							velMovX=-1;
					}
//					else if (distX<0)
//					{
//						velMovX=+6;
//						if(distX>-6 && distX<0 )
//							velMovX=+1;
//						
//					}
					else if(distX==0)
					{
						velMovX=0;
						xDesc=false;
						System.out.println("entra");
						
					}
				}
				
			}
			distY=cordY-player.cordY;
			if(distY>0)
			{
				velMovY=-6;
				if(distY<6 && distY>0)
					velMovY=-1;
				
				
			}
			else if (distY<0)
			{
				velMovY=+6;
				if(distY>-6 && distY<0 )
					velMovY=+1;
			
			}
			else if (distY==0)
			{
				velMovY=0;
				yDesc=false;
			}				
			if (!yDesc && !xDesc)
			Ia= Ia.ATACANDO;
		
			break;
		}
		case ATACANDO :
		{
			velMovX=0;
			velMovY=0;
			xDesc = true;
			yDesc = true;
			ataca(gameTime);
			break;
		}
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
				
			break;
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
        charizardAttFrontAnim.changeCoordinates(cordX, cordY);
        charizardAttBackAnim.changeCoordinates(cordX, cordY);
        charizardFlameSpitFrontAnim.changeCoordinates(cordX, cordY);
        charizardFlameSpitBackAnim.changeCoordinates(cordX, cordY);
        r2D.setRect(cordX, cordY, charizardFlyFrontImg.getWidth()/4, charizardFlyFrontImg.getHeight());
	}
	public void switchDireccion(PlayerAlex player)
	{
		if(player.cordX>cordX)
		{
			
			mirandoFrente=true;
		}
		else if( player.cordX< cordX + charizardAttFrontImg.getWidth()/4)
		{
			mirandoFrente=false;
		}
	}
	
	public void ataca(long gameTime)
	{  	
		atacando=true;		
		if(gameTime-tiempoUltimoataque>= tiempoEntreAtaques)
		{
		int j =random.nextInt(6);
		if(j==1)
		{
			numAtaque=1;
			timeToStrike=true;
			tiempoUltimoataque=gameTime;
			charizardFlameSpitFrontAnim.resetAnim();
			charizardFlameSpitBackAnim.resetAnim();
			Ia=Ia.ACERCANDOSE;
			
		}
		else if(j==2)
		{
			numAtaque=2;
			timeToStrike=true;
			tiempoUltimoataque=gameTime;
//			charizardFlameSpitFrontAnim.resetAnim();
//			charizardFlameSpitBackAnim.resetAnim();
			charizardAttFrontAnim.resetAnim();
			charizardAttBackAnim.resetAnim();
			Ia=Ia.ACERCANDOSE;
		}
		
		
		}
		
		
	}
	public void Draw(Graphics2D g2d)
	{
		switch(Ia)
		{
		case ATACANDO:
		{
			if(numAtaque==1)
			{
				if(mirandoFrente)
				{
					charizardFlameSpitFrontAnim.Draw(g2d);
				}
				else
				{
					charizardFlameSpitBackAnim.Draw(g2d);
				}
				
			}
			else if (numAtaque==2)
			{
				if(mirandoFrente)
				{
					charizardAttFrontAnim.Draw(g2d);
				}
				else
				{
					charizardAttBackAnim.Draw(g2d);
				}
				
				
			
			}
			else
			{
				if(mirandoFrente)
				{
					charizardFlyFrontAnim.Draw(g2d);
				}
				else
				{
					charizardFlyBackAnim.Draw(g2d);
				}
			}
			
		break;
		}
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
	
	public void  tocaAcercarse()
	{
		if(Ia!=Ia.ATACANDO)
		{
		Ia=Ia.ACERCANDOSE;
		}
		
	}
	
	
	@Override
	public Rectangle2D getRec2D() {
		// TODO Auto-generated method stub
		return r2D;
	}
}
