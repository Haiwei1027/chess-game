package group.gachi.pieces;

import group.gachi.ChessBoard;

public class Knight extends ChessPiece{

	public Knight(ChessBoard board, boolean isWhite, String personalName) {
		super(board, isWhite, personalName);
		this.id = ChessBoard.KNIGHT;
		super.maxHealth = 45;
		super.health = super.maxHealth;
	}

	@Override
	public boolean isMoveValid(int toX, int toY, int fromX, int fromY) {
		// Make sure that the knight is in positions from
		if (board.getPiece(fromX, fromY).getId() != this.id) return false;

		// Find the change in x and y
		int dx = toX - fromX;
		int dy = toY - fromY;

		// Make sure that the knight is moving in an L shape
		return Math.abs(dx) == 2 && Math.abs(dy) == 1 || Math.abs(dx) == 1 && Math.abs(dy) == 2;
	}

}
