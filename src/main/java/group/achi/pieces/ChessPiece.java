package group.achi.pieces;

import group.achi.ChessBoard;

import java.awt.*;

public abstract class ChessPiece {

    protected int id;
    protected boolean isWhite;

    protected ChessBoard board;
    private String personalName;

    public boolean whiteInCheck;
    public boolean blackInCheck;

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

        //Set if in check
        this.blackInCheck = checkCheck(false);
        this.whiteInCheck = checkCheck(true);

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

    //Checks if in check, isWhite is the colour of the side being checked
    public boolean checkCheck(boolean isWhite) {
        int kingX;
        int kingY;

        Point kingPoint = findKing(isWhite);

        if (kingPoint == null) {
            return false;
        }

        kingX = (int) kingPoint.getX();
        kingY = (int) kingPoint.getY();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece pieceAtPosition = board.getPiece(i, j);
                if (pieceAtPosition == null) {
                    continue;
                }

                if (pieceAtPosition.isWhite() != isWhite) {
                    if (pieceAtPosition.isMoveValid(kingX, kingY, i, j)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //finds where the king is on the board
    public Point findKing(boolean isWhite) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece pieceAtPosition = board.getPiece(i, j);
                if (pieceAtPosition == null) {
                    continue;
                }
                if (pieceAtPosition.getId() == ChessBoard.KING && pieceAtPosition.isWhite() == isWhite) {
                    return new Point(i, j);

                }
            }
        }
        return null;
    }
}
