package group.gachi.pieces;

import group.gachi.ChessBoard;

import java.awt.*;
import java.util.ArrayList;

public abstract class ChessPiece {

    protected int id;
    protected boolean isWhite;

    protected ChessBoard board;
    private String personalName;

    public ChessPiece(ChessBoard board, boolean isWhite, String personalName ) {
        this.board = board;
        this.isWhite = isWhite;
        this.personalName = personalName;
    }

    // Get id
    public int getId() {
        return id;
    }

    public abstract boolean isMoveValid(int toX, int toY, int fromX, int fromY);

    public boolean movePiece(int toX, int toY, int fromX, int fromY) {
        // Check if move is valid

        // Make sure that the knight is not moving to a position with a piece of the same color
        if (board.getPiece(toX, toY) != null) {
            if (board.getPiece(toX, toY).isWhite() == this.isWhite()) return false;
        }

        // Check the move fits the piece move pattern
        if (!(isMoveValid(toX, toY, fromX, fromY)) || (toX == fromX && toY == fromY)) return false;

        // Do move two cases taking piece and not
        ChessPiece chessP = board.getPiece(toX, toY);
        board.setPiece(toX, toY, this);
        board.setPiece(fromX, fromY, null);

        if (checkCheck() != null) {
            board.setPiece(toX, toY, chessP);
            board.setPiece(fromX, fromY, this);
            return false;
        }
        return true;
    }

    public boolean isWhite(){
        return isWhite;
    }

    public ArrayList<Point> getValidMoves(int x, int y) {
        ArrayList<Point> validMoves = new ArrayList<Point>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isMoveValid(i, j, x, y) && (board.getPiece(i, j) == null || board.getPiece(i, j).isWhite() != this.isWhite())) {

                    //check if move puts king in check
                    ChessPiece chessP = board.getPiece(i, j);
                    board.setPiece(i, j, this);
                    board.setPiece(x, y, null);
                    if (checkCheck() == null) {
                        validMoves.add(new Point(i, j));
                    }
                    board.setPiece(i, j, chessP);
                    board.setPiece(x, y, this);
                }
            }
        }
        return validMoves;
    }

    public Point checkCheck() {
        for (int i = 0; i < board.getSize(); i++)
            for (int j = 0; j < board.getSize(); j++)
                if (board.getPiece(i, j) != null && board.getPiece(i, j).isWhite() != isWhite)
                    for (int k = 0; k < board.getSize(); k++)
                        for (int l = 0; l < board.getSize(); l++)
                            if (board.getPiece(i, j).isMoveValid(k, l, i, j))
                                if (board.getPiece(k, l) != null && board.getPiece(k, l).getId() == board.KING && board.getPiece(k, l).isWhite() == isWhite)
                                    return new Point(k, board.getSize() - l - 1);
        return null;
    }
}
