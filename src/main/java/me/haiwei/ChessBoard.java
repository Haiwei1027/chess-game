package me.haiwei;

import java.awt.*;
import java.awt.image.BufferedImage;

import me.haiwei.pieces.*;

public class ChessBoard { // pawn, knight, rook, bishop, king, queen //white, black

	public final int PAWN=0,KNIGHT=1,ROOK=2,BISHOP=3,KING=4,QUEEN=5;
	public final int WHITE=0,BLACK=1;
	public BufferedImage image;

	private ChessPiece[] pieceTypes;
	private int[][] board;
	private int row, col;

	private Main main;
	private Point selected;

	private int nextSideToMove = WHITE;
	private int sideWon = -1;
	private int enPassantFile = -1;
	private int sideChecked = -1;

	private int whiteCastleDir = 0, blackCastleDir = 0;
	private Color checkColor = new Color(255,0,0,100);

	public ChessBoard(Main main,int row,int col) {
		this.main = main;
		this.row = row;
		this.col = col;
		board = new int[row][col];
		for (int x = 0; x < row; x++) {
			for (int y = 0; y < col; y++) {
				board[x][y] = -1;
			}
		}
		Bishop bishop = new Bishop(this);
		Rook rook = new Rook(this);
		pieceTypes = new ChessPiece[] { new Pawn(this), new Knight(this), rook, bishop,
				new King(this), new Queen(this, bishop, rook) };

		resetBoard();
		paint();
	}

	public boolean canWhiteCastle(int dir){
		return whiteCastleDir == dir || whiteCastleDir == 0;
	}

	public boolean canBlackCastle(int dir){
		return blackCastleDir == dir || blackCastleDir == 0;
	}

	public int getEnPassantFile(){
		return enPassantFile;
	}

	public void resetBoard(){
		for (int i = 0; i < 8; i++) {
			board[i][6] = PAWN;
			board[i][1] = PAWN+6;
			// pieces[i][1] = i+6;
		}
		for (int i = 0; i < 2; i++){
			for (int j = 0; j < 2; j++) {
				board[j*7][7-i*7] = ROOK + i*6;
				board[1+j*5][7-i*7] = KNIGHT + i*6;
				board[2+j*3][7-i*7] = BISHOP + i*6;
			}
			board[3][7-i*7] = QUEEN + i*6;
			board[4][7-i*7] = KING + i*6;
		}
	}

	public boolean onBoard(int x, int y) {
		return x >= 0 && x < row && y >= 0 && y < col;
	}

	public void setPiece(int x, int y, int id) {
		if (!onBoard(x, y)) {
			return;
		}
		board[x][y] = id;
		paint();
	}

	public int getPiece(int x, int y) {
		if (!onBoard(x, y)) {
			return -1;
		}
		return board[x][y];
	}

	private void checkCheck(){
		sideChecked = -1;
		for (int i = 0; i < row * col; i++) {
			int kx = i % row;
			int ky = i / row;
			if (getPiece(kx,ky) % 6 == KING){
				for (int j = 0; j < row * col; j++) {
					int cx = j % row;
					int cy = j / row;
					if (getPiece(cx,cy) < 0) continue;
					if (getPiece(cx,cy) / 6 != getPiece(kx,ky) / 6){
						if (pieceTypes[getPiece(cx,cy) % 6].isMoveValid(cx,cy,kx-cx,ky-cy,getPiece(cx,cy) / 6)){
							sideChecked = getPiece(kx,ky) / 6;

						}
					}
				}
			}
		}

	}

	public int move(int x1, int y1, int x2, int y2) {
		if (!onBoard(x1, y1) || !onBoard(x2, y2) || (x1 == x2 && y1 == y2)) {
			return -1; //outta the board
		}
		int p1 = getPiece(x1, y1);
		if (p1 == -1) {
			return -1; //missing piece
		}

		int p2 = getPiece(x2, y2);
		if (p2 > -1) {
			if (p2 / 6 == p1 / 6) {//cant take ur own pieces
				return -1;
			}
		}

		if (!pieceTypes[p1 % 6].isMoveValid(x1, y1, x2 - x1, y2 - y1, p1 / 6))
			return -1; //invalid move

		if (p1 % 6 == PAWN && (y2 > y1 ? y2 - y1: y1 - y2) == 2){
			enPassantFile = x1; //log pawn leap
		}else{
			enPassantFile = -1;
		}





		setPiece(x2, y2, p1);
		setPiece(x1, y1, -1);
		checkCheck();
		if (sideChecked == nextSideToMove){ //check not putting self at check
			setPiece(x1, y1, p1);
			setPiece(x2, y2, p2);
			return -1;
		}

		//did u take en passant
		if (p1 % 6 == PAWN){
			if (p2 == -1){
				if (x1 != x2){
					System.out.println("u used en passant");
					p2 = getPiece(x2,y1);
					setPiece(x2,y1,-1);
				}
			}
		}

		else if (p1 % 6 == KING){ //did u castle
			if (x2 - x1 == 2 || x2 - x1 == -2){
				setPiece(((x2 - x1) / 2 + 1 ) * 7,y1,-1);
			}
		}

		if (p1 % 6 == KING){ //log if king or rook moved
			if (p1 / 6 == 0){
				whiteCastleDir = -2;
			}
			else{
				blackCastleDir = -2;
			}
		} else if (p1 % 6 == ROOK) {
			if (p1 / 6 == 0){
				if (x1 == 0){
					if (whiteCastleDir == 0) whiteCastleDir = 1;
					else if (whiteCastleDir == -1) whiteCastleDir = -2;
				}
				else if (x1 == 7){
					if (whiteCastleDir == 0) whiteCastleDir = -1;
					else if (whiteCastleDir == 1) whiteCastleDir = -2;
				}
			}else{
				if (x1 == 0){
					if (blackCastleDir == 0) blackCastleDir = 1;
					else if (blackCastleDir == -1) blackCastleDir = -2;
				}
				else if (x1 == 7){
					if (blackCastleDir == 0) blackCastleDir = -1;
					else if (blackCastleDir == 1) blackCastleDir = -2;
				}
			}
		}
		if (p1 % 6 == KING){
			if ((x2 > x1 ? x2-x1 : x1 - x2) > 1){
				System.out.println("castle?");
			}
		}

		nextSideToMove = 1 - nextSideToMove;

		if (p2 % 6 == KING){
			//wow u killed the king, nice
			sideWon = 1-(p2/6);
			System.out.println(sideWon);
		}
		return p2; //piece that's been taken, -1 if empty
	}

	public int getSideWon(){
		return sideWon;
	}

	public void selectPiece(Point boardPosition) {
		int piece = getPiece(boardPosition.x, boardPosition.y);
		if (piece < 0 || piece / 6 != nextSideToMove) return;
		if (selected == null) {
			selected = boardPosition;
		}
		paint();
		main.boardPanel.enterDrag(getSelectedSprite());
	}

	public void movePiece(Point boardPosition) {
		if (selected == null) return;
		move(selected.x, selected.y, boardPosition.x, boardPosition.y);
		selected = null;
		paint();
		main.boardPanel.exitDrag();
	}
	
	public BufferedImage getSelectedSprite() {
		int piece = board[selected.x][selected.y];
		BufferedImage sprite;
		sprite = ResourceLoader.instance.getPiece(piece % 6, piece / 6);
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
		for (int x = 0; x < row; x++) {
			for (int y = 0; y < col; y++) {
				
				int piece = board[x][y];
				BufferedImage sprite;
				sprite = ResourceLoader.instance.getPiece(piece % 6, piece / 6);
				if (selected != null) {
					if (x == selected.x && y == selected.y) {
						continue;
					}
				}
				if (sideChecked == nextSideToMove && piece % 6 == KING && piece / 6 == nextSideToMove){
					g.setColor(checkColor);
					g.fillRect(x*16+7,y*16+7,16,16);
				}
				g.drawImage(sprite, x * 16 + 7, y * 16 + 7, null);
			}
		}
	}
}
