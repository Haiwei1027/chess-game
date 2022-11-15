package group.gachi;
import group.gachi.pieces.Pawn;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class ChessScreen extends JPanel implements MouseListener, MouseMotionListener, KeyListener{

    Main main;
    ChessBoard board;
    private int width, height, pieceWidth, pieceHeight, startX, startY;
    private Point mousePosition = new Point();
    private Color bgColor;

    private Button[] promoteButtons = {
            new Button("",new Rectangle(0, 0, 64, 48),
                    () -> {
                        System.out.println("queen");
                        board.promotionDecided(1);
                    }, ResourceLoader.instance.buttonImage),
            new Button("",new Rectangle(64, 0, 64, 48),
                    () -> {
                        System.out.println("rook");
                        board.promotionDecided(2);
                    }, ResourceLoader.instance.buttonImage),
            new Button("",new Rectangle(0, 48, 64, 48),
                    () -> {
                        System.out.println("biship");
                        board.promotionDecided(3);
                    }, ResourceLoader.instance.buttonImage),
            new Button("",new Rectangle(64, 48, 64, 48),
                    () -> {
                        System.out.println("night");
                        board.promotionDecided(4);
                    }, ResourceLoader.instance.buttonImage)
    };

    public ChessScreen(Main main, int width, int height) {
        super();
        this.main = main;
        this.board = new ChessBoard(this);
        this.width = width;
        this.height = height;
        this.bgColor = new Color(69,77,95);

        setSize(width,height);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);

        pieceWidth = (int)(width / 142f) * 16;
        pieceHeight = pieceWidth;
    }

    public void onResize(){
        startX = (main.getWidth()-16)/2-width/2;
        startY = (main.getHeight()-38)/2-height/2;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(bgColor);
        g.fillRect(0,0,main.getWidth(),main.getHeight());
        onResize();

        drawBoard(g, startX, startY);
        if (board.isPromoting()){
            drawPromotion(g, board.getPromotion());
        }
        else if (board.isDragging()) {
        	drawDraggedPiece(g);
        }
    }


    public Point transformPoint(Point point) { //screen pos to chess coord
        return new Point(((int) ((point.x-startX) * (142f / width)) - 7) / 16, 7 - ((int) ((point.y-startY) * (142f / height)) - 7) / 16);
    }

    public Point inverseTransformPoint(Point point) { //chess coord to screen pos
        return new Point((int)((point.x * 16 + 7) / (142f / width) + startX), (int)(((7-point.y) * 16 + 7) / (142f / width) + startY));
    }

    private void drawBoard(Graphics g, int x, int y) {
        g.drawImage(board.image, x, y, width, height, null);
    }

    private void drawDraggedPiece(Graphics g) {
    	//System.out.printf("%d, %d \n", InputHandler.getMousePoint().x, InputHandler.getMousePoint().y);
        int draggedSize = (int)(pieceHeight * 1.3);
    	g.drawImage(board.getSelectedSprite(), mousePosition.x - draggedSize / 2, mousePosition.y - draggedSize / 2, draggedSize, draggedSize, null);
    }

    private void drawPromotion(Graphics g, Point location) {
        location = inverseTransformPoint(location);

        for (Button button : promoteButtons) {
            button.drawButton(location.x, location.y, g);
        }
    }

    public void updatePromotionButtons(){
        boolean side = board.getPromotion().y == 7;
        promoteButtons[0].setForeground(ResourceLoader.instance.getPieceRaw(ChessBoard.QUEEN,side));
        promoteButtons[1].setForeground(ResourceLoader.instance.getPieceRaw(ChessBoard.ROOK,side));
        promoteButtons[2].setForeground(ResourceLoader.instance.getPieceRaw(ChessBoard.BISHOP,side));
        promoteButtons[3].setForeground(ResourceLoader.instance.getPieceRaw(ChessBoard.KNIGHT,side));
        for (Button button : promoteButtons) {
            button.unClick();
            button.checkHover(new Point(-69,-69));
        }
        System.out.println("hi");
    }

    private void checkPromotionClicked(Point mouse){
        if (board.isPromoting()){
            Point location = board.getPromotion();
            Point screenLocation = inverseTransformPoint(location);
            mouse.x -= screenLocation.x;
            mouse.y -= screenLocation.y;
            for (Button button :
                    promoteButtons) {
                button.tryClick(mouse);
            }
        }
    }

    //input handlers
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (board.isPromoting()){
            checkPromotionClicked(e.getPoint());
        }
        else{
            board.mouseDown(transformPoint(e.getPoint()));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        board.mouseUp(transformPoint(e.getPoint()));
        board.setDragging(false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (board.getSelected() != null){
            if (!transformPoint(e.getPoint()).equals(board.getSelected())){
                board.setDragging(true);
            }
        }

        mousePosition = e.getPoint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition.x = e.getX();
        mousePosition.y = e.getY();

        if (board.isPromoting()){
            Point mouse = e.getPoint();
            Point screenLocation = inverseTransformPoint(board.getPromotion());
            mouse.x -= screenLocation.x; //map mouse coords to buttons coords
            mouse.y -= screenLocation.y;
            for (Button button :
                    promoteButtons) {
                button.checkHover(mouse);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            board.resetBoard();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
