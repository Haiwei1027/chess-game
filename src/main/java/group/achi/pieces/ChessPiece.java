package group.achi.pieces;

import group.achi.ChessBoard;

import java.awt.*;

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

        // Make sure that the knight is not moving to a position with a piece of the same color
        if (board.getPiece(to_x, to_y) != null) {
            if (board.getPiece(to_x, to_y).isWhite() == this.isWhite()) return false;
        }

        // Check the move fits the piece move pattern
        if (!(isMoveValid(to_x, to_y, from_x, from_y)) || (to_x == from_x && to_y == from_y)) return false;

        // Do move two cases taking piece and not
        board.setPiece(to_x, to_y, this);
        board.setPiece(from_x, from_y, null);
        return true;
    }

    public boolean isWhite(){
        return isWhite;
    }

    public Point[] getValidMoves(int x, int y) {
        Point[] validMoves = new Point[64];
        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isMoveValid(i, j, x, y) && (board.getPiece(i, j) == null || board.getPiece(i, j).isWhite() != this.isWhite())) {
                    validMoves[index] = new Point(i, j);
                    index++;
                }
            }
        }
        return validMoves;
    }
}
