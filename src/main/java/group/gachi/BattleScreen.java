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
    private int ps = 3, ss = 5; //pawn scale and piece scale
    private int maxAnimFrame = 120, animFrame = maxAnimFrame, weaponBop = 0, dWeaponBop = ss*ps;
    final private String button1Text = "ATTACK", button2Text = "WOUND", button3Text = "HEAL", button4Text = "LEAVE";

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
        animFrame--;
        if (animFrame == 0)
        {
            // if (weaponBop == 0) {weaponBop = 1;} else {weaponBop = 0; }
            weaponBop = weaponBop == 0? -12 : 0;
            animFrame = maxAnimFrame;
        }

        g.setColor(bgColor);
        g.fillRect(0,0,getWidth(),getHeight());
        onResize();
        g.drawImage(battle.image, startX, startY,width,height,null);

        for(int i = 0; i < 4*31*ss; i += 31*ss)
        {
            g.drawImage(ResourceLoader.instance.buttonImage, startX + 8*ss + i, startY + 111*ss,33*ss, 24*ss, null);
        }
        g.drawImage(ResourceLoader.instance.picoString(button1Text), startX + 12*ss, startY + 114*ss, button1Text.length()*4*ss, 5*ss, null );
        g.drawImage(ResourceLoader.instance.picoString(button2Text), startX + 43*ss, startY + 114*ss, button2Text.length()*4*ss, 5*ss, null );
        g.drawImage(ResourceLoader.instance.picoString(button3Text), startX + 74*ss, startY + 114*ss, button3Text.length()*4*ss, 5*ss, null );
        g.drawImage(ResourceLoader.instance.picoString(button4Text), startX + 105*ss, startY + 114*ss, button4Text.length()*4*ss, 5*ss, null );

        g.drawImage(ResourceLoader.instance.getPieceRaw(battle.getPiece2().getId(), battle.getPiece2().isWhite()), (startX + 72 * ss) + 45, (startY + 18 * ss) + 23,16*ps*(ss-1),16*ps*(ss-1), null);
        g.drawImage(ResourceLoader.instance.getWeaponImage(battle.getPiece2().getId()), (startX + 72 * ss) + 45, ((startY + 18 * ss) + 23) + weaponBop,16*ps*(ss-1),16*ps*(ss-1), null);

        g.drawImage(ResourceLoader.instance.getPieceRaw(battle.getPiece1().getId(), battle.getPiece1().isWhite()), (startX + (17 * ss)) + 16*ps*ss, startY + 60 * ss, -16*ps*ss, 16*ps*ss, null);
        g.drawImage(ResourceLoader.instance.getWeaponImage(battle.getPiece1().getId()), (startX + (17 * ss)) + 16*ps*ss, (startY + 60 * ss) + weaponBop, -16*ps*ss, 16*ps*ss, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        e.getPoint();
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
