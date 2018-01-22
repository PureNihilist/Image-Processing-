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
    
    void moveEllipseWithVector(int x, int y){
        //System.out.println(this.left.getX());
        this.left.setX(this.left.getX()+x);
        //System.out.println(this.left.getX());
        this.left.setY(this.left.getY()+y);
        this.up.setX(this.up.getX()+x);
        this.up.setY(this.up.getY()+y);
        this.right.setX(this.right.getX()+x);
        this.right.setY(this.right.getY()+y);
        this.down.setX(this.down.getX()+x);
        this.down.setY(this.down.getY()+y);
        this.Center.setX(this.Center.getX()+x);
        this.Center.setY(this.Center.getY()+y);
        for(Pixel pix : this.ArraysOfPixels){
            if((pix.getX()!=this.left.getX())&&pix.getY()!=this.left.getY()){
                if((pix.getX()!=this.right.getX())&&pix.getY()!=this.right.getY()){
                    if((pix.getX()!=this.up.getX())&&pix.getY()!=this.up.getY()){
                        if((pix.getX()!=this.down.getX())&&pix.getY()!=this.down.getY()){
                            if((pix.getX()!=this.Center.getX())&&pix.getY()!=this.Center.getY()){
                                pix.setX(pix.getX()+x);
                                pix.setY(pix.getY()+y);
                            }
                        }
                    }
                }
        }
        }
        //System.out.println(this.left.getX());
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
