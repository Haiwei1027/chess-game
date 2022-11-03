package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class Pawn extends ChessPiece {

	public Pawn(ChessBoard board, int x, int y, boolean white) {
		super(board, white);
		this.id = board.PAWN;
	}

	@Override
	public boolean isMoveValid(int x, int y, int dx, int dy, int side) {
		return false;
	}
}
