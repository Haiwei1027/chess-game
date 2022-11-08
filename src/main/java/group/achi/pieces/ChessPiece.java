package group.achi.pieces;

import group.achi.ChessBoard;

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

    public abstract boolean isMoveValid(int toX, int toY, int fromX, int fromY);

    public boolean movePiece(int toX, int toY, int fromX, int fromY){
        // Check if move is valid

        // Make sure that the knight is not moving to a position with a piece of the same color
        if (board.getPiece(toX, toY) != null) {
            if (board.getPiece(toX, toY).isWhite() == this.isWhite()) return false;
        }

        // Check the move fits the piece move pattern
        if (!(isMoveValid(toX, toY, fromX, fromY)) || (toX == fromX && toY == fromY)) return false;

        // Do move two cases taking piece and not
        board.setPiece(toX, toY, this);
        board.setPiece(fromX, fromY, null);
        return true;
    }

    public boolean isWhite(){
        return isWhite;
    }

}
