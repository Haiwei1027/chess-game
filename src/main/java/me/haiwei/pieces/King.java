package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class King extends ChessPiece{

	private boolean hasMoved = false;

	public King(ChessBoard board, boolean isWhite) {
		super(board, isWhite);
		this.id = board.KING;
	}

	@Override
	public boolean isMoveValid(int to_x, int to_y, int from_x, int from_y) {
		return false;

	}

}
