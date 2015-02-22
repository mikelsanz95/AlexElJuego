package alexeljuego;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Magias {
	
	// posicionamiento del proyectil
	private int cordX;
	private int cordY;
	// 
	 
	private BufferedImage[] magiaIdleImg= new BufferedImage[5];
	private Animation[] magiaIdleAnim = new Animation[5];
	private static BufferedImage[] magiaCastedImg = new BufferedImage[5];
	private  static Animation[] magiaCastedAnim= new Animation[5];
	
	private int[] dañoMagico= new int[5];
	private int[] costoMana= new int [5]; 
	private long[] cooldown= new long[5];
	private int[] velMovX= new int[5];

	
	//carga de magias
	public Magias(int cordX,int cordY)
	{
		this.cordX=cordX;
		this.cordY=cordY;
		for(int i =0 ; i <5 ; i++)
		{
			dañoMagico[i]= i*10+50;
			costoMana [i]= i*10+30;
			cooldown[i]= Framework.secEnNanosec;
			velMovX[i]= 15;
		}
		
		
	}
	public void LoadContent()
	{
		
			
		try {
			
			URL basicSpell  = this.getClass().getResource("resources/images/basicSpellIdleImg.png");	
			magiaIdleImg[0]= ImageIO.read(basicSpell);
			URL basicSpellDmg= this.getClass().getResource("resources/images/basicSpellIdleImg.png");
			magiaCastedImg[0]=  ImageIO.read(basicSpellDmg);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("FAllo carga hechizos");
		}
		magiaIdleAnim[0]= new Animation(magiaIdleImg[0], 228/4, 58, 4, 60, true, cordX, cordY, 0);
		magiaCastedAnim[0]= new Animation(magiaIdleImg[0], 228/4, 58, 4, 60, true, cordX, cordY, 0);
		
	}
	
	// solo el orbe en la mano los proyectiles se updatean individualmente en la clase proyectil
	public void Update(int cordX, int cordY, int numSpell)
	{                     
		magiaIdleAnim[numSpell].changeCoordinates(cordX, cordY);
	}
	public void Draw(int numSpell,Graphics2D g2d)
	{
		switch (numSpell)
		{
				case 0:
				{
					
					magiaIdleAnim[numSpell].Draw(g2d);
					break;	
				}
				
				case 1:
				{
					magiaIdleAnim[numSpell].Draw(g2d);
					break;	 
				}                  
				case 2:
				{
					magiaIdleAnim[numSpell].Draw(g2d);
					break;
				}
				case 3:
				{
					magiaIdleAnim[numSpell].Draw(g2d);
					break;	
				}
				case 4:
				{
					magiaIdleAnim[numSpell].Draw(g2d);
					break;	 
				}
		
			
		
		}
		
	
	}
	public int getDañoSpell( int numSpell)
	{
		return dañoMagico[numSpell];
	}
	
	public int getCosteSpell(int numSpell)
	{
		return costoMana[numSpell];
	}
	public Animation getCastedAnim(int numSpell)
	{		
		
		return magiaCastedAnim[numSpell];
	}
	public BufferedImage getCastedImg(int numSpell)
	{
		
		return magiaCastedImg[numSpell];
	}
	public int getSpellSpeed(int numSpell)
	{
		return velMovX[numSpell];
	}
}
