package group.gachi.pieces;
import java.util.Scanner;
import group.gachi.ChessBoard;

public class Pawn extends ChessPiece {
	boolean hasMoved = false;
	static int hasLeapedOnFile = -1;
	boolean enPassant = false;

	public Pawn(ChessBoard board, boolean isWhite, String personalName) {
		super(board, isWhite, personalName);
		this.id = ChessBoard.PAWN;
		super.health = 8;
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
				//enpassant
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
			}

			if (canPromote(toY)) this.promote(toX, toY);
			return hasMoved = true;
		}
		return false;
	}

	public boolean enPassant(int x, int y, int dx, int dy){
		//Gets piece in adjacent spot
		ChessPiece adjacentSpot = board.getPiece(x + dx, y);

		if (adjacentSpot == null || adjacentSpot.getId() != ChessBoard.PAWN) return false;

		Pawn pawn = (Pawn)adjacentSpot;

		if (pawn.isWhite != isWhite){
			if (hasLeapedOnFile == x + dx) {
				enPassant = true;
				return true;
			}
		}
		return false;
	}

	public void promote(int x, int y){
		//return user promotion type
		//Glorious text inputs
		Scanner scanner =  new Scanner(System.in);
		int numChoice = -1;
		System.out.println("1: Queen\n2: Rook\n3: Bishop\n4: Knight");
		ChessPiece newPiece = null;
		while (numChoice == -1) {
			numChoice = scanner.nextInt();
			if (numChoice == 1) {
				newPiece = new Queen(board, this.isWhite, this.personalName);
			} else if (numChoice == 2) {
				newPiece = new Rook(board, this.isWhite, this.personalName);
			} else if (numChoice == 3) {
				newPiece = new Bishop(board, this.isWhite, this.personalName);
			} else if (numChoice == 4) {
				newPiece = new Knight(board, this.isWhite, this.personalName) ;
			} else {
				numChoice = -1;
				System.out.println("Invalid input, re-enter input:");
			}
		}

		board.setPiece(x, y, newPiece);
	}

	public boolean canPromote(int y) {
		return (this.isWhite && y == 7) || (!this.isWhite && y == 0);
	}
}
