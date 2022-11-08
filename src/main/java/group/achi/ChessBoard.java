package group.achi;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

import group.achi.pieces.*;

public class ChessBoard { // pawn, knight, rook, bishop, king, queen //white, black

	public final static int PAWN=0,KNIGHT=1,ROOK=2,BISHOP=3,KING=4,QUEEN=5;
	public final int WHITE=0,BLACK=1;

	private final int size = 8;

	public BufferedImage image;

	private ChessPiece[][] board;
	private Point selected;

	private boolean nextSideToMove = true;

	public ChessBoard() {
		board = new ChessPiece[getSize()][getSize()];
		resetBoard();
		paint();
	}

	public int getSize(){
		return size;
	}

	public void mouseUp(Point location){
		if (selected == null) {return;}
		if (location.equals(selected)){
			//mouse up at the same location
			//clicking mode
		}
		dropPiece(location);
	}
	public void mouseDown(Point location){
		if (selected == null){
			selectPiece(location);
		}
		else{
			dropPiece(location);
		}
	}
	private void selectPiece(Point location){
		System.out.printf("pick up at %d, %d \n",location.x,location.y);
		if (getPiece(location.x,location.y) == null) return;
		if (getPiece(location.x,location.y).isWhite() != nextSideToMove) return; //cannot select the opponent pieces
		selected = location;

		paint();
	}

	private void dropPiece(Point location){
		System.out.printf("dropped at %s %s \n",location.x,location.y);

		boolean moved = getPiece(selected.x,selected.y).movePiece(location.x,location.y,selected.x,selected.y);

		if (moved) nextSideToMove = !nextSideToMove;

		selected = null;

		paint();
	}



	public void resetBoard(){
		for (int i = 0; i < 8; i++) {
			board[i][1] = new Pawn(this, true);
			board[i][6] = new Pawn(this, false);
		}
		for (int i = 0; i < 2; i++) {
			board[0][i*7] = new Rook(this, i==0);
			board[7][i*7] = new Rook(this, i==0);
			board[1][i*7] = new Knight(this, i==0);
			board[6][i*7] = new Knight(this, i==0);
			board[2][i*7] = new Bishop(this, i==0);
			board[5][i*7] = new Bishop(this, i==0);
			board[3][i*7] = new Queen(this, i==0);
			board[4][i*7] = new King(this, i==0);
		}

	}

	public boolean onBoard(int x, int y) {
		return x >= 0 && x < getSize() && y >= 0 && y < getSize();
	}

	public void setPiece(int x, int y, ChessPiece piece) {
		if (!onBoard(x, y)) {
			return;
		}
		board[x][y] = piece;
		paint();
	}

	public ChessPiece getPiece(int x, int y) {
		if (!onBoard(x, y)) {
			return null;
		}
		return board[x][y];
	}

	public boolean isDragging(){
		return selected != null;
	}
	public BufferedImage getSelectedSprite() {
		ChessPiece piece = board[selected.x][selected.y];
		if (piece == null) return null;
		BufferedImage sprite;
		sprite = ResourceLoader.instance.getPiece(piece.getId(), piece.isWhite());
    	return sprite;
    }

	public void paint() {
		if (image == null) {
			image = new BufferedImage(142, 142, BufferedImage.TYPE_INT_RGB);
		} else {
			image.flush();
		}
		Graphics g = image.getGraphics();
		g.drawImage(ResourceLoader.instance.board, 0, 0, null);
		g.setColor(Color.GREEN);
		g.fillRect(7,(nextSideToMove?1:0)*141,8*16,1);
		for (int x = 0; x < getSize(); x++) {
			for (int y = 0; y < getSize(); y++) {
				ChessPiece piece = board[x][y];
				BufferedImage sprite;
				sprite = piece != null ? ResourceLoader.instance.getPiece(piece.getId(), piece.isWhite()) : null;

				if (selected != null) {
					if (x == selected.x && y == selected.y) {
						g.setColor(new Color(255, 255, 0, 200));
						g.fillRect(x * 16 + 7, (7 - y) * 16 + 7, 16, 16);

						ChessPiece selectedPiece = board[selected.x][selected.y];
						if(selectedPiece != null){
							for (Point p : selectedPiece.getValidMoves(selected.x,selected.y)) {
								if(p == null) continue;
								g.setColor(new Color(255, 255, 0, 200));
								g.fillRect(p.x * 16 + 11, (7 - p.y) * 16 + 11, 8, 8);
							}
						}

						continue;
					}
				}
				g.drawImage(sprite, x * 16 + 7, (7 - y) * 16 + 7, null);
			}
		}
	}
}
