package group.gachi.pieces;

import group.gachi.ChessBoard;

public class Queen extends ChessPiece{


	public Queen(ChessBoard board, boolean white, String personalName) {
		super(board, white, personalName);
		this.id = ChessBoard.QUEEN;
		super.maxHealth = 180;
		super.health = 180;
	}
	@Override
	public boolean isMoveValid(int toX, int toY, int fromX, int fromY) {

		return (new Rook(this.board, this.isWhite, "").isMoveValid(toX, toY, fromX, fromY) || new Bishop(this.board, this.isWhite, "").isMoveValid(toX, toY, fromX, fromY));

	}

}
