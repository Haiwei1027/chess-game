package group.gachi.pieces;

import group.gachi.ChessBoard;

public class Knight extends ChessPiece{

	public Knight(ChessBoard board, boolean isWhite, String personalName) {
		super(board, isWhite, personalName);
		this.id = ChessBoard.KNIGHT;
	}

	@Override
	public boolean isMoveValid(int toX, int toY, int fromX, int fromY) {
		// Make sure that the knight is in positions from
		if (board.getPiece(fromX, fromY).getId() != this.id) return false;

		// Find the change in x and y
		int dx = toX - fromX;
		int dy = toY - fromY;

		// Make sure that the knight is moving in an L shape
		if (!((dx == 2 && dy == 1) || (dx == 1 && dy == 2) || (dx == -2 && dy == 1) || (dx == -1 && dy == 2) || (dx == 2 && dy == -1) || (dx == 1 && dy == -2) || (dx == -2 && dy == -1) || (dx == -1 && dy == -2))) return false;


		return true;
	}

}
