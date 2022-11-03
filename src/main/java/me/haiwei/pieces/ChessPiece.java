package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public abstract class ChessPiece {

    protected int id;
    private boolean isWhite;

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

    public boolean isWhite(){
        return isWhite;
    }

}
