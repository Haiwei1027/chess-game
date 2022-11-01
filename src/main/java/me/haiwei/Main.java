package me.haiwei;
import javax.swing.JFrame;

public class Main extends JFrame implements Runnable{

    BoardRenderer boardPanel;
    InputHandler inputHandler;

    Thread mainLoop;

    ChessBoard board;

    public static void main(String[] args) {
        new Main();
    }

    public Main(){
        super("hello ");

        setSize(1024,1024);
        setLocation(32,32);
        setUndecorated(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        new ResourceLoader();
        board = new ChessBoard(this, 8,8);
        boardPanel = new BoardRenderer(this, 1024, 1024);
        inputHandler = new InputHandler(this);
        addKeyListener(inputHandler);
        boardPanel.addMouseListener(inputHandler);
        boardPanel.addMouseMotionListener(inputHandler);
        setContentPane(boardPanel);
        setVisible(true);



        mainLoop = new Thread(this);
        mainLoop.start();
    }

	public void run() {
		while (true){
          long frameTime = System.currentTimeMillis();
          boardPanel.repaint();
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
