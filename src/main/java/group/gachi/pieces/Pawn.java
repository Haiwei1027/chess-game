package group.gachi.pieces;

import group.gachi.ChessBoard;

public class Pawn extends ChessPiece {
	boolean hasMoved = false;
	static int hasLeapedOnFile = -1;
	boolean enPassant = false;

	public Pawn(ChessBoard board, boolean isWhite, String personalName) {
		super(board, isWhite, personalName);
		this.id = board.PAWN;
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
			if (leapBlocked || absdx != 0 || hasMoved) return false;
			return true;
		}

		return false;

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
			return hasMoved = true;
		}
		return false;
	}

	public boolean enPassant(int x, int y, int dx, int dy){
		//Gets piece in adjacent spot
		ChessPiece adjacentSpot = board.getPiece(x + dx, y);

		if (adjacentSpot == null || adjacentSpot.getId() != board.PAWN) return false;

		Pawn pawn = (Pawn)adjacentSpot;

		if (pawn.isWhite != isWhite){
			if (hasLeapedOnFile == x + dx){
				enPassant = true;
				return true;
			}
		}
		return false;
	}

	public ChessPiece promote(int x, int y, int dx, int dy){
		//return user promotion type
		return null;
	}
}
