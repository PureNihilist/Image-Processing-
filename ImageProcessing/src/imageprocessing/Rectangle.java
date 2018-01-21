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
    
    void moveRectangleWithVector(int x, int y){
        this.leftDown.addX(x);
        this.leftDown.addY(y);
        this.leftTop.addX(x);
        this.leftTop.addY(y);
        this.rightDown.addX(x);
        this.rightDown.addY(y);
        this.rightTop.addX(x);
        this.rightTop.addY(y);
    }
    
    void print(){
        System.out.println("Rectangle");
        System.out.println("Left top corner: "+leftTop.getX() + " " +leftTop.getY());
        System.out.println("Left down corner: "+leftDown.getX() + " " +leftDown.getY());
        System.out.println("Right top corner: "+rightTop.getX() + " " +rightTop.getY());
        System.out.println("Right down corner: "+rightDown.getX() + " " +rightDown.getY());
    }
}
