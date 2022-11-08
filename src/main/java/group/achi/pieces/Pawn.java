package group.achi.pieces;

import group.achi.ChessBoard;

import java.security.spec.RSAOtherPrimeInfo;

public class Pawn extends ChessPiece {
	boolean hasMoved = false;

	public Pawn(ChessBoard board, boolean isWhite, String personalName) {
		super(board, isWhite, personalName);
		this.id = board.PAWN;
	}

	@Override
	public boolean isMoveValid(int toX, int toY, int fromX, int fromY) {

		int dx = toX - fromX,
			dy = toY - fromY,
			absdy = Math.abs(dy),
			absdx = Math.abs(dx);

		if (board.getPiece(fromX, fromY).isWhite()) //If piece is white, move direction is up so dy must be greater than 0
		{
			if (dy < 0) {return false;}
		} else
		{
			if (dy > 0) {return false; }
		}

		if ( (absdy == 2 && hasMoved == false) && (absdx == 0) )
		{
			return true;
		}

		if ( (absdy == 1 && absdx == 1) && (board.getPiece(toX, toY) != null) ) {return true; }

		if (absdy > 1 || absdx > 0) {return false; } //NO moving more than 1 space away!

		if (absdx == 0 && board.getPiece(toX, toY) != null) {return false; }

		if( (absdy == 2 && absdx > 0)) {return false; }
		return true;
	}

	@Override
	public boolean movePiece(int toX, int toY, int fromX, int fromY)
	{
		if (super.movePiece(toX,toY,fromX,fromY))
		{
			hasMoved = true;
			return true;
		}
		return false;
	}

	public boolean canEnPassant(int x, int y, int dx, int dy){
		return false;
	}

	public ChessPiece canPromote(int x, int y, int dx, int dy){
		//return user promotion type
		return null;
	}
}
