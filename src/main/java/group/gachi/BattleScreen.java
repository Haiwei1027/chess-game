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
        this.bgColor = new Color(230,234,215);

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

        g.drawImage(ResourceLoader.instance.getPieceRaw(battle.getPiece2().getId(), battle.getPiece2().isWhite()), (startX + 72 * 5) + 45, (startY + 18 * 5) + 23,16*3*4,16*3*4, null);
        g.drawImage(ResourceLoader.instance.getWeaponImage(battle.getPiece2().getId()), (startX + 72 * 5) + 45, (startY + 18 * 5) + 23,16*3*4,16*3*4, null);

        g.drawImage(ResourceLoader.instance.getPieceRaw(battle.getPiece1().getId(), battle.getPiece1().isWhite()), (startX + (17 * 5)) + 16*3*5, startY + 60 * 5, -16*3*5, 16*3*5, null);
        g.drawImage(ResourceLoader.instance.getWeaponImage(battle.getPiece1().getId()), (startX + (17 * 5)) + 16*3*5, startY + 60 * 5, -16*3*5, 16*3*5, null);
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
