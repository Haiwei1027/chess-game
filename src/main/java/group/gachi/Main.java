package group.gachi;
import javax.swing.*;

public class Main extends JFrame implements Runnable{

    ChessScreen chessScreen;
    BattleScreen battleScreen;

    JPanel currentScreen;
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
        battleScreen = new BattleScreen(this, 142*5, 142*5);
        setCurrentScreen(battleScreen);

        setVisible(true);



        mainLoop = new Thread(this);
        mainLoop.start();
    }

    public void setCurrentScreen(JPanel panel){
        currentScreen = panel;
        setContentPane(panel);
    }

	public void run() {
		while (true){
          long frameTime = System.currentTimeMillis();
          currentScreen.repaint();
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
