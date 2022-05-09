import java.awt.*;


public class Food extends Rectangle{
    
    int xLocation;
    int yLocation;

    Food(int x, int y, int width, int height){
        super(x,y,width, height);
        xLocation =x;
        yLocation =y;
    }

    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
    }
}