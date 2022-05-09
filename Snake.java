import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class Snake extends Rectangle {
    
    Random random;
    int direction;
    int speed = 2;
    int components = 1;
    final int x[] = new int[100];
    final int y[] = new int[100];


    Snake(int xLocation, int yLocation, int width, int height){
        super(xLocation, yLocation, width, height);
        x[0]=xLocation; 
        y[0]=yLocation;
        setDirection(0);


    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_UP){
            if(direction != 2){
                setDirection(0);
                move();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            if(direction !=0){
                setDirection(2);
                move();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(direction != 1){
                setDirection(3);
                move();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(direction != 3){
                setDirection(1);
                move();
            }
        }
    }

    public void setDirection(int Direction){
        direction = Direction;
    }

  

    public void grow(){
        components++;
        System.out.println("Parts :" + components);
    }

    public void move(){
        for (int i = components; i>0; i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }

        switch(direction){
            case 0:
                y[0]=y[0]-8;
                break;
            case 1:
                x[0]=x[0]+8;
                break;
            case 2:
                y[0]=y[0]+8;
                break;
            case 3:
                x[0]=x[0]-8;
                break;
        }
       
    }

    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        for (int i =0; i< components;i++){
            System.out.println(x[i]);
            System.out.println(y[i]);
            g.fillRect(x[i], y[i], width, height);

        }
    }
}