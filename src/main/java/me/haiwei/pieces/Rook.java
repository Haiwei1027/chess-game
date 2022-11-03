package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class Rook extends ChessPiece{

	public Rook(ChessBoard board, boolean isWhite) {
		super(board, isWhite);
		this.id = board.ROOK;
	}

	@Override
	public boolean isMoveValid(int x, int y, int dx, int dy, int side) {
		return false;
	}

}
