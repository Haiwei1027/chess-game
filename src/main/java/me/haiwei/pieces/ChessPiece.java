package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public abstract class ChessPiece {

    private int x, y;
    private int id;
    private boolean white;

    protected ChessBoard board;

    public ChessPiece(ChessBoard board, int x, int y, boolean white) {
        this.board = board;
        this.x = x;
        this.y = y;
        this.white = white;
    }

    public abstract boolean isMoveValid(int x, int y, int dx, int dy, int side);

}
