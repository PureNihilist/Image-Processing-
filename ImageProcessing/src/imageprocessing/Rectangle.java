package imageprocessing;

/**
 *
 * @author Mateusz Galas
 */
public class Rectangle extends Shape{
    
    private Pixel leftTop;
    private Pixel leftDown;
    private Pixel rightTop;
    private Pixel rightDown;
    
    public Rectangle(Pixel leftTop, Pixel leftDown, Pixel rightTop, Pixel rightDown) {
        this.leftTop = leftTop;
        this.leftDown = leftDown;
        this.rightTop = rightTop;
        this.rightDown = rightDown;
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
