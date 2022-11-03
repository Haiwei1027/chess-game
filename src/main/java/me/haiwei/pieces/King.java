package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class King extends ChessPiece{

	private boolean hasMoved = false;

	public King(ChessBoard board, boolean isWhite) {
		super(board, isWhite);
		this.id = board.KING;
	}

	@Override
	public boolean isMoveValid(int x, int y, int dx, int dy, int side) {
		return false;

	}

}
