package group.achi;
import javax.swing.JFrame;

public class Main extends JFrame implements Runnable{

    ChessScreen chessScreen;
    Thread mainLoop;

    public static void main(String[] args) {
        new Main();
    }

    public Main(){
        super("hello ");

        setSize(142*6,142*6);
        setLocation(16,16);
        setUndecorated(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        new ResourceLoader();

        chessScreen = new ChessScreen(this,142*5, 142*5);
        setContentPane(chessScreen);
        setVisible(true);



        mainLoop = new Thread(this);
        mainLoop.start();
    }

	public void run() {
		while (true){
          long frameTime = System.currentTimeMillis();
          chessScreen.repaint();
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
