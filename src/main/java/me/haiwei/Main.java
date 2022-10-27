package me.haiwei;
import javax.swing.JFrame;

public class Main extends JFrame implements Runnable{

    Renderer panel;
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
        panel = new Renderer(board, 1024, 1024);
        inputHandler = new InputHandler(this);
        addKeyListener(inputHandler);
        panel.addMouseListener(inputHandler);
        panel.addMouseMotionListener(inputHandler);
        setContentPane(panel);
        setVisible(true);



        mainLoop = new Thread(this);
        mainLoop.start();
    }

	public void run() {
		while (true){
          long frameTime = System.currentTimeMillis();
          panel.repaint();
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
