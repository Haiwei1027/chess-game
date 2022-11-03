package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class Queen extends ChessPiece{

	public Queen(ChessBoard board, int x, int y, boolean white) {
		super(board, white);
		this.id = board.QUEEN;
	}
	@Override
	public boolean isMoveValid(int to_x, int to_y, int from_x, int from_y, int side) {
		// Make sure that queen is in positions from
		if (board.getPiece(from_x, from_y).getId() != id) return false;

		// Find the change in x and y
		int dx = to_x - from_x;
		int dy = to_y - from_y;

		// Make sure that the queen is moving in a straight line
		if (!(dx == dy || dx == -dy || dx == 0 || dy == 0)) return false;

		// Make sure that the queen is not moving through any pieces
		int i = from_x, j = from_y;
		while (i != to_x || j != to_y){
			if (i != from_x || j != from_y){
				if (board.getPiece(i,j).getId() > -1){
					return false;
				}
			}
			if (dx == 0){
				j += dy > 0 ? 1 : -1;
			} else if (dy == 0){
				i += dx > 0 ? 1 : -1;
			} else {
				i += dx > 0 ? 1 : -1;
				j += dy > 0 ? 1 : -1;
			}
		}

		// Make sure that the queen is not moving to a position with a piece of the same color
		if (board.getPiece(to_x, to_y).isWhite() == this.white) return false;

		return true;
	}

}
