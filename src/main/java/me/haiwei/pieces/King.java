package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public class King extends ChessPiece{

	public King(ChessBoard board) {
		super(board);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isMoveValid(int x, int y, int dx, int dy, int side) {
		if (dx <= 1 && dx >= -1 && dy <= 1 && dy >= -1) return true;
		if (dx == 2 || dx == -2){
			System.out.println("oh?");
			if (side == 0 ? board.canWhiteCastle(dx / 2) : board.canBlackCastle(dx / 2)){
				System.out.println("nice");
				boolean blocked = false;
				for (int i = x; i != 0 && i != 7; i+= dx / 2) {
					if (i==x)continue;
					if (board.getPiece(i,y) > -1) blocked = true;
				}
				System.out.println(blocked);
				if (!blocked){
					return true;
				}
			}
		}
		return false;
	}

}
