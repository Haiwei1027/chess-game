package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class Rook extends ChessPiece{

	public Rook(ChessBoard board, int x, int y, boolean white) {
		super(board, x, y, white);
		this.id = board.ROOK;
	}

	@Override
	public boolean isMoveValid(int x, int y, int dx, int dy, int side) {
		if (!(dx == 0 || dy == 0)) return false;
		if (dx != 0){
			for (int i = x; i != x + dx; i += dx > 0 ? 1 : -1) {
				if (board.getPiece(i,y) > -1 && i != x) return false;
			}
		}
		else{
			for (int i = y; i != y + dy; i += dy > 0 ? 1 : -1) {
				if (board.getPiece(x,i) > -1 && i != y) return false;
				System.out.println(i);
			}
		}
		return true;
	}

}
