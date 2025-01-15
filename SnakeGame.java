import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

//jpanel class acts as container for gui components
public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    int boardWidth;
    int boardHieght;
    int tileSize=25;
    

    //snake
    Tile snakeHead;
    ArrayList<Tile> snakebody;

    //food
    Tile food;
    Random random;

    //game logic
    Timer gameloop;
    int velocityX;
    int velocityY;
    boolean gameOver=false;

    //only SnakeGame can access it
    private class Tile{
        int x;
        int y;
        Tile(int x,int y){
            this.x=x;
            this.y=y;
        }
    }

    SnakeGame( int boardWidth,int boardHieght){
        this.boardWidth=boardWidth;
        this.boardHieght=boardHieght;
        setPreferredSize(new Dimension(this.boardWidth,this.boardHieght));//dimentions for container
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);


        snakeHead=new Tile(5,5);
        food=new Tile(10,10);
        snakebody=new ArrayList<Tile>();
        random=new Random();
        placeFood();
        gameloop =new Timer(100,this);
        gameloop.start();
        velocityX=0;
        velocityY=1; //goes downwards

    }

    //g is obj of graphics class used for drawing
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //grid
        // for(int i=0; i<boardWidth/tileSize; i++){
        //     //(x1,y1,x2,y2)
        //     g.drawLine(i*tileSize,0,i*tileSize,boardHieght); //vertical lines
        //     g.drawLine(0, i*tileSize, boardWidth, i*tileSize); //horizontal lines
        // }

        //food
        g.setColor(Color.red);
        g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize,true);

        //snake head
        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize ,true);

        //snake body
        for(int i=0;i<snakebody.size();i++){
            Tile snakepart =snakebody.get(i);
            g.fill3DRect(snakepart.x*tileSize, snakepart.y*tileSize, tileSize, tileSize,true);
        }

        //score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if(gameOver){
            g.setColor(Color.RED);
            g.drawString("GAME OVER.   SCORE:  "+String.valueOf(snakebody.size()),tileSize-16,tileSize);
        }
        else{
            g.drawString("SCORE: " +String.valueOf(snakebody.size()), tileSize-16, tileSize);
        }
    }

    public void placeFood(){
        food.x=random.nextInt(boardWidth/tileSize); //random x pos for food (0-24)
        food.y=random.nextInt(boardHieght/tileSize); //random y pos for food (0-24) 
    }

    public boolean collision (Tile tile1,Tile tile2){
        return tile1.x==tile2.x && tile1.y==tile2.y; //two tiles collision

    }
    public void move(){
        //eat food
        if(collision(snakeHead,food)){
            snakebody.add(new Tile(food.x,food.y));
            placeFood();
        }

        //snake body
        for(int i=snakebody.size()-1;i>=0;i--){
            Tile snakepart=snakebody.get(i);
            //first snakepart before head
            if(i==0){
                snakepart.x=snakeHead.x; // Place it where the head was
                snakepart.y=snakeHead.y;
            }
            //snakebody as more tha 1 tile
            else{
                Tile prevsnakepart=snakebody.get(i-1); //get prev tile
                snakepart.x=prevsnakepart.x; // place it where prev tile was
                snakepart.y=prevsnakepart.y;

            }
        }

        //snake head
        snakeHead.x+=velocityX;
        snakeHead.y+=velocityY;

        //game over conditions
        for(int i=0; i<snakebody.size();i++){
            Tile snakepart= snakebody.get(i);
            //collide with snake head
            if(collision(snakeHead, snakepart)){
                gameOver=true;
            }
        }

        if(snakeHead.x*tileSize<0 || snakeHead.x*tileSize>boardWidth ||
           snakeHead.y*tileSize<0 || snakeHead.x*tileSize>boardHieght)   //x and y pos of head is past any walls
        {
            gameOver=true;
        }

        } 

        
    

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();//calls draw over and over again
        if(gameOver){
        gameloop.stop(); 
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        //pressed up arrow
        //snake must not move in opp directions otherwise wud collide with bod
        if(e.getKeyCode()==KeyEvent.VK_UP && velocityY!=-1){
            velocityX=0;
            velocityY=-1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityY!=-1){
            velocityX=0;
            velocityY=1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT && velocityX!=1){
            velocityX=-1;
            velocityY=0;
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT &&velocityX!=-1){
            velocityX=1;
            velocityY=0;
        }
    }



    //dont need

    @Override
    public void keyTyped(KeyEvent e) {}          

    @Override
    public void keyReleased(KeyEvent e) {}
    
    
}
