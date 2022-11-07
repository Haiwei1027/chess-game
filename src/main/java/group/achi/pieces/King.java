package group.achi.pieces;

import group.achi.ChessBoard;

public class King extends ChessPiece{

	private boolean hasMoved = false;

	public King(ChessBoard board, boolean isWhite) {
		super(board, isWhite);
		this.id = board.KING;
	}

	@Override
	public boolean isMoveValid(int to_x, int to_y, int from_x, int from_y) {

		int dx = to_x - from_x, dy = to_y - from_y;

		if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1)
		{
			return true;
		}

		return false;

	}

}
