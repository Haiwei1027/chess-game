package group.gachi;

import group.gachi.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class BattleScreen extends JPanel implements MouseListener, MouseMotionListener {

    Main main;
    Battle battle;

    private int width, height, startX, startY;
    private Point mousePosition = new Point();
    private Color bgColor;

    public BattleScreen(Main main, int width, int height)
    {
        super();
        this.main = main;
        this.battle = new Battle(new Pawn(new ChessBoard(), true, "HAIWEI"), new Pawn(new ChessBoard(), false, "CAMI"));
        this.width = width;
        this.height = height;
        this.bgColor = new Color(69,77,95);

        setSize(width, height);
        addMouseListener(this);
        addMouseMotionListener(this);
        battle.paint();

    }

    public void onResize(){
        startX = (main.getWidth()-16)/2-width/2;
        startY = (main.getHeight()-38)/2-height/2;
    }

    public void paint(Graphics g){
        g.setColor(bgColor);
        g.fillRect(0,0,getWidth(),getHeight());
        onResize();
        g.drawImage(battle.image, startX, startY,width,height,null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
