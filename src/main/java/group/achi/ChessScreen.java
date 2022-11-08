package group.achi;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class ChessScreen extends JPanel implements MouseListener, MouseMotionListener{

    Main main;
    ChessBoard board;
    private int width, height, pieceWidth, pieceHeight, startX, startY;
    private Point mousePosition = new Point();
    private Color bgColor;

    public ChessScreen(Main main, int width, int height) {
        super();
        this.main = main;
        this.board = new ChessBoard();
        this.width = width;
        this.height = height;

        setSize(width,height);
        addMouseListener(this);
        addMouseMotionListener(this);

        pieceWidth = (int)(width / 142f) * 16;
        pieceHeight = pieceWidth;



        int color = ResourceLoader.instance.board.getRGB(0,0);
        int b = color & 0xff;
        int g = (color & 0xff00) >> 8;
        int r = (color & 0xff0000) >> 16;
        bgColor = new Color(r,g,b);
    }

    public void onResize(){
        startX = (main.getWidth()-16)/2-width/2;
        startY = (main.getHeight()-38)/2-height/2;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(bgColor);
        g.fillRect(0,0,main.getWidth(),main.getHeight());
        onResize();

        drawBoard(g, startX, startY);
        if (board.isDragging()) {
        	drawDraggedPiece(g);
        }
    }


    public Point transformPoint(Point point) { //screen pos to chess coord
        Point newPoint = new Point(point.x, point.y);
        transformPointByRef(newPoint);
        return newPoint;
    }

    public void transformPointByRef(Point point) {
        point.x = ((int) ((point.x-startX) * (142f / width)) - 7) / 16;
        point.y = 7 - ((int) ((point.y-startY) * (142f / height)) - 7) / 16;
    }

    public void drawBoard(Graphics g, int x, int y) {
        g.drawImage(board.image, x, y, width, height, null);
    }
    
    public void drawDraggedPiece(Graphics g) {
    	//System.out.printf("%d, %d \n", InputHandler.getMousePoint().x, InputHandler.getMousePoint().y);
        int draggedSize = (int)(pieceHeight * 1.3);
    	g.drawImage(board.getSelectedSprite(), mousePosition.x - draggedSize / 2, mousePosition.y - draggedSize / 2, draggedSize, draggedSize, null);
    }

    //input handlers
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        board.mouseDown(transformPoint(e.getPoint()));
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
        mousePosition = e.getPoint();
        board.setDragging(true);
        board.paint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition.x = e.getX();
        mousePosition.y = e.getY();
    }
}
