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
	private Color checkColor = new Color(255,0,0,100);

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

//	private void checkCheck(){
//		sideChecked = -1;
//		for (int i = 0; i < getSize() * getSize(); i++) {
//			int kx = i % getSize();
//			int ky = i / getSize();
//			if (getPiece(kx,ky) == KING){
//				for (int j = 0; j < getSize() * getSize(); j++) {
//					int cx = j % getSize();
//					int cy = j / getSize();
//					if (getPiece(cx,cy) < 0) continue;
//					if (getPiece(cx,cy) / 6 != getPiece(kx,ky) / 6){
//						if (pieceTypes[getPiece(cx,cy) % 6].isMoveValid(cx,cy,kx-cx,ky-cy,getPiece(cx,cy) / 6)){
//							sideChecked = getPiece(kx,ky) / 6;
//
//						}
//					}
//				}
//			}
//		}
//
//	}

//	public int move(int x1, int y1, int x2, int y2) {
//		if (!onBoard(x1, y1) || !onBoard(x2, y2) || (x1 == x2 && y1 == y2)) {
//			return -1; //outta the board
//		}
//		int p1 = getPiece(x1, y1);
//		if (p1 == -1) {
//			return -1; //missing piece
//		}
//
//		int p2 = getPiece(x2, y2);
//		if (p2 > -1) {
//			if (p2 / 6 == p1 / 6) {//cant take ur own pieces
//				return -1;
//			}
//		}
//
//		if (!pieceTypes[p1 % 6].isMoveValid(x1, y1, x2 - x1, y2 - y1, p1 / 6))
//			return -1; //invalid move
//
//		if (p1 % 6 == PAWN && (y2 > y1 ? y2 - y1: y1 - y2) == 2){
//			enPassantFile = x1; //log pawn leap
//		}else{
//			enPassantFile = -1;
//		}
//
//
//
//
//
//		setPiece(x2, y2, p1);
//		setPiece(x1, y1, -1);
//		checkCheck();
//		if (sideChecked == nextSideToMove){ //check not putting self at check
//			setPiece(x1, y1, p1);
//			setPiece(x2, y2, p2);
//			return -1;
//		}
//
//		//did u take en passant
//		if (p1 % 6 == PAWN){
//			if (p2 == -1){
//				if (x1 != x2){
//					System.out.println("u used en passant");
//					p2 = getPiece(x2,y1);
//					setPiece(x2,y1,-1);
//				}
//			}
//		}
//
//		else if (p1 % 6 == KING){ //did u castle
//			if (x2 - x1 == 2 || x2 - x1 == -2){
//				setPiece(((x2 - x1) / 2 + 1 ) * 7,y1,-1);
//			}
//		}
//
//		if (p1 % 6 == KING){ //log if king or rook moved
//			if (p1 / 6 == 0){
//				whiteCastleDir = -2;
//			}
//			else{
//				blackCastleDir = -2;
//			}
//		} else if (p1 % 6 == ROOK) {
//			if (p1 / 6 == 0){
//				if (x1 == 0){
//					if (whiteCastleDir == 0) whiteCastleDir = 1;
//					else if (whiteCastleDir == -1) whiteCastleDir = -2;
//				}
//				else if (x1 == 7){
//					if (whiteCastleDir == 0) whiteCastleDir = -1;
//					else if (whiteCastleDir == 1) whiteCastleDir = -2;
//				}
//			}else{
//				if (x1 == 0){
//					if (blackCastleDir == 0) blackCastleDir = 1;
//					else if (blackCastleDir == -1) blackCastleDir = -2;
//				}
//				else if (x1 == 7){
//					if (blackCastleDir == 0) blackCastleDir = -1;
//					else if (blackCastleDir == 1) blackCastleDir = -2;
//				}
//			}
//		}
//		if (p1 % 6 == KING){
//			if ((x2 > x1 ? x2-x1 : x1 - x2) > 1){
//				System.out.println("castle?");
//			}
//		}
//
//		nextSideToMove = 1 - nextSideToMove;
//
//		if (p2 % 6 == KING){
//			//wow u killed the king, nice
//			sideWon = 1-(p2/6);
//			System.out.println(sideWon);
//		}
//		return p2; //piece that's been taken, -1 if empty
//	}

//	public void selectPiece(Point boardPosition) {
//		int piece = getPiece(boardPosition.x, boardPosition.y);
//		if (piece < 0 || piece / 6 != nextSideToMove) return;
//		if (selected == null) {
//			selected = boardPosition;
//		}
//		paint();
//		main.boardPanel.enterDrag(getSelectedSprite());
//	}

//	public void movePiece(Point boardPosition) {
//		if (selected == null) return;
//		move(selected.x, selected.y, boardPosition.x, boardPosition.y);
//		selected = null;
//		paint();
//		main.boardPanel.exitDrag();
//	}
	
	public BufferedImage getSelectedSprite() {
		ChessPiece piece = board[selected.x][selected.y];
		BufferedImage sprite;
		sprite = ResourceLoader.instance.getPiece(piece.getId(), piece.getSide());
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
