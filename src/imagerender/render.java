package imagerender;

import java.awt.Color;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.*;
import javax.swing.JFileChooser;
import java.awt.image.BufferedImage;

public class render {
public static File file;
private static int[][] grid;
private int x;
private int y;

public render(int x, int y)
{
	this.x = x;
	this.y = y;
	grid = new int[x][y];
}
private boolean besideEdgeColor(int x, int y)
{
	if(x == (this.x)-1 || y == (this.y-1) || x == 0 || y == 0)
	{
		return false;
	}
	else if(grid[x-1][y] == 1 || grid[x+1][y] == 1 || grid[x][y-1] == 1 || grid[x][y+1] == 1)
	{
		return true;
	}
	return false;
}

private  boolean besideColor(int x, int y)
{
	if(x == (this.x)-1 || y == (this.y-1) || x == 0 || y == 0)
	{
		return false;
	}
	else if(grid[x-1][y] == 0 || grid[x+1][y] == 0 || grid[x][y-1] == 0 || grid[x][y+1] == 0)
	{
		return true;
	}
	return false;
}

public BufferedImage renderFrom(File png)
{
	BufferedImage image = null;
	File file1 = png;
	try {	        	        
		image = javax.imageio.ImageIO.read(file1);

		this.refreshGrid(image);
		Color black = Color.black;
		Color red = Color.red;
		Color orange = Color.orange;
		Color yellow = Color.yellow;
		Color blue = Color.blue;
		Color purple = new Color(142, 68, 173);
		Color darkRed = new Color(169, 50, 38);
		Color green = new Color(174, 213, 129);
		Color skyBlue = new Color(93, 173, 226);
		
		for(int y=0;y<this.y;y++)
		{
			for(int x=0;x<this.x;x++)
			{
				if(grid[x][y] == 1 && besideColor(x,y))
				{
					image.setRGB(x, y, black.getRGB());
				}	
			}
		}
		//this.colorChange(image);
		//this.refreshGrid(image);
		
		/*this.attachColor(image, purple);
		this.refreshGrid(image);
		this.attachColor(image, black);*/
		//this.refreshGrid(image);
		//this.attachColor(image, yellow);
		//this.refreshGrid(image);
		//this.attachColor(image, green);
		//this.refreshGrid(image);
		//this.attachColor(image, skyBlue);
		//this.refreshGrid(image);
		//this.attachColor(image, blue);
		//this.refreshGrid(image);
		//this.attachColor(image, purple);
		
    	} 
    catch(IOException e) {
    	System.out.println("Unable to process file: " + e );
    	}
	return image;
}
private void attachColor(BufferedImage image, Color color)
{
	for(int y=0;y<this.y;y++)
	{
		for(int x=0;x<this.x;x++)
		{
			if(grid[x][y] == 0 && besideEdgeColor(x,y))
			{
				image.setRGB(x, y, color.getRGB());
			}	
		}
	}
}

private void colorChange(BufferedImage image)
{
	for(int y=0;y<this.y;y++)
	{
		for(int x=0;x<this.x;x++)
		{
			if(grid[x][y] == 1)
			{
				Color white = Color.white;
				Color old = new Color(image.getRGB(x, y));
				//image.setRGB(x,y,white.getRGB());	
				Color newColor = new Color(old.getRed(),old.getBlue(),old.getGreen());
				image.setRGB(y, x, newColor.getRGB());
				
			}	
		}
	}
}

private void refreshGrid(BufferedImage image)
{
	for(int y=0;y<this.y;y++)
	{
		for(int x=0;x<this.x;x++)
		{
			if(image.getRGB(x,y) == 0)
			{
				grid[x][y] = 0;
				System.out.print("0");
				if(x == this.x-1)
					System.out.print("\n");
			}
			else
			{
				grid[x][y] = 1;
				System.out.print("1");
			}
				
			//System.out.print(grid[x][y] + "\n");
		}
	}
	
}
public static void main(String[] args) throws IOException
	{
	 /*JFileChooser fc = new JFileChooser();
	 int returnVal =  fc.showOpenDialog(new Frame());
	 if(returnVal == JFileChooser.APPROVE_OPTION) 
	 {		
		 file = fc.getSelectedFile();
	 } 
	 else			
		System.out.println("Action canceled!");
	*/
	 Files.walk(Paths.get("./source")).forEach(filePath -> {
		    if (Files.isRegularFile(filePath)) 
		    {
		    	render renderer = new render(35,35);
		    	File file = new File(filePath.toString());
		    	BufferedImage image = renderer.renderFrom(file);
		   	 	File file1 = new File(filePath.toString());
		   	 	try {
		   	 		ImageIO.write(image, "png", file1);
		   	 	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		   	 	}
		    }
		});
	}

}
