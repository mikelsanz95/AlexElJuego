package alexeljuego;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class SlashHits {
	
	
	
	private int cordX;
	private int cordY;
	
	private int numAtaque;
	
	private BufferedImage frontSlashFrontImg;
    private BufferedImage frontSlashBackImg;
    private BufferedImage downSlashFrontImg;
    private BufferedImage downSlashBackImg;
    private BufferedImage upSlashFrontImg;
    private BufferedImage upSlashBackImg;
    
    private BufferedImage omniSlashFrontImg;
    private BufferedImage omniSlashBackImg;

    private BufferedImage playerArcoAttFrontAnimImg;
    private BufferedImage playerArcoAttBackAnimImg;
//
    private Animation playerFrontSlashFrontAnim;
    private Animation playerFrontSlashBackAnim;
    
    private Animation playerUpSlashFrontAnim;
    private Animation playerUpSlashBackAnim;
    
    private Animation playerDownSlashFrontAnim;
    private Animation playerDownSlashBackAnim;
    
    private Animation playerOmniSlashFrontAnim;
    private Animation playerOmniSlashBackAnim;

    
    public void Inicializar(int cordX, int cordY)
    {
    	this.cordX=cordX;
    	this.cordY= cordY;
    	numAtaque=0;
    }
    
    public void LoadContent()
    {
    	
    	
    	
	  	
	  
	  	try {
	  		
	  		URL frontSlashFrontImgUrl= this.getClass().getResource("resources/images/frontSlashFront.png");
			frontSlashFrontImg = ImageIO.read(frontSlashFrontImgUrl) ;
			URL frontSlashBackImgUrl= this.getClass().getResource("resources/images/frontSlashBack.png");
		  	frontSlashBackImg = ImageIO.read(frontSlashBackImgUrl) ;
		  	
		  	URL  downSlashFrontImgUrl= this.getClass().getResource("resources/images/downSlashFront.png");
		  	downSlashFrontImg = ImageIO.read(downSlashFrontImgUrl) ;
		  	URL  downSlashBackImgUrl= this.getClass().getResource("resources/images/downSlashBack.png");
		  	downSlashBackImg = ImageIO.read(downSlashBackImgUrl) ;	  	
		  	
		  	URL upSlashFrontImgUrl= this.getClass().getResource("resources/images/upSlashFront.png");
		  	upSlashFrontImg = ImageIO.read(upSlashFrontImgUrl) ;
		  	URL upSlashBackImgUrl= this.getClass().getResource("resources/images/upSlashBack.png");
		  	upSlashBackImg = ImageIO.read(upSlashBackImgUrl);		 
		  	
		  	URL omniSlashFrontUrl = this.getClass().getResource("resources/images/omniSlashFront.png");
		  	omniSlashFrontImg = ImageIO.read(omniSlashFrontUrl) ;
		  	URL omniSlashBackUrl = this.getClass().getResource("resources/images/omniSlashBack.png");
		  	omniSlashBackImg = ImageIO.read(omniSlashBackUrl) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
    	playerFrontSlashFrontAnim = new Animation(frontSlashFrontImg, 1266/6, 158, 6, 90, false, cordX,cordY, 0);
		playerFrontSlashBackAnim = new Animation(frontSlashBackImg, 1266/6, 158, 6, 90, false, cordX, cordY, 0);
		
		playerDownSlashBackAnim = new Animation(downSlashBackImg, 965/5, 158, 5, 110 , false, cordX, cordY, 0);
		playerDownSlashFrontAnim = new Animation(downSlashFrontImg, 965/5, 158, 5, 110 , false, cordX, cordY, 0);
		
		playerUpSlashBackAnim= new Animation(upSlashBackImg, 965/5, 158, 5, 110 , false, cordX,cordY, 0);
		playerUpSlashFrontAnim = new Animation(upSlashFrontImg, 965/5, 158,5, 110 , false, cordX,cordY, 0);
		
		playerOmniSlashBackAnim = new Animation(omniSlashBackImg, 1940/5, 158, 5, 110, false, cordX, cordY, 0);
		playerOmniSlashFrontAnim = new Animation(omniSlashFrontImg, 1940/5, 158, 5, 110, false, cordX, cordY, 0);
    }
    public void Update(int numAtaque, int cordX, int cordY)
    {    	
    	this.numAtaque=numAtaque;
    	this.cordX=cordX;
    	this.cordY=cordY;
    	
    	
        playerFrontSlashFrontAnim.changeCoordinates(cordX, cordY);
        playerFrontSlashBackAnim.changeCoordinates(cordX, cordY);
                                 
        playerDownSlashBackAnim.changeCoordinates(cordX, cordY);
        playerDownSlashFrontAnim.changeCoordinates(cordX, cordY);
                                 
        playerUpSlashBackAnim.changeCoordinates(cordX, cordY);
        playerUpSlashFrontAnim.changeCoordinates(cordX, cordY);  
                                 
        playerOmniSlashBackAnim.changeCoordinates(cordX, cordY); 
        playerOmniSlashFrontAnim.changeCoordinates(cordX, cordY);
    }
    public void Draw(Graphics2D g2d, boolean mirandoFrente)
    {
    	switch(numAtaque)
    	{
    	case 0:
    		if(mirandoFrente)
    		{
    			playerFrontSlashFrontAnim.Draw(g2d);
    		}
    		else
    		{
    			playerFrontSlashBackAnim.Draw(g2d);
    		}
    	break;
    	case 1:
    		if(mirandoFrente)
    		{
    			playerUpSlashFrontAnim.Draw(g2d);
    		}
    		else
    		{
    			playerUpSlashBackAnim.Draw(g2d);
    		}
    		
    	break;
    	case 2:
    		if(mirandoFrente)
    		{
    			playerDownSlashFrontAnim.Draw(g2d);
    		}
    		else
    		{
    			playerDownSlashBackAnim.Draw(g2d);
    		}
    	
    		
    	break;    	
    	case 3:
    		if(mirandoFrente)
    		{
    			playerOmniSlashFrontAnim.Draw(g2d);
    		}
    		else
    		{
    			playerOmniSlashBackAnim.Draw(g2d);
    		}
    	break;
    	}
    }
    
    
}
