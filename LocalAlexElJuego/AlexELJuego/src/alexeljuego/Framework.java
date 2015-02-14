package alexeljuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;




public class Framework extends Canvas{

	 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
	     * ancho del frame;
	     */
	    public static int anchoFrame;
	    /**
	     * alto del frame.
	     */
	    public static int altoFrame;

	    /**
	     * 1 segundo en nanosegundos.
	     * 1 segundo = 1 000 000 000 nanosegundos.
	     */
	    public static final long secEnNanosec = 1000000000L;
	    
	    /**
	     * 1 milisegundo en nanosegundos.
	     * 1 milesegundo = 1 000 000 nanosegundos
	     */
	    public static final long milisecEnNanosec = 1000000L;
	    
	    /**
	     * Frames por segundo
	     * cuantas veces se updatea el juego por segundo
	     */
	    private final int GAME_FPS = 60;
	    /**
	     * pausa entre segundos
	     */
	    private final long GAME_UPDATE_PERIOD = secEnNanosec / GAME_FPS;
	    
	    /**
	     * posibles estados del juegos
	     */
	    public static enum GameState{STARTING, VISUALIZING, NEWGAMELOAD,SAVEDGAMELOAD, MAIN_MENU, OPTIONS, PLAYING, GAMEOVER}
	    /**
	     * estado actual del juego22
	     */
	    public static GameState gameState;
	    
	    /**
	     * tiempo de juego en nanosegundos
	     */
	    public static long gameTime;
	    // usado para calcualr el "elapsed"time
	    private long lastTime;
	    
	    private MenuInicial menuInicial;
	    
	    // el juego
	    private Game game;
	    
	    
	    private Font font;
	
	    
	 
    public Framework ()
    {
        super();
        
        gameState = GameState.VISUALIZING;
        
        //empieza un nuevo hilo.
        Thread gameThread = new Thread() 
        {
            @Override
            public void run(){
                GameLoop();
            }
        };
        gameThread.start();
    }
    private void Initialize()
    {
    	menuInicial = new MenuInicial();
    }
    
    private void LoadContent()
    {
    	menuInicial.loadContent();
    }
    
    private void  GameLoop()
    {
    	// This two variables are used in VISUALIZING state of the game. We used them to wait some time so that we get correct frame/window resolution.
        long visualizingTime = 0, lastVisualizingTime = System.nanoTime();
        
        // This variables are used for calculating the time that defines for how long we should put threat to sleep to meet the GAME_FPS.
        long beginTime, timeTaken, timeLeft;
        
        while(true)
        {
            beginTime = System.nanoTime();
            
            switch (gameState)
            {
                case PLAYING:
                    gameTime += System.nanoTime() - lastTime;
                    
                    game.UpdateGame(gameTime, mousePosition());
                    
                    lastTime = System.nanoTime();
                break;
                case GAMEOVER:
                    //...
                break;
                case MAIN_MENU:
                	gameTime += System.nanoTime() - lastTime;
                    menuInicial.cambiarOpcion(gameTime);
                break;
                            	
                
                case OPTIONS:
                    //...
                break;
                case NEWGAMELOAD:
                    //...
                break;
                case STARTING:
                    // Sets variables and objects.
                    Initialize();
                    // Load files - images, sounds, ...
                    LoadContent();

                    // When all things that are called above finished, we change game status to main menu.
                    gameState = GameState.MAIN_MENU;
                break;
                case VISUALIZING:
                  
                    if(this.getWidth() > 1 && visualizingTime > secEnNanosec)
                    {
                        anchoFrame = this.getWidth();
                        altoFrame = this.getHeight();

                        
                        gameState = GameState.STARTING;
                    }
                    else
                    {
                        visualizingTime += System.nanoTime() - lastVisualizingTime;
                        lastVisualizingTime = System.nanoTime();
                    }
                break;
            }
            
           // vuelve a pintar la screen
            repaint();
            
            // caclculos por necesidad para poner el thread a dormir para que concuerde con los fps (60)
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecEnNanosec; // In milliseconds
            // If the time is less than 10 milliseconds, then we will put thread to sleep for 10 millisecond so that some other thread can do some work.
            if (timeLeft < 10) 
                timeLeft = 10; //set a minimum
            try {
                 //Provides the necessary delay and also yields control so that other thread can do work.
                 Thread.sleep(timeLeft);
            } catch (InterruptedException ex) { }
        }
    }
	public void Draw(Graphics2D g2d) {
	       switch (gameState)
	        {
	            case PLAYING:
	                game.Draw(g2d,  gameTime);
	            break;
	            case GAMEOVER:
	                
	            break;
	            case MAIN_MENU:
	            {
	            	menuInicial.Draw(g2d);
//	            	newGame();
	            }
	                
	            break;
	            case OPTIONS:
	                //...
	            break;
	            case NEWGAMELOAD:
	                g2d.setColor(Color.white);
	                g2d.drawString("NEW GAME is LOADING", anchoFrame/2 - 50, altoFrame/2);
	            break;
	        }
	}
	private void newGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        
        game = new Game();
    }

	@Override
	public void keyReleasedFramework(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			switch(gameState)
			{
			case MAIN_MENU:
				{
					switch (menuInicial.opcionSelecionada)
					{
						case 0:
						{
						newGame();
						break;
						}
					}	
					break;
				}
			}
		}
		
	}
    private Point mousePosition()
    {
        try
        {
            Point mp = this.getMousePosition();
            
            if(mp != null)
                return this.getMousePosition();
            else
                return new Point(0, 0);
        }
        catch (Exception e)
        {
            return new Point(0, 0);
        }
    }
    

}
