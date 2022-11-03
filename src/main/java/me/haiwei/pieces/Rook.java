package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class Rook extends ChessPiece{

	public Rook(ChessBoard board, int x, int y, boolean white) {
		super(board, white);
		this.id = board.ROOK;
	}

	@Override
	public boolean isMoveValid(int x, int y, int dx, int dy, int side) {
		return false;
	}

}
