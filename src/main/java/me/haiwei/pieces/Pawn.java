package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class Pawn extends ChessPiece {
	boolean hasMoved = false;

	public Pawn(ChessBoard board, boolean isWhite) {
		super(board, isWhite);
		this.id = board.PAWN;
	}

	@Override
	public boolean isMoveValid(int x, int y, int dx, int dy) {


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
