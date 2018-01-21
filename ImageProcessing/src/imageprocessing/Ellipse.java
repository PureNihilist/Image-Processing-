package imageprocessing;

import java.util.ArrayList;

/**
 *
 * @author Mateusz Galas
 */
public class Ellipse extends Shape{

    private Pixel Center;
    private ArrayList<Pixel> ArraysOfPixels;
    private Pixel left;
    private Pixel right;
    private Pixel up;
    private Pixel down;
       
    public Ellipse(Pixel left, Pixel right, Pixel up, Pixel down, Pixel Center, ArrayList<Pixel> pixarray) {
       this.left = left;
       this.right = right;
       this.up = up;
       this.down = down;
       this.ArraysOfPixels = new ArrayList<>(pixarray);
       this.Center = Center;
    }
    
    Pixel getLeft(){
        return this.left;
    }
    
    Pixel getRight(){
        return this.right;
    }
    
    Pixel getUp(){
        return this.up;
    }
    
    Pixel getDown(){
        return this.down;
    }
    
    void setUp (Pixel arg) {
        this.up = arg;
    }
    
    void setLeft (Pixel arg) {
        this.left = arg;
    }
    
    void setRight (Pixel arg) {
        this.right = arg;
    }
    
    void setDown (Pixel arg) {
        this.down = arg;
    }
    
    public Pixel getCenter(){
        return this.Center;
    }

    public ArrayList<Pixel> getArraysOfPixels() {
        return this.ArraysOfPixels;
    }

    public void setCenter(Pixel Center){
        this.Center = Center;
    }
    
    public void setArraysOfPixels(ArrayList<Pixel> ArraysOfPixels) {
        this.ArraysOfPixels = ArraysOfPixels;
    }
    
    void print() {
        System.out.println("Ellipse");
        System.out.println("Up line center: "+this.getUp().getX() + " " +this.getUp().getY());
        System.out.println("Down line center: "+this.getDown().getX() + " " +this.getDown().getY());
        System.out.println("Right line center: "+this.getRight().getX() + " "+ this.getRight().getY());
        System.out.println("Left line center: "+this.getLeft().getX() + " "+ this.getRight().getY());
        System.out.println("Ellipse center: " + this.getCenter().getX() + " " + this.getCenter().getY());
    }
}
