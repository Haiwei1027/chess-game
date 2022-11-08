package group.achi.pieces;

import group.achi.ChessBoard;

public class Rook extends ChessPiece{

	public Rook(ChessBoard board, boolean isWhite, String personalName) {
		super(board, isWhite, personalName);
		this.id = board.ROOK;
	}

	@Override
	public boolean isMoveValid(int toX, int toY, int fromX, int fromY) {
		// Make sure that the rook is in positions from
		//if (board.getPiece(fromX, fromY).getId() != id) return false;

		//i don't think that check is necessary and it could cause issues with the queen using the rook isMoveValid;

		// Find the change in x and y
		int dx = toX - fromX;
		int dy = toY - fromY;

		// make sure that the queen is moving in a straight line
		if (!(dx == 0 || dy == 0)) return false;

		// Make sure the rook is not moving through any pieces
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
			}
		}
		return true;
	}

}
