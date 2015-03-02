package alexeljuego;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
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
			//charizard
			URL charizardDañadoFront = this.getClass().getResource("resources/images/stage1/charizardDañadoBack.png");	
			Charizard.charizardDañadoFront = ImageIO.read(charizardDañadoFront);
			
			URL charizardDañadoBack = this.getClass().getResource("resources/images/stage1/charizardDañadoFront.png");	
			Charizard.charizardDañadoBack =ImageIO.read(charizardDañadoBack);
			
			
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
			
			URL barraVidaEnemUrl  = this.getClass().getResource("resources/images/barraVidaEnem.png");		
			Enemigos.barraVidaEnem = ImageIO.read(barraVidaEnemUrl);
			// gengar
			

			URL gengarMovFrontUrl  = this.getClass().getResource("resources/images/stage1/gengarMovFront.png");	
			Gengar.gengarFlyFrontImg= ImageIO.read(gengarMovFrontUrl);  
			
			
			URL gengarMovBackUrl  = this.getClass().getResource("resources/images/stage1/gengarMovBack.png");		
			Gengar.gengarFlyBackImg= ImageIO.read(gengarMovBackUrl);                                                   
			                                                                                                          
			URL gengarAttFrontUrl  = this.getClass().getResource("resources/images/stage1/gengarAttFront.png");		      
			Gengar.gengarAttFrontImg= ImageIO.read(gengarAttFrontUrl);                                                 
			                                                                                                          
			URL gengarAttBackImgUrl  = this.getClass().getResource("resources/images/stage1/gengarAttBack.png");		      
			Gengar.gengarAttBackImg= ImageIO.read(gengarAttBackImgUrl);                                                
			                                                                                                          
			
			URL gengarDañadoFront = this.getClass().getResource("resources/images/stage1/gengarDañadoBack.png");	
			Gengar.gengarDañadoFront = ImageIO.read(gengarDañadoFront);
			
			URL gengarDañadoBack = this.getClass().getResource("resources/images/stage1/gengarDañadoFront.png");	
			Gengar.gengarDañadoBack =ImageIO.read(gengarDañadoBack);
			
			URL proyectilFrontUrl1 = this.getClass().getResource("resources/images/stage1/bolaSombraFront.png");		
			Gengar.proyectilFront= ImageIO.read(proyectilFrontUrl1);
			
			URL proyectilBackUrl1 = this.getClass().getResource("resources/images/stage1/bolaSombraBack.png");		
			Gengar.proyectilBack= ImageIO.read(proyectilBackUrl1);
			
			URL gengarFadeUrl = this.getClass().getResource("resources/images/stage1/gengarFade.png");	
			Gengar.gengarFade=ImageIO.read(gengarFadeUrl);
			
			// squirtle
			
			
			URL squirtleMovBackUrl  = this.getClass().getResource("resources/images/stage1/squirtleMovBack.png");		
			Squirtle.squirtleMovBackImg= ImageIO.read(squirtleMovBackUrl);			
			URL sqirtleMovFrontUrl  = this.getClass().getResource("resources/images/stage1/squirtleMovFront.png");		
			Squirtle.squirtleMovFrontImg= ImageIO.read(sqirtleMovFrontUrl);  
			URL squirtleSpinUrl = this.getClass().getResource("resources/images/stage1/squirtleCapaMov.png");		
			Squirtle.squirlteSpinImg= ImageIO.read(squirtleSpinUrl);  
			URL squirtleDañoFront  = this.getClass().getResource("resources/images/stage1/squirtleIncAttFront.png");		
			Squirtle.squirtleIncAttFront= ImageIO.read(squirtleDañoFront);  
			URL squirtleDañoBack  = this.getClass().getResource("resources/images/stage1/squirtleIncAttBack.png");		
			Squirtle.squirtleIncAttBack= ImageIO.read(squirtleDañoBack);  
			
			
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
			random= new Random();
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
			consultaColisionDeEspada();
			createProyectil(gameTime);
			updateProyectil();
			for(int i = 0;i< enemigosList.size();i++)
			{
			createProyectilEnemigo(gameTime, enemigosList.get(i));
			}
			updateProyectilEnemigo(gameTime);
//			createSquirtle(gameTime);
			
			int i =random.nextInt(2)+1;
			if(i==1 )
			{
				createCharizard(gameTime);
			}
			else
			{
				createGengar(gameTime);
			}
			
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
			Rectangle2D proyRec = new Rectangle(proyecEnemList.get(i).cordX, proyecEnemList.get(i).cordY, proyecEnemList.get(i).proyectilImg.getWidth()/proyecEnemList.get(i).numFrames, proyecEnemList.get(i).proyectilImg.getWidth());
			if(proyRec.intersects(player.rec))
			{
				player.health-=proyecEnemList.get(i).daño;
				proyecEnemList.remove(i);
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
			if(enemigosList.get(i).getVida()<=0)
			{
				enemigosList.remove(i);
				continue;
			}
			
			enemigosList.get(i).Update(gameTime, player);
			
			
			
		}	
		
	}
	private void consultaColisionDeEspada()
	{
		for(int i =0;i<enemigosList.size();i++)
		{
			
		if(player.isAtacando && player.isEspadaUp  && enemigosList.get(i).isPuedeSeDañado())
		{
			if(enemigosList.get(i).getRec2D().intersects(player.rec))
			{
				enemigosList.get(i).setVida(enemigosList.get(i).getVida() - player.daño);
			}
			
		}
		}
	}
	private void createSquirtle(long gameTime) 
	{	if(enemigosList.size()<1)
		if(gameTime - tiempoSpawnCharizard >= Framework.secEnNanosec*6 )
		{
			random= new Random();
			Squirtle squrti = new Squirtle();
			int cordenX = random.nextInt((Framework.anchoFrame)-Charizard.charizardFlyFrontImg.getWidth()/4) + 1; 
			
			int cordenY = random.nextInt((Framework.altoFrame)- Charizard.charizardFlyFrontImg.getHeight()) + Framework.altoFrame/3+1;  
			
			squrti.Inicializar(cordenX,cordenY);
			
			enemigosList.add(squrti);
			tiempoSpawnCharizard=gameTime;
		}
		
	}
	
	private void createCharizard(long gameTime) 
	{	if(enemigosList.size()<2)
		if(gameTime - tiempoSpawnCharizard >= Framework.secEnNanosec )
		{
			random= new Random();
			Charizard chari = new Charizard();
			int cordenX = random.nextInt((Framework.anchoFrame)-Charizard.charizardFlyFrontImg.getWidth()/4) + 1; 
			
			int cordenY = random.nextInt((Framework.altoFrame)- Charizard.charizardFlyFrontImg.getHeight()) + Framework.altoFrame/3+1;  
			
			chari.Inicializar(cordenX,cordenY);
			
			enemigosList.add(chari);
			tiempoSpawnCharizard=gameTime;
		}
		
	}
	
	private void createGengar(long gameTime) 
	{	if(enemigosList.size()<2)
		if(gameTime - tiempoSpawnCharizard >= Framework.secEnNanosec )
		{
			random= new Random();
			Gengar gengi = new Gengar();
			int cordenX = random.nextInt((Framework.anchoFrame)-Gengar.gengarFlyFrontImg.getWidth()/2) + 1; 
			
			int cordenY = random.nextInt((Framework.altoFrame)- Gengar.gengarFlyFrontImg.getHeight()) + Framework.altoFrame/3+1;  
			
			gengi.Inicializar(cordenX,cordenY);
			
			enemigosList.add(gengi);
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
			}
			
			Rectangle2D h2d = new Rectangle(proyectilList.get(i).cordX, proyectilList.get(i).cordY,proyectilList.get(i).getImagenWidth() ,proyectilList.get(i).getImagenHeigth() );
			for(int j =0;j<enemigosList.size() ; j++)
			{
				if(enemigosList.get(j).getRec2D().intersects(h2d))
				{
					if(enemigosList.get(j).isPuedeSeDañado())
					{
					enemigosList.get(j).setVida(enemigosList.get(j).getVida() - proyectilList.get(i).daño);
					proyectilList.remove(i);
					}
					break;
					
					
				}
			}
			
			
		}
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
