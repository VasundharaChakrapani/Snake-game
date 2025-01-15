import javax.swing.*;


public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth=600;
        int boardHieght=boardWidth;
        
        JFrame frame=new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardWidth,boardHieght);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SnakeGame snakeGame=new SnakeGame(boardWidth, boardHieght);
        frame.add(snakeGame);
        frame.pack();//panel occupies entire 600x600 within frame
        snakeGame.requestFocus();
    }
}