package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public abstract class ChessPiece {

    protected int id;
    protected boolean isWhite;

    protected ChessBoard board;

    public ChessPiece(ChessBoard board, boolean isWhite) {
        this.board = board;
        this.isWhite = isWhite;
    }

    // Get id
    public int getId() {
        return id;
    }

    public abstract boolean isMoveValid(int to_x, int to_y, int from_x, int from_y);

    public boolean movePiece(int to_x, int to_y, int from_x, int from_y){
        // Check if move is valid
        if (!(isMoveValid(to_x, to_y, from_x, from_y))) return false;

        // Do move two cases taking piece and not
        board.setPiece(to_x, to_y, this);
        board.setPiece(from_x, from_y, null);
        return true;
    }

    public boolean isWhite(){
        return isWhite;
    }

}
