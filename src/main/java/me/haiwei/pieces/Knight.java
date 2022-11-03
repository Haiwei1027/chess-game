package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class Knight extends ChessPiece{

	public Knight(ChessBoard board, int x, int y, boolean white) {
		super(board, white);
		this.id = board.KNIGHT;
	}

	@Override
	public boolean isMoveValid(int x, int y, int dx, int dy, int side) {
		return false;
	}

}
