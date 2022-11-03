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

    public abstract boolean isMoveValid(int x, int y, int dx, int dy, int side);

}
