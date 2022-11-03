package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class Queen extends ChessPiece{

	public Queen(ChessBoard board, boolean isWhite) {
		super(board, isWhite);
		this.id = board.QUEEN;
	}
	@Override
	public boolean isMoveValid(int takeX, int takeY) {

		return false;
	}

}
