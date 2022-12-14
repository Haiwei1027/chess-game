package group.gachi;
import group.gachi.pieces.ChessPiece;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame implements Runnable{

    static Main instance;
    ChessScreen chessScreen;
    BattleScreen battleScreen;
    public Battle battle;
    JPanel currentScreen;
    Thread mainLoop;
    public Boolean inBattle;

    public static void main(String[] args) {
        instance = new Main();
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
        battleScreen = new BattleScreen(this, 142*5, 142*5, null);
        setCurrentScreen(chessScreen);

        setVisible(true);

        mainLoop = new Thread(this);
        mainLoop.start();
    }

    public static void enterBattle(Battle battle) {
        instance.inBattle = true;
        instance.battleScreen.newBattle(battle);
        instance.setCurrentScreen(instance.battleScreen);
        instance.repaint();
    }

    public static void exitBattle(ChessPiece winner) {
        exitBattle(winner, null);
    }

    public static void exitBattle(ChessPiece defender, ChessPiece attacker) {
        instance.chessScreen.getBoard().battleFinished(defender,attacker);
        instance.setCurrentScreen(instance.chessScreen);
        instance.repaint();
    }

    public void setCurrentScreen(JPanel panel) {
        currentScreen = panel;
        setContentPane(panel);
        setVisible(true);
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
