package group.achi.pieces;

import group.achi.ChessBoard;

public class Bishop extends ChessPiece{

	public Bishop(ChessBoard board, boolean isWhite) {
		super(board, isWhite);
		this.id = board.BISHOP;
	}

	@Override
	public boolean isMoveValid(int to_x, int to_y, int from_x, int from_y) {
		// Make sure that the bishop is in positions from
		//if (board.getPiece(from_x, from_y).getId() != id) return false;

		//i don't think that check is necessary and it could cause issues with the queen using the bishop isMoveValid;

		// Find the change in x and y
		int dx = to_x - from_x;
		int dy = to_y - from_y;

		// Make sure moving in diagonal line only
		if (!(dx == dy || dx == -dy)) return false;

		// Make sure that the bishop is not moving through any pieces
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

		return true;
	}

}
