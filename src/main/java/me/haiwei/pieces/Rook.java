package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class Rook extends ChessPiece{

	public Rook(ChessBoard board) {
		super(board);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isMoveValid(int x, int y, int dx, int dy, int side) {
		return dx == 0 || dy == 0;
	}

}
