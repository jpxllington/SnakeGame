import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * 0.666);
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int SNAKE_WIDTH = 10;
    static final int SNAKE_LENGTH = 10;
    static final int FOOD_WIDTH = 10;
    static final int FOOD_HEIGHT = 10;

    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Snake snake;
    Score score;
    Food food;
    Boolean running = true;


    GamePanel(){
        newSnake();
        newFood();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newSnake(){
        snake = new Snake((GAME_WIDTH/2)-(SNAKE_WIDTH/2), (GAME_HEIGHT/2)-(SNAKE_LENGTH/2), SNAKE_WIDTH, SNAKE_LENGTH);
    }

    public void newFood(){
        random = new Random();
        int xLocation = (int)(random.nextFloat()*GAME_WIDTH);
        int yLocation = (int)(random.nextDouble()*GAME_HEIGHT);
        food = new Food(xLocation, yLocation, FOOD_WIDTH, FOOD_HEIGHT);
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }

    public void draw(Graphics g) {
        if (running){
            snake.draw(g);
            score.draw(g);
            food.draw(g);

        } else {
            endGame(g);
        }
    }

    public void move(){
        snake.move();
        System.out.println("snake x: "+ snake.x[0] + " y: " + snake.y[0]);
        System.out.println("Food x: " + food.xLocation + " y: " + food.yLocation);
    }

    public void checkCollision(){
        if (((snake.x[0]>=food.xLocation-10)&&(snake.x[0]<=food.xLocation+10))&&((snake.y[0]>=food.yLocation-10)&&(snake.y[0] <= food.yLocation +10))){
            snake.grow();
            newFood();
            score.gameScore ++;

        }
        if (snake.components>1){
            for (int i = 1; i< snake.components;i++){
                if((snake.x[0]==snake.x[i])&&(snake.y[0]==snake.y[i])){
                    running = false;
                }
            }
        }

        if ((snake.x[0]<=0 || snake.x[0]>=GAME_WIDTH)||(snake.y[0]<=0||snake.y[0]>= GAME_HEIGHT)){
            running = false;
        }
    }

    public void endGame(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Consolas", Font.BOLD,100));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(GAME_WIDTH -  metrics.stringWidth("Game Over"))/2, GAME_HEIGHT/2);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.BOLD, 50));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score : " + score.gameScore, (GAME_WIDTH-metrics2.stringWidth("Score : " + score.gameScore))/2, (GAME_HEIGHT/4)*3);

    }
    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 25.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            if(delta>=1){
                move();
                checkCollision();
                repaint();
                delta--;

            }
        }
       
    }
    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            snake.keyPressed(e);
        }
    }
}