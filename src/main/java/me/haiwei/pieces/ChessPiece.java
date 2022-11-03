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

    public boolean movePiece(ChessPiece piece, int x, int y, int dx, int dy){
        // Check if move is valid
        if (!(piece.isMoveValid(x + dx, y + dy, x, y))) return false;

        // Do move two cases taking piece and not
        board.setPiece(x + dx, y + dy, piece);
        board.setPiece(x, y, null);
        return true;
    }

    public boolean isWhite(){
        return isWhite;
    }

}
