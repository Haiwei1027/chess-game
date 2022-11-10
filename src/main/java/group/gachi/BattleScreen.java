package group.gachi;

import group.gachi.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class BattleScreen extends JPanel implements MouseListener, MouseMotionListener {

    private Main main;
    private Battle battle;
    private int ps = 3, ss = 5; //pawn scale and piece scale
    private int maxAnimFrame = 120, animFrame = maxAnimFrame, weaponBop = 0, dWeaponBop = ss*ps;
    final private String[] buttonTexts = {"ATTACK","WOUND","HEAL","LEAVE"};

    private Button[] buttons = {
            new Button(buttonTexts[0],new Rectangle( 12*ss,  114*ss, buttonTexts[0].length()*4*ss, 5*ss),
                    () -> {
                        System.out.println("attack");
                    }),
            new Button(buttonTexts[1],new Rectangle( 43*ss, 114*ss, buttonTexts[1].length()*4*ss, 5*ss),
                    () -> {
                        System.out.println("wound");
                    }),
            new Button(buttonTexts[2],new Rectangle( 74*ss, 114*ss, buttonTexts[2].length()*4*ss, 5*ss),
                    () -> {
                        System.out.println("heal");
                    }),
            new Button(buttonTexts[3],new Rectangle( 105*ss,  114*ss, buttonTexts[3].length()*4*ss, 5*ss),
                    () -> {
                        System.out.println("leave");
                    })
    };

    private int width, height, startX, startY;
    private Point mousePosition = new Point();
    private Color bgColor;

    interface ButtonAction{
        void run();
    }

    class Button{
        private String label;
        private Rectangle rect;
        private ButtonAction action;
        private boolean clicked;
        private boolean hovered;

        public Button(String label, Rectangle rect, ButtonAction action){
            this.label = label;
            this.rect = rect;
            this.action = action;
        }
        public void drawButton(int startX, int startY, Graphics g){
            int x = startX + rect.x;
            int y = startY + rect.y;
            if (clicked){
                g.setColor(Color.GREEN);
                g.fillRect(x,y,rect.width,rect.height);
            }
            else if (hovered){
                g.setColor(Color.ORANGE);
                g.fillRect(x,y,rect.width,rect.height);
            }
            g.drawImage(ResourceLoader.instance.picoString(label), x, y, rect.width, rect.height, null);
        }
        public boolean hovered(){
            return hovered;
        }
        private boolean isInside(Point point){
            return rect.contains(point);
        }
        public void checkHover(Point mouse){
            hovered = isInside(mouse);
        }
        public void tryClick(Point mouse){
            if (isInside(mouse) && !clicked){
                clicked = true;
                action.run();
            }
        }
        public void unClick(){
            clicked = false;
        }
    }

    public BattleScreen(Main main, int width, int height, Battle battle)
    {
        super();
        this.main = main;
        this.battle = battle;
        this.width = width;
        this.height = height;
        this.bgColor = new Color(230,234,215);

        setSize(width, height);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);

    }

    public Point transformPoint(Point point) {
        return new Point(((point.x-startX)),((point.y-startY)));
    }

    public void setBattle(Battle battle){
        this.battle = battle;
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
        g.fillRect(0,0,main.getWidth(),main.getHeight());
        onResize();
        g.drawImage(battle.image, startX, startY,width,height,null);

        for(int i = 0; i < 4*31*ss; i += 31*ss)
        {
            g.drawImage(ResourceLoader.instance.buttonImage, startX + 8*ss + i, startY + 111*ss,33*ss, 24*ss, null);
        }

        for (Button button : buttons) {
            button.drawButton(startX, startY, g);
        }

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
        for (Button button : buttons) {
            Point transformedPoint = transformPoint(e.getPoint());
            button.tryClick(transformedPoint);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (Button button : buttons) {
            button.unClick();
        }
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
        for (Button button : buttons) {
            Point transformedPoint = transformPoint(e.getPoint());
            button.checkHover(transformedPoint);
        }
    }
}
