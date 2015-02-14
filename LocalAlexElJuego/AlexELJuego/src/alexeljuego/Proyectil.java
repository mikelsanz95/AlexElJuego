package alexeljuego;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;




public class Proyectil {

	private  BufferedImage proyectilImg;
	private  Animation proyectilAnim;
	
	
	private int daño;
	public int cordX;
	private int cordY;
	
	private int velMovX;	
	private boolean direccion;
	

	protected void Update()
	{
		if(direccion)
		{
			cordX+=velMovX;
			if(proyectilAnim!=null)
			{
			proyectilAnim.changeCoordinates(cordX, cordY);
			}
		}
		else
		{
			cordX-=velMovX;
			if(proyectilAnim!=null)
			{
			proyectilAnim.changeCoordinates(cordX, cordY);
			}
		}
	}
	public boolean isItLeftScreen()
	{
	    if(cordX > 0 && cordX < Framework.anchoFrame && cordY > 0 && cordY < Framework.altoFrame)
	        return false;
	    else
	        return true;
	}
	public void Draw (Graphics2D g2d)
	{	
		
			proyectilAnim.Draw(g2d);
			
	
		
	}
	public void Inicializar(int cordX, int cordY, boolean direccion,
			int daño, int velMovX, int numFrames,
			BufferedImage proyectilImg) {
		this.cordX= cordX;
		this.cordY= cordY;
		this.daño = daño;
		this.direccion=direccion;		
		this.proyectilImg= proyectilImg;
		
		this.proyectilAnim=new Animation(proyectilImg, proyectilImg.getWidth()/numFrames, proyectilImg.getHeight(), numFrames, 60, true, cordX, cordY, 0);
		
		this.velMovX=velMovX;
		
	}

	
}
