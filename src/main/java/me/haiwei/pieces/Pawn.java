package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class Pawn extends ChessPiece {

	public final int id = 0;

	public Pawn(ChessBoard board) {
		super(board);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isMoveValid(int x, int y, int dx, int dy, int side) {
		int direction = side * 2 - 1;
		boolean canCapture = board.getPiece(x + 1, y + direction) >= 0 || board.getPiece(x - 1, y + direction) >= 0;
		boolean blocked = board.getPiece(x, y + direction) >= 0;
		return dy == direction && (dx == 0 && !blocked || canCapture);
	}
}
