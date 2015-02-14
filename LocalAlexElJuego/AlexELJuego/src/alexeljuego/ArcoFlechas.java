package alexeljuego;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ArcoFlechas {

	private BufferedImage[] flechaCastedFrontImg = new BufferedImage[5];	
	private BufferedImage[] flechaCastedBackImg = new BufferedImage[5];
	
	
	private int[] daño= new int[5];
	private int[] velMovX= new int[5];
	private int[] limiteMunicion = new int [5];
	public ArcoFlechas()
	{
	
		for(int i =0 ; i <5 ; i++)
		{
			daño[i]= i*10+50;			
			velMovX[i]= 15;
			limiteMunicion[i]  = 100;
		}
	}
	public void LoadContent()
	{
		
			
		try {
			
			URL flechaCastedBackImgUrl  = this.getClass().getResource("resources/images/flechaBasicBack.png");	
			flechaCastedBackImg[0]= ImageIO.read(flechaCastedBackImgUrl);
			URL flechaCastedFrontImgUrl= this.getClass().getResource("resources/images/flechaBasicFront.png");
			flechaCastedFrontImg[0]=  ImageIO.read(flechaCastedFrontImgUrl);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("FAllo carga flechas");
		}
		
	}
	public BufferedImage getFlechaCastImg(int numFlecha, boolean direccion)
	{	
		if(direccion)
		{
			return flechaCastedFrontImg[numFlecha];
		}
		else
		{
			return flechaCastedBackImg[numFlecha];
		}
		
		
		
	}
	public int getFlechaSpeed(int numFlecha)
	{
		return velMovX[numFlecha];
	}
}
