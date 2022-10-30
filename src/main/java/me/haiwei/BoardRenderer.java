package me.haiwei;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BoardRenderer extends JPanel {

    Main main;
    ChessBoard board;
    private int width, height, pieceWidth, pieceHeight, startX, startY;
    private Color bgColor;
    
    private BufferedImage draggedPiece;

    public BoardRenderer(Main main, int width, int height) {
        super();
        this.main = main;
        this.board = main.board;
        this.width = width;
        this.height = height;
        setSize(width,height);
        pieceWidth = (int)(width / 142f) * 16;
        pieceHeight = pieceWidth;


        int color = ResourceLoader.instance.board.getRGB(0,0);
        int b = color & 0xff;
        int g = (color & 0xff00) >> 8;
        int r = (color & 0xff0000) >> 16;
        bgColor = new Color(r,g,b);
    }
    
    public void enterDrag(BufferedImage pieceSprite) {
    	draggedPiece = pieceSprite;
    }
    
    public void exitDrag() {
    	draggedPiece = null;
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
        if (draggedPiece != null) {
        	drawDraggedPiece(g);
        }
    }

    /*public void drawPiece(Graphics g, int id, int side, int x, int y) {
        g.drawImage(ResourceLoader.instance.pieces, x, y,
                x + 128, y + 128, 0 + 135 * id, 0 + side * 128, 135 * id + 128, 128 + side * 135, null);
    }*/

    public Point transformPoint(Point point) { //screen pos to chess coord
        Point newPoint = new Point(point.x, point.y);
        transformPointByRef(newPoint);
        return newPoint;
    }

    public void transformPointByRef(Point point) {
        point.x = ((int) ((point.x-startX) * (142f / width)) - 7) / 16;
        point.y = ((int) ((point.y-startY) * (142f / height)) - 7) / 16;
    }

    public void drawBoard(Graphics g, int x, int y) {
        g.drawImage(board.image, x, y, width, height, null);
    }
    
    public void drawDraggedPiece(Graphics g) {
    	//System.out.printf("%d, %d \n", InputHandler.getMousePoint().x, InputHandler.getMousePoint().y);
    	g.drawImage(draggedPiece, InputHandler.getMousePoint().x - pieceWidth / 2, InputHandler.getMousePoint().y - pieceHeight / 2, pieceWidth, pieceHeight, null);
    }
}
