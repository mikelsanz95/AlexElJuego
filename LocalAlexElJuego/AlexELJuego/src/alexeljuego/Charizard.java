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
	
	private int dañoAc;
	private long tiempoMinCambio = Framework.secEnNanosec*5;
	private long tiempoUltimoCambio;
	private int ultimaVida;
	
	
	private boolean cambio;
	
	private int vida;
	
	private Rectangle2D r2D;
	
	private int daño;
	private int dañoLlama;
	private int dañoZarpa;
	private Random random;
	private int rangoDeTaunt;
	private boolean puedeSeDañado;
	
	
	private boolean xDesc;
	private boolean yDesc;
	
	private long tiempoEntreAtaques = Framework.secEnNanosec /2;
	private long tiempoUltimoataque;
	
	private long tiempoDeRecarga= Framework.secEnNanosec;
	private long tiempoUltimaRecarga=0;
	
	private long tiempoInvuln = Framework.secEnNanosec /5;
	private long tiempoLastDaño; 
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
	public static BufferedImage charizardDañadoFront;
	public static BufferedImage charizardDañadoBack;
	
	
	public static BufferedImage proyectilFront;
	public static BufferedImage proyectilBack;
	
	
	
	private Animation charizardFlyFrontAnim;
	private Animation charizardAttFrontAnim;
	
	private Animation charizardFlameSpitFrontAnim;
	private Animation charizardFlyBackAnim;
	
	private Animation charizardAttBackAnim;
	private Animation charizardFlameSpitBackAnim;
	
	private Animation charizardDañadoFrontAnim;
	private Animation charizardDañadoBackAnim;
	
	
	public enum IA{ ATACANDO, MOVIENDOSEALEATORIO, ACERCANDOSE, DAÑADO}
	public IA Ia;
	
	@SuppressWarnings("static-access")
	public void Inicializar(int cordX , int cordY)
	{
		this.vida=200;
		this.cordX = cordX;
		this.cordY = cordY;
		ultimaVida = vida;
		
		velMovX = 0;
		velMovY = 0;
		this.daño=20;
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
		this.charizardDañadoFrontAnim= new Animation(charizardDañadoFront,1268/4 , 209, 4, 50, false, cordX, cordY, 0);
		this.charizardDañadoBackAnim= new Animation(charizardDañadoBack, 1268/4, 209, 4, 50, false, cordX, cordY, 0);
		
		cambio=true;
		xDesc=true;
		numAtaque=1;
		atacando=false;
		double rng = random.nextDouble()*random.nextInt(2)+0.5;
		tiempoMinCambio= (long) (Framework.secEnNanosec*rng);
		tiempoUltimoCambio=0;
		r2D= UpdateRec();
		puedeSeDañado=true;
	}
	
	
	public void Update(long gameTime,PlayerAlex player)
	{	
		
		
		switchDireccion(player);		
		
		if(ultimaVida!=vida)
		{
			ultimaVida=vida;
			tiempoLastDaño=gameTime;
			
			puedeSeDañado=false;
			Ia=Ia.DAÑADO;
		}
			
		switch (Ia) 
		{
		case ACERCANDOSE:
		{	
			int distY;
			int distX;
			timeToStrike =false;
			if(mirandoFrente)
			{
				if(xDesc)
				{
					distX=cordX-player.cordX+charizardFlyFrontImg.getWidth()/4;
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
			else if(!mirandoFrente)
			{
			
				if(xDesc)
				{
					distX=cordX-player.cordX-150;
					if(distX>0)
					{
						velMovX=-6;
						if(distX<6 && distX>0)
							velMovX=-1;
						
					}
					else if(distX==0)
					{
						velMovX=0;
						xDesc=false;
						
						
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
			{
				Ia= Ia.ATACANDO;
			}
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
		case DAÑADO:
			
			if(mirandoFrente)
			{
				velMovY=6;
				velMovX=-9;
			}
			else
			{
				velMovY=6;
				velMovX=9;
			}
			if(gameTime - tiempoLastDaño >= tiempoInvuln)
			{
				
				Ia=Ia.ACERCANDOSE;
				puedeSeDañado=true;
			}
			break;
		
			
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
        charizardDañadoFrontAnim.changeCoordinates(cordX, cordY);
        charizardDañadoBackAnim.changeCoordinates(cordX, cordY);
        
        r2D.setRect(cordX, cordY, charizardFlyFrontImg.getWidth()/4, charizardFlyFrontImg.getHeight());
	}
	private Rectangle2D UpdateRec()
	{ Rectangle2D r = new Rectangle();
		switch(Ia){
		case ACERCANDOSE:
			
			r= new Rectangle(cordX, cordY, charizardFlyFrontImg.getWidth()/4, charizardFlyFrontImg.getHeight());
			break;
		case ATACANDO:
			
			r= new Rectangle(cordX, cordY, charizardFlameSpitBackImg.getWidth()/4, charizardFlameSpitBackImg.getHeight());
			break;
		
		
		default:
			
			r= new Rectangle(cordX, cordY, charizardFlyFrontImg.getWidth()/4, charizardFlyFrontImg.getHeight());
			break;
		
		}
		
		return r;
		
	}
	
	public int getVida() {
		return vida;
	}
	public boolean isPuedeSeDañado() {
		return puedeSeDañado;
	}
	public void setVida(int vida) {
		this.vida = vida;
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
	
		if(gameTime - tiempoUltimaRecarga >=tiempoDeRecarga && !atacando)
		{	
			
			tiempoUltimaRecarga=gameTime;
			tiempoUltimoataque= gameTime;
			atacando=true;
		
		}
		else if(atacando && gameTime - tiempoUltimoataque>= tiempoEntreAtaques)
		{
			
			timeToStrike=true;
			tiempoUltimoataque=gameTime;
		}
		else if(atacando&& timeToStrike)
		{
			
			timeToStrike=false;
			atacando=false;
			tiempoUltimaRecarga=gameTime;
			Ia=Ia.ACERCANDOSE;
		}
		

		
		
	}
	public void Draw(Graphics2D g2d)
	{
		for(int i=0 ;i<vida;i++)
		{
		g2d.drawImage(Enemigos.barraVidaEnem, cordX+i, cordY-80, null);
		}
		switch(Ia)
		{
		case ATACANDO:
		{
			if(numAtaque==1 && atacando==true)
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
			else if (numAtaque==2 && atacando==true)
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
				if(mirandoFrente && atacando==false)
				{
					charizardFlyFrontAnim.Draw(g2d);
				}
				else if(!mirandoFrente && atacando==false )
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
		case DAÑADO:
			
			
			if(mirandoFrente)
			{
				charizardDañadoFrontAnim.Draw(g2d);
			}
			else
			{
				charizardDañadoBackAnim.Draw(g2d);
			}
		break;
			
		
		}
	}
	public void UpdateHitbox()
	{
		r2D.setRect(cordX, cordY, charizardFlyFrontImg.getWidth()/4, charizardFlyFrontImg.getHeight());
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
		if(Ia==Ia.ATACANDO || Ia==Ia.DAÑADO) // && Ia!=Ia.DAÑADO
		{
		
		}
		else
		{
			Ia=Ia.ACERCANDOSE;			

		}
		
	}
	
	public boolean isTimeToStrike() {
		return timeToStrike;
	}
	
	@Override
	public Rectangle2D getRec2D() {
		
		return r2D;
	}
	public int getCordDisparoX() {
		// TODO Auto-generated method stub
		int r;
		if(mirandoFrente)
		{
			r = cordX+280;
		}
		else
		{
			r = cordX-80;
		}	
		return r;
	}

	public int getCordDisparoY() {
		// TODO Auto-generated method stub
		int r;
		if(mirandoFrente)
		{
			r = cordY+80;
		}
		else
		{
			r = cordY+80;
		}	
		return r;
	}

	public boolean getDireccion() {
		// TODO Auto-generated method stub
		return mirandoFrente;
	}

	public int getVelmovProy() {
		// TODO Auto-generated method stub
		return 10;
	}

	public int GetNumFrames() {
		// TODO Auto-generated method stub
		return 5;
	}

	public BufferedImage getProy() {
		// TODO Auto-generated method stub
		if(mirandoFrente)
		{
			return proyectilFront ;
		}
		else
		{
			return proyectilBack ; 
		}
		
	}
	public int getDaño() {
		// TODO Auto-generated method stub
		return daño;
	}
}
