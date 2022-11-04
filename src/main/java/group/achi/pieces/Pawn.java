package group.achi.pieces;

import group.achi.ChessBoard;

public class Pawn extends ChessPiece {
	boolean hasMoved = false;

	public Pawn(ChessBoard board, boolean isWhite) {
		super(board, isWhite);
		this.id = board.PAWN;
	}

	@Override
	public boolean isMoveValid(int to_x, int to_y, int from_x, int from_y) {

		int dx = to_x - from_x, dy = to_y - from_y;


		return true;
	}

	@Override
	public boolean movePiece(int to_x, int to_y, int from_x, int from_y)
	{
		if (super.movePiece(to_x,to_y,from_x,from_y))
		{
			hasMoved = true;
			System.out.println("Pawn has moved!");
			return true;
		}
		return false;
	}

	public boolean canEnPassant(int x, int y, int dx, int dy){
		return false;
	}

	public ChessPiece canPromote(int x, int y, int dx, int dy){
		//return user promotion type
		return null;
	}
}
