package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class Queen extends ChessPiece{

	private Rook rook;
	private Bishop bishop;

	public Queen(ChessBoard board, int x, int y, boolean white) {
		super(board, white);
		this.id = board.QUEEN;
	}
	@Override
	public boolean isMoveValid(int x, int y, int dx, int dy, int side) {
		// TODO Auto-generated method stub
		return false;
	}

}
