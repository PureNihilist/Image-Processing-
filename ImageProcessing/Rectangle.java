package imageprocessing;

/**
 *
 * @author Mateusz Galas
 */
public class Rectangle extends Shape{
    
    Pixel leftTop = null;
    Pixel leftDown = null;
    Pixel rightTop = null;
    Pixel rightDown = null;
    
    public Rectangle() {
       
    }
    
    void setLeftTop (Pixel arg) {
        this.leftTop = arg;
    }
    
    void setLeftDown (Pixel arg) {
        this.leftDown = arg;
    }
    
    void setRightTop (Pixel arg) {
        this.rightTop = arg;
    }
    
    void setRightDown (Pixel arg) {
        this.rightDown = arg;
    }
   
    Pixel getLeftTop () {
        return this.leftTop;
    }
    
    Pixel getLeftDown () {
        return this.leftDown;
    }
    
    Pixel getRightTop () {
        return this.rightTop;
    }
    
    Pixel getRightDown () {
        return this.rightDown;
    }
    
}
