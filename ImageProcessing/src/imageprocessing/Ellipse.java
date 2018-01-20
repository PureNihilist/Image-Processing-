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
}
