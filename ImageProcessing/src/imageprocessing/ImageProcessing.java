package imageprocessing;

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Mateusz Galas
 */
public class ImageProcessing  extends Component {
    
  public static void main(String[] foo) {
    new ImageProcessing();
  }
  public static void identifyShape() {
  
      
  }
  
  
    public static double max(double[] a) { // Compute maximum value in a[]
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < a.length; i++)
            if(a[i] > max)
                max = a[i];
        return max;
    }

    public static double mean(double[] a) { // Compute the average of the values in a[]
        double sum = 0.0;
        for (int i = 0; i < a.length; i++)
            sum += a[i];
        return sum / a.length;
    }

    private void marchThroughImage(BufferedImage image) {
      int w = image.getWidth();
      int h = image.getHeight();
      
    //  System.out.println("width, height: " + w + ", " + h);
      double [][] pixels = new double[w][h];
      Color myWhite = new Color(255, 255, 255); // Color white
      Color myBlack = new Color(0,0,0); // black
      int white = myWhite.getRGB();
      int black = myBlack.getRGB();
      for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
       //   System.out.println("x,y: " + j + ", " + i);
          int pixel = image.getRGB(j, i);
          int alpha = (pixel>>24)&0xff;
          int red = (pixel >> 16) & 0xff;
          int green = (pixel >> 8) & 0xff;
          int blue = (pixel) & 0xff;
          int avg = (red+green+blue)/3;
      //    pixel = (alpha<<24) | (avg<<16) | (avg<<8) | avg;
          if(red <= 50 && green <= 50 && blue <= 50) {
          //System.out.println(red + ", " + green + ", " + blue);
            pixels[j][i] = 1;
            image.setRGB(j, i, black);
          } else {
            pixels[j][i] = 0;
            image.setRGB(j, i, white);
          }
        }
      }
      
      
      try {
          File outputfile = new File("output_image.jpg");
          ImageIO.write(image, "jpg", outputfile);
      } catch(IOException ex) {
          ex.printStackTrace();
      }
    }

    public ImageProcessing() {
      try {
        // get the BufferedImage, using the ImageIO class
        BufferedImage image = 
          ImageIO.read(this.getClass().getResource("image.jpg"));
        marchThroughImage(image);
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
    }
}

