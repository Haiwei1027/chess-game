package me.haiwei.pieces;

import me.haiwei.ChessBoard;

public abstract class ChessPiece {

    protected ChessBoard board;

    public ChessPiece(ChessBoard board) {
        this.board = board;
    }

    public abstract boolean isMoveValid(int x, int y, int dx, int dy, int side);

}
