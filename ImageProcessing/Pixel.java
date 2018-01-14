package imageprocessing;

/**
 *
 * @author Mateusz Galas
 */
public class Pixel {
    int x;
    int y;
    String role = "";
    Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    String getRole(){
        return this.role;
    }
    
    void setRole(String newRole){
        this.role = newRole;
    }
    
    int getX() {
        return this.x;
    }
    
    int getY() {
        return this.y;
    }
}
