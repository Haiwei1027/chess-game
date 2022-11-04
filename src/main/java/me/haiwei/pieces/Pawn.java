package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class Pawn extends ChessPiece {
	boolean hasMoved = false;

	public Pawn(ChessBoard board, boolean isWhite) {
		super(board, isWhite);
		this.id = board.PAWN;
	}

	@Override
	public boolean isMoveValid(int to_x, int to_y, int from_x, int from_y) {


		return to_x == from_x;
	}

	public boolean canEnPassant(int x, int y, int dx, int dy){
		return false;
	}

	public ChessPiece canPromote(int x, int y, int dx, int dy){
		//return user promotion type
		return null;
	}
}
