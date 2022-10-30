package me.haiwei;
import javax.swing.JFrame;
import java.awt.*;

public class Main extends JFrame implements Runnable{

    BoardRenderer boardRenderer;
    InputHandler inputHandler;

    Thread mainLoop;

    ChessBoard board;

    public static void main(String[] args) {
        new Main();
    }

    public Main(){
        super("hello ");

        setSize(142 * 7,142 * 7);
        setLocation(16,16);
        setUndecorated(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        new ResourceLoader();
        board = new ChessBoard(this, 8,8);
        boardRenderer = new BoardRenderer(this, 142*6, 142*6);
        inputHandler = new InputHandler(this);
        addKeyListener(inputHandler);
        boardRenderer.addMouseListener(inputHandler);
        boardRenderer.addMouseMotionListener(inputHandler);
        setContentPane(boardRenderer);
        setVisible(true);



        mainLoop = new Thread(this);
        mainLoop.start();
    }

    public void run() {
		while (true){
          long frameTime = System.currentTimeMillis();
          boardRenderer.repaint();
          frameTime = System.currentTimeMillis() - frameTime;
          try{
              if (frameTime < 8) {
                  Thread.sleep(8 - frameTime);
              }
          }
          catch(InterruptedException e){

          }
      }
		
	}
}
