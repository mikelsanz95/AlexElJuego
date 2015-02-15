package alexeljuego;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;









public class PlayerAlex {

	public  int cordX;
	public  int cordY;
	private  int healthInit = 100;
	public int health;	    
	
    // velocidad y direccion
    public double velMovX;
    public double velMovY;
    private double acelX;
    private double acelY;
    private double frenadoX;
    private double frenadoY;	 
   
    // magias
    private Magias magiasTotal;
   
    //flechas y arco 
    private ArcoFlechas carcaj;
    
    // daño de ataque
    public int daño;
   
    
    //Cooldowns
    
   public  long tiempoUltimoSlash;
   public long tiempoUltimoMagias;
   public  long tiempoUltimoFlechas;
   
    
    public  long tiempoEntreSlash=(long) (0.2*Framework.secEnNanosec);
    public long tiempoEntreMagias=Framework.secEnNanosec/5;
    public  long tiempoEntreFlechas= Framework.secEnNanosec;
    


	
	private long tiempoEntreRec = Framework.secEnNanosec/5;
    private long tiempoUltimaRec;
    
    
    private BufferedImage barraMagiaImg;
    private BufferedImage barraStaminaImg;
    private BufferedImage barraVidaImg;
    // atributos de consumibles
    
    private int puntosMagicosInit= 100;
    private int puntosMagicos;
    public int numSpell;
    
    private int puntosStaminaInit= 100;
    private int puntosStamina;    
    
    private int municion;
    
    // atributos de juego no visibles
    public boolean mirandoFrente;   
    public boolean armaCambiada;    
    public boolean isEnCombo;
    public boolean isAtacando;
    public int numAtaque;
    public boolean tocaDisparar;
    //Imagenes y animaciones del del jugador estatico-sin atacar, y moviendo-sin tacar
    

//    protected BufferedImage playerNotMovFrontAnimImg;
//    protected BufferedImage playerNotMovBackAnimImg;
    
    protected BufferedImage playerMovFrontEspadaAnimImg;
    private BufferedImage playerMovBackEspadaAnimImg;
    private BufferedImage playerNotMovingEspadaImg;
    
    private BufferedImage playerMovFrontBaculoAnimImg;
    private BufferedImage playerMovBackBaculoAnimImg;
    private BufferedImage playerNotMovingBaculoImg;
    
    private BufferedImage playerMovFrontArcoAnimImg;
    private BufferedImage playerMovBackArcoAnimImg;
    private BufferedImage playerNotMovingArcoImg;
    
    private BufferedImage poofAnimImg;
    private Animation poofAnim;
    private ArrayList<Animation> poofAnimList;
    
    private Animation playerMovFrontEspadaAnim;
    private Animation playerMovBackEspadaAnim;
  
    
    private Animation playerMovFrontBaculoAnim;
    private Animation playerMovBackBaculoAnim;
   
    
    private Animation playerMovFrontArcoAnim;
    private Animation playerMovBackArcoAnim;
    
  //Imagenes y animaciones del del jugador atacando.(por ahora estatico)      
    private BufferedImage frontSlashImg;
    private BufferedImage downSlashImg;
    private BufferedImage upSlashImg;
    private BufferedImage omniSlashImg;
    
//    private BufferedImage playerEspadaAttAnimImg;
//    private BufferedImage playerBaculoAttAnimImg;
    private BufferedImage playerArcoAttFrontAnimImg;
    private BufferedImage playerArcoAttBackAnimImg;
//    private Animation playerEspadaAttAnim;
//    private Animation playerBaculoAttAnim;
    private Animation playerArcoAttFrontAnim;
    private Animation playerArcoAttBackAnim;
   
  
    // arma en posesion
    public boolean isBaculoUp;
    public boolean isEspadaUp;
    public boolean isArcoUp;
    
    // offset de los disparos 
    public int offsetFlechaX;
    public int offsetFlechaY;
    public int offsetMagiaX;
    public int offsetMagiaY;
   
    public PlayerAlex(int xCoordinate, int yCoordinate)
    {
        this.cordX = xCoordinate;
        this.cordY = yCoordinate;
        Inicializar(cordX, cordY);
        LoadContent();
        
        
       
       
    }

	private void Inicializar(int cordX, int cordY ) {
		// TODO Auto-generated method stub
		
		// coordenadas y movimientos
		this.cordX=cordX;
		this.cordY=cordY;
		this.velMovX = 0;
        this.velMovY = 0;
	    this.acelX = 0.2;
	    this.acelY = 0.2;
	    this.frenadoX = 0.5;
	    this.frenadoY = 0.5;
	    this.numSpell=0;
	    // offsets etc 
	    this.offsetFlechaX= 0;
	    this.offsetMagiaX=100;
	    this.offsetFlechaY=0;
	    this.offsetMagiaY=20;
	    this.isEspadaUp=true;
	    this.health=healthInit;
	    this.mirandoFrente=true;
	    this.puntosMagicos=puntosStaminaInit;
	    this.puntosStamina=puntosMagicosInit;
	    armaCambiada=false;
	    poofAnimList= new ArrayList<Animation>();
	    isEnCombo=false;
	    isAtacando = false;
	    this.numAtaque=0;
	    magiasTotal= new Magias(cordX+ offsetMagiaX, cordY + offsetMagiaY);
	    carcaj= new ArcoFlechas();
	}

	public void Update(long gameTime)	
	{		
		if(!isAtacando)
		{
		switchDireccion();	
		switchArma();
		isMoving();
	 	cordX += velMovX;
        cordY += velMovY;
		}
       
		else
		{
		velMovX=0;
		velMovY=0;
		}
		if(isBaculoUp)
		{
			skillWay();
			magiasTotal.Update(cordX+offsetMagiaX, cordY+ offsetMagiaY, numSpell);
		}
		else if(isEspadaUp)
		{
			isAtacandoEspada(gameTime);
		}
		else if(isArcoUp)
		{
			skillWay();
			isArcoAtacando(gameTime);
		}
        if(cordY>Framework.altoFrame-playerNotMovingEspadaImg.getHeight())
    	{
	    	cordY=Framework.altoFrame-playerNotMovingEspadaImg.getHeight();
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
        else if(cordX>Framework.anchoFrame-playerNotMovingEspadaImg.getWidth())
        {
        	cordX=Framework.anchoFrame-playerNotMovingEspadaImg.getWidth();
        	velMovX=0;
        }	
        
        if(!isAtacando)
        {
        playerMovFrontBaculoAnim.changeCoordinates(cordX, cordY);
       
        playerMovBackBaculoAnim.changeCoordinates(cordX, cordY);
        playerMovFrontEspadaAnim.changeCoordinates(cordX, cordY);
        
        playerMovBackEspadaAnim.changeCoordinates(cordX, cordY);
        playerMovFrontArcoAnim.changeCoordinates(cordX, cordY);       
        playerMovBackArcoAnim.changeCoordinates(cordX, cordY);
        playerArcoAttBackAnim.changeCoordinates(cordX, cordY);
        playerArcoAttFrontAnim.changeCoordinates(cordX, cordY);
        
        }
        
        if((gameTime-tiempoUltimaRec) >= tiempoEntreRec)
        {	
        		        
	        if (puntosStamina < puntosStaminaInit )
	        {
	        puntosStamina+=5;
	        tiempoUltimaRec= gameTime;
	       
	        }
	        else
	        {	 
	        	
	        puntosStamina= puntosStaminaInit;
	        
	        }
	        if(puntosMagicos< puntosMagicosInit)
	        {
	        puntosMagicos+=1;
	        tiempoUltimaRec= gameTime;
	        }
	        else
	        {
	        	puntosMagicos=puntosMagicosInit;
	        }
        }
	}
	private void LoadContent() 
	
	{
		
		try{
		
//		URL playerNotMovANimImgUrl = this.getClass().getResource("resources/images/corazonVidaPng.png");		
			
		URL barraVidaImgUrl  = this.getClass().getResource("resources/images/barraVidaImg.png");		
		barraVidaImg= ImageIO.read(barraVidaImgUrl);
		
		URL barraMagiaImgUrl  = this.getClass().getResource("resources/images/pMagia.png");		
		barraMagiaImg= ImageIO.read(barraMagiaImgUrl);
		
		URL barraStaminaImgUrl  = this.getClass().getResource("resources/images/pStamina.png");		
		barraStaminaImg = ImageIO.read(barraStaminaImgUrl);
			
		URL playerMovEspadaFrontAnimImgUrl= this.getClass().getResource("resources/images/espadaMovFront.png");
		playerMovFrontEspadaAnimImg = ImageIO.read(playerMovEspadaFrontAnimImgUrl); 
		
		URL playerMovBackEspadaAnimImgUrl= this.getClass().getResource("resources/images/espadaMovBack.png");
		playerMovBackEspadaAnimImg =ImageIO.read(playerMovBackEspadaAnimImgUrl);
		
		URL playerNotMovingEspadaAnimImgURL= this.getClass().getResource("resources/images/EstaticFrontEspada.png");
		playerNotMovingEspadaImg = ImageIO.read(playerNotMovingEspadaAnimImgURL);
		
		URL playerMovFrontBaculoAnimImgUrl= this.getClass().getResource("resources/images/baculoMovFront.png");
		playerMovFrontBaculoAnimImg= ImageIO.read(playerMovFrontBaculoAnimImgUrl);
		
		URL playerMovBackBaculoAnimImgUrl= this.getClass().getResource("resources/images/baculoMovBack.png");
		playerMovBackBaculoAnimImg= ImageIO.read(playerMovBackBaculoAnimImgUrl);
		
		URL playerNotMovingBaculoAnimImgUrl= this.getClass().getResource("resources/images/EstaticFrontBaculo.png");
		playerNotMovingBaculoImg = ImageIO.read(playerNotMovingBaculoAnimImgUrl);
		
		URL playerMovFrontArcoAnimImgUrl= this.getClass().getResource("resources/images/arcoMovFront.png");    
	  	playerMovFrontArcoAnimImg = ImageIO.read(playerMovFrontArcoAnimImgUrl);
	  	
	  	URL playerMovBackArcoAnimImgUrl= this.getClass().getResource("resources/images/arcoMovBack.png");
	  	playerMovBackArcoAnimImg = ImageIO.read(playerMovBackArcoAnimImgUrl);
	  	
	  	URL playerNotMovingArcoAnimImgUrl= this.getClass().getResource("resources/images/EstaticFrontArco.png");
	  	playerNotMovingArcoImg = ImageIO.read(playerNotMovingArcoAnimImgUrl) ;
		// espadazos!!
	  	
	  	URL frontSlashImgUrl= this.getClass().getResource("resources/images/frontSlashImg.png");
	  	frontSlashImg = ImageIO.read(frontSlashImgUrl) ;
	  	URL  downSlashImgUrl= this.getClass().getResource("resources/images/downSlashImg.png");
	  	downSlashImg = ImageIO.read(downSlashImgUrl) ;
	  	URL upSlashImgUrl= this.getClass().getResource("resources/images/upSlashImg.png");
	  	upSlashImg = ImageIO.read(upSlashImgUrl) ;
	  	URL omniSlashUrl = this.getClass().getResource("resources/images/omniSlashImg.png");
	  	omniSlashImg = ImageIO.read(omniSlashUrl) ;
	  	// flechazos
	  	URL playerArcoAttFrontAnimImgUrl= this.getClass().getResource("resources/images/arcoAttFront.png");
	  	playerArcoAttFrontAnimImg= ImageIO.read(playerArcoAttFrontAnimImgUrl);
	  	URL playerArcoAttBackAnimImgUrl= this.getClass().getResource("resources/images/arcoAttBack.png");
	  	playerArcoAttBackAnimImg= ImageIO.read(playerArcoAttBackAnimImgUrl);
	  	
	  	URL poofAnimImgUrl = this.getClass().getResource("resources/images/poofImg.png");
	  	poofAnimImg= ImageIO.read(poofAnimImgUrl);
	  	magiasTotal.LoadContent();
	  	carcaj.LoadContent();
	  	
		}
		catch(Exception e)
		{
			 Logger.getLogger(PlayerAlex.class.getName()).log(Level.SEVERE, null, e);
		}
		// Igual no las pongo aqui quiza es mejor que las ponga en la clase ataques o por el estilo
//	  	playerEspadaAttAnimImg;
//		playerBaculoAttAnimImg;
//		playerArcoAttAnimImg;
		    
		playerMovFrontEspadaAnim = new Animation(playerMovFrontEspadaAnimImg,(int)1412/9, 158, 9,100, false, cordX , cordY , 0);
		playerMovBackEspadaAnim = new Animation(playerMovBackEspadaAnimImg, (int )1412/9, 158, 9, 100, false, cordX , cordY , 0);
		
		    
		playerMovFrontBaculoAnim = new Animation(playerMovFrontBaculoAnimImg, (int)1287/9, 156, 9, 60, false, cordX , cordY , 0);
		playerMovBackBaculoAnim = new Animation(playerMovBackBaculoAnimImg, (int) 1287/9, 156, 9, 60, false, cordX , cordY , 0);

		    
		playerMovFrontArcoAnim = new Animation(playerMovFrontArcoAnimImg,(int) 1368/9, 160, 9, 100, false, cordX , cordY , 0);
		playerMovBackArcoAnim = new Animation(playerMovBackArcoAnimImg, (int) 1368/9, 160, 9, 100, false, cordX , cordY , 0);
		
		    
//		playerEspadaAttAnim = new Animation(playerEspadaAttAnimImg, 69, 52, 3, 50, false, cordX , cordY , 0);
//		playerBaculoAttAnim = new Animation(playerBaculoAttAnimImg, 69, 52, 3, 50, false, cordX , cordY , 0);
		playerArcoAttFrontAnim = new Animation(playerArcoAttFrontAnimImg, 1360/9, 177, 9, 105, false, cordX , cordY , 0);
		playerArcoAttBackAnim = new Animation(playerArcoAttBackAnimImg, 1360/9, 177, 9, 105, false, cordX , cordY , 0);
	}
	private void switchDireccion()
	{
		if (Canvas.keyboardKeyState(KeyEvent.VK_D))
		{
			mirandoFrente=true;
			
		}
		else if (Canvas.keyboardKeyState(KeyEvent.VK_A))
		{
			mirandoFrente=false;
		}
		else
		{
			mirandoFrente=true;
		}
	}
	
	public void switchArma()
	{
		if(Canvas.keyboardKeyState(KeyEvent.VK_J))
		{	
			
			isEspadaUp=true;
			isBaculoUp=false;
			isArcoUp=false;
			armaCambiada=true;
			poofAnim = new Animation(poofAnimImg, (int) 1120/5 ,235 , 4 ,100, false, cordX, cordY, 0 );
			
		}
		else if (Canvas.keyboardKeyState(KeyEvent.VK_K))
		{
			isBaculoUp=true;
			isEspadaUp=false;
			isArcoUp=false;
			armaCambiada=true;
			poofAnim = new Animation(poofAnimImg, (int) 1120/5 ,235 , 4 ,100, false, cordX, cordY, 0 );
			
		}
		else if (Canvas.keyboardKeyState(KeyEvent.VK_L))
		{
			isArcoUp=true;
			isBaculoUp=false;
			isEspadaUp=false;
			armaCambiada=true;
			poofAnim = new Animation(poofAnimImg, (int) 1120/5 ,235 , 4 ,100, false, cordX, cordY, 0 );
			
		}
		
	}
	public void switchSpell()
	{
		if(Canvas.keyboardKeyState(KeyEvent.VK_Q))
		{
			numSpell++;
		}
		else if(Canvas.keyboardKeyState(KeyEvent.VK_E))
		{
			numSpell--;
		}
	} 
	public void skillWay()
	{
	if(isBaculoUp)
	{
		if(mirandoFrente)
		{
			offsetMagiaX=110;
			offsetMagiaY=20;
		}
		else
		{
			offsetMagiaX=-30;
			offsetMagiaY=20;
		}
	}
	else if(isArcoUp)
	{
		if(mirandoFrente)
		{
			offsetFlechaX=80;
			offsetFlechaY=70;
		}
		else
		{
			offsetFlechaX=-80;
			offsetFlechaY=70;
		}
	}
	}
	public void isArcoAtacando(long gameTime)
	{
		if(Canvas.keyboardKeyState(KeyEvent.VK_SPACE) && puntosStamina>10)
		{
			isAtacando=true;			
			if(gameTime-tiempoUltimoFlechas >= tiempoEntreFlechas && puntosStamina>=30)
			{
			puntosStamina-=30;
			tiempoUltimoFlechas=gameTime;
			tocaDisparar=true;
			}
		}
		else if(Canvas.keyboardKeyState(KeyEvent.VK_SPACE) && puntosStamina <30 )
		{
			isAtacando = false;
			tiempoUltimoFlechas=gameTime;
		}
		else if(!Canvas.keyboardKeyState(KeyEvent.VK_SPACE))
		{
			isAtacando=false;
			tiempoUltimoFlechas=gameTime;
		}
		
	}
	public void isAtacandoEspada(long gameTime)
	{
		 
		
		if((gameTime-tiempoUltimoSlash) >= (tiempoEntreSlash +tiempoEntreSlash*2) && !Canvas.keyboardKeyState(KeyEvent.VK_SPACE))		
		{
			isEnCombo=false;
			numAtaque=0;
			isAtacando=false;
			tiempoEntreSlash=(long) (Framework.secEnNanosec*0.2);
			
			
		}
		else if( Canvas.keyboardKeyState(KeyEvent.VK_SPACE)&& isEnCombo&&isEspadaUp  && (gameTime-tiempoUltimoSlash)>=tiempoEntreSlash) 
		{        
			if(numAtaque==0 && puntosStamina>=10)
			{ 	
				tiempoEntreSlash= Framework.secEnNanosec/10;
				puntosStamina-=10;				
				tiempoUltimoSlash=gameTime;
				isAtacando=true;
				numAtaque++;
			
			}
			else if (numAtaque==1 && puntosStamina>=10)
			{
				tiempoEntreSlash= Framework.secEnNanosec/5;
				puntosStamina-=10;				
				tiempoUltimoSlash=gameTime;
				isAtacando=true;				
				numAtaque++;
			}
			else if(numAtaque==2 && puntosStamina>=40)
			{
				tiempoEntreSlash= Framework.secEnNanosec/5;				
				puntosStamina-=40;				
				isEnCombo=false;
				tiempoUltimoSlash=gameTime;
				isAtacando=true;				
				numAtaque++;
				
			}
			else if(numAtaque>3)
			{
				tiempoEntreSlash=Framework.secEnNanosec/50;
				numAtaque=0;
			}
			
		 } 
		 else if( Canvas.keyboardKeyState(KeyEvent.VK_SPACE) && (gameTime-tiempoUltimoSlash)>=tiempoEntreSlash && puntosStamina>=10)
		 {		
			 puntosStamina-=10;
			 isEnCombo=true;			 
			 tiempoUltimoSlash=gameTime;
			 isAtacando=true;
			 numAtaque=0;			
			
			 
		 }
		 
		
	 
	 }

	public boolean isBaculoUp() {
		return isBaculoUp;
	}



	public void isMoving()
	 {
		 
		 // Moving on the x coordinate.
	        if(Canvas.keyboardKeyState(KeyEvent.VK_D))
	            velMovX += acelX;
	        else if(Canvas.keyboardKeyState(KeyEvent.VK_A) )
	            velMovX -= acelX;
	        else    // Stoping
	            if(velMovX < 0)
	                velMovX =0;
	            else if(velMovX >= 0)
	                velMovX =0;
	        
	        // Moving on the y coordinate.
	        if(Canvas.keyboardKeyState(KeyEvent.VK_W) )
	            velMovY -= acelY;
	        else if(Canvas.keyboardKeyState(KeyEvent.VK_S) )
	            velMovY += acelY;
	        else    // Stoping
	            if(velMovY <0)
	                velMovY =0;
	            else if(velMovY >= 0)
	                velMovY = 0;
		 
	 }
	 public void Reset(int xCoordinate, int yCoordinate)
	 {
	        this.health = healthInit;
	        
	        this.cordX = xCoordinate;
	        this.cordY = yCoordinate;
	        
	        
	        this.velMovX = 0;
	        this.velMovY = 0;
	        
	        this.daño= 100;
	        
	        this.isEspadaUp=true;
	        this.isBaculoUp= false;
	        this.isArcoUp= false;
	        this.mirandoFrente=true;
	 }
	  
	 public void Draw(Graphics2D g2d, long gameTime)
	 {
		
		for(int i=0 ;i<(int)health;i++)
		{
		g2d.drawImage(barraVidaImg, 20 + (barraVidaImg.getWidth()+(2*i)), 40, null);
		}
		for(int r=0;r < (int)puntosMagicos ;r++)
		{
		g2d.drawImage(barraMagiaImg, 20 + (barraMagiaImg.getWidth()+(2*r)), 70, null);
		}
		for(int k=0 ; k<puntosStamina;k++)
		{
		g2d.drawImage(barraStaminaImg, 20 +(barraStaminaImg.getWidth()+(2*k)), 100, null);
		}
		if(isBaculoUp)
		{
			
			
			if((Canvas.keyboardKeyState(KeyEvent.VK_D)|| Canvas.keyboardKeyState(KeyEvent.VK_S)||Canvas.keyboardKeyState(KeyEvent.VK_W)) && mirandoFrente==true)
			{	
				
				playerMovFrontBaculoAnim.Draw(g2d);
				mirandoFrente=true;
				
			
				if(armaCambiada==true && poofAnimList.size()==0)					
				{	
					
					poofAnimList.add(poofAnim);					
					armaCambiada=false;
				}
				
			}
			else if(!Canvas.keyboardKeyState(KeyEvent.VK_W)&&!Canvas.keyboardKeyState(KeyEvent.VK_A)&&!Canvas.keyboardKeyState(KeyEvent.VK_S)&&!Canvas.keyboardKeyState(KeyEvent.VK_D))
			{
				
				g2d.drawImage(playerNotMovingBaculoImg, cordX, cordY, null);
				if(armaCambiada==true && poofAnimList.size()==0)
				{	
					
					poofAnimList.add(poofAnim);
					armaCambiada=false;
					
				}

			}
			else if((Canvas.keyboardKeyState(KeyEvent.VK_A)|| Canvas.keyboardKeyState(KeyEvent.VK_S)||Canvas.keyboardKeyState(KeyEvent.VK_W)) && !mirandoFrente)
			{
				
				playerMovBackBaculoAnim.Draw(g2d);				
				mirandoFrente=false;
				if(armaCambiada==true&& poofAnimList.size()==0)
				{	
					
					poofAnimList.add(poofAnim);
					armaCambiada=false;
					
				}
			}
			magiasTotal.Draw( numSpell, g2d);
		}		
		else if(isEspadaUp)
		{	
			
			if(isAtacando)
			{
				switch (numAtaque)
				{
				
					case 0:
					{  	
						g2d.drawImage(frontSlashImg, cordX, cordY, null);
						
						break;
					}
					case 1:
					{	
						g2d.drawImage(upSlashImg,cordX,cordY,null);
						
						break;
					}
					case 2:
					{
						g2d.drawImage(downSlashImg,cordX,cordY,null);
						
						break;
					}
					case 3:
					{
						g2d.drawImage(omniSlashImg,cordX,cordY,null);	
						
						break;
						
					}
				
				}
				
			}
			
			else if((Canvas.keyboardKeyState(KeyEvent.VK_D)|| Canvas.keyboardKeyState(KeyEvent.VK_S)||Canvas.keyboardKeyState(KeyEvent.VK_W)) && mirandoFrente==true)
			{
				
				playerMovFrontEspadaAnim.Draw(g2d);
				mirandoFrente=true;
				
				if(armaCambiada==true && poofAnimList.size()==0)
				{	
					
					poofAnimList.add(poofAnim);
					armaCambiada=false;
					
				}

			}
			else if(!Canvas.keyboardKeyState(KeyEvent.VK_W)&&!Canvas.keyboardKeyState(KeyEvent.VK_A)&&!Canvas.keyboardKeyState(KeyEvent.VK_S) && !Canvas.keyboardKeyState(KeyEvent.VK_D))
			{
				
				g2d.drawImage(playerNotMovingEspadaImg, cordX, cordY, null);
				if(armaCambiada==true && poofAnimList.size()==0)
				{	
					
					poofAnimList.add(poofAnim);
					armaCambiada=false;
					
				}
				
			}
			else if((Canvas.keyboardKeyState(KeyEvent.VK_A)|| Canvas.keyboardKeyState(KeyEvent.VK_S)||Canvas.keyboardKeyState(KeyEvent.VK_W)) && !mirandoFrente)
			{
				
				playerMovBackEspadaAnim.Draw(g2d );				
				mirandoFrente=false;
				if(armaCambiada==true && poofAnimList.size()==0)
				{	
					
					poofAnimList.add(poofAnim);
					armaCambiada=false;
					
				}

			}
		}
		else if(isArcoUp)
		{
			if(isAtacando)
			{
				if(mirandoFrente)
				{
					
					playerArcoAttFrontAnim.Draw(g2d);
				}
				else
				{
					System.out.println("dibujaBack");
					playerArcoAttBackAnim.Draw(g2d);
				}
				
			}
			else if((Canvas.keyboardKeyState(KeyEvent.VK_D)|| Canvas.keyboardKeyState(KeyEvent.VK_S)||Canvas.keyboardKeyState(KeyEvent.VK_W)) && mirandoFrente)
			{
				
				playerMovFrontArcoAnim.Draw(g2d);
				mirandoFrente=true;
				
				if(armaCambiada==true && poofAnimList.size()==0)
				{	
					
					poofAnimList.add(poofAnim);
					armaCambiada=false;
					
				}

			}
			else if(!Canvas.keyboardKeyState(KeyEvent.VK_W)&&!Canvas.keyboardKeyState(KeyEvent.VK_A)&&!Canvas.keyboardKeyState(KeyEvent.VK_S) && !Canvas.keyboardKeyState(KeyEvent.VK_D))
			{
			
				g2d.drawImage(playerNotMovingArcoImg, cordX, cordY, null);
				if(armaCambiada==true && poofAnimList.size()==0)
				{	
					
					poofAnimList.add(poofAnim);
					armaCambiada=false;
					
				}

			}
			else if(Canvas.keyboardKeyState(KeyEvent.VK_A)|| Canvas.keyboardKeyState(KeyEvent.VK_S)||Canvas.keyboardKeyState(KeyEvent.VK_W)  && !mirandoFrente)
			{
			
				
				playerMovBackArcoAnim.Draw(g2d);
				mirandoFrente=false;		
				if(armaCambiada==true && poofAnimList.size()==0)
				{	
					
					poofAnimList.add(poofAnim);
					armaCambiada=false;
					
				}
			}	
		
		}
		try
		{
		if(poofAnimList.get(0)!= null)
		{
			poofAnimList.get(0).Draw(g2d);
		}
		}catch(IndexOutOfBoundsException e)
		{
			
		}
		updatePoof();
		
		
	}
	
	public void updatePoof()
	{	
		
		for(int i = 0; i < poofAnimList.size(); i++)
        {
           
            if(!poofAnimList.get(0).active)
            {
            	poofAnimList.remove(0);
            }
         
        }
	}
    public ArcoFlechas getCarcaj() {
		return carcaj;
	}
	public Magias getMagias()
	{
		return magiasTotal;
	}

	public void consumeMana() 
	{
		
		puntosMagicos-=magiasTotal.getCosteSpell(numSpell);
	}
	public int getPuntosMagia()
	{
		return puntosMagicos;
	}
	
}
