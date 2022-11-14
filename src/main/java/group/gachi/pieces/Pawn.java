package group.gachi.pieces;
import java.util.Scanner;
import group.gachi.ChessBoard;

public class Pawn extends ChessPiece {
	boolean hasMoved = false;
	static int hasLeapedOnFile = -1;
	public boolean enPassant = false;

	public Pawn(ChessBoard board, boolean isWhite, String personalName) {
		super(board, isWhite, personalName);
		this.id = ChessBoard.PAWN;
		super.health = 20;
	}

	@Override
	public boolean isMoveValid(int toX, int toY, int fromX, int fromY) {
		int dx = toX - fromX, dy = toY - fromY,
			absdy = Math.abs(dy), absdx = Math.abs(dx);
		int step = isWhite ? 1 : -1;
		boolean stepBlocked = board.getPiece(fromX, fromY + step) != null;
		boolean leapBlocked = board.getPiece(fromX, fromY + step * 2) != null || stepBlocked;
		boolean canCapture = board.getPiece(fromX + dx, fromY + step) != null;

		if (dy == step){
			//move forward
			if (dx == 0) return !stepBlocked;
			else if (absdx == 1){
				//capture
				if (canCapture) return true;
				else return enPassant(fromX, fromY, dx, dy);
				//en passant
			}
		}
		else if (dy == step * 2){
			return !(leapBlocked || absdx != 0 || hasMoved);
		}

		return false;

//		if ( (absdy == 2 && hasMoved == false) && (absdx == 0))
//		{
//			return true;
//		}
//		if ( (absdy == 1 && absdx == 1) && (board.getPiece(toX, toY) != null) ) {return true; }
//
//		if (absdy > 1 || absdx > 0) {return false; } //NO moving more than 1 space away!
//
//		if (absdx == 0 && board.getPiece(toX, toY) != null) {return false; }
//
//		if( (absdy == 2 && absdx > 0)) {return false; }
	}

	@Override
	public boolean movePiece(int toX, int toY, int fromX, int fromY)
	{
		if (super.movePiece(toX,toY,fromX,fromY))
		{
			if (Math.abs(fromY - toY) == 2){
				hasLeapedOnFile = toX;
			}
			if (enPassant){
				board.setPiece(toX,toY - (isWhite ? 1 : -1),null);
				enPassant = false;
			}

			if (canPromote(toY)) board.awaitPromotion(toX, toY);
			return hasMoved = true;
		}
		return false;
	}

	public boolean enPassant(int x, int y, int dx, int dy){
		//Gets piece in adjacent spot
		ChessPiece adjacentSpot = board.getPiece(x + dx, y);

		if (adjacentSpot == null || adjacentSpot.getId() != ChessBoard.PAWN) return false;

		Pawn pawn = (Pawn)adjacentSpot;

		if (pawn.isWhite != isWhite && ((isWhite && y == 4) || (!isWhite && y == 3))) {
			if (hasLeapedOnFile == x + dx) {
				enPassant = true;
				return true;
			}
		}
		return false;
	}

	public boolean promote(int x, int y, int choice){
		ChessPiece newPiece;
		switch (choice) {
			case 1: newPiece = new Queen(board, this.isWhite, this.personalName); break;
			case 2: newPiece = new Rook(board, this.isWhite, this.personalName); break;
			case 3: newPiece = new Bishop(board, this.isWhite, this.personalName); break;
			case 4: newPiece = new Knight(board, this.isWhite, this.personalName); break;
			default: return false;
		}
		board.setPiece(x, y, newPiece);
		return true;
	}

	public boolean canPromote(int y) {
		return (this.isWhite && y == 7) || (!this.isWhite && y == 0);
	}
}
