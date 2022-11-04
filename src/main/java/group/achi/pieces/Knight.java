package group.achi.pieces;

import group.achi.ChessBoard;

public class Knight extends ChessPiece{

	public Knight(ChessBoard board, boolean isWhite) {
		super(board, isWhite);
		this.id = board.KNIGHT;
	}

	@Override
	public boolean isMoveValid(int to_x, int to_y, int from_x, int from_y) {
		// Make sure that the knight is in positions from
		if (board.getPiece(from_x, from_y).getId() != this.id) return false;

		// Find the change in x and y
		int dx = to_x - from_x;
		int dy = to_y - from_y;

		// Make sure that the knight is moving in an L shape
		if (!((dx == 2 && dy == 1) || (dx == 1 && dy == 2) || (dx == -2 && dy == 1) || (dx == -1 && dy == 2) || (dx == 2 && dy == -1) || (dx == 1 && dy == -2) || (dx == -2 && dy == -1) || (dx == -1 && dy == -2))) return false;

		// Make sure that the knight is not moving to a position with a piece of the same color
		if (board.getPiece(to_x, to_y).isWhite() == this.isWhite()) return false;
		return true;
	}

}
