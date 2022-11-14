package group.gachi;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import group.gachi.pieces.*;

public class ChessBoard { // pawn, knight, rook, bishop, king, queen //white, black

	public final static int PAWN=0,KNIGHT=1,ROOK=2,BISHOP=3,KING=4,QUEEN=5;
	public final int WHITE=0,BLACK=1;
	private final String[] pawnPersonalNames = new String[]{"MESLET", "PODREY", "BINMAN", "NARSI", "KEVIN", "AMLEIA", "SORSON", "JAMES", //white pawn
															"RORY", "CARWYN", "MAISEI", "BANNED", "VANSYR", "RERTY", "NEYSHA", "BORIS"}; //black pawn
	private final String[] rookPersonalNames = new String[]{"CASSOR", "STEVE", //white rook names
															"TOVER", "CAER"}; //black rook names
	private final String[] knightPersonalNames = new String[]{"GARRY", "HAIWEI", //white knight names
																"CAMI", "ISAAC"};//black white names
	private final String[] bishopPersonalNames = new String[]{"SHAH", "VIVIAN",  //white bishop names
																"BETHAN", "BOI"};//black bishop names
	private final String[] monarchPersonalNames = new String[]{"MICU", "ROMAN",  //white king queen names
																"XANDER", "ARIAN"};//black king queen names
	private final int size = 8;

	public BufferedImage image;
	private Point selected;
	private boolean isDragging = false;

	private Point pawnToBePromoted = null;
	private int sideInCheck = -1;
	private boolean nextSideToMove = true;

	//HashMap to keep track and quickly access every non-empty point
	//Key - co-ordinate, value - id
	public HashMap<Point, ChessPiece> nonEmptySpaces = new HashMap<>();

	public HashMap<Point, ChessPiece> board = new HashMap<>();

	public ChessBoard() {
		resetBoard();
		paint();
	}

	public int getSize(){
		return size;
	}

	public void mouseUp(Point location){
		if (selected == null) {return;}
		if (location.equals(selected) && !isDragging) {
			if (getPiece(location.x,location.y) != null){
				selectPiece(location);
			} else {
				selected = null;
			}
			return;
		}
		dropPiece(location);
	}
	public void mouseDown(Point location){
		if (selected == null){
			selectPiece(location);
		}
		else{
			if (!dropPiece(location)){
				selectPiece(location);
			}
		}
	}
	private void selectPiece(Point location){
		if (getPiece(location) == null) return;
		if (pawnToBePromoted != null) return;
		if (getPiece(location).isWhite() != nextSideToMove) return; //cannot select the opponent pieces
		selected = location;

		paint();
	}

	public void setDragging(boolean dragging) {
		boolean changed = isDragging != dragging;
		isDragging = dragging;
		if (changed) paint();
	}

	private boolean dropPiece(Point location) {
		ChessPiece attacker = getPiece(selected);
		Point enemyLocation = location;

		if (attacker.getId() == ChessBoard.PAWN) {
			if (((Pawn) attacker).enPassant && Math.abs(selected.x - location.x) == 1) {
				enemyLocation = new Point(location.x, attacker.isWhite() ? location.y - 1 : location.y + 1);
			}
		}
		ChessPiece defender = getPiece(enemyLocation);

		boolean moved = getPiece(selected).movePiece(location.x,location.y,selected.x,selected.y);

		if (moved) {
			if (!isPromoting()) {
				nextSideToMove = !nextSideToMove;
			}
			if (defender != null) {
				if (defender.isEnemy(attacker)){
					//Main.enterBattle(new Battle(attacker,defender));
				}
			}
		}

		selected = null;
		setDragging(false);

		paint();

		return moved;
	}
	public void resetBoard(){
		for (int i = 0; i < 8; i++) {
			board.put(new Point(i, 1), new Pawn(this, true, pawnPersonalNames[i]));
			board.put(new Point(i, 6), new Pawn(this, false, pawnPersonalNames[i + 8]));
		}
		for (int i = 0; i < 2; i++) {
			board.put(new Point(0, i*7), new Rook(this, i==0, rookPersonalNames[i]));
			board.put(new Point(1, i*7), new Knight(this, i==0, knightPersonalNames[i]));
			board.put(new Point(2, i*7), new Bishop(this, i==0, bishopPersonalNames[i]));
			board.put(new Point(3, i*7), new Queen(this, i==0, monarchPersonalNames[i+2]));
			board.put(new Point(4, i*7), new King(this, i==0, monarchPersonalNames[i]));
			board.put(new Point(5, i*7), new Bishop(this, i==0, bishopPersonalNames[i+2]));
			board.put(new Point(6, i*7), new Knight(this, i==0, knightPersonalNames[i+2]));
			board.put(new Point(7, i*7), new Rook(this, i==0, rookPersonalNames[i+2]));
		}

		for (int i = 0; i < 8; i ++) {
			for (int j = 2; j < 6; j ++) {
				board.put(new Point(i, j), null);
			}
		}

		setInitialNonEmptySpots();
		paint();
	}

	public void setCheck(int sideChecked){
		sideInCheck = sideChecked;
	}

	public boolean onBoard(int x, int y) {
		return x >= 0 && x < getSize() && y >= 0 && y < getSize();
	}

	public void awaitPromotion(int x, int y){
		pawnToBePromoted = new Point(x,y);
	}

	public void promotionDecided(int choice){
		if (((Pawn)getPiece(pawnToBePromoted)).promote(pawnToBePromoted.x, pawnToBePromoted.y , choice)){
			pawnToBePromoted = null;
			nextSideToMove = !nextSideToMove;
		}
		paint();
	}

	public void setPiece(Point point, ChessPiece piece) {
		setPiece(point.x, point.y, piece);
	}

	public void setPiece(int x, int y, ChessPiece piece) {
		if (!onBoard(x, y)) {
			return;
		}

		board.put(new Point(x, y), piece);

		//Updates nonEmptySpaces hash map
		if (piece != null) insertIntoNonEmpty(x, y);
		else removeFromNonEmpty(x, y);
	}

	public ChessPiece getPiece(Point point){
		return board.get(point);
	}

	public ChessPiece getPiece(int x, int y) {
		if (!onBoard(x, y)) {
			return null;
		}

		return board.get(new Point(x, y));
	}

	public boolean isDragging(){
		return selected != null && isDragging;
	}

	public boolean isPromoting(){
		return pawnToBePromoted != null;
	}

	public Point getPromotion(){
		return pawnToBePromoted;
	}

	public BufferedImage getSelectedSprite() {
		ChessPiece piece = board.get(selected);
		if (piece == null) return null;
		BufferedImage sprite;
		sprite = ResourceLoader.instance.getPiece(piece.getId(), piece.isWhite());
    	return sprite;
    }

    public Point getSelected(){
		return selected;
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
		g.fillRect(7, (nextSideToMove ? 1 : 0) * 141, 8 * 16, 1);
		for (int x = 0; x < getSize(); x++) {
			for (int y = 0; y < getSize(); y++) {
				ChessPiece piece = board.get(new Point(x, y));
				BufferedImage sprite;
				sprite = piece != null ? ResourceLoader.instance.getPiece(piece.getId(), piece.isWhite()) : null;

				if (selected != null && selected.equals(new Point(x, y))) {
					g.setColor(new Color(255, 255, 0, 128));
					g.fillRect(x * 16 + 7, (7 - y) * 16 + 7, 16, 16);
					if (isDragging) continue;
				}
				g.drawImage(sprite, x * 16 + 7, (7 - y) * 16 + 7, null);
			}
		}
		g.setColor(new Color(255, 0, 0, 128));
		if (sideInCheck != -1){
			g.fillRect(King.getKing(sideInCheck == 1).getX() * 16 + 7, (7 - King.getKing(sideInCheck == 1).getY()) * 16 + 7, 16, 16);
		}
		if (selected == null) return;
		ChessPiece selectedPiece = board.get(selected);
		if (selectedPiece != null) {
			for (Point p : selectedPiece.getValidMoves(selected.x, selected.y)) {
				if (p == null) continue;
				g.setColor(new Color(255, 255, 0, 128));
				g.fillRect(p.x * 16 + 11, (7 - p.y) * 16 + 11, 8, 8);
			}
		}
		g.dispose();
	}

	public void setInitialNonEmptySpots() {
		for (Point point : nonEmptySpaces.keySet()) {
			nonEmptySpaces.remove(point);
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 2; j++) {
				insertIntoNonEmpty(i, j);
			}
			for (int j = 7; j > 5; j--) {
				insertIntoNonEmpty(i, j);
			}
		}
	}

	public void insertIntoNonEmpty(int x, int y) {
		nonEmptySpaces.put(new Point(x, y), getPiece(x, y));
	}

	public void removeFromNonEmpty(int x, int y) {
		nonEmptySpaces.remove(new Point(x, y));
	}
}
