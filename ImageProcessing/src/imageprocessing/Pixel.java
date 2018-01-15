package imageprocessing;

/**
 *
 * @author Mateusz Galas
 */
public class Pixel {
    private int x;
    private int y;
    String role = "";
    Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    String getRole(){
        return role;
    }
    
    void setRole(String newRole){
        role = newRole;
    }
    
    int getX() {
        return x;
    }
    
    int getY(){
        return y;
    }
}
