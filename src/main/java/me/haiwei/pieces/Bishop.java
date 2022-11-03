package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class Bishop extends ChessPiece{

	public Bishop(ChessBoard board, boolean isWhite) {
		super(board, isWhite);
		this.id = board.BISHOP;
	}

	@Override
	public boolean isMoveValid(int x, int y, int dx, int dy, int side) {
		if (!(dx == dy || dx == -dy)) return false;
		int i = x,j = y;
		while (i != x + dx){
			if (i != x){
				if (board.getPiece(i,j) > -1){
					return false;
				}
			}
			i += dx > 0 ? 1 : -1;
			j += dy > 0 ? 1 : -1;
		}
		return true;
	}

}
