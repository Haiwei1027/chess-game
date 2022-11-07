package group.achi.pieces;

import group.achi.ChessBoard;

public class Rook extends ChessPiece{

	public Rook(ChessBoard board, boolean isWhite) {
		super(board, isWhite);
		this.id = board.ROOK;
	}

	@Override
	public boolean isMoveValid(int to_x, int to_y, int from_x, int from_y) {
		// Make sure that the rook is in positions from
		//if (board.getPiece(from_x, from_y).getId() != id) return false;

		//i don't think that check is necessary and it could cause issues with the queen using the rook isMoveValid;

		// Find the change in x and y
		int dx = to_x - from_x;
		int dy = to_y - from_y;

		// make sure that the queen is moving in a straight line
		if (!(dx == 0 || dy == 0)) return false;

		// Make sure the rook is not moving through any pieces
		int i = from_x, j = from_y;
		while (i != to_x || j != to_y){
			if (i != from_x || j != from_y){
				if (board.getPiece(i,j) != null){
					return false;
				}
			}
			if (dx == 0){
				j += dy > 0 ? 1 : -1;
			} else if (dy == 0){
				i += dx > 0 ? 1 : -1;
			}
		}
		return true;
	}

}
