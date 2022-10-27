package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class King extends ChessPiece{

	public King(ChessBoard board) {
		super(board);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isMoveValid(int x, int y, int dx, int dy, int side) {
		if (dx < 0) dx = -dx;
		if (dy < 0) dy = -dy;
		return dx <= 1 && dy <= 1;
	}

}
