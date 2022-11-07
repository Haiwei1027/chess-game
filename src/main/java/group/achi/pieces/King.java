package group.achi.pieces;

import group.achi.ChessBoard;

public class King extends ChessPiece{

	private boolean hasMoved = false;

	public King(ChessBoard board, boolean isWhite) {
		super(board, isWhite);
		this.id = board.KING;
	}

	// Doesn't account for castling
	@Override
	public boolean isMoveValid(int to_x, int to_y, int from_x, int from_y) {
		// Make sure that the king is in positions from
		if (board.getPiece(from_x, from_y).getId() != id) return false;

		// Find the change in x and y
		int dx = to_x - from_x;
		int dy = to_y - from_y;

		// Make sure that the king is moving only one square
		if (Math.abs(dx) > 1 || Math.abs(dy) > 1) return false;

		// Make sure that the king is not moving to a position with a piece of the same color
		if (board.getPiece(to_x, to_y).isWhite() == this.isWhite()) return false;

		return true;
	}
}
