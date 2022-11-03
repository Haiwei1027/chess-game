package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class Knight extends ChessPiece{

	public Knight(ChessBoard board, int x, int y, boolean white) {
		super(board, x, y, white);
		this.id = board.KNIGHT;
	}

	@Override
	public boolean isMoveValid(int x, int y, int dx, int dy, int side) {
		if (dx < 0) dx = -dx;
		if (dy < 0) dy = -dy;
		return (dx == 2 && dy == 1) || (dy == 2 && dx == 1);
	}

}
