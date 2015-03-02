package alexeljuego;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;



public class Gengar extends Enemigos 
{
	private int cordX;
	private int cordY;

	private int velMovX;
	private int velMovY;
	
	private long tiempoMinCambio = Framework.secEnNanosec*5;
	private long tiempoUltimoCambio;

	
	private int ultimaVida;
	
	private boolean cambio;
	
	private int vida;

	private int daño;	
	private Random random;
	private boolean puedeSeDañado;
	private boolean xDesc;
	private boolean yDesc;
	
	private long tiempoEntreAtaques = Framework.secEnNanosec /2;
	private long tiempoUltimoataque;
	
	private long tiempoDeRecarga= Framework.secEnNanosec/5;
	private long tiempoUltimaRecarga=0;
	
	private long tiempoInvuln = Framework.secEnNanosec /5;
	private long tiempoLastDaño; 
	
	private int numAtaque ;
	private boolean atacando;
	private boolean timeToStrike;
	private boolean fadeCreado;
	
	private boolean mirandoFrente;	
	
	//Imagenes gengar
	
	public static BufferedImage gengarFlyFrontImg;
	public static BufferedImage gengarAttFrontImg;
	
	public static BufferedImage gengarFlyBackImg;
	public static BufferedImage gengarAttBackImg;
	
	public static BufferedImage gengarDañadoFront;
	public static BufferedImage gengarDañadoBack;
	
	public static BufferedImage gengarFade;
	
	
	public static BufferedImage proyectilFront;
	public static BufferedImage proyectilBack;
	
	// Animaciones
	
	private Animation gengarFlyFrontAnim;
	private Animation gengarAttFrontAnim;
	private Animation gengarFlyBackAnim;
	private Animation gengarAttBackAnim;
	private Animation gengarDañadoFrontAnim;
	private Animation gengarDañadoBackAnim;
	
	private Rectangle2D r2D;
	
	//Desaparicion 
	
	
	// tiempo que sera visible?
    public long fadeTiempoVida;
    
    // para el calculo de la vida del trail.
    public long inicioDelFade;
	
 // El huno va desapareciendo 
    public float transparenciaDeImagen;
    
    
	public enum IA{ ATACANDO, MOVIENDOSEALEATORIO, ACERCANDOSE, DAÑADO , TP}
	public IA Ia;
	public void Inicializar(int cordX , int cordY)
	{
		this.vida=200;
		this.cordX = cordX;
		this.cordY = cordY;
		ultimaVida = vida;
		
		velMovX = 0;
		velMovY = 0;
		this.daño=5;
		
		random= new Random();
		Ia = Ia.MOVIENDOSEALEATORIO;
		this.gengarAttBackAnim = new Animation(gengarAttBackImg, 764/4, 202, 4, 200, false, cordX,cordY, 0);
		this.gengarAttFrontAnim= new Animation(gengarAttFrontImg,764/4 , 202, 4, 200, false, cordX, cordY, 0);
		
		this.gengarFlyFrontAnim = new Animation(gengarFlyFrontImg, 279/2, 184, 2, 300, false, cordX, cordY, 0);
		this.gengarFlyBackAnim= new Animation(gengarFlyBackImg, 279/2 , 184, 2, 300, false, cordX, cordY, 0);
		this.gengarDañadoFrontAnim= new Animation(gengarDañadoFront,490/2 , 186,2, 100, false, cordX, cordY, 0);
		this.gengarDañadoBackAnim= new Animation(gengarDañadoBack, 490/2, 186, 2, 100, false, cordX, cordY, 0);
		 
        this.fadeTiempoVida = Framework.secEnNanosec;                
        this.transparenciaDeImagen = 1.0f;
        
        fadeCreado=false;
        
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
					distX=cordX-player.cordX+gengarFlyFrontImg.getWidth()/4+200;
					if (distX<0)
					{
						velMovX=+6;
						if(distX>-6 && distX<0 )
							velMovX=+1;
						
					}
					else if(distX>=0)
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
					distX=cordX-player.cordX-300;
					if(distX>0)
					{
						velMovX=-6;
						if(distX<6 && distX>0)
							velMovX=-1;
						
					}
					else if(distX<=0)
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
			int i = random.nextInt(2)+1;
			if(i==1 && !atacando)
			{
				Ia=Ia.TP;
			}
			else
			{
				ataca(gameTime);
			}			
			
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
		case TP:
			
			
			if(!fadeCreado)	
			{
			
			inicioDelFade=gameTime;
			fadeCreado=true;
			}
			puedeSeDañado=false;
			velMovX=0;
			velMovY=0;
			
			long tiempoVidaActual = gameTime - inicioDelFade;	        
	        int TVactualPorcentaje = (int)(tiempoVidaActual * 100 / fadeTiempoVida);
	        TVactualPorcentaje = 100 - TVactualPorcentaje;
	        float lTrailTransparencia = 1.0f * (TVactualPorcentaje * 0.01f);

	        if(lTrailTransparencia > 0 )
	            transparenciaDeImagen = lTrailTransparencia;
			if(didTrailDisappear(gameTime))
			{
				
				puedeSeDañado=false;
				fadeTiempoVida = Framework.secEnNanosec;                
			    transparenciaDeImagen = 1.0f;
			    fadeCreado=false;
			    puedeSeDañado=true;
			    if(mirandoFrente)
			    {
			    	cordX=+800;
			    }
			    else
			    {
			    	cordX=-800;
			    }
			    Ia=Ia.ACERCANDOSE;
			}
			
			
			break;
	
		
			
		}
		if(cordY>Framework.altoFrame-gengarFlyFrontImg.getHeight()-2)
    	{
	    	cordY=Framework.altoFrame-gengarFlyFrontImg.getHeight()-2;
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
        else if(cordX>Framework.anchoFrame-gengarFlyFrontImg.getWidth()/4)
        {
        	cordX=Framework.anchoFrame-gengarFlyFrontImg.getWidth()/4;
        	velMovX=0;
        }	
        cordX+=velMovX;
        cordY+=velMovY;
       
        gengarFlyFrontAnim.changeCoordinates(cordX, cordY);
        gengarFlyBackAnim.changeCoordinates(cordX, cordY);
        gengarAttFrontAnim.changeCoordinates(cordX, cordY);
        gengarAttBackAnim.changeCoordinates(cordX, cordY);
        
        gengarDañadoFrontAnim.changeCoordinates(cordX, cordY);
        gengarDañadoBackAnim.changeCoordinates(cordX, cordY);
        
        r2D.setRect(cordX, cordY, gengarFlyFrontImg.getWidth()/4, gengarFlyFrontImg.getHeight());
	}
	private Rectangle2D UpdateRec()
	{ Rectangle2D r = new Rectangle();
		switch(Ia){
		case ACERCANDOSE:
			
			r= new Rectangle(cordX, cordY, gengarFlyFrontImg.getWidth()/4, gengarFlyFrontImg.getHeight());
			break;
		case ATACANDO:
			
			r= new Rectangle(cordX, cordY, gengarAttBackImg.getWidth()/4, gengarAttFrontImg.getHeight());
			break;
		
		
		default:
			
			r= new Rectangle(cordX, cordY, gengarFlyFrontImg.getWidth()/4, gengarFlyFrontImg.getHeight());
			break;
		
		}
		
		return r;
		
	}
    public boolean didTrailDisappear(long gameTime)
    {
        long tiempoVidaActual = gameTime - inicioDelFade;
        
        if(tiempoVidaActual >= fadeTiempoVida)
            return true;
        else
            return false;
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
		else if( player.cordX< cordX + gengarAttFrontImg.getWidth()/4)
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
			if (numAtaque==1 && atacando==true)
			{
				if(mirandoFrente)
				{
					gengarAttFrontAnim.Draw(g2d);
				}
				else
				{
					gengarAttBackAnim.Draw(g2d);
				}
						
			}
			else
			{
				if(mirandoFrente && atacando==false)
				{
					gengarFlyFrontAnim.Draw(g2d);
				}
				else if(!mirandoFrente && atacando==false )
				{
					gengarFlyBackAnim.Draw(g2d);
				}
			}
			
		break;
		}
		case ACERCANDOSE:
		
			if(mirandoFrente)
			{
				gengarFlyFrontAnim.Draw(g2d);
			}
			else
			{
				gengarFlyBackAnim.Draw(g2d);
			}
		break;
		
		case MOVIENDOSEALEATORIO:
		{	if(mirandoFrente)
			{
				gengarFlyFrontAnim.Draw(g2d);
			}
			else
			{
				gengarFlyBackAnim.Draw(g2d);
			}
		break;
		
		
		}
		case DAÑADO:
			
			
			if(mirandoFrente)
			{
				gengarDañadoFrontAnim.Draw(g2d);
			}
			else
			{
				gengarDañadoBackAnim.Draw(g2d);
			}
		break;
		case TP:
			
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparenciaDeImagen));
			float imageMultiplier = 2 - transparenciaDeImagen; // usando imagemultiplier hacemos mas grande la imagen
	        int newImagenAncho = (int)(gengarFade.getWidth() * imageMultiplier);
	        int newImagenAlto = (int)(gengarFade.getHeight() * imageMultiplier);
	        int newImagencordY = (int)(gengarFade.getHeight()/2 * (1-transparenciaDeImagen));
	        g2d.drawImage(gengarFade , cordX, cordY - newImagencordY, newImagenAncho, newImagenAlto, null);
	        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		break;
			
		
		}
	}
	public void UpdateHitbox()
	{
		r2D.setRect(cordX, cordY, gengarFlyFrontImg.getWidth()/4, gengarFlyFrontImg.getHeight());
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
		if(Ia==Ia.ATACANDO || Ia==Ia.DAÑADO || Ia==Ia.TP) // && Ia!=Ia.DAÑADO
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
			r = cordX+100;
		}
		else
		{
			r = cordX-20;
		}	
		return r;
	}

	public int getCordDisparoY() {
		// TODO Auto-generated method stub
		int r;
		if(mirandoFrente)
		{
			r = cordY+60;
		}
		else
		{
			r = cordY+60;
		}	
		return r;
	}
	
	

	public boolean getDireccion() {
		// TODO Auto-generated method stub
		return mirandoFrente;
	}

	public int getVelmovProy() {
		// TODO Auto-generated method stub
		return 8;
	}

	public int GetNumFrames() {
		// TODO Auto-generated method stub
		return 4;
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



