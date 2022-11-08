package group.achi.pieces;

import group.achi.ChessBoard;

public class King extends ChessPiece{

	private boolean hasMoved = false;

	public King(ChessBoard board, boolean isWhite, String personalName) {
		super(board, isWhite, personalName);
		this.id = board.KING;
	}

	// Doesn't account for castling
	@Override
	public boolean isMoveValid(int toX, int toY, int fromX, int fromY) {
		// Make sure that the king is in positions from
		if (board.getPiece(fromX, fromY).getId() != id) return false;

		// Find the change in x and y
		int dx = toX - fromX;
		int dy = toY - fromY;

		// Make sure that the king is moving only one square
		if (Math.abs(dx) > 1 || Math.abs(dy) > 1) return false;

		return true;
	}
}
