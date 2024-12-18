import java.awt.Color;

/**
 * Demonstrates the scaling (resizing) operation featured by Runigram.java. 
 * The program recieves three command-line arguments: a string representing the name
 * of the PPM file of a source image, and two integers that specify the width and the
 * height of the scaled, output image. For example, to scale/resize ironman.ppm to a width
 * of 100 pixels and a height of 900 pixels, use: java Editor2 ironman.ppm 100 900
 */
public class Editor2 {

	public static void main (String[] args){
		if(args.length!=3){
			System.out.println("Enter the image file name, width, and height.");
		}else{
		String fileName = args[0];
		int width=Integer.parseInt(args[1]);
		int height=Integer.parseInt(args[2]);
		Color[][] imageIn = Runigram.read(fileName);	
		Color[][] imageOut = changeSizeImg(imageIn, width, height);
		Runigram.setCanvas(imageIn);
		Runigram.display(imageIn); 
		Runigram.setCanvas(imageOut);
		Runigram.display(imageOut);
	
	}
	}
	public static Color[][]	changeSizeImg(Color [][] arr, int newW,int newH){
	Color[][] newImage= new Color [newW][newH];
    double raidoW=(double)arr.length/newW;
	double raidoH=(double)arr[0].length/newH;
    for (int i = 0; i < newImage.length; i++) {
	    for (int j = 0; j < newImage[0].length; j++) {
        int x= (int)(i*raidoW );
	    int y=(int)(j*raidoH );
		newImage[i][j]=arr[x][y];
        }
	}
	return  newImage;
	}
}




