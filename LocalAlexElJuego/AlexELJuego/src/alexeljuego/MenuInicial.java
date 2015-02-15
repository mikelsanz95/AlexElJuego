package alexeljuego;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class MenuInicial 
{
	private BufferedImage fondoDelMenuImg;
	private BufferedImage opcionNewGameImg;
	private BufferedImage opcionCargar;	
	private BufferedImage opcionEndeless;
	private BufferedImage opcionAjustes;
	public int opcionSelecionada;
	private int[] posicionFlecha= new int [4];
	private BufferedImage flechaOpcion;
	private long minimoSegPorOpcion = Framework.secEnNanosec/5;
	private long tiempoEnOpcion;
	
	
	public void loadContent()
	{
	posicionFlecha[0] =-100;
	posicionFlecha[1] =  0;
	posicionFlecha[2] = 100;
	posicionFlecha[3] = 200;
	// insertar imagenes
	try{
	URL opcionNewgameUrl  = this.getClass().getResource("resources/images/menu/opcionNewGameImg.png");	
	opcionNewGameImg= ImageIO.read(opcionNewgameUrl);
	URL opcionCargarUrl= this.getClass().getResource("resources/images/menu/opcionCargarPartidaImg.png");
	opcionCargar=  ImageIO.read(opcionCargarUrl);
	URL opcionEndlessUrl= this.getClass().getResource("resources/images/menu/opcionEndlessModeImg.png");
	opcionEndeless=  ImageIO.read(opcionEndlessUrl);
	URL opcionAjustesUrl= this.getClass().getResource("resources/images/menu/opcionOpciones.png");
	opcionAjustes=  ImageIO.read(opcionAjustesUrl);
	URL flechaUrl= this.getClass().getResource("resources/images/menu/flechaOpciones.png");
	flechaOpcion=  ImageIO.read(flechaUrl);
	
	}catch(IOException e)
	{
		e.printStackTrace();
	}
	}
	public void Draw(Graphics2D g2d)	
	{	
			
		g2d.drawImage(opcionNewGameImg, Framework.anchoFrame/3, Framework.altoFrame/2-100, null);	
		g2d.drawImage(opcionCargar,Framework.anchoFrame/3, Framework.altoFrame/2-0, null);
		g2d.drawImage(opcionEndeless,Framework.anchoFrame/3, Framework.altoFrame/2+100, null);
		g2d.drawImage(opcionAjustes, Framework.anchoFrame/3, Framework.altoFrame/2+200, null);
		g2d.drawImage(flechaOpcion, Framework.anchoFrame/3-40, Framework.altoFrame/2+posicionFlecha[opcionSelecionada], null);
		
	}
	public void cambiarOpcion(long gameTime)
	{
		if(Canvas.keyboardKeyState(KeyEvent.VK_W) && gameTime- tiempoEnOpcion >= minimoSegPorOpcion)
		{
			
		opcionSelecionada--;
		tiempoEnOpcion=gameTime;
		if(opcionSelecionada<0)
		{
			opcionSelecionada=3;
		}
		}
		else if (Canvas.keyboardKeyState(KeyEvent.VK_S) && gameTime- tiempoEnOpcion >= minimoSegPorOpcion)
		{
		opcionSelecionada++;
		tiempoEnOpcion=gameTime;
		if(opcionSelecionada>3)
		{
			opcionSelecionada=0;
		}
		}
	}
	
}
