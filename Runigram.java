import java.awt.Color;
import java.awt.Image;

/** A library of image processing functions. */
public class Runigram {
	
	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);
		
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();//the lable
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		for(int i=0;i<numRows;i++){
			for(int j=0;j<numCols;j++){
				int r=in.readInt();
				int g=in.readInt();
				int b=in.readInt();
				image[i][j]= new Color(r,g,b);
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
	for(int i=0;i<image.length;i++){
		for(int j=0;j<image[i].length;j++){
			System.out.print(image[i][j] +" ");
		}
		System.err.println();
	}
	}
	 
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		int numR=image.length;
		int numC=image[0].length;
    Color [][] flippImage= new Color [numR][numC];
	for(int j=0;j<numC;j++){
		for(int i=0;i<numR;i++)
		{
			flippImage[i][j]=image[i][numC-1-j];
		}
	}		
		return flippImage;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		int numR= image.length;
		int numC=image[0].length;
	Color [][] flipImage= new Color[numR][numC];
	for(int j=0;j<0;j++){
	   for(int i=0;i<numR;i++){
			flipImage[i][j]=image[numR-1-i][j];
		}
	}

		return flipImage;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		 int r=pixel.getRed();
		 int g=pixel.getGreen();
		 int b=pixel.getBlue();
		 int lum=(int)(0.299 * r + 0.587 * g + 0.114 * b);
		 Color newPixel =new Color(lum,lum,lum);
		return newPixel; 
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color [][] grayVarision= new Color[image.length][image[0].length];
		for(int i=0;i<grayVarision.length;i++){
			for (int j=0;j <grayVarision[0].length;j++){
			grayVarision[i][j]= luminance(image[i][j]);
			}	
			}
			return grayVarision;
		}
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		int w= image.length;
		int h=image[0].length;
		Color [][] newImage= new Color[width][height];
		double ratiow=w/width;
		double ratioh=h/height;
		for (int i = 0; i <width; i++) {
		  for (int j = 0; j < height; j++) {
				   int origX = (int) (i * ratiow);
				   int origY = (int) (j * ratioh);
				   int r = image[origX][origY].getRed();
				   int g = image[origX][origY].getGreen();
				   int b = image[origX][origY].getBlue();
				   Color pixel = new Color(r, g, b);
				   newImage[i][j] = pixel;
			
		 }	
		}
		return newImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		if( alpha>1.0)alpha=1.0;
		if( alpha<0.0)alpha=0.0;
		int r=calculatedV(c1.getRed(), c2.getRed(), alpha);
		int g=calculatedV(c1.getGreen(), c2.getGreen(), alpha);
		int b=calculatedV(c1.getBlue(), c2.getBlue(), alpha);
		Color blendColor= new Color(r,g,b);
		 return blendColor;
	}
	public static int calculatedV(int v1, int v2, double alpha){
		return (int)(alpha * v1 + (1 - alpha) * v2);

	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		Color[][] blend = new Color[image1.length][image1[0].length];
		for (int i = 0; i < blend.length; i++) {
			for (int j = 0; j < blend[0].length; j++) {
				blend[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}
 		return blend;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		Color[][] morph = new Color[source.length][source[0].length];
		if ((source.length != target.length) || (source[0].length != target[0].length)) {
			target = scaled(target, source[0].length, source.length);
		}
		for (int i = 0; i <= n; i++) {
			double alpha = (double)(n-i)/n;
			morph = blend(source, target, alpha);
			Runigram.display(morph);
			StdDraw.pause(500);
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

