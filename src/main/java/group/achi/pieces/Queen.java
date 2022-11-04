package group.achi.pieces;

import group.achi.ChessBoard;

public class Queen extends ChessPiece{


	public Queen(ChessBoard board, boolean white) {
		super(board, white);
		this.id = board.QUEEN;
	}
	@Override
	public boolean isMoveValid(int to_x, int to_y, int from_x, int from_y) {

		return (new Rook(this.board, this.isWhite).isMoveValid(to_x, to_y, from_x, from_y) || new Bishop(this.board, this.isWhite).isMoveValid(to_x, to_y, from_x, from_y));

	}

}
