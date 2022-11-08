package group.achi.pieces;

import group.achi.ChessBoard;

import java.security.spec.RSAOtherPrimeInfo;

public class Pawn extends ChessPiece {
	boolean hasMoved = false;

	public Pawn(ChessBoard board, boolean isWhite) {
		super(board, isWhite);
		this.id = board.PAWN;
	}

	@Override
	public boolean isMoveValid(int to_x, int to_y, int from_x, int from_y) {

		int dx = to_x - from_x,
			dy = to_y - from_y,
			absdy = Math.abs(dy),
			absdx = Math.abs(dx);

		if (board.getPiece(from_x, from_y).isWhite()) //If piece is white, move direction is up so dy must be greater than 0
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

		if ( (absdy == 1 && absdx == 1) && (board.getPiece(to_x, to_y) != null) ) {return true; }

		if (absdy > 1 || absdx > 0) {return false; } //NO moving more than 1 space away!

		if (absdx == 0 && board.getPiece(to_x, to_y) != null) {return false; }

		if( (absdy == 2 && absdx > 0)) {return false; }
		return true;
	}

	@Override
	public boolean movePiece(int to_x, int to_y, int from_x, int from_y)
	{
		if (super.movePiece(to_x,to_y,from_x,from_y))
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