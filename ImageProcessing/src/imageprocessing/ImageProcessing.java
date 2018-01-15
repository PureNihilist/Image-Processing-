package imageprocessing;

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.util.Vector;

/**
 *
 * @author Mateusz Galas
 */

public class ImageProcessing  extends Component {
    
    static double [][] pixels;
    
    ArrayList<Rectangle> rectangles = new ArrayList<>();
    
    BufferedImage output;
    
    ArrayList<Pixel> corners = new ArrayList<>();
    ArrayList<Pixel> biases = new ArrayList<>();
    
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
                    if(i+1 < pixels[j].length && pixels[j][i+1] == 1) {
                        /*left top corner*/
                        if(j+1 < pixels.length && pixels[j+1][i] == 1 && pixels[j+1][i+1] == 0) {
                            Pixel p = new Pixel(j,i);
                            p.setRole("leftTop");
                            corners.add(p);
                            System.out.println("Left top corner detected at "+ j +" "+ i);
                        }
                        /*left down corner*/
                        if(j+1 < pixels.length && pixels[j+1][i+1] == 1 && pixels[j+1][i] == 0 ) {
                            Pixel p = new Pixel(j,i+1);
                            p.setRole("leftDown");
                            corners.add(p);
                            System.out.println("Left down corner detected at "+ j +" "+ new Integer(i+1));
                        }
                        
                        if(j-1 > 0 && pixels[j-1][i] == 1 && pixels[j-1][i+1] == 0){
                            Pixel p = new Pixel(j,i);
                            p.setRole("rightTop");
                            corners.add(p);
                            System.out.println("Right top corner detected at "+ j +" "+ i);
                        }
                        
                        if(j-1 > 0 && pixels[j-1][i+1] == 1 && pixels[j-1][i] == 0){
                            Pixel p = new Pixel(j,i+1);
                            p.setRole("rightDown");
                            corners.add(p);
                            System.out.println("Right down corner detected at "+ j +" "+ new Integer(i+1));
                        }
                       /*skok od lewego dolnego do prawego gornego */ 
                    } else if (i+1 < pixels[j].length && j-1 > 0 && pixels[j-1][i+1] == 1) {
                        if(!corners.contains(new Pixel(j-1,i))) {
                            Pixel p1 = new Pixel(j, i);
                            p1.setRole("biasUpFirst");
                            biases.add(p1);
                            Pixel p2 = new Pixel(j-1, i+1);
                            p2.setRole("biasUpSecond");
                            biases.add(p2);
                       //  System.out.println(j + " " + i);
                      //  System.out.println(new Integer(j-1) + " " + new Integer(i+1));
                        }
             
                       /* lewy gorny do prawy dolny*/
                    } else if (i+1 < pixels[j].length && j+1 < pixels.length && pixels[j+1][i+1] == 1 ) {
                       if(!corners.contains(new Pixel(j,i+1))) {
                        Pixel p1 = new Pixel(j, i);
                        p1.setRole("biasDownFirst");
                        biases.add(p1);
                        Pixel p2 = new Pixel(j+1, i+1);
                        p2.setRole("biasDownSecond");
                        biases.add(p2);
                       //  System.out.println(j + " " + i);
                       // System.out.println(new Integer(j+1) + " " + new Integer(i+1));
                       }
                
                    }
                }
           }
       }
        boolean processing;
        while(true){
            processing = false;
            Pixel [] recPixels = new Pixel[4];
            
            for (Pixel leftTop : corners) {
                if(leftTop.getRole().equals("leftTop")){    
                    for (Pixel leftDown : corners) {
                        if(leftDown.getRole().equals("leftDown") && leftTop.getY() < leftDown.getY() && leftTop.getX() == leftDown.getX()){
                            recPixels[0] = leftTop;
                            recPixels[1] = leftDown;
                            processing = true;
                            break;
                        }
                    }
                }
            }
            if(corners.remove(recPixels[0]) &&  corners.remove(recPixels[1])){
                for (Pixel rightTop : corners) {
                //    System.out.println("loop" + rightTop.getX() + " " + rightTop.getY() + " " + rightTop.getRole());
                    if(rightTop.getRole().equals("rightTop") && rightTop.getY() == recPixels[0].getY()){    
                        for (Pixel rightDown : corners) {
                            if(rightDown.getRole().equals("rightDown") && rightTop.getY() < rightDown.getY() && rightTop.getX() == rightDown.getX() && rightDown.getY() == recPixels[1].getY()){
                                recPixels[2] = rightTop;
                                recPixels[3] = rightDown;
                                processing = true;
                                break;
                            }
                        }
                    }
                }
                corners.remove(recPixels[2]);
                corners.remove(recPixels[3]);
                Rectangle rectangle = new Rectangle(recPixels[0], recPixels[1], recPixels[2], recPixels[3]);
                rectangles.add(rectangle);
                System.out.println("rectangle");
                System.out.println("leftTop" + rectangle.getLeftTop().getX() + " " + rectangle.getLeftTop().getY()); 
                System.out.println("leftDown" + rectangle.getLeftDown().getX() + " " + rectangle.getLeftDown().getY()); 
                System.out.println("rightTop" + rectangle.getRightTop().getX() + " " + rectangle.getRightTop().getY()); 
                System.out.println("rightDown" + rectangle.getRightDown().getX() + " " + rectangle.getRightDown().getY()); 
            }
            
            if(!processing)
                break;
        }
        System.out.println("Rozpoznano: " + rectangles.size() + " prostokątów");
        
        
        while(biases.size()>0){
            int right_range;
            int left_range;
            ArrayList<Pixel> EllipsePixels = new ArrayList<>();
            if(biases.get(0).getRole().equals("biasUpFirst")){
                EllipsePixels.add(biases.get(0));
                left_range = biases.get(0).getX();
                biases.remove(0);
                if(biases.get(1).getRole().equals("biasUpSecond")){
                    EllipsePixels.add(biases.get(1));
                    if(biases.get(2).getRole().equals("biasDownFirst")){
                    int range = (biases.get(2).getX() - biases.get(1).getX());
                    for(int xi = 1;xi < range;xi++){
                            Pixel pix1 = new Pixel(biases.get(1).getX()+xi, biases.get(1).getY());
                            EllipsePixels.add(pix1);
                        }
                    EllipsePixels.add(biases.get(2));
                    biases.remove(2);
                    EllipsePixels.add(biases.get(3));
                    right_range = biases.get(3).getX()+1;
                    biases.remove(3);
                    }
                }
            }
        }
    }

    private void binarization(BufferedImage image) {
      int w = image.getWidth(); //rows
      int h = image.getHeight(); //cols
      pixels = new double[w][h];
      Color myWhite = new Color(255, 255, 255); //  white
      Color myBlack = new Color(0,0,0); // black
      int white = myWhite.getRGB();
      int black = myBlack.getRGB();
      for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
          int pixel = image.getRGB(j, i);
          //int alpha = (pixel>>24)&0xff;
          int red = (pixel >> 16) & 0xff;
          int green = (pixel >> 8) & 0xff;
          int blue = (pixel) & 0xff;
          int avg = (red+green+blue)/3;
          //pixel = (alpha<<24) | (avg<<16) | (avg<<8) | avg;
          if(red <= 170 && green <= 170 && blue <= 170) {
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
          ex.printStackTrace();+
      }
    }
    
    

    public ImageProcessing() {
      try {
        BufferedImage image = ImageIO.read(this.getClass().getResource("img_two.jpg"));
        output = image;
        binarization(image);
        identifyShape();
        drawOutputImage(output); 
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
    }
}

