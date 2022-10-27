package me.haiwei;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer extends JPanel {

    ChessBoard board;
    private int width, height, pieceWidth, pieceHeight;
    
    private BufferedImage draggedPiece;

    public Renderer(ChessBoard board, int width, int height) {
        super();
        this.board = board;
        this.width = width;
        this.height = height;
        pieceWidth = (int)(width / 142f) * 16;
        pieceHeight = pieceWidth;
    }
    
    public void enterDrag(BufferedImage pieceSprite) {
    	draggedPiece = pieceSprite;
    }
    
    public void exitDrag() {
    	draggedPiece = null;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawBoard(g, 0, 0);
        if (draggedPiece != null) {
        	drawDraggedPiece(g);
        }
    }

    /*public void drawPiece(Graphics g, int id, int side, int x, int y) {
        g.drawImage(ResourceLoader.instance.pieces, x, y,
                x + 128, y + 128, 0 + 135 * id, 0 + side * 128, 135 * id + 128, 128 + side * 135, null);
    }*/

    public void drawBoard(Graphics g, int x, int y) {
        g.drawImage(board.image, x, y, width, height, null);
    }
    
    public void drawDraggedPiece(Graphics g) {
    	System.out.printf("%d, %d \n", InputHandler.getMousePoint().x, InputHandler.getMousePoint().y);
    	g.drawImage(draggedPiece, InputHandler.getMousePoint().x - pieceWidth / 2, InputHandler.getMousePoint().y - pieceHeight / 2, pieceWidth, pieceHeight, null);
    }
}
