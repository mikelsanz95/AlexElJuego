package alexeljuego;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Enemigos 
{
	private int cordX;
	private int cordY;
	
	public Rectangle2D rec;
	
	
	public int getCordX() {
		return cordX;
	}

	public void setCordX(int cordX) {
		this.cordX = cordX;
	}

	public int getCordY() {
		return cordY;
	}

	public void setCordY(int cordY) {
		this.cordY = cordY;
	}

	public void Draw(Graphics2D g2d )
	{}
	
	public void Update(long gameTime, PlayerAlex player)
	{}
	
	public Rectangle2D getRec2D()
	{
		return rec;
	}
	public void tocaAcercarse()
	{}
	
}
