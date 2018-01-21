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
    ArrayList<Rectangle> rectangles = new ArrayList<>();
    ArrayList<Ellipse> ellipses = new ArrayList<>();
    BufferedImage output;
    ArrayList<Pixel> corners = new ArrayList<>();
    ArrayList<Pixel> biases = new ArrayList<>();
    
    public static void main(String[] foo) {
        new ImageProcessing();
    }
    /* detect corners */
    public void identifyShape() {
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
                        //    System.out.println("Left top corner detected at "+ j +" "+ i);
                        }
                        /*left down corner*/
                        if(j+1 < pixels.length && pixels[j+1][i+1] == 1 && pixels[j+1][i] == 0 ) {
                            Pixel p = new Pixel(j,i+1);
                            p.setRole("leftDown");
                            corners.add(p);
                        //    System.out.println("Left down corner detected at "+ j +" "+ new Integer(i+1));
                        }
                        
                        if(j-1 > 0 && pixels[j-1][i] == 1 && pixels[j-1][i+1] == 0){
                            Pixel p = new Pixel(j,i);
                            p.setRole("rightTop");
                            corners.add(p);
                        //    System.out.println("Right top corner detected at "+ j +" "+ i);
                        }
                        
                        if(j-1 > 0 && pixels[j-1][i+1] == 1 && pixels[j-1][i] == 0){
                            Pixel p = new Pixel(j,i+1);
                            p.setRole("rightDown");
                            corners.add(p);
                        //    System.out.println("Right down corner detected at "+ j +" "+ new Integer(i+1));
                        }
                       /*skok od lewego dolnego do prawego gornego */ 
                    } else if (i+1 < pixels[j].length && j-1 > 0 && pixels[j-1][i+1] == 1) {
                        if(!corners.contains(new Pixel(j-1,i))) {
                            Pixel p1 = new Pixel(j, i);
                            biases.add(p1);
                            Pixel p2 = new Pixel(j-1, i+1);
                            biases.add(p2);
                       //  System.out.println(j + " " + i);
                      //  System.out.println(new Integer(j-1) + " " + new Integer(i+1));
                        }
             
                       /* lewy gorny do prawy dolny*/
                    } else if (i+1 < pixels[j].length && j+1 < pixels.length && pixels[j+1][i+1] == 1 ) {
                       if(!corners.contains(new Pixel(j,i+1))) {
                            Pixel p1 = new Pixel(j, i);
                            biases.add(p1);
                            Pixel p2 = new Pixel(j+1, i+1);
                            biases.add(p2);
                        //System.out.println(j + " " + i);
                        //System.out.println(new Integer(j+1) + " " + new Integer(i+1));
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
            }    
            if(!processing)
                break;
        }
        System.out.println("Rozpoznano: " + rectangles.size() + " prostokąty");        
        for(Rectangle r : rectangles) {
           cleanBiasesFromCorners(r.getLeftTop());
           cleanBiasesFromCorners(r.getRightTop());
           cleanBiasesFromCorners(r.getLeftDown());
           cleanBiasesFromCorners(r.getRightDown());
        }
        Color myBlack = new Color(0,0,0); // black
        int black = myBlack.getRGB();
          for(Pixel p : biases) {
            //System.out.println(p.getX() +  " " + p.getY());
            output.setRGB(p.getX(), p.getY(), black);        
        }
          
          
          
        collectEllipses();
             // collectEllipses();
              /*
        while(true) {
            collectEllipses();
            if(biases.isEmpty())
                break;
        }  */
        System.out.println("Rozpoznano: " + ellipses.size() + " ellipsy");
//        commonFieldofTwoEllipses(ellipses.get(0), ellipses.get(1));
    }
    
    void commonFieldofTwoEllipses(Ellipse e1, Ellipse e2) {
        ArrayList<Pixel> e1Pixels = e1.getArraysOfPixels();
        ArrayList<Pixel> e2Pixels = e2.getArraysOfPixels();
        ArrayList<Pixel> commonField = new ArrayList<>();
        int commonPixels = 0;
        for(Pixel p1 : e1Pixels) {
            if(e2Pixels.contains(p1)){
                commonPixels++;
                commonField.add(p1);
            }
        }
        if(commonPixels == 0) {
            /*e1 zawiera e2 */
            if(e1.getUp().getY() < e2.getUp().getY() && e1.getDown().getY() > e1.getDown().getY() && e1.getLeft().getX() < e2.getLeft().getX() && e1.getRight().getX() > e2.getRight().getX()){
               System.out.println("e1 zaiera e2");
                
            } else if(e2.getUp().getY() < e1.getUp().getY() && e2.getDown().getY() > e1.getDown().getY() && e2.getLeft().getX() < e1.getLeft().getX() && e2.getRight().getX() > e1.getRight().getX()){
              /* e2 zawiera e1*/
              System.out.println("e2 zawiera e1");
            } else {
                System.out.println("Rozłączne");
            }
        } else if(commonPixels == 1) {
            System.out.println("Styczne");
        } else if(commonPixels > 1) {
            System.out.println("Czesc wspolna");
            for(Pixel p : commonField) {
                p.print();
            }
        }
        System.out.println("COMMON PIXELS: " +commonPixels);
    }
    
    ArrayList<Pixel> commonFieldofTwoRectangles(Rectangle rec1, Rectangle rec2){
        ArrayList<Pixel> rec1pixels = new ArrayList<>();
        ArrayList<Pixel> rec2pixels = new ArrayList<>();
        ArrayList<Pixel> rec1rec2common = new ArrayList<>();
        for(int i = rec1.getLeftTop().getX();i<=rec1.getRightTop().getX();i++){
            rec1pixels.add(new Pixel(i, rec1.getLeftTop().getY()));
        }
        for(int i = rec1.getLeftTop().getY();i<=rec1.getLeftDown().getY();i++){
            rec1pixels.add(new Pixel(rec1.getLeftTop().getX(), i));
        } 
        for(int i = rec1.getRightTop().getY();i<=rec1.getRightDown().getY();i++){
            rec1pixels.add(new Pixel(rec1.getRightTop().getX(), i));
        }
        for(int i = rec1.getLeftDown().getX();i<=rec1.getRightDown().getX();i++){
            rec1pixels.add(new Pixel(i, rec1.getLeftDown().getY()));
        }
        for(int i = rec2.getLeftTop().getX();i<=rec2.getRightTop().getX();i++){
            rec2pixels.add(new Pixel(i, rec2.getLeftTop().getY()));
        }
        for(int i = rec2.getLeftTop().getY();i<=rec2.getLeftDown().getY();i++){
            rec2pixels.add(new Pixel(rec2.getLeftTop().getX(), i));
        } 
        for(int i = rec2.getRightTop().getY();i<=rec2.getRightDown().getY();i++){
            rec2pixels.add(new Pixel(rec2.getRightTop().getX(), i));
        }
        for(int i = rec2.getLeftDown().getX();i<=rec2.getRightDown().getX();i++){
            rec2pixels.add(new Pixel(i, rec2.getLeftDown().getY()));
        }
        for(Pixel px1 : rec1pixels){
            for(Pixel px2 : rec2pixels){
                if(px1.getX()==px2.getX()&&px1.getY()==px2.getY()){
                    Pixel pixel = new Pixel(px1.getX(), px1.getY());
                    int count = 0;
                    for(Pixel pix123 : rec1rec2common){
                    if(pix123.getX()==pixel.getX()&&pix123.getY()==pixel.getY())
                        count++;
                    }
                    if(count==0){
                        rec1rec2common.add(pixel);
                    }
                }
            }
        }
        return rec1rec2common;
    }
    
    boolean isRectangleInsideAnotherRectangle(Rectangle rec1, Rectangle rec2){
        if(rec1.getLeftDown().getX()<=rec2.getLeftDown().getX()&&rec1.getLeftDown().getY()>=rec2.getLeftDown().getY()){
            if(rec1.getLeftTop().getX()<=rec2.getLeftTop().getX()&&rec1.getLeftTop().getY()<=rec2.getLeftTop().getY()){
                if(rec1.getRightDown().getX()>=rec2.getRightDown().getX()&&rec1.getRightDown().getY()>=rec2.getRightDown().getY()){
                   if(rec1.getRightTop().getX()<=rec2.getRightTop().getX()&&rec1.getRightTop().getY()<=rec2.getRightTop().getY()){
                            return true;
                    }
                }
            }
        }
        if(rec2.getLeftDown().getX()<=rec1.getLeftDown().getX()&&rec2.getLeftDown().getY()>=rec1.getLeftDown().getY()){
            if(rec2.getLeftTop().getX()<=rec1.getLeftTop().getX()&&rec2.getLeftTop().getY()<=rec1.getLeftTop().getY()){
                if(rec2.getRightDown().getX()>=rec1.getRightDown().getX()&&rec2.getRightDown().getY()>=rec1.getRightDown().getY()){
                   if(rec2.getRightTop().getX()<=rec1.getRightTop().getX()&&rec2.getRightTop().getY()<=rec1.getRightTop().getY()){
                            return true;
                    }
                }
            }
        }
        return false;
    }
    
    Pixel getLeftCenter(){
        int lowX = Integer.MAX_VALUE;
        int lowY = 0;
        int highY = Integer.MAX_VALUE;
        Pixel lowest = null;
        for(int i = 0 ; i < biases.size() ; i++){
            Pixel p = biases.get(i);
            if(p.getX() < lowX){
                lowX = p.getX(); 
                lowY = p.getY();
                lowest = new Pixel(lowX, lowY);
            }
            if(lowest != null && p.getX() == lowX && p.getY() < lowY)
                highY = p.getY(); 
            else if(p.getX() > highY) /*im above left border*/
                break;
            else if(lowest == null ) /*didnt find any pixel*/
                return null;
        }    
        return new Pixel( lowX ,(lowY - highY)/2 + highY );
    }
    
    Pixel lookForIntersectionRight(Pixel leftCenter) {
        Pixel intersection = null;
        for(int i = leftCenter.getX()+1 ; i < pixels.length ; i++) {
            if(pixels[i][leftCenter.getY()] == 1) {
                intersection = new Pixel(i, leftCenter.getY());
                break;
            }
        }
        return intersection;
    }
    
    Pixel lookForIntersectionDown(Pixel center){
        Pixel intersection = null;
        for(int i = center.getY() ; i < pixels.length ; i++) {
            if(pixels[center.getX()][i] == 1) {
                intersection = new Pixel(center.getX(),i);
                break;
            }
        }
        return intersection;
    }
    
    Pixel lookForIntersectionUp(Pixel center){
        Pixel intersection = null;
        for(int i = center.getY() ; i >= 0 ; i--) {
            if(pixels[center.getX()][i] == 1) {
                intersection = new Pixel(center.getX(),i);
                break;
            }
        }
        return intersection;
    }
    
    void collectEllipses(){
        Pixel leftCenter = getLeftCenter(); // zwraca null jak null to nie ma w ogole elips na obrazku
        leftCenter.print();
        Pixel intersection = lookForIntersectionRight(leftCenter);
        intersection.print();
        int x1 = (intersection.getX() - leftCenter.getX())/2 + leftCenter.getX();
        Pixel middle = new Pixel(x1 , leftCenter.getY());
        middle.print();
        Pixel middleIntersectionDown = lookForIntersectionDown(middle);
        Pixel middleIntersectionUp = lookForIntersectionUp(middle);
        middleIntersectionDown.print();
        middleIntersectionUp.print();
        
        int x2 = (intersection.getX() - leftCenter.getX())/4 + leftCenter.getX();
        Pixel middle2 = new Pixel(x2, leftCenter.getY());
        Pixel middleIntersectionDown2 = lookForIntersectionDown(middle2);
        Pixel middleIntersectionUp2 = lookForIntersectionUp(middle2);
        middleIntersectionDown2.print();
        middleIntersectionUp2.print();
        
        ArrayList<Pixel> ellipse_points = new ArrayList<>();
        ellipse_points.add(leftCenter);
        ellipse_points.add(middleIntersectionDown);
        ellipse_points.add(middleIntersectionUp);
        ellipse_points.add(middleIntersectionDown2);
        ellipse_points.add(middleIntersectionUp2);
        for(Pixel p : ellipse_points) {
            /*if() {
                
            } */   
        }
        
       
        /*
        int leftMostOppositeX = 0;
                int leftMostUpperY = 0;
        for(int i = 0 ; i < biases.size() ; i++) {
            Pixel p = biases.get(i);
            if(p.getX() == leftMost.getX() && p.getY() == leftMost.getY()) {
                biases.remove(p);
                continue;
            }          
            if(p.getX() == leftMost.getX()){
                leftMostUpperY = p.getY();
            }
            if(p.getY() == leftMost.getY()){
                leftMostOppositeX = p.getX();
                break;
            }
        }
        System.out.print("leftMostOpposite: " +leftMostOppositeX+" "+leftMostUpperY); 
        leftMost.print();
        
        /*wypelnianie lewej strony, mozna zrobic dla innych stron
        
        for(int j = leftMostUpperY ; j < leftMost.getY() ; j++){
            Pixel p = new Pixel(leftMost.getX(), j);
            if(pixels[leftMost.getX()][j] == 1 && !biases.contains(p)) 
               biases.add(p);
        }
        int centerX = (leftMost.getX() - leftMostOppositeX)/2;
        int centerY = (leftMost.getY() - leftMostUpperY)/2;
        Pixel ellipseCenter = new Pixel(leftMostOppositeX + centerX,leftMostUpperY + centerY);
        System.out.print("center");
        ellipseCenter.print();
        int leftMostUpY = 0;
        for(int j = ellipseCenter.getY() ; j > 0 ; j--){
            Pixel p = new Pixel(ellipseCenter.getX(), j);
            if(pixels[ellipseCenter.getX()][j] == 1 && !biases.contains(p)) {
                biases.add(p);
                leftMostUpY = p.getY();
                break;
            }
        }
        int leftMostDownY = 0;
        for(int j = ellipseCenter.getY() ; j < pixels.length ; j++){
            Pixel p = new Pixel(ellipseCenter.getX(), j);
            if(pixels[ellipseCenter.getX()][j] == 1 && !biases.contains(p)) {
                biases.add(p);
                leftMostDownY = p.getY();
                break;
            }
        }
        Pixel left = new Pixel(leftMost.getX(), ellipseCenter.getY());
        Pixel right = new Pixel(leftMostOppositeX, ellipseCenter.getY());
        Pixel up = new Pixel(ellipseCenter.getX(), leftMostUpY );    
        Pixel down = new Pixel(ellipseCenter.getX(), leftMostDownY);
        left.print(); right.print(); up.print(); down.print();
        System.out.println("center"); ellipseCenter.print();
        ArrayList<Pixel> ellipsePoints = new ArrayList<>();
        for(Pixel p : biases) {
            if(p.getX() >= leftMost.getX() && p.getX() <= leftMostOppositeX && p.getY() >= up.getY() && p.getY() <= down.getY()) 
                ellipsePoints.add(p);
        }
        for(Pixel p : ellipsePoints){
            if(biases.contains(p))
                biases.remove(p);
        }
        Ellipse ellipse = new Ellipse(left,right,up,down,ellipseCenter,ellipsePoints);
        ellipses.add(ellipse);
        */
        /*
        for(Pixel p : ellipsePoints){
         output.setRGB(p.getX(), p.getY(), black);     
        }*/
    }
    
    
    void cleanBiasesFromCorners(Pixel corner){
        corner.setY(corner.getY()+1); /*below*/
        biases.remove(searchForPixel(corner, biases));
        corner.setX(corner.getX()+1); /*right side*/
        corner.setY(corner.getY()-1);
        biases.remove(searchForPixel(corner, biases));
        corner.setX(corner.getX()-2); /*left side*/
        biases.remove(searchForPixel(corner, biases));
        corner.setX(corner.getX()+1);
        corner.setY(corner.getY()-1); /*upper*/
        biases.remove(searchForPixel(corner, biases));       
    }
    
    Pixel searchForPixel(Pixel goal, ArrayList<Pixel> pixel_list){
        Pixel found = null;
        for(Pixel p : pixel_list){
            if(p.getX() == goal.getX() && p.getY() == goal.getY()){
                found = p;
                break;
            }
        }
        return found;
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
          int red = (pixel >> 16) & 0xff;
          int green = (pixel >> 8) & 0xff;
          int blue = (pixel) & 0xff;
          if(red <= 50 && green <= 50 && blue <= 50) {
            pixels[j][i] = 1;
            output.setRGB(j, i, white);
          } else {
            pixels[j][i] = 0;
            output.setRGB(j, i, white);
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
            BufferedImage image = ImageIO.read(this.getClass().getResource("TwoE.jpg"));
            output = image;
            binarization(image);
            identifyShape();
            drawOutputImage(output); 
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

