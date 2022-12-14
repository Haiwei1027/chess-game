package group.gachi.pieces;

import group.gachi.ChessBoard;

public class Rook extends ChessPiece{

	boolean hasMoved = false;

	public Rook(ChessBoard board, boolean isWhite, String personalName) {
		super(board, isWhite, personalName);
		this.id = ChessBoard.ROOK;
		super.maxHealth = 6;
		super.health = super.maxHealth;
	}

	@Override
	public boolean movePiece(int toX, int toY, int fromX, int fromY) {
		if (super.movePiece(toX,toY,fromX,fromY)){
			return hasMoved = true;
		}
		return false;
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
			} else {
				i += dx > 0 ? 1 : -1;
			}
		}
		return true;
	}

}
