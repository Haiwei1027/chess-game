package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class Queen extends ChessPiece{

	private Rook rook;
	private Bishop bishop;

	public Queen(ChessBoard board, Bishop bishop, Rook rook) {
		super(board);
		this.rook = rook;
		this.bishop = bishop;
	}

	@Override
	public boolean isMoveValid(int x, int y, int dx, int dy, int side) {
		return bishop.isMoveValid(x,y,dx,dy,side) || rook.isMoveValid(x,y,dx,dy,side);
	}

}
