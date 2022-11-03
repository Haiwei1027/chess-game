package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class Pawn extends ChessPiece {

	public Pawn(ChessBoard board, int x, int y, boolean white) {
		super(board, x, y, white);
		this.id = board.PAWN;
	}

	@Override
	public boolean isMoveValid(int x, int y, int dx, int dy, int side) {
		int step = side * 2 - 1;
		boolean leap = dy == step * 2 && y == ((1-side) * 5 + 1);
		boolean canEnPassant = (y == 3 + side) && (board.getEnPassantFile() == x + dx);
		boolean canCapture = false;
		if (board.getPiece(x + 1, y + step) >= 0 && dx == 1){
			canCapture = true;
		}
		else if (board.getPiece(x - 1, y + step) >= 0 && dx == -1){
			canCapture = true;
		}
		boolean blocked = board.getPiece(x, y + step) >= 0;
		boolean leapBlocked = board.getPiece(x, y + step * 2) >= 0;
		return ((dy == step) || (leap && !leapBlocked)) && ((dx == 0 && !blocked) || (canCapture || canEnPassant));
	}
}
