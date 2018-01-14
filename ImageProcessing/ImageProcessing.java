package imageprocessing;

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Mateusz Galas
 */

public class ImageProcessing  extends Component {
    
    static double [][] pixels;
    
    BufferedImage output;
    
    ArrayList<Pixel> corners = new ArrayList<>();
    
    public static void main(String[] foo) {
        new ImageProcessing();
    }
    /* detect corners */
    public void identifyShape() {
       // Color myRed = new Color(255, 0, 0); // red
       // int red = myRed.getRGB();
       for(int j = 0 ; j < pixels.length ; j++){
        for(int i = 0 ; i < pixels[j].length ; i++) {
                if(pixels[j][i] == 1 ) {
                    //System.out.println(j +" " + i);
                    if(i+1 < pixels[i].length && pixels[j][i+1] == 1) {
                        /*left top corner*/
                        if(j+1 < pixels.length && pixels[j+1][i] == 1) {
                            Pixel p = new Pixel(j,i);
                            p.setRole("leftTop");
                            corners.add(p);
                            System.out.println("Left top corner detected at "+ j +" "+ i);
                        }
                        
                        if(j+1 < pixels.length && pixels[j+1][i+1] == 1) {
                            Pixel p = new Pixel(j,i+1);
                            p.setRole("leftDown");
                            corners.add(p);
                            System.out.println("Left down corner detected at "+ j +" "+ new Integer(i+1));
                        }
                        
                        if(j-1 > 0 && pixels[j-1][i] == 1){
                            Pixel p = new Pixel(j,i);
                            p.setRole("rightTop");
                            corners.add(p);
                            System.out.println("Right top corner detected at "+ j +" "+ i);
                        }
                        
                        if(j-1 > 0 && i-1 > 0 && pixels[j-1][i+1] == 1){
                            Pixel p = new Pixel(j,i+1);
                            p.setRole("rightDown");
                            corners.add(p);
                            System.out.println("Right down corner detected at "+ j +" "+ new Integer(i+1));
                        }
                    }
                }
           }
        }
       
        
    }

    private void binarization(BufferedImage image) {
      int w = image.getWidth(); //rows
      int h = image.getHeight(); //cols
  //    System.out.println(w + " " + h);
      pixels = new double[w][h];
      Color myWhite = new Color(255, 255, 255); //  white
      Color myBlack = new Color(0,0,0); // black
      int white = myWhite.getRGB();
      int black = myBlack.getRGB();
      for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
             //    System.out.println(j + " " + i);
          int pixel = image.getRGB(j, i);
          //int alpha = (pixel>>24)&0xff;
          int red = (pixel >> 16) & 0xff;
          int green = (pixel >> 8) & 0xff;
          int blue = (pixel) & 0xff;
          int avg = (red+green+blue)/3;
          //pixel = (alpha<<24) | (avg<<16) | (avg<<8) | avg;
          if(red <= 170 && green <= 170 && blue <= 170) {
             // System.out.println(j + " " + i);
            pixels[j][i] = 1;
     //       output.setRGB(j, i, white);
          } else {
            pixels[j][i] = 0;
     //       output.setRGB(j, i, black);
          }
        }
      }
    }
    
    void drawOutputImage(BufferedImage image){
      try {
          File outputfile = new File("output_image.jpg");
          ImageIO.write(image, "jpg", outputfile);
      } catch(IOException ex) {
          ex.printStackTrace();
      }
    }
    
    

    public ImageProcessing() {
      try {
        BufferedImage image = ImageIO.read(this.getClass().getResource("img_lol.jpg"));
        output = image;
        binarization(image);
        identifyShape();
        drawOutputImage(output); 
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
    }
}

