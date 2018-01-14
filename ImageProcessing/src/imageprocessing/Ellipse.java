package imageprocessing;

import java.util.ArrayList;

/**
 *
 * @author Mateusz Galas
 */
public class Ellipse extends Shape{

    Pixel Center;
    ArrayList<Pixel> ArraysOfPixels;
    
    public Ellipse(Pixel Center, ArrayList<Pixel> pixarray) {
       this.ArraysOfPixels = new ArrayList<>(pixarray);
       this.Center = Center;
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
