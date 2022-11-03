package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class Knight extends ChessPiece{

	public Knight(ChessBoard board, boolean isWhite) {
		super(board, isWhite);
		this.id = board.KNIGHT;
	}

	@Override
	public boolean isMoveValid(int x, int y, int dx, int dy, int side) {
		return false;
	}

}
