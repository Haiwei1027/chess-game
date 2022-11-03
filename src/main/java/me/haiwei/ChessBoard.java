package me.haiwei;

import java.awt.*;
import java.awt.image.BufferedImage;

import me.haiwei.pieces.*;

public class ChessBoard { // pawn, knight, rook, bishop, king, queen //white, black

	public final static int PAWN=0,KNIGHT=1,ROOK=2,BISHOP=3,KING=4,QUEEN=5;
	public final int WHITE=0,BLACK=1;

	private final int size = 8;

	public BufferedImage image;

	private ChessPiece[] pieceTypes;

	private ChessPiece[][] board;

	private Main main;
	private Point selected;

	private boolean nextSideToMove = true;
	private int sideWon = -1;
	private int enPassantFile = -1;
	private int sideChecked = -1;

	private int whiteCastleDir = 0, blackCastleDir = 0;

	public ChessBoard(Main main) {
		this.main = main;
		board = new ChessPiece[getSize()][getSize()];
		resetBoard();
		paint();
	}

	public int getSize(){
		return size;
	}

	public void resetBoard(){
		for (int i = 0; i < 8; i++) {
			board[i][1] = new Pawn(this, true);
			board[i][7] = new Pawn(this, false);
		}
		board[0][0] = new Rook(this, true);
		board[7][0] = new Rook(this, true);
		board[1][0] = new Knight(this, true);
		board[6][0] = new Knight(this, true);
		board[2][0] = new Bishop(this, true);
		board[5][0] = new Bishop(this, true);
		board[3][0] = new Queen(this, true);
		board[4][0] = new King(this, true);

		board[0][7] = new Rook(this, false);
		board[7][7] = new Rook(this, false);
		board[1][7] = new Knight(this, false);
		board[6][7] = new Knight(this, false);
		board[2][7] = new Bishop(this, false);
		board[5][7] = new Bishop(this, false);
		board[3][7] = new Queen(this, false);
		board[4][7] = new King(this, false);

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
	
	public BufferedImage getSelectedSprite() {
		ChessPiece piece = board[selected.x][selected.y];
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
		g.fillRect(7,141-nextSideToMove*141,8*16,1);
		for (int x = 0; x < getSize(); x++) {
			for (int y = 0; y < getSize(); y++) {
				
				ChessPiece piece = board[x][y];
				BufferedImage sprite;
				sprite = ResourceLoader.instance.getPiece(piece.getId(), piece.isWhite());
				if (selected != null) {
					if (x == selected.x && y == selected.y) {
						continue;
					}
				}
				if (sideChecked == nextSideToMove && piece.getId() == KING && piece.isWhite() == nextSideToMove){
					g.setColor(checkColor);
					g.fillRect(x*16+7,y*16+7,16,16);
				}
				g.drawImage(sprite, x * 16 + 7, y * 16 + 7, null);
			}
		}
	}
}
