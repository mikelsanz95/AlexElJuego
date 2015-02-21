package alexeljuego;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Enemigos 
{
	private int cordX;
	private int cordY;
	private int vida;
	
	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public Rectangle2D rec;
	
	private boolean timeToStrike;
	

	public boolean isTimeToStrike() {
		return timeToStrike;
	}

	public void setTimeToStrike(boolean timeToStrike) {
		this.timeToStrike = timeToStrike;
	}

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

	public int getCordDisparoX() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getCordDisparoY() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean getDireccion() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getVelmovProy() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int GetNumFrames() {
		// TODO Auto-generated method stub
		return 0;
	}

	public BufferedImage getProy() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getDaño() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
