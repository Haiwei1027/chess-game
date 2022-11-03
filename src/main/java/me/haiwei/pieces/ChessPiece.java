package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public abstract class ChessPiece {

    protected int id;
    private boolean white;

    protected ChessBoard board;

    public ChessPiece(ChessBoard board, boolean white) {
        this.board = board;
        this.white = white;
    }

    // Get id
    public int getId() {
        return id;
    }

    public abstract boolean isMoveValid(int x, int y, int dx, int dy, int side);

    public boolean movePiece(ChessPiece piece, int x, int y, int dx, int dy, int side){
        // Check peice is not taking its own side
        if (board.getPiece((x + dx), (y + dy)).getSide() == side) return false;

        // Check if move is valid
        if (!(piece.isMoveValid(x, y, dx, dy, side))) return false;

        // Do move two cases taking piece and not
        board.setPiece(x + dx, y + dy, piece);
        board.setPiece(x, y, null);
        return true;
    }

    public int getSide(){
        return white ? 0 : 1;
    }

}
