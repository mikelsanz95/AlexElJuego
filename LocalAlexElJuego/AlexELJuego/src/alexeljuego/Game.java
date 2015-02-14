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
	
	private static PlayerAlex player;
	private ArrayList<Proyectil> proyectilList;
	
	private BufferedImage skyImg;
	private BufferedImage grassImg;
	
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void Inicializar() 
	{
			player = new PlayerAlex(0,Framework.altoFrame+300 );
			proyectilList = new ArrayList<Proyectil>();
			
	}
   
 
        
	public void UpdateGame(long gameTime, Point mousePosition) 
	{
		
		player.Update(gameTime);
		createProyectil(gameTime);
		updateProyectil();
		
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
		player.Draw(g2d, gameTime);
		
		for(int i=0;i<proyectilList.size()  ; i++ )
		{
			proyectilList.get(i).Draw(g2d);
	
		}
	}

}
