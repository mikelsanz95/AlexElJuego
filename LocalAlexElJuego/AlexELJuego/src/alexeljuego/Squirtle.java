package alexeljuego;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;



public class Squirtle extends Enemigos {
	
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
		
		private long tiempoInicioAtaques;
		private long tiempoSpinAtaques;
		private long tiempoEntreAtaques=Framework.secEnNanosec *6;
		
		private long tiempoDuracionAtaque=Framework.secEnNanosec *4;
		
		private long tiempoDeRecarga= Framework.secEnNanosec/5;
		private long tiempoUltimaRecarga=0;
		
		private long tiempoInvuln = Framework.secEnNanosec /5;
		private long tiempoLastDaño; 
		
		
		private boolean atacando;
		
	
		
		private boolean mirandoFrente;	
		
		//Imagenes squirtle
		
		public static BufferedImage squirtleMovFrontImg;
		public static BufferedImage squirtleMovBackImg;
		
		public static BufferedImage squirlteSpinImg;	
				
		public static BufferedImage squirtleIncAttFront;
		public static BufferedImage squirtleIncAttBack;
		
//		public static BufferedImage squirtleDañadoFront;
//		public static BufferedImage squirtleDañadoBack;
		
		
//		public static BufferedImage proyectilFront;
//		public static BufferedImage proyectilBack;
		
		// Animaciones
		
		private Animation squirtleMovBackAnim;
		private Animation squirtleMovFrontAnim;
		
		
		private Animation squirtleIncAttFromAnim;
		private Animation squirtleIncAttBackAnim;
		
		private Animation squirlteSpinAnim;	
		
		private Animation squirtleDañadoFrontAnim;
		private Animation squirtleDañadoBackAnim;
//		
		private Rectangle2D r2D;
		
		//Desaparicion 
		
		
		// tiempo que sera visible?
	    public long fadeTiempoVida;
	    
	    // para el calculo de la vida del trail.
	    public long inicioDelFade;
		
	 // El huno va desapareciendo 
	    public float transparenciaDeImagen;
	    
	    
		public enum IA{ ATACANDO, MOVIENDOSEALEATORIO, ACERCANDOSE, DAÑADO, SPINNIG }
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
			this.squirlteSpinAnim = new Animation(squirlteSpinImg, 648 /8, 72, 8, 20, false, cordX,cordY, 0);

			
			this.squirtleMovFrontAnim = new Animation(squirtleMovFrontImg, 774/6, 123, 6, 50, false, cordX, cordY, 0);
			this.squirtleMovBackAnim= new Animation(squirtleMovBackImg, 774/6, 123, 6, 50, false, cordX, cordY, 0);
			
			
			this.squirtleDañadoFrontAnim= new Animation(squirtleIncAttBack,256/2 ,112,2, 300, false, cordX, cordY, 0);
			this.squirtleDañadoBackAnim= new Animation(squirtleIncAttFront, 256/2, 112, 2, 300, false, cordX, cordY, 0);
			 
			this.squirtleIncAttBackAnim = new Animation(squirtleIncAttBack,256/2 , 112,2, 100, false, cordX, cordY, 0);
			this.squirtleIncAttFromAnim = new Animation(squirtleIncAttFront, 256/2, 112, 2, 100, false, cordX, cordY, 0);
			
	        
	        
	        
	        
			cambio=true;
			xDesc=true;
			yDesc=true;
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
				
				if(mirandoFrente)
				{
					if(xDesc)
					{
						distX=cordX-player.cordX+squirtleMovFrontImg.getWidth()/8+100;
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
						distX=cordX-player.cordX-100;
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
				distY=cordY-player.cordY-50;
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
				if(!atacando)
				{
				velMovX=0;
				velMovY=0;
				}
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
				
				
				atacando=false;				
				Ia=Ia.ACERCANDOSE;
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
			
			if(cordY>Framework.altoFrame-squirtleMovFrontImg.getHeight()-2)
	    	{
				
		    	cordY=Framework.altoFrame-squirtleMovFrontImg.getHeight()-2;
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
        		velMovY=0;	        
        		
        		
	        }
	        else if(cordX>Framework.anchoFrame-squirtleMovFrontImg.getWidth()/8)
	        {
	        	
	        	cordX=Framework.anchoFrame-squirtleMovFrontImg.getWidth()/8;
	        	velMovX=0;
	        	
	        	
	        	
	        }	
	        cordX+=velMovX;
	        cordY+=velMovY;
	       
	        squirtleMovFrontAnim.changeCoordinates(cordX, cordY);
	        squirtleMovBackAnim.changeCoordinates(cordX, cordY);
	        squirlteSpinAnim.changeCoordinates(cordX, cordY);        
	        squirtleDañadoFrontAnim.changeCoordinates(cordX, cordY);
	        squirtleDañadoBackAnim.changeCoordinates(cordX, cordY);
	        squirtleIncAttFromAnim.changeCoordinates(cordX, cordY);
	        squirtleIncAttBackAnim.changeCoordinates(cordX, cordY);
	        
	        r2D = UpdateRec();
		}
		private Rectangle2D UpdateRec()
		{ 
			
			Rectangle2D r = new Rectangle();
			switch(Ia){
			case ACERCANDOSE:
				
				r= new Rectangle(cordX, cordY, squirtleMovFrontImg.getWidth()/4, squirtleMovFrontImg.getHeight());
				break;
			case ATACANDO:
				
				r= new Rectangle(cordX, cordY, squirlteSpinImg.getWidth()/8, squirlteSpinImg.getHeight());
				break;
			
			
			default:
				
				r= new Rectangle(cordX, cordY, squirtleMovFrontImg.getWidth()/4, squirtleMovFrontImg.getHeight());
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
			else if( player.cordX< cordX + squirlteSpinImg.getWidth()/4)
			{
				mirandoFrente=false;
			}
		}
		
		public void ataca(long gameTime)
		{  	
		
			
			if(gameTime-tiempoSpinAtaques<=tiempoDuracionAtaque)
			{
				
				atacando=true;
				
			}
			else
			{
				atacando=false;
				Ia=IA.ACERCANDOSE;
				if(gameTime - tiempoInicioAtaques>= tiempoEntreAtaques)
				{
					atacando=true;
					tiempoInicioAtaques=gameTime;
					tiempoSpinAtaques=gameTime;
					if(mirandoFrente)
					{
						
						velMovX=15;
					}
					else
					{
						velMovX=-15;
					}
				}
				
			
				
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
				if(atacando)
				squirlteSpinAnim.Draw(g2d);
				else
				{
					if(mirandoFrente)
					{	
						squirtleMovFrontAnim.Draw(g2d);
					
					}
					else
					{
						squirtleMovBackAnim.Draw(g2d);
					}
						
				}
			}	
			break;
			
			case ACERCANDOSE:
			if(mirandoFrente)
				
			{	
				squirtleMovFrontAnim.Draw(g2d);
			
			}
			else
			{
				squirtleMovBackAnim.Draw(g2d);
			}
				
			break;
			
			case MOVIENDOSEALEATORIO:
			{	
				if(mirandoFrente)
					
				{	
					squirtleMovFrontAnim.Draw(g2d);
				
				}
				else
				{
					squirtleMovBackAnim.Draw(g2d);
				}
			break;
			
			
			}
			case DAÑADO:
				
				if(mirandoFrente)
					
				{	
					squirtleDañadoFrontAnim.Draw(g2d);
				
				}
				else
				{
					
					squirtleDañadoFrontAnim.Draw(g2d);
					
				}
				
			break;
			
			
			}
		}
		public void UpdateHitbox()
		{
			r2D.setRect(cordX, cordY, squirtleMovFrontImg.getWidth()/4, squirtleMovFrontImg.getHeight());
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
			if(Ia==Ia.ATACANDO || Ia==Ia.DAÑADO ) // && Ia!=Ia.DAÑADO
			{
			
			}
			else
			{
				Ia=Ia.ACERCANDOSE;			

			}
			
		}
		
		public boolean isTimeToStrike() {
			return false;
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
			return 6;
		}

		public int GetNumFrames() {
			// TODO Auto-generated method stub
			return 4;
		}

//		public BufferedImage getProy() {
//			// TODO Auto-generated method stub
//			if(mirandoFrente)
//			{
//				return proyectilFront ;
//			}
//			else
//			{
//				return proyectilBack ; 
//			}
			
//		}
		public int getDaño() {
			// TODO Auto-generated method stub
			return daño;
		}
	}





