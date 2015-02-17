package alexeljuego;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;







import alexeljuego.Framework.GameState;



public class Game{
	
	private Random random;
	public static enum GameStage{STAGE1,STAGE2,STAGE3,STAGE4,STAGE5,STAGE6,STAGE7,TIENDA,ENDLESS}
	public static GameStage gameStage;
	private static PlayerAlex player;
	private ArrayList<Proyectil> proyectilList;	
	private ArrayList<ProyectilEnem> proyecEnemList;
	private BufferedImage skyImg;
	private BufferedImage grassImg;
	
	//Stage 1
	private ArrayList<Enemigos> enemigosList;
	private long tiempoSpawnCharizard;
	
	
	public Game()
    {
        Framework.gameState = Framework.GameState.NEWGAMELOAD;
        
        Thread threadForInitGame = new Thread() 
        {
            @Override
            public void run()
            {
                // Sets variables and objects for the game.
              
            	Inicializar();
                // Load game files (images, sounds, ...)
                LoadContent();
                
                Framework.gameState = Framework.GameState.PLAYING;
                
            }

       
    };
	 threadForInitGame.start();
    }
	/**
	 * metodo para cargar contenidos que sean necesarios en el juego y vaya a ser utilizados casi siempre
	 */
	private void LoadContent() 
	{
			
		try {
			URL skyImgUrl  = this.getClass().getResource("resources/images/skyImg.png");		
			skyImg= ImageIO.read( skyImgUrl);
			URL grassImgUrl  = this.getClass().getResource("resources/images/grassImg.png");	
			grassImg= ImageIO.read( grassImgUrl);
			switch(gameStage)
			{
			case ENDLESS:
			{
				URL charizardMovFrontUrl  = this.getClass().getResource("resources/images/stage1/charizardFlyFront.png");	
				Charizard.charizardFlyFrontImg= ImageIO.read(charizardMovFrontUrl);                                                 
				URL charizardMovBackUrl  = this.getClass().getResource("resources/images/stage1/charizardFlyBack.png");		
				Charizard.charizardFlyBackImg= ImageIO.read(charizardMovBackUrl);                                                   
				                                                                                                          
				URL charizardAttFrontUrl  = this.getClass().getResource("resources/images/charizardZarpaFront.png");		      
				Charizard.charizardAttFrontImg= ImageIO.read(charizardAttFrontUrl);                                                 
				                                                                                                          
				URL charizardAttBackImgUrl  = this.getClass().getResource("resources/images/charizardZarpa.png");		      
				Charizard.charizardAttBackImg= ImageIO.read(charizardAttBackImgUrl);                                                
				                                                                                                          
				URL charizardFlameSpitFrontImgUrl  = this.getClass().getResource("resources/images/charizardLlamaradaFront.png");		
				Charizard.charizardFlameSpitFrontImg= ImageIO.read(charizardFlameSpitFrontImgUrl);                                  
				                                                                                                          
				URL charizardFlameSpitBackImgUrl  = this.getClass().getResource("resources/images/charizardLlamaradaBack.png");		
				Charizard.charizardFlameSpitBackImg= ImageIO.read(charizardFlameSpitBackImgUrl);
				break;
			}				
			case STAGE1:
			{
			
			URL charizardMovFrontUrl  = this.getClass().getResource("resources/images/stage1/charizardFlyFront.png");	
			Charizard.charizardFlyFrontImg= ImageIO.read(charizardMovFrontUrl);                                                 
			URL charizardMovBackUrl  = this.getClass().getResource("resources/images/stage1/charizardFlyBack.png");		
			Charizard.charizardFlyBackImg= ImageIO.read(charizardMovBackUrl);                                                   
			                                                                                                          
			URL charizardAttFrontUrl  = this.getClass().getResource("resources/images/stage1/charizardZarpaFront.png");		      
			Charizard.charizardAttFrontImg= ImageIO.read(charizardAttFrontUrl);                                                 
			                                                                                                          
			URL charizardAttBackImgUrl  = this.getClass().getResource("resources/images/stage1/charizardZarpaBack.png");		      
			Charizard.charizardAttBackImg= ImageIO.read(charizardAttBackImgUrl);                                                
			                                                                                                          
			URL charizardFlameSpitFrontImgUrl  = this.getClass().getResource("resources/images/stage1/charizardLlamaradaFront.png");		
			Charizard.charizardFlameSpitFrontImg= ImageIO.read(charizardFlameSpitFrontImgUrl);                                  
			                                                                                                          
			URL charizardFlameSpitBackImgUrl  = this.getClass().getResource("resources/images/stage1/charizardLlamaradaBack.png");		
			Charizard.charizardFlameSpitBackImg= ImageIO.read(charizardFlameSpitBackImgUrl);
			
			URL proyectilFrontUrl = this.getClass().getResource("resources/images/stage1/flameFront.png");		
			Charizard.proyectilFront= ImageIO.read(proyectilFrontUrl);
			
			URL proyectilBackUrl  = this.getClass().getResource("resources/images/stage1/flameBack.png");		
			Charizard.proyectilBack= ImageIO.read(proyectilBackUrl);
			
			break;
			}
			case STAGE2:
				break;
			case STAGE3:
				break;
			case STAGE4:
				break;
			case STAGE5:
				break;
			case STAGE6:
				break;
			case STAGE7:
				break;
			case TIENDA:
				break;
			default:
				break;
			
			}	
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void Inicializar() 
	{
			player = new PlayerAlex(0,Framework.altoFrame+300 );
			proyectilList = new ArrayList<Proyectil>();
			enemigosList = new ArrayList<Enemigos>();
			proyecEnemList = new ArrayList<ProyectilEnem>();
			gameStage=GameStage.STAGE1;
			
	}
   
 
        
	public void UpdateGame(long gameTime, Point mousePosition) 
	{
		switch(gameStage)
		{
		case ENDLESS:
		{
			player.Update(gameTime);
		createProyectil(gameTime);
		updateProyectil();
		
			break;
		}
		case STAGE1:
		{
			player.Update(gameTime);
			createProyectil(gameTime);
			updateProyectil();
			for(int i = 0;i< enemigosList.size();i++)
			{
			createProyectilEnemigo(gameTime, enemigosList.get(i));
			}
			updateProyectilEnemigo(gameTime);
			createCharizard(gameTime);
			updateEnemigos(gameTime);
			
			break;
		}
		case STAGE2:
			break;
		case STAGE3:
			break;
		case STAGE4:
			break;
		case STAGE5:
			break;
		case STAGE6:
			break;
		case STAGE7:
			break;
		case TIENDA:
			break;
		default:
			break;
		
		}
		
	}

	private void updateProyectilEnemigo(long gameTime) {
		for(int i=0; i< proyecEnemList.size();i++)
		{
		
			if(proyecEnemList.get(i).isItLeftScreen())
			{
			proyecEnemList.remove(i);
			continue;
			}
			else
			{
			ProyectilEnem p= proyecEnemList.get(i);
			p.Update();
			
			}
			
		}	
		
	}
	private void createProyectilEnemigo(long gameTime, Enemigos enemigos) 
	{
		if(enemigos.isTimeToStrike())
		{
			ProyectilEnem pe = new ProyectilEnem();
			pe.Inicializar(enemigos.getCordDisparoX(), enemigos.getCordDisparoY(), enemigos.getDireccion(), enemigos.getDaño(), enemigos.getVelmovProy(), enemigos.GetNumFrames(), enemigos.getProy());
			
			proyecEnemList.add(pe);
		}
		
	}
	private void updateEnemigos(long gameTime) {
		
		for(int i=0; i< enemigosList.size();i++)
		{
			if(enemigosList.size()<=2)
			{
				enemigosList.get(i).tocaAcercarse();
			}
			else if (enemigosList.size()>2)
			{
			enemigosList.get(0).tocaAcercarse();
			enemigosList.get(1).tocaAcercarse();
			}
			enemigosList.get(i).Update(gameTime, player);
			
			
		}	
		
	}
	private void createCharizard(long gameTime) 
	{	if(enemigosList.size()<3)
		if(gameTime - tiempoSpawnCharizard >= Framework.secEnNanosec )
		{
			random= new Random();
			Charizard chari = new Charizard();
			int cordenX = random.nextInt((Framework.anchoFrame)-Charizard.charizardFlyFrontImg.getWidth()/4) + 1; 
			System.out.println("cordX"+ cordenX);
			int cordenY = random.nextInt((Framework.altoFrame)- Charizard.charizardFlyFrontImg.getHeight()) + Framework.altoFrame/3+1;  
			System.out.println("cordY"+ cordenY);
			chari.Inicializar(cordenX,cordenY);
			
			enemigosList.add(chari);
			tiempoSpawnCharizard=gameTime;
		}
		
	}
	private void updateProyectil() 
	{
	try {
		for(int i=0; i< proyectilList.size();i++)
		{
		
			if(proyectilList.get(i).isItLeftScreen())
			{
			proyectilList.remove(i);
			continue;
			}
			else
			{
			Proyectil p= proyectilList.get(i);
			p.Update();
			
			
			
		}	}
	} catch (Exception e) {
		// TODO: handle exception
	}
		
			
		
		
	}
	private void createProyectil(long gameTime) 
	{
		if((player.isBaculoUp && Canvas.keyboardKeyState(KeyEvent.VK_SPACE) && gameTime-player.tiempoUltimoMagias >=player.tiempoEntreMagias )&& player.getPuntosMagia()>=player.getMagias().getCosteSpell(player.numSpell))
		{
			Proyectil p = new Proyectil();
			p.Inicializar(player.cordX+player.offsetMagiaX, player.cordY +player.offsetMagiaY, player.mirandoFrente, player.daño,player.getMagias().getSpellSpeed(player.numSpell), 
			4,
			player.getMagias().getCastedImg(player.numSpell));
			proyectilList.add(p);
			player.consumeMana();
			player.tiempoUltimoMagias=gameTime;
		
		}
		else if(player.isArcoUp&& Canvas.keyboardKeyState(KeyEvent.VK_SPACE)&& player.isAtacando&& player.tocaDisparar)
		{
			
			Proyectil p = new Proyectil();
			p.Inicializar(player.cordX+ player.offsetFlechaX, player.cordY+player.offsetFlechaY, player.mirandoFrente, player.daño,player.getCarcaj().getFlechaSpeed(player.numSpell),
			1 ,
			player.getCarcaj().getFlechaCastImg(player.numSpell, player.mirandoFrente));
			proyectilList.add(p);
			player.tocaDisparar=false;
			player.tiempoUltimoFlechas=gameTime;
			
		}
		
	}
	public void Draw(Graphics2D g2d, long gameTime) 
	{
		// TODO Auto-generated method stub
		 g2d.drawImage(skyImg, 0,0 , Framework.anchoFrame, Framework.altoFrame/2, null);
		 g2d.drawImage(grassImg, 0,Framework.altoFrame/2, Framework.anchoFrame, Framework.altoFrame, null);
		
		 
		
		
		for(int i=0;i<proyectilList.size()  ; i++ )
		{
			
			proyectilList.get(i).Draw(g2d);
			
		}
		
		if (enemigosList.size()>0)
		for(int i = 0 ; i< enemigosList.size(); i++)
		{	
			
			if(	enemigosList.get(i).getCordY() + 150	> player.cordY +150)
			{
			player.Draw(g2d, gameTime);
			enemigosList.get(i).Draw(g2d);
			
			}
			else if(enemigosList.get(i).getCordY() + 150	== player.cordY +150)
			{	
				
				player.Draw(g2d, gameTime);
				enemigosList.get(i).Draw(g2d);
			}
			else
			{
				enemigosList.get(i).Draw(g2d);
				player.Draw(g2d, gameTime);
			}
			
		}
		
		else
		player.Draw(g2d, gameTime);
		for(int i=0;i<proyecEnemList.size()  ; i++ )
		{
			
			proyecEnemList.get(i).Draw(g2d);
			
		}
	}
	

}
