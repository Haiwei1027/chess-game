package group.gachi.pieces;

import group.gachi.ChessBoard;

public class Bishop extends ChessPiece{

	public Bishop(ChessBoard board, boolean isWhite, String personalName) {
		super(board, isWhite, personalName);
		this.id = ChessBoard.BISHOP;
		super.health = 60;
	}

	@Override
	public boolean isMoveValid(int toX, int toY, int fromX, int fromY) {
		// Make sure that the bishop is in positions from
		//if (board.getPiece(fromX, fromY).getId() != id) return false;

		//i don't think that check is necessary and it could cause issues with the queen using the bishop isMoveValid;

		// Find the change in x and y
		int dx = toX - fromX;
		int dy = toY - fromY;

		// Make sure moving in diagonal line only
		if (!(dx == dy || dx == -dy)) return false;

		// Make sure that the bishop is not moving through any pieces
		int i = fromX, j = fromY;
		while (i != toX || j != toY){
			if (i != fromX || j != fromY){
				if (board.getPiece(i,j) != null){
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

		return true;
	}

}
